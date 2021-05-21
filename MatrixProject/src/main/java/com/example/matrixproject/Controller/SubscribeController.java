package com.example.matrixproject.Controller;

import com.example.matrixproject.Dao.Repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class SubscribeController {


    @Autowired
    ContactRepository contactRepository;

    @Autowired
    JavaMailSender mailSender;

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public  Object submitContact(HttpServletRequest request, Model model)throws MessagingException, IOException{
        String subscribe = request.getParameter("subscribe");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("freshmarket.message@gmail.com");
        helper.setTo(subscribe);

        String mailSubject = "Fresh Market "+"  Has sent a message";

        String mailContent="<div>";
        mailContent +="<p style=\"font-size: 30px; text-align: center;color:black;\"><b>Welcome to Fresh Market family</b></p><br>"+ "\n";
        mailContent += "<p style=\"font-size: 20px;text-align:center;color:black;\">You've signed up to bethe first to know about our exclusive offers, new products, and recipes." +
                "You've signed up to bethe first to know about.</p>"+  "\n";
        mailContent +="</div>";
        mailContent +="<br><img src='cid:fre.png'>";

        helper.setSubject(mailSubject);
        helper.setText(mailContent,true);

        String path = "C:/Users/lenovo/Desktop/Project/Matrix-Spring/freshmarket/src/main/resources/static/gallery/fre.png";
        FileSystemResource file = new FileSystemResource(new File(path));
        helper.addInline("fre.png",file);
        mailSender.send(mimeMessage);

        return "redirect:http://localhost:8080" ;
    }

}
