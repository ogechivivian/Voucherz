package com.iswAcademy.Voucherz.Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = User.class)
public class User extends BaseEntity implements Serializable {

    private  String FirstName;

    private String LastName;

    private String Email;

    private String Password;

    private int CompanySize;

    private String Role;

    private  String resetToken;

    public User() {
    }


    public User(@JsonProperty("firstName")String firstName, @JsonProperty("lastName")String lastName, @JsonProperty("email")String email, @JsonProperty("password")String password, @JsonProperty("companySize")int companySize, @JsonProperty("role")String role, @JsonProperty("resetToken")String resetToken) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Password = password;
        CompanySize = companySize;
        Role = role;
        this.resetToken = resetToken;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        this.Role = role;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getCompanySize() {
        return CompanySize;
    }

    public void setCompanySize(int companySize) {
        CompanySize = companySize;
    }

    @Override
    public String toString() {
        return "User{" +
                "FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", CompanySize=" + CompanySize +
                ", Role='" + Role + '\'' +
                '}';
    }


    //    @Override
//    public String toString() {
//        JSONObject jsonInfo = new JSONObject();
//
//        try{
//            jsonInfo.put("FirstName", this.FirstName);
//            jsonInfo.put("LastName", this.LastName);
//            jsonInfo.put("Email", this.Email);
//            jsonInfo.put("Password", this.Password);
//            jsonInfo.put("CompanySame", this.CompanySize);
//            jsonInfo.put("Role", this.Role);
//        }catch (JSONException el){}
//        return jsonInfo.toString();
//
//
//    }
}
