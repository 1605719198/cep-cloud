package com.jlkj.finance.AC.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wang'mai
 */
@Data
public class FinanceCostDTO {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 年度
     */
    private String fiscalYear;

    /**
     * 成本中心
     */
    private String costCenter;

    /**
     * 成本中心说明
     */
    private String costCenterDesc;

    /**
     * 会计归属码
     */
    private String allocCode;

    /**
     * 成本中心形态属性
     */
    private String ccType;

    /**
     * 产品产出属性
     */
    private String isProduct;

    /**
     * 状态
     */
    private String stusCode;

    /**
     * 建立人
     */
    private String createUser;

    /**
     * 建立日期
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 最后修改人
     */
    private String updateUser;

    /**
     * 最后修改日期
     */
    private Date updateDate;

    /**
     * 是否抛AC帐
     */
    private String isPostAc;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;

    /**
     * 属性名称
     */
    private String attriName;

    /**
     * 属性说明
     */
    private String attriDesc;

    /**
     * 父类
     */
    private String parentId;
    /**
     * 属性说明
     */
    private String label;
    /**
     * 属性名称
     */
    private String value;

    private List<FinanceCostCostcenterAttriDTO> propertydata;

    private String attriValue;
}
