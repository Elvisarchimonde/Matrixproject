package com.example.matrixproject.Controller;

import com.example.matrixproject.Dao.Entity.ConfirmationToken;
import com.example.matrixproject.Dao.Entity.User;
import com.example.matrixproject.Dao.Repository.ConfirmationTokenRepository;
import com.example.matrixproject.Dao.Repository.UserRepository;
import com.example.matrixproject.Model.Dto.UserData;
import com.example.matrixproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @GetMapping("/signUpWithEmail")
    public String signUpWithEmailPage( Model model) {
        model.addAttribute("title", "Sign Up With Email");
        model.addAttribute("user",new UserData());
        return "SignUpWithEmail";
    }

    @RequestMapping(value="/signUpWithEmail", method=RequestMethod.POST)
    public Object registerUser(@Valid @ModelAttribute("user") UserData userData,Model model,BindingResult result
    ,HttpServletRequest  request)throws MessagingException {
        String password = userData.getPassword();
        String matchingPassword = userData.getMatchingPassword();
        User existingUser = userRepository.findByEmail(userData.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (!password.equals(matchingPassword)) {
            result.rejectValue("matchingPassword", null, "Passwords don't match");
        }
        if (result.hasErrors()) {
            return "signUpWithEmail";
        }
            System.out.println("Controller");
            userService.saveUserRegister(userData);
            model.addAttribute("success", "Verification email has sent to   " + userData.getEmail() + "!");

            return "signUpWithEmail";


    }
    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(Model model, @RequestParam("token")String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail());
            user.setIsenabled(true);
            userRepository.save(user);
            model.addAttribute("success", "Congratulations ! Your account has been activated and email is verified.");
        } else {
            model.addAttribute("error", "The link is invalid or broken!");

        }

        return "RegisterMessage";
    }
}
