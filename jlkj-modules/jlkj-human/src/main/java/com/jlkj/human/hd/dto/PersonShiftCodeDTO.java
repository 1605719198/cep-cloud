package com.jlkj.human.hd.dto;
import lombok.Data;
import java.util.Date;

/**
 * 排班人员工号及日期
 * @author 266861
 * @date 2023-04-13
 */

@Data
public class PersonShiftCodeDTO {
    //工号
    private String empId;

    //时间
    private Date date;
}
