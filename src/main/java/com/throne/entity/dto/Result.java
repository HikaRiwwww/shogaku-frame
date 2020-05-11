package com.throne.entity.dto;

public class Result<T> {
    private Boolean success;
    private String msg;
    private T data;
}
