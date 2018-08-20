package cn.umidata.eos.handler;

import cn.umidata.core.constants.ExceptionCode;
import cn.umidata.core.constants.Message;
import cn.umidata.core.constants.MsgStatus;
import cn.umidata.core.exception.BizException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    private Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Message defaultErrorHandler(HttpServletRequest req, Exception ex) throws Exception {
        ex.printStackTrace();
        Message message = null;
        if (ex instanceof BizException) {
            BizException be = (BizException) ex;
            message = new Message(MsgStatus.FAILURE, be.getErrCode(), be.getErrMsg(),be.getData());
            log.error(message.getErrCode(), ex.getCause());
        } else if (ex instanceof SQLException || ex instanceof IOException) {
            message = new Message(MsgStatus.FAILURE, ExceptionCode.SERVICE_INTERNAL_EXCEPTION.getCode(), ExceptionCode.SERVICE_INTERNAL_EXCEPTION.getMsg());
            log.fatal(message.getErrCode()+",[ SQL&IOException:"+ex.getMessage()+" ]", ex.getCause());
        } else {
            message = new Message(MsgStatus.FAILURE,  ExceptionCode.SERVICE_INTERNAL_EXCEPTION.getCode(), ExceptionCode.SERVICE_INTERNAL_EXCEPTION.getMsg());
            log.fatal(message.getErrCode()+",[ UnknownException:"+ex.getMessage()+" ]",ex.getCause());
        }
        return message;
    }

}
