package com.roger.mmst.component;

import com.roger.mmst.constants.exception.VerifyException;
import com.roger.mmst.obj.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(NoResourceFoundException.class)
    public void ignoreExceptionHandle(NoResourceFoundException e) {
    }

    @ExceptionHandler(Exception.class)
    public R<Void> globalExceptionHandle(Exception e) {
        log.error("", e);
        return R.error("系统异常");
    }
}
