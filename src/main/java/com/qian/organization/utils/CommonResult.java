package com.qian.organization.utils;

import com.qian.organization.dataobject.Department;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Coco
 * @Date: 2020.05.20 14:20
 * @Version: v0.0.1
 */
public class CommonResult extends HashMap<String,Object> {
    public static final long serialVersionUID = 1L;

    /**
     *
     * 状态码
     *
     */
    public static final String CODE_TAG = "code";

    /**
     *
     * 返回内容
     *
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    /**
     * 初始化CommonResult对象，空消息
     */
    public CommonResult(){}

    /**
     * 初始化CommonResult对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public CommonResult(int code,String msg){
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    public CommonResult(int code, String msg, Object data){
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (CommonStringUtils.isNotNull(data)){
            super.put(DATA_TAG,data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static CommonResult success()
    {
        return CommonResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static CommonResult success(Object data)
    {
        return CommonResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static CommonResult success(String msg)
    {
        return CommonResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static CommonResult success(String msg, Object data)
    {
        return new CommonResult(HttpStatus.SUCCESS, msg, data);
    }
}
