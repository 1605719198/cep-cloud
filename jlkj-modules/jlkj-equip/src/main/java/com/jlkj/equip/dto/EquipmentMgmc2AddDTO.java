package com.jlkj.equip.dto;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 停机原因数据
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
@TableName("t_equipment_mgmc2")
public class EquipmentMgmc2AddDTO {

    /**
     * 系统码
     */
    private String id;

    /**
     * 停机原因编号
     */
    private String mgmc2no;

    /**
     * 停机原因名称
     */
    private String mgmc2name;

    /**
     * 停机原因群码
     */
    private String groupcode;

    /**
     * 停机类型编号
     */
    private String mgmc1no;

    /**
     * 备注
     */
    private String memo;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改人姓名
     */
    private String updateUserName;
    /**
     * 创建人姓名
     */
    private String createUserName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getMgmc2no() {
        return mgmc2no;
    }

    public void setMgmc2no(String mgmc2no) {
        this.mgmc2no = mgmc2no;
    }
    public String getMgmc2name() {
        return mgmc2name;
    }

    public void setMgmc2name(String mgmc2name) {
        this.mgmc2name = mgmc2name;
    }
    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }
    public String getMgmc1no() {
        return mgmc1no;
    }

    public void setMgmc1no(String mgmc1no) {
        this.mgmc1no = mgmc1no;
    }
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public String toString() {
        return "EquipmentMgmc2AddDTO{" +
                "id='" + id + '\'' +
                ", mgmc2no='" + mgmc2no + '\'' +
                ", mgmc2name='" + mgmc2name + '\'' +
                ", groupcode='" + groupcode + '\'' +
                ", mgmc1no='" + mgmc1no + '\'' +
                ", memo='" + memo + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", createUserName='" + createUserName + '\'' +
                '}';
    }
}
