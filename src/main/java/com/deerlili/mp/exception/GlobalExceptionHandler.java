package com.deerlili.mp.exception;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error("系统异常{0}", e);
        return R.failed(e.getMessage());
    }
}
