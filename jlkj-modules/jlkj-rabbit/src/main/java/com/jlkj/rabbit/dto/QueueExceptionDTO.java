package com.jlkj.rabbit.dto;

import lombok.Data;

/**
 * @author zyf
 * @Description
 * @create 2022-07-07 16:14
 */
@Data
public class QueueExceptionDTO {
    private String queueName;

    private String message;

    private String exception;
}
