package com.jlkj.equip.me.dto;

/**
 * <p>
 * 设备台账
 * </p>
 *
 * @author xin
 * @since 2022-06-13
 */
public class EquipmentBasicAccountDTO {
    /**
     * id 设备id
     */
    private String id;
    /**
     * 设备位置
     */
    private String equipmentLocation;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 设备编码
     */
    private String equipmentNumbert;
    /**
     * 设备类型编号
     */
    private String equipmentType;
    /**
     * 第一责任人编号
     */
    private String firstResponsiblePerson;
    /**
     * 台账类型
     */
    private String accountType;
    /**
     * 资产状态
     */
    private String assetStatus;
    /**
     * 设备级别
     */
    private String equipmentLevel;
    /**
     * 成本中心
     */
    private String costCenter;
    /**
     * 设备阶层
     */
    private String equipmentClass;
    /**
     * 购入日期
     */
    private String purchaseDate;

    /**
     * 维护单位
     */
    private String maintenanceUnit;

    /**
     * 维护人员
     */
    private String maintenanceUser;

    /**
     * 产权单位
     */
    private String propertyUnit;

    /**
     * 安装日期
     */
    private String installationUnit;
    /**
     * 设备层级
     */
    private String equipmentGrade;

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

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getFirstResponsiblePerson() {
        return firstResponsiblePerson;
    }

    public void setFirstResponsiblePerson(String firstResponsiblePerson) {
        this.firstResponsiblePerson = firstResponsiblePerson;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(String equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getEquipmentClass() {
        return equipmentClass;
    }

    public void setEquipmentClass(String equipmentClass) {
        this.equipmentClass = equipmentClass;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getMaintenanceUnit() {
        return maintenanceUnit;
    }

    public void setMaintenanceUnit(String maintenanceUnit) {
        this.maintenanceUnit = maintenanceUnit;
    }

    public String getMaintenanceUser() {
        return maintenanceUser;
    }

    public void setMaintenanceUser(String maintenanceUser) {
        this.maintenanceUser = maintenanceUser;
    }

    public String getPropertyUnit() {
        return propertyUnit;
    }

    public void setPropertyUnit(String propertyUnit) {
        this.propertyUnit = propertyUnit;
    }

    public String getInstallationUnit() {
        return installationUnit;
    }

    public void setInstallationUnit(String installationUnit) {
        this.installationUnit = installationUnit;
    }

    public String getEquipmentGrade() {
        return equipmentGrade;
    }

    public void setEquipmentGrade(String equipmentGrade) {
        this.equipmentGrade = equipmentGrade;
    }

    @Override
    public String toString() {
        return "EquipmentBasicAccountDTO{" +
                "id='" + id + '\'' +
                ", equipmentLocation='" + equipmentLocation + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentNumbert='" + equipmentNumbert + '\'' +
                ", equipmentType='" + equipmentType + '\'' +
                ", firstResponsiblePerson='" + firstResponsiblePerson + '\'' +
                ", accountType='" + accountType + '\'' +
                ", assetStatus='" + assetStatus + '\'' +
                ", equipmentLevel='" + equipmentLevel + '\'' +
                ", costCenter='" + costCenter + '\'' +
                ", equipmentClass='" + equipmentClass + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", maintenanceUnit='" + maintenanceUnit + '\'' +
                ", maintenanceUser='" + maintenanceUser + '\'' +
                ", propertyUnit='" + propertyUnit + '\'' +
                ", installationUnit='" + installationUnit + '\'' +
                ", equipmentGrade='" + equipmentGrade + '\'' +
                '}';
    }
}
