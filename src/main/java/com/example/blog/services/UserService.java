package com.example.blog.services;

import com.example.blog.entities.post.Post;
import com.example.blog.entities.user.User;
import com.example.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }

    public void create(User user){
        userRepository.save(user);
    }

    public Optional<User> findById(String id) { return userRepository.findById(id); }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void follow(User follower, User followed) {

        if (followed.getFollowers().contains(follower)) {
            followed.removeFollower(follower);
            follower.removeFollowing(followed);
        } else {
            follower.addFollowing(followed);
            followed.addFollower(follower);
        }

        save(followed);
        save(follower);
    }

    public List<Post> getFeed(User user){
        List<User> following = user.getFollowing();

        return following
                .stream()
                .flatMap(u -> u.getPosts().stream())
                .collect(Collectors.toList());
    }

    public List<User> findUsersByUsername(String username){
        return userRepository.findByRegexInUsername(username);
    }


}
