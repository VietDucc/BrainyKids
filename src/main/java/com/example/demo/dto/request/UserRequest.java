package com.example.demo.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// DTO (Data Transfer Object) là một class đơn giản chứa các thuộc tính và phương thức getter/setter để truyền dữ liệu giữa các lớp.
@Getter
@Setter
public class UserRequest {
    private Data data;
    @Getter
    @Setter
    public static class Data {
        private String id;
        private String first_name;
        private String last_name;
        private String profile_image_url;
        private List<Email> email_addresses;
    }

    @Getter
    @Setter
    public static class Email {
        private String email_address;
    }


}
