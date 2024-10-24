package com.roger.mmst.component;

import com.roger.mmst.obj.dto.R;
import com.roger.mmst.constants.exception.VerifyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(VerifyException.class)
    public R<Void> verifyHandle(VerifyException e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> validationHandle(MethodArgumentNotValidException e) {
        return R.error(e.getBindingResult().getAllErrors().stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage).orElse("校验异常"));
    }

    @ExceptionHandler(Exception.class)
    public R<Void> globalExceptionHandle(Exception e) {
        log.error("", e);
        return R.error("系统异常");
    }
}
