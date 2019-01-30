package com.iswAcademy.Voucherz.Api.Controller;

import com.iswAcademy.Voucherz.Api.Controller.Model.*;
import com.iswAcademy.Voucherz.Producer.Producer;
import com.iswAcademy.Voucherz.Util.JwtTokenProvider;
import com.iswAcademy.Voucherz.Model.RoleName;
import com.iswAcademy.Voucherz.Model.User;
import com.iswAcademy.Voucherz.Service.RoleService;
import com.iswAcademy.Voucherz.Service.UserService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/auth")

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

    @Autowired
    Producer producer;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginInRequest loginInRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginInRequest.getEmail(),
                        loginInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        logger.info(String.format("signin.authenticateUser(%s)", jwt));
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }


    @PostMapping("/signup" )
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request){
        User user = new User();

        if(userService.findUser(user.getEmail()) != null){
            return new ResponseEntity(new Response("400", "Email Already in use"),
                    HttpStatus.BAD_REQUEST);
        }
//Creating User's account
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword( passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        user.setCompanySize(request.getCompanySize());

        user.setRole(RoleName.ROLE_USER.toString());
        logger.info("user is being produced"+user.toString());
        producer.produce(user);
        User  result= userService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/auth/{email}")
                .buildAndExpand(result.getEmail()).toUri();
        logger.info(String.format("Signup.registerUser(%s)", user));

        return ResponseEntity.created(location).body(new Response("201", "created"));

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
//        producer.produce(user);
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


//    @PostMapping("/signout")
//    @ResponseBody
//    public ResponseEntity<AuthResponse> logout(@RequestHeader(value="Authorization") String token){
//        HttpHeaders headers = new HttpHeaders();
//        if(loginService.logout(token)){
//            headers.remove("Authorisation");
//            return new ResponseEntity<AuthResponse>(new AuthResponse("logged out"), headers,HttpStatus.CREATED);
//
//        }
//        return new ResponseEntity<AuthResponse>(new AuthResponse("Logout Failed"),headers, HttpStatus.NOT_MODIFIED);
//    }




}