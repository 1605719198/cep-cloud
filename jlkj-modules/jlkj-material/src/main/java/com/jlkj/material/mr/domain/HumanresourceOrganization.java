package com.jlkj.material.mr.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 机构数据
 * @author zyf
 * @TableName t_humanresource_organization
 */
@TableName(value ="human_hm_organization")
@Data
public class HumanresourceOrganization implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 机构编码
     */
    private String organizationCode;

    /**
     * 机构名称
     */
    private String organizationName;

    /**
     * 板块ID
     */
    private String boardId;

    /**
     * 上级机构ID
     */
    private String parentId;

    /**
     * 上级机构名称
     */
    private String parentName;

    /**
     * 上级机构全称
     */
    private String parentFullName;

    /**
     * 机构层级ID
     */
    private String organizationTierId;

    /**
     * 机构类别ID
     */
    private String organizationTypeId;

    /**
     * 机构细类ID
     */
    private String organizationCategoryId;

    /**
     * 排序序号
     */
    private Integer serialNo;

    /**
     * 成本中心
     */
    private String costCenterId;

    /**
     * 批准文号
     */
    private String approvalNo;

    /**
     * 传真
     */
    private String fax;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 变更原因
     */
    private String changeReason;

    /**
     * 生效日期
     */
    private String effectDate;

    /**
     * 版本号
     */
    private Integer versionNo;

    /**
     * 是否公司
     */
    private String ifCompany;

    /**
     * 公司别
     */
    private String companyId;

    /**
     * 是否显示
     */
    private String ifDisplay;

    /**
     * 输入人
     */
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入日期
     */
    private String createDate;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
