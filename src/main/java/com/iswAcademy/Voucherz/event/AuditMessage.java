package com.iswAcademy.Voucherz.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class AuditMessage implements Serializable {
    private final String Description;
    private final String Email;
    private final Date EventDate;

    public AuditMessage(@JsonProperty("description")String description,@JsonProperty("email") String email, @JsonProperty("date") Date eventDate) {
        Description = description;
        Email = email;
        EventDate = eventDate;
    }

    public String getDescription() {
        return Description;
    }

    public String getEmail() {
        return Email;
    }

    public Date getEventDate() {
        return EventDate;
    }

    @Override
    public String toString() {
        return "AuditMessage{" +
                "Description='" + Description + '\'' +
                ", Email='" + Email + '\'' +
                ", EventDate=" + EventDate +
                '}';
    }
}
