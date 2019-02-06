package com.iswAcademy.Voucherz.Api.Controller;

import com.iswAcademy.Voucherz.Model.User;
import com.iswAcademy.Voucherz.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserService userService;

    @MockBean
    private AuthController authController;

    @Test
    public void authenticateUser() {
    }

    @Test
    public void registerUser() throws Exception {
        //for checking assert
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void findUser() {
    }

    @Test
    public void findAllUser() {
    }

    @Test
    public void signout() {
    }
}