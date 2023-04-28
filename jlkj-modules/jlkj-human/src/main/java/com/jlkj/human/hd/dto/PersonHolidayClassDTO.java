package com.jlkj.human.hd.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 人员排班数据对象
 *
 * @author 266861
 * @date 2023-03-21
 */
@Data
public class PersonHolidayClassDTO {

    /**
     * 员工工号
     */
    private Long empId;

    /**
     * 请假开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 请假结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 排班天数
     */
    private Long classNumber;


}
