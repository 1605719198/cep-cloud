package com.jlkj.human.hs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 社保公积金核定导入DTO
 *
 * @author 266861
 * @date 2023-06-02
 */
@Data
public class SocialSecurityBasisDTO {


    /**
     * 员工工号
     */
    @Excel(name = "工号*")
    private String empNo;

    /**
     * 员工姓名
     */
    @Excel(name = "姓名")
    private String empName;

    /**
     * 社保核定原因编码
     */
    @Excel(name = "社保核定原因编码*")
    private String insureReaCode;

    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期*(例：2023-01-01)", width = 30)
    private Date effectDate;

    /**
     * 社保编号
     */
    @Excel(name = "社保编号")
    private String insureNo;

    /**
     * 医保编号
     */
    @Excel(name = "医保编号")
    private String medicalInsuranceNo;

    /**
     * 公司保险手册编号
     */
    @Excel(name = "公司保险手册编号")
    private String compInsureBookNo;

    /**
     * 住房公积金账号
     */
    @Excel(name = "住房公积金账号")
    private String houseAccount;

    /**
     * 社保缴费地类别编码
     */
    @Excel(name = "社保缴费地类别编码")
    private String payAreaTypeCode;

    /**
     * 社保项目1编码
     */
    @Excel(name = "社保项目1编码（如个人住房公积金项目编码）")
    private String payProCode1;

    /**
     * 社保项目1是否缴纳
     */
    @Excel(name = "社保项目1是否缴纳（如个人住房公积金项目）")
    private String thrIdSta1;

    /**
     * 社保项目1缴费基数
     */
    @Excel(name = "社保项目1缴费基数（如个人住房公积金）")
    private String fivSta1;

    /**
     * 社保项目1缴费地
     */
    @Excel(name = "社保项目1缴费地（如个人住房公积金--不填默认为公司所在地区缴纳）")
    private String sevIdSta1;

    /**
     * 社保项目2编码
     */
    @Excel(name = "社保项目2编码（如个人养老保险项目编码）")
    private String payProCode2;

    /**
     * 社保项目2是否缴纳
     */
    @Excel(name = "社保项目2是否缴纳（如个人养老保险项目）")
    private String thrIdSta2;

    /**
     * 社保项目2缴费基数
     */
    @Excel(name = "社保项目2缴费基数（如个人养老保险）")
    private String fivSta2;

    /**
     * 社保项目2缴费地
     */
    @Excel(name = "社保项目2缴费地（如个人养老保险--不填默认为公司所在地区缴纳）")
    private String sevIdSta2;

    /**
     * 社保项目3编码
     */
    @Excel(name = "社保项目3编码（如个人医疗保险项目编码）")
    private String payProCode3;

    /**
     * 社保项目3是否缴纳
     */
    @Excel(name = "社保项目3是否缴纳（如个人医疗保险项目）")
    private String thrIdSta3;

    /**
     * 社保项目3缴费基数
     */
    @Excel(name = "社保项目3缴费基数（如个人医疗保险）")
    private String fivSta3;

    /**
     * 社保项目3缴费地
     */
    @Excel(name = "社保项目3缴费地（如个人医疗保险--不填默认为公司所在地区缴纳）")
    private String sevIdSta3;

    /**
     * 社保项目4编码
     */
    @Excel(name = "社保项目4编码（如个人失业保险项目编码）")
    private String payProCode4;

    /**
     * 社保项目4是否缴纳
     */
    @Excel(name = "社保项目4是否缴纳（如个人失业保险项目）")
    private String thrIdSta4;

    /**
     * 社保项目4缴费基数
     */
    @Excel(name = "社保项目4缴费基数（如个人失业保险）")
    private String fivSta4;

    /**
     * 社保项目4缴费地
     */
    @Excel(name = "社保项目4缴费地（如个人失业保险--不填默认为公司所在地区缴纳）")
    private String sevIdSta4;

    /**
     * 社保项目5编码
     */
    @Excel(name = "社保项目5编码（如个人工伤保险项目编码）")
    private String payProCode5;

    /**
     * 社保项目5是否缴纳
     */
    @Excel(name = "社保项目5是否缴纳（如个人工伤保险项目）")
    private String thrIdSta5;

    /**
     * 社保项目5缴费基数
     */
    @Excel(name = "社保项目5缴费基数（如个人工伤保险）")
    private String fivSta5;

    /**
     * 社保项目5缴费地
     */
    @Excel(name = "社保项目5缴费地（如个人工伤保险--不填默认为公司所在地区缴纳）")
    private String sevIdSta5;

    /**
     * 社保项目6编码
     */
    @Excel(name = "社保项目6编码")
    private String payProCode6;

    /**
     * 社保项目6是否缴纳
     */
    @Excel(name = "社保项目6是否缴纳")
    private String thrIdSta6;

    /**
     * 社保项目6缴费基数
     */
    @Excel(name = "社保项目6缴费基数")
    private String fivSta6;

    /**
     * 社保项目6缴费地
     */
    @Excel(name = "社保项目6缴费地")
    private String sevIdSta6;

    /**
     * 社保项目7编码
     */
    @Excel(name = "社保项目7编码")
    private String payProCode7;

    /**
     * 社保项目7是否缴纳
     */
    @Excel(name = "社保项目7是否缴纳")
    private String thrIdSta7;

    /**
     * 社保项目7缴费基数
     */
    @Excel(name = "社保项目7缴费基数")
    private String fivSta7;

    /**
     * 社保项目7缴费地
     */
    @Excel(name = "社保项目7缴费地")
    private String sevIdSta7;

}
