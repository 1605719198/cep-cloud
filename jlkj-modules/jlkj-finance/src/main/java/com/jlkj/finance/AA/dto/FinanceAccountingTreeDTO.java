package com.jlkj.finance.AA.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wang'mai
 */
@Data
public class FinanceAccountingTreeDTO {

    /**
     * 户号类别
     */
    private String idTypeStus;

    /**
     * 参号类别
     */
    private String refTypeStus;
    /**
     * 公司别
     */
    @TableId
    private String id;

    /**
     * 公司代码
     */
    private String compId;

    /**
     * 会计科目编号(组合)
     */
    private String accountCode;
    /**
     * 存会计科目名称
     */
    private String accountName;
    /**
     * 会计科目中文名称
     */
    private String accountChineseName;

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
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

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

    /**
     * 父节点id
     */
    private String parentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用来封装子节点数据
     */
    List<FinanceAccountingTreeDTO> children ;

    public FinanceAccountingTreeDTO() {
    }

    public FinanceAccountingTreeDTO(String refTypeStus, String idTypeStus, String accountName, String accountCode, String parentAccountCode, String folderName, String isFolder, String parentId, String id, List<FinanceAccountingTreeDTO> children) {
        this.id = id;
        this.parentId = parentId;
        this.children = children;
        this.accountCode = accountCode;
        this.parentAccountCode = parentAccountCode;
        this.folderName = folderName;
        this.isFolder = isFolder;
        this.accountName= accountName;
        this.refTypeStus = refTypeStus;
        this.idTypeStus = idTypeStus;
    }

    public FinanceAccountingTreeDTO(FinanceAccountingTreeDTO financeAccountingTreeDTO) {
        this.id = financeAccountingTreeDTO.getId();
        this.parentId = financeAccountingTreeDTO.getParentId();
        this.children = financeAccountingTreeDTO.getChildren();
        this.accountCode = financeAccountingTreeDTO.getAccountCode();
        this.parentAccountCode = financeAccountingTreeDTO.getParentAccountCode();
        this.folderName = financeAccountingTreeDTO.getFolderName();
        this.isFolder = financeAccountingTreeDTO.getIsFolder();
        this.accountName = financeAccountingTreeDTO.getAccountName();
        this.idTypeStus = financeAccountingTreeDTO.getIdTypeStus();
        this.refTypeStus = financeAccountingTreeDTO.getRefTypeStus();
    }

}
