package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 出差会计科目对象 human_hd_trip_account_rule
 *
 * @author 266861
 * @date 2023-04-23
 */
@Data
public class TripAccountRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 会计类别 */
    @Excel(name = "会计类别")
    private String acctType;

    /** 费用类别 */
    @Excel(name = "费用类别")
    private String apprGroup;

    /** 类别说明 */
    @Excel(name = "类别说明")
    private String acctDesc;

    /** 类别分类 */
    @Excel(name = "类别分类")
    private String acctClassify;

    /** 借方会计科目 */
    @Excel(name = "借方会计科目")
    private String acctCoded;

    /** 借方会计科目中文名称*/
    private String acctNamed;

    /** 借方户号 */
    @Excel(name = "借方户号")
    private String idCoded;

    /** 借方参号 */
    @Excel(name = "借方参号")
    private String refNod;

    /** 借方到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借方到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDated;

    /** 贷方会计科目 */
    @Excel(name = "贷方会计科目")
    private String acctCodec;

    /** 贷方会计科目中文名称*/
    private String acctNamec;

    /** 贷方户号 */
    @Excel(name = "贷方户号")
    private String idCodec;

    /** 贷方参号 */
    @Excel(name = "贷方参号")
    private String refNoc;

    /** 贷方到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "贷方到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDatec;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

}
