package com.jlkj.finance.ac.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
/**
 * @author wang'mai
 */
@Data
public class FinanceCostAddTreeNodeDTO {
    /**
     * 当前页
     */
    private Long page;
    /**
     * 每页记录数
     */
    private Long limit;
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 成本科目阶层编码
     */
    private String costCodeLevelCode;

    /**
     * 上层代码
     */
    private String upperCode;
    /**
     * 上层代码
     */
    private String upperCodex;
    /**
     * 上层代码名称
     */
    private String upperName;
    /**
     * 阶层代码
     */
    private String levelCode;

    /**
     * 阶层名称
     */
    private String levelName;

    /**
     * 节点类别
     */
    private String nodeType;

    /**
     * 建立人
     */
    private String createUser;


    /**
     * 父类
     */
    private String parentId;

    /**
     * 建立日期
     */
    private String createDate;

    /**
     * 最后异动人
     */
    private String updateUser;

    /**
     * 最后异动日期
     */
    private String updateDate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    public FinanceCostAddTreeNodeDTO(Long page, Long limit, String costCodeLevelCode,String upperCode, String  upperName) {
        this.page = page;
        this.limit = limit;
        this.costCodeLevelCode = costCodeLevelCode;
        this.upperName = upperName;
    }
}
