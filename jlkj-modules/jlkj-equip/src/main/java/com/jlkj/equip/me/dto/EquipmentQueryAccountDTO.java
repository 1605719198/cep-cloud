package com.jlkj.equip.me.dto;
/**
 * @author xin
 */
public class EquipmentQueryAccountDTO {
    /**
     * 当前页
     */
    private Integer page;
    /**
     * 每页记录数
     */
    private Integer limit;
    /**
     * 设备位置
     */
    private String equipmentLocation;
    /**
     * 设备类型编号
     */
    private String equipmentTypeLabel;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 设备编码
     */
    private String equipmentNumbert;
    /**
     * 第一责任人编号
     */
    private String firstResponsiblePerson;
    /**
     * 台账类型
     */
    private String accountType;
    /**
     * 制造厂商
     */
    private String manufacturers;
    /**
     * 维护人员
     */
    private String maintenanceUser;

    public EquipmentQueryAccountDTO() {
    }

    public EquipmentQueryAccountDTO(Integer page, Integer limit, String equipmentLocation, String equipmentTypeLabel, String equipmentName, String equipmentNumbert, String firstResponsiblePerson, String accountType, String manufacturers, String maintenanceUser) {
        this.page = page;
        this.limit = limit;
        this.equipmentLocation = equipmentLocation;
        this.equipmentTypeLabel = equipmentTypeLabel;
        this.equipmentName = equipmentName;
        this.equipmentNumbert = equipmentNumbert;
        this.firstResponsiblePerson = firstResponsiblePerson;
        this.accountType = accountType;
        this.manufacturers = manufacturers;
        this.maintenanceUser = maintenanceUser;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(String equipmentLocation) {
        this.equipmentLocation = equipmentLocation;
    }

    public String getEquipmentTypeLabel() {
        return equipmentTypeLabel;
    }

    public void setEquipmentTypeLabel(String equipmentTypeLabel) {
        this.equipmentTypeLabel = equipmentTypeLabel;
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

    public String getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(String manufacturers) {
        this.manufacturers = manufacturers;
    }

    public String getMaintenanceUser() {
        return maintenanceUser;
    }

    public void setMaintenanceUser(String maintenanceUser) {
        this.maintenanceUser = maintenanceUser;
    }

    @Override
    public String toString() {
        return "EquipmentQueryAccountDTO{" +
                "page=" + page +
                ", limit=" + limit +
                ", equipmentLocation='" + equipmentLocation + '\'' +
                ", equipmentTypeLabel='" + equipmentTypeLabel + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentNumbert='" + equipmentNumbert + '\'' +
                ", firstResponsiblePerson='" + firstResponsiblePerson + '\'' +
                ", accountType='" + accountType + '\'' +
                ", manufacturers='" + manufacturers + '\'' +
                ", maintenanceUser='" + maintenanceUser + '\'' +
                '}';
    }
}
