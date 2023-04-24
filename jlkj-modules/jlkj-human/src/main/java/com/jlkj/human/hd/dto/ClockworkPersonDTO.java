package com.jlkj.human.hd.dto;

import lombok.Data;

/**
 * 通过卡钟编号查人员
 * @author 266861
 * @date 2023-03-08
 */
@Data
public class ClockworkPersonDTO {

    /**
     * 工号
     */
    private String empNo;

    /**
     * 姓名
     */
    private String empName;

    /**
     * 公司别
     */
    private String compId;
}
