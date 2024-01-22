package com.chuwa.redbook.service.impl;

import com.chuwa.redbook.dao.PostRepository;
import com.chuwa.redbook.entity.Post;
import com.chuwa.redbook.payload.PostDto;
import com.chuwa.redbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    private static Post convertDTOtoEntity(PostDto postDto){
        Post tobeSaved = new Post();
        tobeSaved.setTitle(postDto.getTitle());
        tobeSaved.setDescription(postDto.getDescription());
        tobeSaved.setContent(postDto.getContent());
        return tobeSaved;
    }

    private static PostDto convertEntityToDTO(Post saved){
        PostDto response = new PostDto();

        response.setId(saved.getId());
        response.setTitle(saved.getTitle());
        response.setDescription(saved.getDescription());
        response.setContent(saved.getContent());

        return response;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        Post tobeSaved = convertDTOtoEntity(postDto);
        Post saved = this.postRepository.save(tobeSaved);

        PostDto response = convertEntityToDTO(saved);

        return response;
    }
    @Override
    public List<PostDto> getAllPost(){
        List<Post> posts = this.postRepository.findAll();

        return posts.stream().map(PostServiceImpl::convertEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id){
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        return convertEntityToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id){
        Post toBeUpdatedPost = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));

        //update date
        toBeUpdatedPost.setTitle(postDto.getTitle());
        toBeUpdatedPost.setDescription(postDto.getDescription());
        toBeUpdatedPost.setContent(postDto.getContent());

        Post updatedPost = this.postRepository.save(toBeUpdatedPost);
        return convertEntityToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id){
        this.postRepository.deleteById(id);
    }





}
