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
public class EquipmentAccountDTO{

    /**
     * 主键ID
     */
    private String id;

    /**
     * 设备id
     */
    private String equipmentId;

    /**
     * 设备名称
     */
    private String equipmentName;

    /**
     * 设备编码
     */
    private String equipmentNumbert;

    /**
     * 供货厂商
     */
    private String supplier;

    /**
     * 规格型号
     */
    private String specificationsAndModels;

    /**
     * 台账类型
     */
    private String accountType;

    /**
     * 使用单位
     */
    private String useUnit;

    /**
     * 使用日期
     */
    private String useDate;

    /**
     * 使用部门
     */
    private String useDepartmentId;

    /**
     * 制造厂商
     */
    private String manufacturers;

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
    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
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
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public String getSpecificationsAndModels() {
        return specificationsAndModels;
    }

    public void setSpecificationsAndModels(String specificationsAndModels) {
        this.specificationsAndModels = specificationsAndModels;
    }
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getUseUnit() {
        return useUnit;
    }

    public void setUseUnit(String useUnit) {
        this.useUnit = useUnit;
    }
    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }
    public String getUseDepartmentId() {
        return useDepartmentId;
    }

    public void setUseDepartmentId(String useDepartmentId) {
        this.useDepartmentId = useDepartmentId;
    }
    public String getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(String manufacturers) {
        this.manufacturers = manufacturers;
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
        return "EquipmentAccountDTO{" +
                "id='" + id + '\'' +
                ", equipmentId='" + equipmentId + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentNumbert='" + equipmentNumbert + '\'' +
                ", supplier='" + supplier + '\'' +
                ", specificationsAndModels='" + specificationsAndModels + '\'' +
                ", accountType='" + accountType + '\'' +
                ", useUnit='" + useUnit + '\'' +
                ", useDate='" + useDate + '\'' +
                ", useDepartmentId='" + useDepartmentId + '\'' +
                ", manufacturers='" + manufacturers + '\'' +
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
                ", createUser='" + createUser + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                '}';
    }
}
