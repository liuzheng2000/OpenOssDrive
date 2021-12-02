package edu.sdut.Handler;

import edu.sdut.Vo.ReturnMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * TODO
 * 全局异常处理
 * @author qingyun
 * @version 1.0
 * @date 2021/11/1 21:16
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandler {


    /**
     * 处理空指针异常
     * @param e
     * @return
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = NullPointerException.class)
    public ReturnMsgBody ReturnNullException(Exception e){
        log.error(e.getMessage());
        return ReturnMsgBody.ReturnFail(e.getMessage());
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = IOException.class)
    public ReturnMsgBody ReturnIOException(Exception e){
        log.error(e.getMessage());
        return ReturnMsgBody.ReturnFail(e.getMessage());
    }

}
