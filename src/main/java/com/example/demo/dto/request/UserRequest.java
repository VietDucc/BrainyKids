package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

// DTO (Data Transfer Object) là một class đơn giản chứa các thuộc tính và phương thức getter/setter để truyền dữ liệu giữa các lớp.
@Getter
@Setter
public class UserRequest {

    private String id;
    private String first_name;
    private String last_name;



}
