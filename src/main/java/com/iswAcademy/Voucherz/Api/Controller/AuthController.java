package com.iswAcademy.Voucherz.Api.Controller;

import com.iswAcademy.Voucherz.Api.Controller.Model.*;
import com.iswAcademy.Voucherz.Producer.AuditProducer;
import com.iswAcademy.Voucherz.Util.JwtTokenProvider;
import com.iswAcademy.Voucherz.Model.RoleName;
import com.iswAcademy.Voucherz.Model.User;
import com.iswAcademy.Voucherz.Service.RoleService;
import com.iswAcademy.Voucherz.Service.UserService;
import com.iswAcademy.Voucherz.event.AuditMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class AuthController {

    protected Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;


    @Autowired
    RoleService roleService;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

//    @Autowired
//    Producer producer;
    @Autowired
    AuditProducer auditProducer;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginInRequest loginInRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginInRequest.getEmail(),
                        loginInRequest.getPassword()
                )
        );
       AuditMessage event = new AuditMessage("Login",loginInRequest.getEmail(),new Date());
       auditProducer.sendAudit(event);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        logger.info(String.format("signin.authenticateUser(%s)", jwt));
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup" )
    public ResponseEntity<Response> registerUser(@Valid @RequestBody UserRegistrationRequest request){
        User user = new User();

        if(userService.findUser(user.getEmail()) != null){
            return new ResponseEntity<Response>(new Response("400","Email Already Exist"),HttpStatus.BAD_REQUEST);
        }
//Creating User's account
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword( passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setCompanySize(request.getCompanySize());
        user.setRole(RoleName.ROLE_USER.toString());
        AuditMessage event = new AuditMessage("Registered ", user.getEmail(), new Date());
        auditProducer.sendAudit(event);
        User  result= userService.createUser(user);

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/auth/{email}")
//                .buildAndExpand(result.getEmail()).toUri();
        logger.info(String.format("Signup.registerUser(%s)", user));
        return new ResponseEntity<Response>(new Response("201", "created"), HttpStatus.CREATED);

//        return ResponseEntity.created(location).body(new Response("201", "created"));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public Response updateUser(@PathVariable( "id" ) long id, @RequestBody @Validated final UpdateUserRequest request) {
        User user = userService.findById(id);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setCompanySize(request.getCompanySize());
        AuditMessage event = new AuditMessage("Updated", user.getEmail(), new Date());
        auditProducer.sendAudit(event);
      logger.info(String.format("this user updated registration details", user.getEmail()));
        userService.updateUser(id,user);
        return  new Response ("200", "Updated");

    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.FOUND)
    public User findUser(@PathVariable String email){
       User user =  userService.findUser(email);
       return user;
    }

    @RequestMapping(value ="/users", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAllUser(){
        List<User> userList = userService.findAll();
        return userList;
    }

    @PostMapping("/signout")
    @ResponseBody
    public ResponseEntity<Response> signout(@RequestHeader(value="Authorization") String token){
        HttpHeaders headers = new HttpHeaders();
        if(userService.logout(token)){
            headers.remove("Authorisation");
//            AuditMessage event = new AuditMessage("Logout")
            return new ResponseEntity<Response>(new Response("","logged out"), headers,HttpStatus.CREATED);

        }
        return new ResponseEntity<Response>(new Response( "","Logout Failed"),headers, HttpStatus.NOT_MODIFIED);
    }




}