package com.yufei.status.common;

import lombok.Data;

/**
 * @author wangzhenqing
 * @date 2025/12/01 13:32
 * @description
 */
@Data
public class Result<T> {
    private Integer code; // 业务状态码：200成功，500失败
    private String msg;   // 提示信息
    private T data;       // 真正的数据（泛型）

    // 快速成功的静态方法
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }

    // 快速失败的静态方法
    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }
}