package com.yufei.search.dto;

import co.elastic.clients.elasticsearch._types.FieldValue;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangzhenqing
 * @date 2023/12/11 20:16
 * @description
 */

@Data
public class ResponseDto<T> implements Serializable {
    private int code;
    private String message;
    private long total;
    private double maxScore;
    private int page;
    private int size;
    private int maxPage;
    private T data;
    private List<FieldValue> searchAfter;
    private String type;
    private String dsl;

    public static ResponseDto successEmptyDto() {
        ResponseDto resultData = new ResponseDto();
        resultData.setCode(200);
        resultData.setMessage("success");
        return resultData;
    }

    public static ResponseDto successEmptyDto(String type) {
        ResponseDto resultData = new ResponseDto();
        resultData.setCode(200);
        resultData.setMessage("success");
        resultData.setType(type);
        return resultData;
    }
}
