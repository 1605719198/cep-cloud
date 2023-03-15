package com.jlkj.human.hm.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 选单配置表
 * @TableName human_hm_baseinfo
 * @author 111191
 */
@TableName(value ="human_hm_baseinfo")
@Data
public class Baseinfo implements Serializable {
    /**
     * uuid 主键ID
     */
    @TableId
    private String uuid;

    /**
     * 父节点ID
     */
    private String parentId;

    /**
     * 资料代码
     */
    private String dicNo;

    /**
     * 资料名称
     */
    private String dicName;

    /**
     * 资料状态 （0-停用，1-可用）
     */
    private String status;

    /**
     * 更新人员
     */
    private String updateEmp;

    /**
     * 更新日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用来封装子节点数据
     */
    @TableField(exist = false)
    private List<Baseinfo> children = new ArrayList<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Baseinfo() {
    }

    public Baseinfo(String uuid, String parentId, String dicNo, String dicName, String status, String updateEmp, Date updateDate, String remark, List<Baseinfo> children) {
        this.uuid = uuid;
        this.parentId = parentId;
        this.dicNo = dicNo;
        this.dicName = dicName;
        this.status = status;
        this.updateEmp = updateEmp;
        this.updateDate = updateDate;
        this.remark = remark;
        this.children = children;
    }

    public Baseinfo(Baseinfo baseinfo) {
        this.uuid = baseinfo.getUuid();
        this.parentId = baseinfo.getParentId();
        this.dicNo = baseinfo.getDicNo();
        this.dicName = baseinfo.getDicName();
        this.status = baseinfo.getStatus();
        this.updateEmp = baseinfo.getUpdateEmp();
        this.updateDate = baseinfo.getUpdateDate();
        this.remark = baseinfo.getRemark();
        this.children = baseinfo.getChildren().stream().map(Baseinfo::new).collect(Collectors.toList());
    }

}