package com.iswAcademy.Voucherz;

import com.iswAcademy.Voucherz.Service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailServiceTest {

    @Autowired
    private EmailService service;

    @Test
    public void send(){
        String to = "entspecialisthospital@gmail.com";
        String subject = "Mail test";
        String message = "What's up ";

//        service.sendEmail();
    }
}
