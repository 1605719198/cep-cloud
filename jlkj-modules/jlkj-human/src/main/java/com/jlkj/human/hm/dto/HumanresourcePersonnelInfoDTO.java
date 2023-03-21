package com.jlkj.human.hm.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 人事管理-人员登录数据
 * @TableName human_hm_personnel
 */
@TableName(value ="human_hm_personnel_info")
@Data
public class HumanresourcePersonnelInfoDTO implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 公司
     */
    private String compId;

    /**
     * 工号
     */
    private String empNo;

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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joinWorkDate;

    /**
     * 入企日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date entryDate;

    /**
     * 工龄起始日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date workStartDate;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date firstRecordGraduationTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastRecordGraduationTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date firstForeignLanguageAcquisitionTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date secondForeignLanguageAcquisitionTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date computerTypeAcquisitionTime;

    /**
     * 特长
     */
    private String specialty;

    /**
     * 办公地点
     */
    private String officeAddress;

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
     * 是否解除合同
     */
    private String ifTerminateContract;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date archivesTransferDate;

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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 是否在职
     */
    private String status;

    /**
     * 是否居民
     */
    private String ifResidents;

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
     * 部门id
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 岗位id
     */
    private String postId;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 职位等级ID
     */
    private String postLevelId;

    /**
     * 职位等级名称
     */
    private String postLevelName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
     * 出生日期2
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthTwoDate;

    /**
     * 签章图片
     */
    private String signature;


    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}