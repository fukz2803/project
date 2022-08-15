package com.foodei.project.service;

import com.foodei.project.dto.UserDto;
import com.foodei.project.entity.User;
import com.foodei.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> findAllUserByNameAndEmail(int page, int pageSize, String name, String email){
        Pageable pageable = PageRequest.of(page, pageSize);
        return userRepository.findByNameContainsIgnoreCaseAndEmailContainsIgnoreCase(name, email, pageable);
    }
}
