package com.iswAcademy.Voucherz.Api.Controller.Model;

import com.iswAcademy.Voucherz.Model.User;

import java.util.List;

public class Response {

    private String code;
    private String description;
    List<Error> errors;
    private String Email;

    public Response(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Response(String code, String description, String Email) {
        this.code = code;
        this.description = description;
        this.Email= Email;
    }

    public Response(String code, String description, List<Error> errors) {
        this.code = code;
        this.description = description;
        this.errors = errors;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public String getEmail() {
        return Email;
    }
}
