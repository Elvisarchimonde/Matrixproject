package com.example.matrixproject.Service;

import com.example.matrixproject.Dao.Entity.ConfirmationToken;
import com.example.matrixproject.Dao.Repository.ConfirmationTokenRepository;
import com.example.matrixproject.Dao.Repository.RoleRepository;
import com.example.matrixproject.Model.Dto.UserData;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.matrixproject.Dao.Entity.Role;
import com.example.matrixproject.Dao.Entity.User;
import com.example.matrixproject.Dao.Repository.UserRepository;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    public User findByUsername(String username){
        return  userRepository.findByEmail(username);
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=findByUsername(username);
        if (username=="null"){
            throw new UsernameNotFoundException(String.format("User '%s' not found",username));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return  roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
    public void updatePassword(User user,String password){
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder();
        String encodePassword=bc.encode(password);
        user.setPassword(encodePassword);
        userRepository.save(user);
    }

    public void saveUserRegister(UserData userData) throws MessagingException {
        System.out.println("UserService");
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder();
        String encodePassword=bc.encode(userData.getPassword());
        User newUser=new User();
        newUser.setFirstName(userData.getFirstName());
        newUser.setLastName(userData.getLastName());
        newUser.setEmail(userData.getEmail());
        newUser.setPassword(encodePassword);
        newUser.setIsenabled(false);
        newUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")  ));

        System.out.println("Save");
        userRepository.saveAndFlush(newUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(newUser);
        confirmationTokenRepository.save(confirmationToken);


        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper help=new MimeMessageHelper(message,true);

        help.setFrom("freshmarket.message@gmail.com");
        help.setTo(userData.getEmail());

        String mailSubject="Complete Registration !";
        String mailContent= "  <div  style=\"display : block; width: 90%;\">\n" +
                "  <div>\n" +
                "       <p style=\"font-size: 35px; text-align: center; color: forestgreen;\"><b>Fresh Market</b></p><br>\n" +
                "       <p style=\"text-align: center; color:black; font-size: 17px;\">Thank you for Registration and Welcome to Fresh Market family" +
                " You've signed up to bethe first to know about our exclusive offers, new products, and recipes.\n" +
                "                \"Confirm your account , please click button !\n" +
                "    </p>\n";
        mailContent+="<br><br><div style=\"margin-left: 40%;\">\n"+
                "<a href=\"http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken()+"\">\n" +
                "            <button class=\"btn-dark\"  style=\" border: none; background-color: black; color: white; padding-top: 3%; padding-bottom: 3%; padding-left: 6%; padding-right: 6%;\" >Confirm account</button>\n" +
                "        </a>\n" +
                "        </div><br>\n" ;

        help.setSubject(mailSubject);
        help.setText(mailContent,true);


        mailSender.send(message);


    }
}
