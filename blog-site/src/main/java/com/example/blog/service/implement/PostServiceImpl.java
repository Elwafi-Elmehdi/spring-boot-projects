package com.example.blog.service.implement;

import com.example.blog.bean.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import org.apache.commons.lang3.RandomStringUtils;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public int addPost(Post post) {
        if(post.getReference()!= null){
            Post post1 = postRepository.findByReference(post.getReference());
            if(post1 != null){
                return 0;
        }
        }
        else if(post.getTitle() == null || post.getSummary()==null){
            return -1;
        }

            post.setReference(genReference());
            post.setCreatedAt(new Date());
            post.setUpdatedAt(null);
            postRepository.save(post);
            return 1;
    }
    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findByRef(String ref) {
        return postRepository.findByReference(ref);
    }

    private String genReference() {
        return RandomStringUtils.random(8);
    }

}
