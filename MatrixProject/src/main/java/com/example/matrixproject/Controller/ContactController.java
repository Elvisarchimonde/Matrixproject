package com.example.matrixproject.Controller;

import com.example.matrixproject.Dao.Entity.ContactEntity;
import com.example.matrixproject.Dao.Repository.ContactRepository;
import com.example.matrixproject.Service.ContactService;
import com.example.matrixproject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ContactController {
    @Autowired
    ProductService productService;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/contact")
    public String contact( Model model) {
    model.addAttribute("title", "Contact");
    model.addAttribute("contact",new ContactEntity());
    return "Contact.html";
}

    @RequestMapping(value = "/contact" , method = RequestMethod.POST)
    public Object submitContact(@Valid @ModelAttribute("contact")ContactEntity contactEntity, BindingResult bindingResult,
                              HttpServletRequest request,Model model)throws  MessagingException{

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email =  request.getParameter("email");
        String message = request.getParameter("comment");

        if (bindingResult.hasErrors()){
            return "Contact.html";
        }


                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

                helper.setFrom("freshmarket.message@gmail.com");
                helper.setTo("cavidahadov7@gmail.com");
                String mailSubject = firstName + "\n" + lastName + "\n"  + "Has sent a message";
                String mailContent = "<p><b>Sender Name : </b>" + firstName + "</p>" + "\n";
                mailContent += "<p><b>Sender Last Name : </b>" + lastName + "</p>" + "\n";
                mailContent += "<p><b>Sender E-mail : </b>" +  email+ "</p>" + "\n";
                mailContent += "<p><b>Content : </b>" + message + "</p>" + "\n";

                helper.setSubject(mailSubject);
                helper.setText(mailContent,true);

                mailSender.send(mimeMessage);
                model.addAttribute("success","Thank you for contacting us . We'll get back to you shortly !");;

                contactEntity = new ContactEntity(firstName,lastName,email);
                contactRepository.saveAndFlush(contactEntity);
                return "Contact.html";


        }

}