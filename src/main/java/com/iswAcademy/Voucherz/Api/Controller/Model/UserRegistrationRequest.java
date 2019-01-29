package com.iswAcademy.Voucherz.Api.Controller.Model;

import com.iswAcademy.Voucherz.Model.Role;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserRegistrationRequest extends LoginInRequest {

    @Length(min=3, max =50)
    @NotBlank(message = "required")
    private String FirstName;

    @Length(min=3, max=50)
    @NotBlank(message = "required")
    private String LastName;


    private int CompanySize;

//    private ArrayList<Role> roles = new ArrayList<>();
//
//    public ArrayList<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(ArrayList<Role> roles) {
//        this.roles = roles;
//    }


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


    public int getCompanySize() {
        return CompanySize;
    }

    public void setCompanySize(int companySize) {
        CompanySize = companySize;
    }

//    @Override
//    public String toString() {
//        return "UserRegistrationRequest{" +
//                "FirstName='" + FirstName + '\'' +
//                ", LastName='" + LastName + '\'' +
//                ", PhoneNumber='" + PhoneNumber + '\'' +
//                ", CompanySize=" + CompanySize +
//                ", Email=" + getEmail() + '\'' +
//                ", Password=" + getPassword() + '\'' +
//                ", roles=" + roles +
//                '}';
//    }
}
