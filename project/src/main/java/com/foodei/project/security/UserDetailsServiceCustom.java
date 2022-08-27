package com.foodei.project.security;

import com.foodei.project.entity.User;
import com.foodei.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        try {
            UserDe user = userRepository.findByEmail(email).get();
            if (user == null){
                throw new UsernameNotFoundException("Not found user with email " + email);
            }

            return new UserDetailCustom(
                    user.getEmail(),
                    user.getPassword().toLowerCase(),
                    user.
            );

        } catch (Exception e){
            throw new RuntimeException(e);
        }



    }
}
