package com.ryota.common.vo;

import com.ryota.common.common.Result;
import com.ryota.common.constant.HttpStatus;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Ryota
 * @create 2022/6/4 12:18
 */
@Data
public class ResultVO extends HashMap<String, Object> {

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    public ResultVO(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static ResultVO success(String msg, Object data) {
        return new ResultVO(HttpStatus.SUCCESS, msg, data);
    }



}
