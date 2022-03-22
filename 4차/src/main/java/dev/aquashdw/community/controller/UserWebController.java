package dev.aquashdw.community.controller;

import dev.aquashdw.community.entity.UserEntity;
import dev.aquashdw.community.repository.AreaRepository;
import dev.aquashdw.community.repository.UserRepository;
import dev.aquashdw.community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("user")
public class UserWebController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;

    public UserWebController(@Autowired UserService userService,
                          @Autowired PasswordEncoder passwordEncoder,
                          @Autowired UserRepository userRepository,
                          @Autowired AreaRepository areaRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
    }

    @GetMapping("login")
    public String login(){
        return "login-form";
    }

    @GetMapping("signup")
    public String signUp(){
        return "signup-form";
    }

    @PostMapping("signup")
    public String signUpPost(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("password_check") String passwordCheck){
        if (!password.equals(passwordCheck)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setResidence(areaRepository.findById((long) ((Math.random()*10000)%3)).get());
        userRepository.save(newUser);
        return "redirect:/home";
    }
}
