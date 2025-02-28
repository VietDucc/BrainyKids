package com.example.demo.dto.request;

import java.time.LocalDate;

// DTO (Data Transfer Object) là một class đơn giản chứa các thuộc tính và phương thức getter/setter để truyền dữ liệu giữa các lớp.
public class UserUpdateRequest {

    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
