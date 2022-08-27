package com.foodei.project.service;

import com.foodei.project.entity.User;
import com.foodei.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //Hiển thị User dưới dạng phân trang + tìm kiếm theo tên hoặc email
    public Page<User> findAllUserByNameAndEmail(int page, int pageSize, String name, String email){
        Pageable pageable = PageRequest.of(page, pageSize);
        return userRepository.findByNameContainsIgnoreCaseAndEmailContainsIgnoreCase(name, email, pageable);
    }

    //Lấy thông tin user bằng id
    public User getUserById(String id){
        return userRepository.findById(id);
    }

    //Hiển thị danh sách role dưới dạng "a, b, c"
    public String showRoles(User user){
        return String.join(", ", user.getRole());
    }

    //Tìm user bằng email
    public Optional<User> getUserByEmail(String mail){
        return userRepository.findByEmail(mail);
    }

    // Kiểm tra email đã tồn tại chưa
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean emailExistsAndNotEnable(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && !user.get().getEnabled();
    }

    // Active user
    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    // Disable user
    public void disableUser(String email) {
        userRepository.disableUser(email);
    }


    // Save user
    public void saveUser(User user){
        userRepository.save(user);
    }

    //Reset Password
    public void resetPassword(String email, String password){
        User user = userRepository.findByEmail(email).get();
        user.setPassword(password);
    }


}
