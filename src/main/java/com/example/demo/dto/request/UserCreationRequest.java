package com.example.demo.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// DTO (Data Transfer Object) là một class đơn giản chứa các thuộc tính và phương thức getter/setter để truyền dữ liệu giữa các lớp.
public class UserCreationRequest {

    @NotBlank(message = "Tên không dược để trống")// Kiem tra khong duoc de trong
    private String name;

    @Size(min = 8, message = "Mật khẩu phải dài hơn 8 ký tự")// Vi dong nay nam tren bien password nen spring biet rang rang buoc nay duoc ap dung cho truong password
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;


}
