package com.jlkj.common.dto.dto.financeperdto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author wang'mai
 */
@Data
public class FinanceperCostDTO {
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
     * 节点指示码
     */
    private String leafId;

    /**
     * 项次一
     */
    private String itemA;

    /**
     * 项次二
     */
    private String itemB;

    /**
     * 项次三
     */
    private String itemC;

    /**
     * 项次四
     */
    private String itemD;

    /**
     * 项次五
     */
    private String itemE;

    /**
     * 资料一
     */
    private String dataA;

    /**
     * 资料二
     */
    private String dataB;

    /**
     * 资料三
     */
    private String dataC;

    /**
     * 资料四
     */
    private String dataD;

    /**
     * 资料五
     */
    private String dataE;

    /**
     * 资料六
     */
    private String dataF;

    /**
     * 资料七
     */
    private BigDecimal dataG;

    /**
     * 资料排列指示
     */
    private String dataSeq;

    /**
     * 状态
     */
    private String status;

    /**
     * 建立人
     */
    private String createUser;

    /**
     * 建立日期
     */
    private Date createDate;

    /**
     * 最后异动人
     */
    private String updateUser;

    /**
     * 最后异动日期
     */
    private Date updateDate;

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
}
