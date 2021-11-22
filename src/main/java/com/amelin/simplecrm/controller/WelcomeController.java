package com.amelin.simplecrm.controller;

import com.amelin.simplecrm.dao.repository.JobRepo;
import com.amelin.simplecrm.domain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class WelcomeController {

    @GetMapping("/")
    public String welcome(@AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/login";
        }
        return "home";
    }

    @GetMapping( "/home")
    public String getHomePage() {
        return "home";
    }
}
