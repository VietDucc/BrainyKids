package com.example.demo.dto.request;

public class ApiResponse <T> {
    private int code;
    private String message;
    private T result;
}
