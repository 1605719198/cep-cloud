package com.jlkj.human.hm.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 人员数据中间表
 * @TableName human_hm_personnel_temporary
 */
@TableName(value ="human_hm_personnel_temporary")
@Data
public class HumanresourcePersonnelTemporary implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    private String id;

    /**
     * 公司
     */
    private String compId;

    /**
     * 证件号码
     */
    private String certificateNumber;

    /**
     * 姓名
     */
    private String fullName;

    /**
     * 性别ID
     */
    private String genderId;

    /**
     * 出生日期
     */
    private String birthDate;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}