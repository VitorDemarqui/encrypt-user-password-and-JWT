package com.example.encryptuserpassword.service;

import com.example.encryptuserpassword.data.UserDetailsData;
import com.example.encryptuserpassword.model.UserModel;
import com.example.encryptuserpassword.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailServiceImpl implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userModel = userRepository.findByLogin(username);
        if(userModel.isEmpty())
        throw new UsernameNotFoundException("User ["+username+"] not found");

        return new UserDetailsData(userModel);
    }
}
