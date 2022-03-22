package dev.aquashdw.community.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String home(Authentication authentication) {
            try {
                logger.info("connected user: {}", authentication.getName());
            } catch (NullPointerException e) {
                logger.info("Anonymous user logged in");
            }
            return "index";
        }
}

