package com.jlkj.flow.workflow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 流程id绑定表单id
 * @author Xin
 * @TableName sys_instance_form
 */
@TableName(value ="sys_instance_form")
public class SysInstanceForm implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 业务表单id
     */
    private String formId;

    /**
     * 流程部署id
     */
    private String deployId;

    /**
     * 路由地址
     */
    private String routerPath;

    /**
     * 流程实例id
     */
    private String instanceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 业务表单id
     */
    public String getFormId() {
        return formId;
    }

    /**
     * 业务表单id
     */
    public void setFormId(String formId) {
        this.formId = formId;
    }

    /**
     * 流程定义id
     */
    public String getDeployId() {
        return deployId;
    }

    /**
     * 流程定义id
     */
    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysInstanceForm other = (SysInstanceForm) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFormId() == null ? other.getFormId() == null : this.getFormId().equals(other.getFormId()))
            && (this.getDeployId() == null ? other.getDeployId() == null : this.getDeployId().equals(other.getDeployId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFormId() == null) ? 0 : getFormId().hashCode());
        result = prime * result + ((getDeployId() == null) ? 0 : getDeployId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SysInstanceForm{" +
                "id=" + id +
                ", formId='" + formId + '\'' +
                ", deployId='" + deployId + '\'' +
                ", routerPath='" + routerPath + '\'' +
                ", instanceId='" + instanceId + '\'' +
                '}';
    }
}
