package com.jlkj.common.dto.dto.energyprovider;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yzl
 * @create 2022-09-02 13:32:21.116575
 */
@Data
public class EnergyDayPlanOutputDTO {
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 序号 */
    private String serialNo ;

    /** 工序名称 */
    private String factoryName ;

    /** 作业区 */
    private String factoryArea ;

    /** 日计划产量 */
    private BigDecimal planValue ;

    /** 年月日(YYYY-MM-DD) */
    private String planyearMonthDay ;

    /** 单位 */
    private String unit ;

    /** 新增时间(YYYY-MM-DD HH:mm:ss) */
    private String createDate ;

    /** 新增人员 */
    private String createEmpNo ;

    /** 修改时间(YYYY-MM-DD HH:mm:ss) */
    private String updateDate ;

    /** 修改人员 */
    private String updateEmpNo ;
    /** 备注 */
    private String memo ;
}
