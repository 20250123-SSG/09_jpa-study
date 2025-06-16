package com.younggalee.springdatajpa.advice;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    // 예외발생시 동작하는 컨트롤러

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) { // 객체로 받으면 get 에러메시지 받을 수 있다.
        model.addAttribute("message", e.getMessage());
        return "error/customError";
    }
}
