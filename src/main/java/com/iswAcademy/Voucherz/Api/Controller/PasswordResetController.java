package com.iswAcademy.Voucherz.Api.Controller;

import com.iswAcademy.Voucherz.Api.Controller.Model.ForgotPasswordRequest;
import com.iswAcademy.Voucherz.Api.Controller.Model.PasswordResetRequest;
import com.iswAcademy.Voucherz.Model.User;
import com.iswAcademy.Voucherz.Service.EmailService;
import com.iswAcademy.Voucherz.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value="/forgot", method= RequestMethod.POST)
    public String forgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest request){
        User user = userService.findUser(forgotPasswordRequest.getEmail());
//        if(userService.findUser(user.getEmail()) != null){
//            return ("Kindly confirm Your email and try again");
//        }
        if(user == null) {
            return ("Kindly confirm Your email and try again");
        }
        user.getEmail();
        user.setResetToken(UUID.randomUUID().toString());
        userService.updateUser(3l, user);
        String appUrl = request.getScheme() + ":/" + request.getServletPath();

        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setFrom("oge2020@gmail.com");
        passwordResetEmail.setTo(user.getEmail());
        passwordResetEmail.setSubject("Password Reset Request");
        passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl +"/reset?token=" + user.getResetToken());
        emailService.sendEmail(passwordResetEmail);
        return ("check your mail for the reset link");
    }

    // Process reset password form

    @RequestMapping(value="/reset", method=RequestMethod.POST)
    public  String setNewpassword(@RequestParam ("token")String token, @RequestBody PasswordResetRequest request){
        if(userService.findUserbytoken(token)== null){
            return ("kindly click forgot password ");
        }
        User user= userService.findUser(token);
        User resetUser = userService.findUserbytoken(token) ;

        // set new password
        resetUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        // set the reset token to null so it cannot be used again

//        resetUser.setResetToken(null);

        //Save user

        userService.createUser(resetUser);
        return("login");
    }


}
