package edu.sdut.Vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回的统一返回实体类
 * @author qingyun
 * @version 1.0
 * @date 2021/11/3 16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class ReturnMsgBody<T> {
    /**
     * 返回的状态
     */
    private boolean Flag;
    /**
     * 返回的状态码
     */
    private Integer Code;
    /**
     * 返回的实体信息
     */
    private String Msg;
    /**
     * 返回的主体
     */
    private T data;

    public static <T> ReturnMsgBody<T> ReturnSuccess(String msg,T data){
        ReturnMsgBody<T> result = new ReturnMsgBody<>();
        result.setFlag(ReturnMsgAndCode.ReturnFlagTrue);
        result.setCode(ReturnMsgAndCode.ReturnCodeTrue);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> ReturnMsgBody<T> ReturnFail(String msg){
        ReturnMsgBody<T> result = new ReturnMsgBody<>();
        result.setFlag(ReturnMsgAndCode.ReturnFlagFalse);
        result.setCode(ReturnMsgAndCode.ReturnCodeFalse);
        result.setMsg(msg);
        return result;
    }

}
