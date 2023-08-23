package com.betrybe.alexandria.controller.dto;

public record ResponseDTO<T>(String message, T data) {

}