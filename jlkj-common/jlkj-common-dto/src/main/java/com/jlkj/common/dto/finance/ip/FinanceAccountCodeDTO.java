package com.jlkj.common.dto.finance.ip;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
/**
 * @author wang'mai
 */
@Data
public class FinanceAccountCodeDTO {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 公司代码
     */
    private String compId;

    /**
     * 会计科目编号
     */
    private String accountCode;

    /**
     * 会计科目中文名称
     */
    private String accountChineseName;

    /**
     * 会计科目英文名称
     */
    private String accountEnglishName;

    /**
     * 传票性科目
     */
    private String isVoucher;

    /**
     * 单位
     */
    private String isUnit;

    /**
     * 户号类别
     */
    private String idTypeStus;

    /**
     * 参号类别
     */
    private String refTypeStus;

    /**
     * 到期日
     */
    private String isDueDate;

    /**
     * 悬计帐
     */
    private String isLqdt;

    /**
     * 币别
     */
    private String isFrnCrcy;

    /**
     * 机器专用
     */
    private String isPrvlg;

    /**
     * 现金科目
     */
    private String isCash;

    /**
     * 借/贷方会计科目
     */
    private String drOrCr;

    /**
     * 级别
     */
    private String level;

    /**
     * 禁用标识
     */
    private String disabledCode;

    /**
     * 禁用日期
     */
    private String disabledDate;

    /**
     * 新增日期
     */
    private String inputDate;

    /**
     * 新增人
     */
    private String inputUser;

    /**
     * 修改日期
     */
    private String updateDate;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 会计科目关联(父亲)
     */
    private String parentAccountCode;

    /**
     * 资料夹或叶子名称
     */
    private String folderName;

    /**
     * 叶子或资料夹(是否可点开取下层)
     */
    private String isFolder;


    /**
     * 存会计科目名称
     */
    private String accountName;


}
