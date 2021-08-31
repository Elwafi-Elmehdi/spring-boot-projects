package com.example.demo.service.impls;

import com.example.demo.bean.User;
import com.example.demo.bean.UserPrinciple;
import com.example.demo.bean.enumeration.Role;
import static com.example.demo.consts.FileConsts.*;
import com.example.demo.dao.UserDao;
import com.example.demo.exception.domain.EmailExistsException;
import com.example.demo.exception.domain.UsernameExistsException;
import com.example.demo.service.LoginAttemptService;
import com.example.demo.service.UserService;
import com.example.demo.service.security.SecurityConsts;
import com.example.demo.service.security.utils.JWTTokenProvider;
import javassist.bytecode.MethodParametersAttribute;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private LoginAttemptService loginAttemptService;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByUsername(s);
        if(user == null )
            throw new UsernameNotFoundException("Cannot find User with "+ s);
        else{
            validateLoginAttempts(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userDao.save(user);
            UserPrinciple userPrinciple = new UserPrinciple(user);
            return userPrinciple;
        }

    }



    @Override
    public User register(String firstname, String lastname, String username, String email) throws EmailExistsException, UsernameExistsException {
        validateEmailAndUsername(StringUtils.EMPTY,email,username);
        User user = new User();
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        String userId = genrateUserId();
        user.setUsername(username);
        user.setNotLocked(true);
        user.setActive(true);
        user.setJoinDate(new Date());
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        user.setPassword(encodedPassword);
        user.setUserId(userId);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setProfileImgURL(getDefaultImage());
        userDao.save(user);
        LOGGER.info(username +" "+password);
        return user;
    }
    @Override
    public User addNewUser(User user, MultipartFile img) throws UsernameExistsException, EmailExistsException, IOException {

        validateEmailAndUsername(StringUtils.EMPTY,user.getEmail(),user.getUsername());

        String password = generatePassword();
        user.setPassword(encodePassword(password));
        user.setUserId(genrateUserId());
        user.setJoinDate(new Date());
        user.setLastLoginDate(null);
        user.setLastLoginDateDisplay(null);
        user.setRole(getRoleEnumName(user.getRole()).name());
        user.setAuthorities(getRoleEnumName(user.getRole()).getAuthorities());
        user.setProfileImgURL(getDefaultImage());
        userDao.save(user);
        saveProfileImage(user,img);
        LOGGER.info(user.getUsername() +" "+password);
        return user;
    }



    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public User updateUser(String username, User user, MultipartFile img) throws UsernameExistsException, EmailExistsException, IOException {
        User curentUser = validateEmailAndUsername(username,user.getEmail(),user.getUsername());

        curentUser.setUsername(username);
        curentUser.setNotLocked(true);
        curentUser.setActive(true);
        curentUser.setRole(getRoleEnumName(user.getRole()).name());
        curentUser.setAuthorities(getRoleEnumName(user.getRole()).getAuthorities());
        curentUser.setFirstName(user.getFirstName());
        curentUser.setLastName(user.getLastName());
        curentUser.setEmail(user.getEmail());
//        curentUser.setProfileImgURL();
        saveProfileImage(user,img);
        return null;
    }

    @Override
    public void resetPassword(String email) {

    }

    @Override
    public ResponseEntity<User> login(String username, String password) {
        authenticate(username,password);
        User loginUser = findUserByUsername(username);
        UserPrinciple userPrinciple = new UserPrinciple(loginUser);
        HttpHeaders tokenHeader = getJwtToken(userPrinciple);
        return new ResponseEntity<>(loginUser,tokenHeader, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

    private HttpHeaders getJwtToken(UserPrinciple userPrinciple) {
        String token= jwtTokenProvider.generateJWTToken(userPrinciple);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConsts.JWT_TOKEN_HEADER,token);
        return httpHeaders;
    }

    private User validateEmailAndUsername(String currentUsername, String email, String username) throws UsernameExistsException, EmailExistsException {
        User userByUsername = findUserByUsername(username);
        User userByEmail = findUserByEmail(email);

        if(StringUtils.isNotBlank(currentUsername)){
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null){
                throw new UsernameNotFoundException("No user found with username"+ currentUsername);
            }
            if(userByUsername!= null && !currentUser.getId().equals(userByUsername.getId())){
                throw new UsernameExistsException("Username already exists");
            }
            if(userByEmail != null && currentUser.getId().equals(userByEmail.getId())){
                throw new EmailExistsException("Email already exists");
            }
            return currentUser;
        }else {
            if(userByUsername != null){
                throw new UsernameExistsException("Username already exists");
            }
            if(userByEmail != null){
                throw new EmailExistsException("Email already exists");
             }
            return null;
        }
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String genrateUserId() {
        return  RandomStringUtils.randomNumeric(10);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
    private String getDefaultImage() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/default").toUriString();
    }
    private void validateLoginAttempts(User user)  {
        if(user.isNotLocked()){
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())){
                user.setNotLocked(false);
                loginAttemptService.removeUserFromLoginAttemptCache(user.getUsername());
            }else{
                user.setNotLocked(true);
            }
        }else{
            loginAttemptService.removeUserFromLoginAttemptCache(user.getUsername());
        }
    }

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    private void saveProfileImage(User user, MultipartFile img) throws IOException {
        if(img != null){
            Path userPath = Paths.get(USER_FOLDER+user.getUsername()).toAbsolutePath().normalize();
            if(!Files.exists(userPath)){
                Files.createDirectories(userPath);
                LOGGER.info(DIRECTORY_CREATED + userPath);
            }
            Files.deleteIfExists(Paths.get(USER_FOLDER+user.getUsername()+DOT+JPG_EXTENSION));
            Files.copy(img.getInputStream(),userPath.resolve(user.getUsername()+DOT+JPG_EXTENSION), StandardCopyOption.REPLACE_EXISTING);
            user.setProfileImgURL(setProfileImageUrl(user.getUsername()));
            userDao.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + img.getOriginalFilename());
        }
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/"+username+SLASH+username+DOT+JPG_EXTENSION).toUriString();

    }
}
