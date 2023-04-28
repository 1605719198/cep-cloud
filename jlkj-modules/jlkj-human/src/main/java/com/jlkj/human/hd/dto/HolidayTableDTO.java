package com.jlkj.human.hd.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 查询公司假日设定数据
 *
 * @author 266861
 * @date 2023-03-21
 */
@Data
public class HolidayTableDTO {

    /**
     * 公司别
     */
    private String compId;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 日期类型
     */
    private String dateType;

    /**
     * 日期
     */
    private String holDay;

}
