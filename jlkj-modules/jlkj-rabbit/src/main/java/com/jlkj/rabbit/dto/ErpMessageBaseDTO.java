package com.jlkj.rabbit.dto;

import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-05-19 15:11
 */
@Data
public class ErpMessageBaseDTO<T> {

    private String messageId;

    private String actionCode;

    private String dataSource;

    private T data;
}
