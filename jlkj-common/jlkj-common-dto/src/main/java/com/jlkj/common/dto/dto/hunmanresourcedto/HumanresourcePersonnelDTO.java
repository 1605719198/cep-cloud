package com.jlkj.common.dto.dto.hunmanresourcedto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * <p>
 * 人员数据
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@TableName("t_humanresource_personnel")
public class HumanresourcePersonnelDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 证件类型ID
     */
    private String certificateTypeId;

    /**
     * 证件号码
     */
    private String certificateNumber;

    /**
     * 姓名
     */
    private String fullName;

    /**
     * 姓名拼音
     */
    private String fullNamePinyin;

    /**
     * 曾用名
     */
    private String nameUsedBefore;

    /**
     * 英文名
     */
    private String englishName;

    /**
     * 照片地址
     */
    private String photoAddress;

    /**
     * 性别ID
     */
    private String genderId;

    /**
     * 出生日期
     */
    private String birthDate;

    /**
     * 生日选择
     */
    private String birthdayChoice;

    /**
     * 政治面貌ID
     */
    private String politicalOutlookId;

    /**
     * 民族ID
     */
    private String nationId;

    /**
     * 婚姻状况ID
     */
    private String maritalStatusId;

    /**
     * 血型ID
     */
    private String bloodTypeId;

    /**
     * 身高
     */
    private String height;

    /**
     * 体重
     */
    private String weight;

    /**
     * 国籍ID
     */
    private String nationalityId;

    /**
     * 出生地（省/市）ID
     */
    private String provinceOfBirthId;

    /**
     * 出生地（市/区）ID
     */
    private String cityOfBirthId;

    /**
     * 身体状况ID
     */
    private String healthId;

    /**
     * 是否有病史
     */
    private String ifMedicalHistory;

    /**
     * 病史说明
     */
    private String medicalHistoryDescription;

    /**
     * 宗教信仰ID
     */
    private String religiousBeliefId;

    /**
     * 户口性质ID
     */
    private String natureOfHouseholdRegistrationId;

    /**
     * 户口所在地（省/市）ID
     */
    private String registeredPermanentResidenceProvinceId;

    /**
     * 户口所在地（市/区）ID
     */
    private String registeredPermanentResidenceCityId;

    /**
     * 户口所在地邮编
     */
    private String registeredPermanentResidencePostalCode;

    /**
     * 籍贯ID
     */
    private String nativePlaceId;

    /**
     * 参加工作时间
     */
    private String joinWorkDate;

    /**
     * 入企日期
     */
    private String entryDate;

    /**
     * 工龄起始日
     */
    private String workStartDate;

    /**
     * 职工来源ID
     */
    private String sourceOfEmployees;

    /**
     * 第一学历ID
     */
    private String firstRecordId;

    /**
     * 第一学历毕业学校
     */
    private String firstRecordGraduateSchool;

    /**
     * 第一学历培养方式ID
     */
    private String firstRecordTrainingMethod;

    /**
     * 第一学历毕业时间
     */
    private String firstRecordGraduationTime;

    /**
     * 第一学位ID
     */
    private String firstDegreeId;

    /**
     * 第一学位专业ID
     */
    private String firstDegreeMajorId;

    /**
     * 第一学位专业细分ID
     */
    private String firstDegreeMajorSpecializationId;

    /**
     * 最高学历ID
     */
    private String lastRecordId;

    /**
     * 最高学历毕业学校
     */
    private String lastRecordGraduateSchool;

    /**
     * 最高学历培养方式ID
     */
    private String lastRecordTrainingMethod;

    /**
     * 最高学历毕业时间
     */
    private String lastRecordGraduationTime;

    /**
     * 最高学位ID
     */
    private String lastDegreeId;

    /**
     * 最高学位专业ID
     */
    private String lastDegreeMajorId;

    /**
     * 最高学位专业细分ID
     */
    private String lastDegreeMajorSpecializationId;

    /**
     * 第一外语语种ID
     */
    private String firstForeignLanguageId;

    /**
     * 第一外语掌握程度ID
     */
    private String firstForeignLanguageProficiencyId;

    /**
     * 第一外语等级证书
     */
    private String firstForeignLanguageLevelCertificate;

    /**
     * 第一外语取得时间
     */
    private String firstForeignLanguageAcquisitionTime;

    /**
     * 第二外语语种ID
     */
    private String secondForeignLanguageId;

    /**
     * 第二外语掌握程度ID
     */
    private String secondForeignLanguageProficiencyId;

    /**
     * 第二外语等级证书
     */
    private String secondForeignLanguageLevelCertificate;

    /**
     * 第二外语取得时间
     */
    private String secondForeignLanguageAcquisitionTime;

    /**
     * 计算机类型
     */
    private String computerType;

    /**
     * 计算机类型掌握程度
     */
    private String computerTypeMastery;

    /**
     * 计算机类型等级证书
     */
    private String computerTypeLevelCertificate;

    /**
     * 计算机类型取得时间
     */
    private String computerTypeAcquisitionTime;

    /**
     * 特长
     */
    private String specialty;

    /**
     * 本人手机
     */
    private String myMobilePhone;

    /**
     * 办公电话
     */
    private String officeTelephone;

    /**
     * 内线
     */
    private String insideLine;

    /**
     * 办公Email
     */
    private String officeEmail;

    /**
     * 未解除合同原因
     */
    private String reasonsForNotTerminatingTheContract;

    /**
     * 个人Email
     */
    private String personnelEmail;

    /**
     * 档案所在地
     */
    private String archivesLocation;

    /**
     * 档案转入时间
     */
    private String archivesTransferDate;

    /**
     * 档案编码
     */
    private String archivesNumber;

    /**
     * 与建龙员工亲属姓名
     */
    private String relativeFullName;

    /**
     * 亲属关系
     */
    private String relationship;

    /**
     * 亲属单位
     */
    private String relationDepartment;

    /**
     * 亲属联系电话
     */
    private String relationTelephone;

    /**
     * 推荐人姓名
     */
    private String recommenderName;

    /**
     * 推荐人与本人关系
     */
    private String recommenderRelationship;

    /**
     * 推荐人单位
     */
    private String recommenderDepartment;

    /**
     * 推荐人联系电话
     */
    private String recommenderTelephone;

    /**
     * 紧急联络人
     */
    private String emergencyContactPerson;

    /**
     * 紧急联络人与本人关系
     */
    private String emergencyContactPersonRelationship;

    /**
     * 紧急联络人联系电话
     */
    private String emergencyContactPersonTelephone;

    /**
     * 紧急联络人Email
     */
    private String emergencyContactPersonEmail;

    /**
     * 家庭住址
     */
    private String homeAddress;

    /**
     * 家庭邮政编码
     */
    private String homePostCode;

    /**
     * 家庭联系电话
     */
    private String homeTelephone;

    /**
     * 现住址
     */
    private String nowAddress;

    /**
     * 现住址邮政编码
     */
    private String nowAddressPostCode;

    /**
     * 现住址联系电话
     */
    private String nowAddressTelephone;

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
     * 公司ID
     */
    private String companyId;

    /**
     * 是否在职
     */
    private String status;

    /**
     * 备用字段1
     */
    private String alternateField1;

    /**
     * 备用字段2
     */
    private String alternateField2;

    /**
     * 备用字段3
     */
    private String alternateField3;

    /**
     * 备用字段4
     */
    private String alternateField4;

    /**
     * 备用字段5
     */
    private String alternateField5;

    /**
     * 备用字段6
     */
    private String alternateField6;

    /**
     * 备用字段7
     */
    private String alternateField7;

    /**
     * 备用字段8
     */
    private String alternateField8;

    /**
     * 备用字段9
     */
    private String alternateField9;

    /**
     * 备用字段10
     */
    private String alternateField10;

    /**
     * 备用字段11
     */
    private String alternateField11;

    /**
     * 备用字段12
     */
    private String alternateField12;

    /**
     * 备用字段13
     */
    private String alternateField13;

    /**
     * 备用字段14
     */
    private String alternateField14;

    /**
     * 备用字段15
     */
    private String alternateField15;

    /**
     * 备用字段16
     */
    private String alternateField16;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 岗位名称
     */
    private String postName;
    /**
     * 岗位名称
     */
    private String postId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
     * 是否解除合同
     */
    private String ifTerminateContract;
    /**
     * 出生日期2
     */
    private String birthTwoDate;

    public String getIfTerminateContract() {
        return ifTerminateContract;
    }

    public void setIfTerminateContract(String ifTerminateContract) {
        this.ifTerminateContract = ifTerminateContract;
    }

    public String getBirthTwoDate() {
        return birthTwoDate;
    }

    public void setBirthTwoDate(String birthTwoDate) {
        this.birthTwoDate = birthTwoDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(String certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }
    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getFullNamePinyin() {
        return fullNamePinyin;
    }

    public void setFullNamePinyin(String fullNamePinyin) {
        this.fullNamePinyin = fullNamePinyin;
    }
    public String getNameUsedBefore() {
        return nameUsedBefore;
    }

    public void setNameUsedBefore(String nameUsedBefore) {
        this.nameUsedBefore = nameUsedBefore;
    }
    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }
    public String getGenderId() {
        return genderId;
    }

    public void setGenderId(String genderId) {
        this.genderId = genderId;
    }
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public String getBirthdayChoice() {
        return birthdayChoice;
    }

    public void setBirthdayChoice(String birthdayChoice) {
        this.birthdayChoice = birthdayChoice;
    }
    public String getPoliticalOutlookId() {
        return politicalOutlookId;
    }

    public void setPoliticalOutlookId(String politicalOutlookId) {
        this.politicalOutlookId = politicalOutlookId;
    }
    public String getNationId() {
        return nationId;
    }

    public void setNationId(String nationId) {
        this.nationId = nationId;
    }
    public String getMaritalStatusId() {
        return maritalStatusId;
    }

    public void setMaritalStatusId(String maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }
    public String getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(String bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
    }
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }
    public String getProvinceOfBirthId() {
        return provinceOfBirthId;
    }

    public void setProvinceOfBirthId(String provinceOfBirthId) {
        this.provinceOfBirthId = provinceOfBirthId;
    }
    public String getCityOfBirthId() {
        return cityOfBirthId;
    }

    public void setCityOfBirthId(String cityOfBirthId) {
        this.cityOfBirthId = cityOfBirthId;
    }
    public String getHealthId() {
        return healthId;
    }

    public void setHealthId(String healthId) {
        this.healthId = healthId;
    }
    public String getIfMedicalHistory() {
        return ifMedicalHistory;
    }

    public void setIfMedicalHistory(String ifMedicalHistory) {
        this.ifMedicalHistory = ifMedicalHistory;
    }
    public String getMedicalHistoryDescription() {
        return medicalHistoryDescription;
    }

    public void setMedicalHistoryDescription(String medicalHistoryDescription) {
        this.medicalHistoryDescription = medicalHistoryDescription;
    }
    public String getReligiousBeliefId() {
        return religiousBeliefId;
    }

    public void setReligiousBeliefId(String religiousBeliefId) {
        this.religiousBeliefId = religiousBeliefId;
    }
    public String getNatureOfHouseholdRegistrationId() {
        return natureOfHouseholdRegistrationId;
    }

    public void setNatureOfHouseholdRegistrationId(String natureOfHouseholdRegistrationId) {
        this.natureOfHouseholdRegistrationId = natureOfHouseholdRegistrationId;
    }
    public String getRegisteredPermanentResidenceProvinceId() {
        return registeredPermanentResidenceProvinceId;
    }

    public void setRegisteredPermanentResidenceProvinceId(String registeredPermanentResidenceProvinceId) {
        this.registeredPermanentResidenceProvinceId = registeredPermanentResidenceProvinceId;
    }
    public String getRegisteredPermanentResidenceCityId() {
        return registeredPermanentResidenceCityId;
    }

    public void setRegisteredPermanentResidenceCityId(String registeredPermanentResidenceCityId) {
        this.registeredPermanentResidenceCityId = registeredPermanentResidenceCityId;
    }
    public String getRegisteredPermanentResidencePostalCode() {
        return registeredPermanentResidencePostalCode;
    }

    public void setRegisteredPermanentResidencePostalCode(String registeredPermanentResidencePostalCode) {
        this.registeredPermanentResidencePostalCode = registeredPermanentResidencePostalCode;
    }
    public String getNativePlaceId() {
        return nativePlaceId;
    }

    public void setNativePlaceId(String nativePlaceId) {
        this.nativePlaceId = nativePlaceId;
    }
    public String getJoinWorkDate() {
        return joinWorkDate;
    }

    public void setJoinWorkDate(String joinWorkDate) {
        this.joinWorkDate = joinWorkDate;
    }
    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
    public String getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(String workStartDate) {
        this.workStartDate = workStartDate;
    }
    public String getSourceOfEmployees() {
        return sourceOfEmployees;
    }

    public void setSourceOfEmployees(String sourceOfEmployees) {
        this.sourceOfEmployees = sourceOfEmployees;
    }
    public String getFirstRecordId() {
        return firstRecordId;
    }

    public void setFirstRecordId(String firstRecordId) {
        this.firstRecordId = firstRecordId;
    }
    public String getFirstRecordGraduateSchool() {
        return firstRecordGraduateSchool;
    }

    public void setFirstRecordGraduateSchool(String firstRecordGraduateSchool) {
        this.firstRecordGraduateSchool = firstRecordGraduateSchool;
    }
    public String getFirstRecordTrainingMethod() {
        return firstRecordTrainingMethod;
    }

    public void setFirstRecordTrainingMethod(String firstRecordTrainingMethod) {
        this.firstRecordTrainingMethod = firstRecordTrainingMethod;
    }
    public String getFirstRecordGraduationTime() {
        return firstRecordGraduationTime;
    }

    public void setFirstRecordGraduationTime(String firstRecordGraduationTime) {
        this.firstRecordGraduationTime = firstRecordGraduationTime;
    }
    public String getFirstDegreeId() {
        return firstDegreeId;
    }

    public void setFirstDegreeId(String firstDegreeId) {
        this.firstDegreeId = firstDegreeId;
    }
    public String getFirstDegreeMajorId() {
        return firstDegreeMajorId;
    }

    public void setFirstDegreeMajorId(String firstDegreeMajorId) {
        this.firstDegreeMajorId = firstDegreeMajorId;
    }
    public String getFirstDegreeMajorSpecializationId() {
        return firstDegreeMajorSpecializationId;
    }

    public void setFirstDegreeMajorSpecializationId(String firstDegreeMajorSpecializationId) {
        this.firstDegreeMajorSpecializationId = firstDegreeMajorSpecializationId;
    }
    public String getLastRecordId() {
        return lastRecordId;
    }

    public void setLastRecordId(String lastRecordId) {
        this.lastRecordId = lastRecordId;
    }
    public String getLastRecordGraduateSchool() {
        return lastRecordGraduateSchool;
    }

    public void setLastRecordGraduateSchool(String lastRecordGraduateSchool) {
        this.lastRecordGraduateSchool = lastRecordGraduateSchool;
    }
    public String getLastRecordTrainingMethod() {
        return lastRecordTrainingMethod;
    }

    public void setLastRecordTrainingMethod(String lastRecordTrainingMethod) {
        this.lastRecordTrainingMethod = lastRecordTrainingMethod;
    }
    public String getLastRecordGraduationTime() {
        return lastRecordGraduationTime;
    }

    public void setLastRecordGraduationTime(String lastRecordGraduationTime) {
        this.lastRecordGraduationTime = lastRecordGraduationTime;
    }
    public String getLastDegreeId() {
        return lastDegreeId;
    }

    public void setLastDegreeId(String lastDegreeId) {
        this.lastDegreeId = lastDegreeId;
    }
    public String getLastDegreeMajorId() {
        return lastDegreeMajorId;
    }

    public void setLastDegreeMajorId(String lastDegreeMajorId) {
        this.lastDegreeMajorId = lastDegreeMajorId;
    }
    public String getLastDegreeMajorSpecializationId() {
        return lastDegreeMajorSpecializationId;
    }

    public void setLastDegreeMajorSpecializationId(String lastDegreeMajorSpecializationId) {
        this.lastDegreeMajorSpecializationId = lastDegreeMajorSpecializationId;
    }
    public String getFirstForeignLanguageId() {
        return firstForeignLanguageId;
    }

    public void setFirstForeignLanguageId(String firstForeignLanguageId) {
        this.firstForeignLanguageId = firstForeignLanguageId;
    }
    public String getFirstForeignLanguageProficiencyId() {
        return firstForeignLanguageProficiencyId;
    }

    public void setFirstForeignLanguageProficiencyId(String firstForeignLanguageProficiencyId) {
        this.firstForeignLanguageProficiencyId = firstForeignLanguageProficiencyId;
    }
    public String getFirstForeignLanguageLevelCertificate() {
        return firstForeignLanguageLevelCertificate;
    }

    public void setFirstForeignLanguageLevelCertificate(String firstForeignLanguageLevelCertificate) {
        this.firstForeignLanguageLevelCertificate = firstForeignLanguageLevelCertificate;
    }
    public String getFirstForeignLanguageAcquisitionTime() {
        return firstForeignLanguageAcquisitionTime;
    }

    public void setFirstForeignLanguageAcquisitionTime(String firstForeignLanguageAcquisitionTime) {
        this.firstForeignLanguageAcquisitionTime = firstForeignLanguageAcquisitionTime;
    }
    public String getSecondForeignLanguageId() {
        return secondForeignLanguageId;
    }

    public void setSecondForeignLanguageId(String secondForeignLanguageId) {
        this.secondForeignLanguageId = secondForeignLanguageId;
    }
    public String getSecondForeignLanguageProficiencyId() {
        return secondForeignLanguageProficiencyId;
    }

    public void setSecondForeignLanguageProficiencyId(String secondForeignLanguageProficiencyId) {
        this.secondForeignLanguageProficiencyId = secondForeignLanguageProficiencyId;
    }
    public String getSecondForeignLanguageLevelCertificate() {
        return secondForeignLanguageLevelCertificate;
    }

    public void setSecondForeignLanguageLevelCertificate(String secondForeignLanguageLevelCertificate) {
        this.secondForeignLanguageLevelCertificate = secondForeignLanguageLevelCertificate;
    }
    public String getSecondForeignLanguageAcquisitionTime() {
        return secondForeignLanguageAcquisitionTime;
    }

    public void setSecondForeignLanguageAcquisitionTime(String secondForeignLanguageAcquisitionTime) {
        this.secondForeignLanguageAcquisitionTime = secondForeignLanguageAcquisitionTime;
    }
    public String getComputerType() {
        return computerType;
    }

    public void setComputerType(String computerType) {
        this.computerType = computerType;
    }
    public String getComputerTypeMastery() {
        return computerTypeMastery;
    }

    public void setComputerTypeMastery(String computerTypeMastery) {
        this.computerTypeMastery = computerTypeMastery;
    }
    public String getComputerTypeLevelCertificate() {
        return computerTypeLevelCertificate;
    }

    public void setComputerTypeLevelCertificate(String computerTypeLevelCertificate) {
        this.computerTypeLevelCertificate = computerTypeLevelCertificate;
    }
    public String getComputerTypeAcquisitionTime() {
        return computerTypeAcquisitionTime;
    }

    public void setComputerTypeAcquisitionTime(String computerTypeAcquisitionTime) {
        this.computerTypeAcquisitionTime = computerTypeAcquisitionTime;
    }
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    public String getMyMobilePhone() {
        return myMobilePhone;
    }

    public void setMyMobilePhone(String myMobilePhone) {
        this.myMobilePhone = myMobilePhone;
    }
    public String getOfficeTelephone() {
        return officeTelephone;
    }

    public void setOfficeTelephone(String officeTelephone) {
        this.officeTelephone = officeTelephone;
    }
    public String getInsideLine() {
        return insideLine;
    }

    public void setInsideLine(String insideLine) {
        this.insideLine = insideLine;
    }
    public String getOfficeEmail() {
        return officeEmail;
    }

    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }
    public String getReasonsForNotTerminatingTheContract() {
        return reasonsForNotTerminatingTheContract;
    }

    public void setReasonsForNotTerminatingTheContract(String reasonsForNotTerminatingTheContract) {
        this.reasonsForNotTerminatingTheContract = reasonsForNotTerminatingTheContract;
    }
    public String getPersonnelEmail() {
        return personnelEmail;
    }

    public void setPersonnelEmail(String personnelEmail) {
        this.personnelEmail = personnelEmail;
    }
    public String getArchivesLocation() {
        return archivesLocation;
    }

    public void setArchivesLocation(String archivesLocation) {
        this.archivesLocation = archivesLocation;
    }
    public String getArchivesTransferDate() {
        return archivesTransferDate;
    }

    public void setArchivesTransferDate(String archivesTransferDate) {
        this.archivesTransferDate = archivesTransferDate;
    }
    public String getArchivesNumber() {
        return archivesNumber;
    }

    public void setArchivesNumber(String archivesNumber) {
        this.archivesNumber = archivesNumber;
    }
    public String getRelativeFullName() {
        return relativeFullName;
    }

    public void setRelativeFullName(String relativeFullName) {
        this.relativeFullName = relativeFullName;
    }
    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    public String getRelationDepartment() {
        return relationDepartment;
    }

    public void setRelationDepartment(String relationDepartment) {
        this.relationDepartment = relationDepartment;
    }
    public String getRelationTelephone() {
        return relationTelephone;
    }

    public void setRelationTelephone(String relationTelephone) {
        this.relationTelephone = relationTelephone;
    }
    public String getRecommenderName() {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
    }
    public String getRecommenderRelationship() {
        return recommenderRelationship;
    }

    public void setRecommenderRelationship(String recommenderRelationship) {
        this.recommenderRelationship = recommenderRelationship;
    }
    public String getRecommenderDepartment() {
        return recommenderDepartment;
    }

    public void setRecommenderDepartment(String recommenderDepartment) {
        this.recommenderDepartment = recommenderDepartment;
    }
    public String getRecommenderTelephone() {
        return recommenderTelephone;
    }

    public void setRecommenderTelephone(String recommenderTelephone) {
        this.recommenderTelephone = recommenderTelephone;
    }
    public String getEmergencyContactPerson() {
        return emergencyContactPerson;
    }

    public void setEmergencyContactPerson(String emergencyContactPerson) {
        this.emergencyContactPerson = emergencyContactPerson;
    }
    public String getEmergencyContactPersonRelationship() {
        return emergencyContactPersonRelationship;
    }

    public void setEmergencyContactPersonRelationship(String emergencyContactPersonRelationship) {
        this.emergencyContactPersonRelationship = emergencyContactPersonRelationship;
    }
    public String getEmergencyContactPersonTelephone() {
        return emergencyContactPersonTelephone;
    }

    public void setEmergencyContactPersonTelephone(String emergencyContactPersonTelephone) {
        this.emergencyContactPersonTelephone = emergencyContactPersonTelephone;
    }
    public String getEmergencyContactPersonEmail() {
        return emergencyContactPersonEmail;
    }

    public void setEmergencyContactPersonEmail(String emergencyContactPersonEmail) {
        this.emergencyContactPersonEmail = emergencyContactPersonEmail;
    }
    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
    public String getHomePostCode() {
        return homePostCode;
    }

    public void setHomePostCode(String homePostCode) {
        this.homePostCode = homePostCode;
    }
    public String getHomeTelephone() {
        return homeTelephone;
    }

    public void setHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
    }
    public String getNowAddress() {
        return nowAddress;
    }

    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }
    public String getNowAddressPostCode() {
        return nowAddressPostCode;
    }

    public void setNowAddressPostCode(String nowAddressPostCode) {
        this.nowAddressPostCode = nowAddressPostCode;
    }
    public String getNowAddressTelephone() {
        return nowAddressTelephone;
    }

    public void setNowAddressTelephone(String nowAddressTelephone) {
        this.nowAddressTelephone = nowAddressTelephone;
    }
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getAlternateField1() {
        return alternateField1;
    }

    public void setAlternateField1(String alternateField1) {
        this.alternateField1 = alternateField1;
    }
    public String getAlternateField2() {
        return alternateField2;
    }

    public void setAlternateField2(String alternateField2) {
        this.alternateField2 = alternateField2;
    }
    public String getAlternateField3() {
        return alternateField3;
    }

    public void setAlternateField3(String alternateField3) {
        this.alternateField3 = alternateField3;
    }
    public String getAlternateField4() {
        return alternateField4;
    }

    public void setAlternateField4(String alternateField4) {
        this.alternateField4 = alternateField4;
    }
    public String getAlternateField5() {
        return alternateField5;
    }

    public void setAlternateField5(String alternateField5) {
        this.alternateField5 = alternateField5;
    }
    public String getAlternateField6() {
        return alternateField6;
    }

    public void setAlternateField6(String alternateField6) {
        this.alternateField6 = alternateField6;
    }
    public String getAlternateField7() {
        return alternateField7;
    }

    public void setAlternateField7(String alternateField7) {
        this.alternateField7 = alternateField7;
    }
    public String getAlternateField8() {
        return alternateField8;
    }

    public void setAlternateField8(String alternateField8) {
        this.alternateField8 = alternateField8;
    }
    public String getAlternateField9() {
        return alternateField9;
    }

    public void setAlternateField9(String alternateField9) {
        this.alternateField9 = alternateField9;
    }
    public String getAlternateField10() {
        return alternateField10;
    }

    public void setAlternateField10(String alternateField10) {
        this.alternateField10 = alternateField10;
    }
    public String getAlternateField11() {
        return alternateField11;
    }

    public void setAlternateField11(String alternateField11) {
        this.alternateField11 = alternateField11;
    }
    public String getAlternateField12() {
        return alternateField12;
    }

    public void setAlternateField12(String alternateField12) {
        this.alternateField12 = alternateField12;
    }
    public String getAlternateField13() {
        return alternateField13;
    }

    public void setAlternateField13(String alternateField13) {
        this.alternateField13 = alternateField13;
    }
    public String getAlternateField14() {
        return alternateField14;
    }

    public void setAlternateField14(String alternateField14) {
        this.alternateField14 = alternateField14;
    }
    public String getAlternateField15() {
        return alternateField15;
    }

    public void setAlternateField15(String alternateField15) {
        this.alternateField15 = alternateField15;
    }
    public String getAlternateField16() {
        return alternateField16;
    }

    public void setAlternateField16(String alternateField16) {
        this.alternateField16 = alternateField16;
    }
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "HumanresourcePersonnelDTO{" +
                "id='" + id + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", password='" + password + '\'' +
                ", certificateTypeId='" + certificateTypeId + '\'' +
                ", certificateNumber='" + certificateNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", fullNamePinyin='" + fullNamePinyin + '\'' +
                ", nameUsedBefore='" + nameUsedBefore + '\'' +
                ", englishName='" + englishName + '\'' +
                ", photoAddress='" + photoAddress + '\'' +
                ", genderId='" + genderId + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", birthdayChoice='" + birthdayChoice + '\'' +
                ", politicalOutlookId='" + politicalOutlookId + '\'' +
                ", nationId='" + nationId + '\'' +
                ", maritalStatusId='" + maritalStatusId + '\'' +
                ", bloodTypeId='" + bloodTypeId + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", nationalityId='" + nationalityId + '\'' +
                ", provinceOfBirthId='" + provinceOfBirthId + '\'' +
                ", cityOfBirthId='" + cityOfBirthId + '\'' +
                ", healthId='" + healthId + '\'' +
                ", ifMedicalHistory='" + ifMedicalHistory + '\'' +
                ", medicalHistoryDescription='" + medicalHistoryDescription + '\'' +
                ", religiousBeliefId='" + religiousBeliefId + '\'' +
                ", natureOfHouseholdRegistrationId='" + natureOfHouseholdRegistrationId + '\'' +
                ", registeredPermanentResidenceProvinceId='" + registeredPermanentResidenceProvinceId + '\'' +
                ", registeredPermanentResidenceCityId='" + registeredPermanentResidenceCityId + '\'' +
                ", registeredPermanentResidencePostalCode='" + registeredPermanentResidencePostalCode + '\'' +
                ", nativePlaceId='" + nativePlaceId + '\'' +
                ", joinWorkDate='" + joinWorkDate + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", workStartDate='" + workStartDate + '\'' +
                ", sourceOfEmployees='" + sourceOfEmployees + '\'' +
                ", firstRecordId='" + firstRecordId + '\'' +
                ", firstRecordGraduateSchool='" + firstRecordGraduateSchool + '\'' +
                ", firstRecordTrainingMethod='" + firstRecordTrainingMethod + '\'' +
                ", firstRecordGraduationTime='" + firstRecordGraduationTime + '\'' +
                ", firstDegreeId='" + firstDegreeId + '\'' +
                ", firstDegreeMajorId='" + firstDegreeMajorId + '\'' +
                ", firstDegreeMajorSpecializationId='" + firstDegreeMajorSpecializationId + '\'' +
                ", lastRecordId='" + lastRecordId + '\'' +
                ", lastRecordGraduateSchool='" + lastRecordGraduateSchool + '\'' +
                ", lastRecordTrainingMethod='" + lastRecordTrainingMethod + '\'' +
                ", lastRecordGraduationTime='" + lastRecordGraduationTime + '\'' +
                ", lastDegreeId='" + lastDegreeId + '\'' +
                ", lastDegreeMajorId='" + lastDegreeMajorId + '\'' +
                ", lastDegreeMajorSpecializationId='" + lastDegreeMajorSpecializationId + '\'' +
                ", firstForeignLanguageId='" + firstForeignLanguageId + '\'' +
                ", firstForeignLanguageProficiencyId='" + firstForeignLanguageProficiencyId + '\'' +
                ", firstForeignLanguageLevelCertificate='" + firstForeignLanguageLevelCertificate + '\'' +
                ", firstForeignLanguageAcquisitionTime='" + firstForeignLanguageAcquisitionTime + '\'' +
                ", secondForeignLanguageId='" + secondForeignLanguageId + '\'' +
                ", secondForeignLanguageProficiencyId='" + secondForeignLanguageProficiencyId + '\'' +
                ", secondForeignLanguageLevelCertificate='" + secondForeignLanguageLevelCertificate + '\'' +
                ", secondForeignLanguageAcquisitionTime='" + secondForeignLanguageAcquisitionTime + '\'' +
                ", computerType='" + computerType + '\'' +
                ", computerTypeMastery='" + computerTypeMastery + '\'' +
                ", computerTypeLevelCertificate='" + computerTypeLevelCertificate + '\'' +
                ", computerTypeAcquisitionTime='" + computerTypeAcquisitionTime + '\'' +
                ", specialty='" + specialty + '\'' +
                ", myMobilePhone='" + myMobilePhone + '\'' +
                ", officeTelephone='" + officeTelephone + '\'' +
                ", insideLine='" + insideLine + '\'' +
                ", officeEmail='" + officeEmail + '\'' +
                ", reasonsForNotTerminatingTheContract='" + reasonsForNotTerminatingTheContract + '\'' +
                ", personnelEmail='" + personnelEmail + '\'' +
                ", archivesLocation='" + archivesLocation + '\'' +
                ", archivesTransferDate='" + archivesTransferDate + '\'' +
                ", archivesNumber='" + archivesNumber + '\'' +
                ", relativeFullName='" + relativeFullName + '\'' +
                ", relationship='" + relationship + '\'' +
                ", relationDepartment='" + relationDepartment + '\'' +
                ", relationTelephone='" + relationTelephone + '\'' +
                ", recommenderName='" + recommenderName + '\'' +
                ", recommenderRelationship='" + recommenderRelationship + '\'' +
                ", recommenderDepartment='" + recommenderDepartment + '\'' +
                ", recommenderTelephone='" + recommenderTelephone + '\'' +
                ", emergencyContactPerson='" + emergencyContactPerson + '\'' +
                ", emergencyContactPersonRelationship='" + emergencyContactPersonRelationship + '\'' +
                ", emergencyContactPersonTelephone='" + emergencyContactPersonTelephone + '\'' +
                ", emergencyContactPersonEmail='" + emergencyContactPersonEmail + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", homePostCode='" + homePostCode + '\'' +
                ", homeTelephone='" + homeTelephone + '\'' +
                ", nowAddress='" + nowAddress + '\'' +
                ", nowAddressPostCode='" + nowAddressPostCode + '\'' +
                ", nowAddressTelephone='" + nowAddressTelephone + '\'' +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", companyId='" + companyId + '\'' +
                ", status='" + status + '\'' +
                ", alternateField1='" + alternateField1 + '\'' +
                ", alternateField2='" + alternateField2 + '\'' +
                ", alternateField3='" + alternateField3 + '\'' +
                ", alternateField4='" + alternateField4 + '\'' +
                ", alternateField5='" + alternateField5 + '\'' +
                ", alternateField6='" + alternateField6 + '\'' +
                ", alternateField7='" + alternateField7 + '\'' +
                ", alternateField8='" + alternateField8 + '\'' +
                ", alternateField9='" + alternateField9 + '\'' +
                ", alternateField10='" + alternateField10 + '\'' +
                ", alternateField11='" + alternateField11 + '\'' +
                ", alternateField12='" + alternateField12 + '\'' +
                ", alternateField13='" + alternateField13 + '\'' +
                ", alternateField14='" + alternateField14 + '\'' +
                ", alternateField15='" + alternateField15 + '\'' +
                ", alternateField16='" + alternateField16 + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", postName='" + postName + '\'' +
                ", postId='" + postId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", ifTerminateContract='" + ifTerminateContract + '\'' +
                ", birthTwoDate='" + birthTwoDate + '\'' +
                '}';
    }
}
