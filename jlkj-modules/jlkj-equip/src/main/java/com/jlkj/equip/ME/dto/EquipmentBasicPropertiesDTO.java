package com.jlkj.equip.ME.dto;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 设备台账
 * </p>
 *
 * @author xin
 * @since 2022-06-13
 */
public class EquipmentBasicPropertiesDTO {

    /**
     * 编号
     */
    private String id;

    /**
     * 设备位置
     */
    private String equipmentLocation;

    /**
     * 上级设备
     */
    private String upperLevel;

    /**
     * 设备类型编号
     */
    private String equipmentType;

    /**
     * 设备专业编码
     */
    private String equipmentProfessionNumber;

    /**
     * 设备名称
     */
    private String equipmentName;

    /**
     * 设备编码
     */
    private String equipmentNumbert;

    /**
     * 是否物联设备
     */
    private String internetOfThings;

    /**
     * 关联应用
     */
    private String associatedApps;

    /**
     * 第一责任人编号
     */
    private String firstResponsiblePerson;

    /**
     * 油品类别编号
     */
    private String oilTypeId;

    /**
     * 油品名称
     */
    private String oilName;
    /**
     * 创建人编号
     */
    private String createUser;

    /**
     * 创建人姓名
     */
    private String createUserName;
    /**
     * 修改人编号
     */
    private String updateUser;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(String equipmentLocation) {
        this.equipmentLocation = equipmentLocation;
    }
    public String getUpperLevel() {
        return upperLevel;
    }

    public void setUpperLevel(String upperLevel) {
        this.upperLevel = upperLevel;
    }
    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }
    public String getEquipmentProfessionNumber() {
        return equipmentProfessionNumber;
    }

    public void setEquipmentProfessionNumber(String equipmentProfessionNumber) {
        this.equipmentProfessionNumber = equipmentProfessionNumber;
    }
    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
    public String getEquipmentNumbert() {
        return equipmentNumbert;
    }

    public void setEquipmentNumbert(String equipmentNumbert) {
        this.equipmentNumbert = equipmentNumbert;
    }
    public String getInternetOfThings() {
        return internetOfThings;
    }

    public void setInternetOfThings(String internetOfThings) {
        this.internetOfThings = internetOfThings;
    }
    public String getAssociatedApps() {
        return associatedApps;
    }

    public void setAssociatedApps(String associatedApps) {
        this.associatedApps = associatedApps;
    }
    public String getFirstResponsiblePerson() {
        return firstResponsiblePerson;
    }

    public void setFirstResponsiblePerson(String firstResponsiblePerson) {
        this.firstResponsiblePerson = firstResponsiblePerson;
    }
    public String getOilTypeId() {
        return oilTypeId;
    }

    public void setOilTypeId(String oilTypeId) {
        this.oilTypeId = oilTypeId;
    }
    public String getOilName() {
        return oilName;
    }

    public void setOilName(String oilName) {
        this.oilName = oilName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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

    @Override
    public String toString() {
        return "EquipmentBasicPropertiesDTO{" +
                "id='" + id + '\'' +
                ", equipmentLocation='" + equipmentLocation + '\'' +
                ", upperLevel='" + upperLevel + '\'' +
                ", equipmentType='" + equipmentType + '\'' +
                ", equipmentProfessionNumber='" + equipmentProfessionNumber + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentNumbert='" + equipmentNumbert + '\'' +
                ", internetOfThings='" + internetOfThings + '\'' +
                ", associatedApps='" + associatedApps + '\'' +
                ", firstResponsiblePerson='" + firstResponsiblePerson + '\'' +
                ", oilTypeId='" + oilTypeId + '\'' +
                ", oilName='" + oilName + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                '}';
    }
}
