package com.example.matrixproject.Controller.Logout;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutContoller {

    @RequestMapping("/logout")
    public String logout(){
        return "LogInWithEmail";
    }
}
