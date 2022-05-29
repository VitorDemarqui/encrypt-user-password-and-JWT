package com.example.encryptuserpassword.controller;

import com.example.encryptuserpassword.model.UserModel;
import com.example.encryptuserpassword.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class userController {
        UserRepository userRepository;
        //para instanciar o encoder eu criei o bean na main
        PasswordEncoder encoder;

        @GetMapping
        public ResponseEntity<List<UserModel>> listAll() {
                return  ResponseEntity.ok(userRepository.findAll());
        }

        @PostMapping
        public ResponseEntity<UserModel> save(@RequestBody UserModel user) {
                user.setPassword(encoder.encode(user.getPassword()));
                return ResponseEntity.ok(userRepository.save(user));
        }

        @GetMapping("/validate-password")
        public ResponseEntity<Boolean> validatePassword(@RequestParam String login,
                                                        @RequestParam String password)  {

                Optional<UserModel> optionalUserModel = userRepository.findByLogin(login);

                if (optionalUserModel.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
                }

                UserModel userModel = optionalUserModel.get();
                boolean valid = encoder.matches(password, userModel.getPassword());

                HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
                return ResponseEntity.status(status).body(valid);
        };


}
