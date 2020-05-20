package com.qian.organization.utils;

import org.apache.commons.lang3.StringUtils;


/**
 * @Author: Coco
 * @Date: 2020.05.20 14:50
 * @Version: v0.0.1
 */
public class CommonStringUtils extends StringUtils {


    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }


}
