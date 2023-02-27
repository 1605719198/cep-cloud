package com.jlkj.common.dto.finance.aa;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
/**
 * @author wang'mai
 */
@Data
public class FinanceperMenuDTO {
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
    /**
     * 父键
     */
    private String parentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
