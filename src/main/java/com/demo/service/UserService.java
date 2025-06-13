package com.demo.service;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public User findByName(String name){
        return userRepository.findByName(name);
    }

}
