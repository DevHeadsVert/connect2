package com.linkedin.linkedinclone.controllers;


import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.Post;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.PostRepository;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class FeedController {

    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/feed")
    public Set<Post> getFeed(@PathVariable Long id) {

        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Set<Post> feedPosts = new HashSet<>();

        // Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());

        // Posts made by user
        feedPosts.addAll(currentUser.getPosts());

        // Posts from users connections
        Set<User> connectedUsers = currentUser.getUsersConnectedWith();
        for(User u: connectedUsers) {
            feedPosts.addAll(u.getPosts());
        }

        // Posts from connections that are interested

        return feedPosts;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/in/{id}/feed/new-post")
    public ResponseEntity newPost(@PathVariable Long id,@RequestBody Post post) {

        // AUDIO IMAGES AND VIDEO TO BE DONEEE

        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        userService.newPost(currentUser,post);
        postRepository.save(post);
        return ResponseEntity.ok("\"Post created with success!\"");
    }
}
