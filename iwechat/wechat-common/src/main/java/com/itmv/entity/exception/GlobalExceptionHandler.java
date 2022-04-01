package com.itmv.entity.exception;


import com.itmv.entity.base.BusinessException;
import com.itmv.entity.base.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice   // 全局异常
public class GlobalExceptionHandler {

    /**
     * 处理系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultEntity systemException(Exception e){
        System.out.println("系统异常" + e.getMessage());
        return ResultEntity.error(e.getMessage());
    }

    /**
     * 处理业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResultEntity businessException(Exception e){
        System.out.println("业务异常" + e.getMessage());
        return ResultEntity.error(e.getMessage());
    }


}
