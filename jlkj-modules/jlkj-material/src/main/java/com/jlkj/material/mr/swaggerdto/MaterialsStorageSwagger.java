package com.jlkj.material.mr.swaggerdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zyf
 */
@Data
public class MaterialsStorageSwagger implements Serializable {

@Schema(title = "主键标识;UUID")
private String id;

@Schema(title = "计划单号")
@JsonProperty("plan_list_no")
private String planListNo;

@Schema(title = "储位代码")
@JsonProperty("stg_no")
private String stgNo;

@Schema(title = "储位说明")
@JsonProperty("stg_name")
private String stgName;

@Schema(title = "组批号")
@JsonProperty("chk_no")
private String chkNo;

@Schema(title = "传送类型")
@JsonProperty("trans_type")
private String transType;

@Schema(title = "运输方式")
@JsonProperty("carrier_type")
private String carrierType;

@Schema(title = "到货日期")
@JsonProperty("recv_date")
private String recvDate;

@Schema(title = "编组号")
@JsonProperty("lot_no")
private String lotNo;

@Schema(title = "车号")
@JsonProperty("carrier_no")
private String carrierNo;

@Schema(title = "序号")
@JsonProperty("seq_no")
private String seqNo;

@Schema(title = "料号")
@JsonProperty("mtrl_no")
private String mtrlNo;

@Schema(title = "料号说明")
@JsonProperty("mtrl_name")
private String mtrlName;

@Schema(title = "来源代码")
@JsonProperty("from_code")
private String fromCode;

@Schema(title = "来源说明")
@JsonProperty("from_desc")
private String fromDesc;

@Schema(title = "去向代码")
@JsonProperty("to_code")
private String toCode;

@Schema(title = "去向说明")
@JsonProperty("to_desc")
private String toDesc;

@Schema(title = "采购合同号")
@JsonProperty("pchr_no")
private String pchrNo;

@Schema(title = "是否过磅")
@JsonProperty("is_load")
private String isLoad;

@Schema(title = "过磅点")
@JsonProperty("load_station")
private String loadStation;

@Schema(title = "申请单位")
@JsonProperty("issue_dept")
private String issueDept;

@Schema(title = "申请人")
@JsonProperty("issue_emp_no")
private String issueEmpNo;

@Schema(title = "申请日期")
@JsonProperty("issue_date")
private String issueDate;

@Schema(title = "是否回皮")
@JsonProperty("is_back")
private String isBack;

@Schema(title = "磅单号")
@JsonProperty("wgt_list_no")
private String wgtListNo;

@Schema(title = "毛重")
@JsonProperty("gross_wgt")
private String grossWgt;

@Schema(title = "皮重")
@JsonProperty("tart_wgt")
private String tartWgt;

@Schema(title = "净重")
@JsonProperty("net_wgt")
private String netWgt;

@Schema(title = "检斤实绩时间")
@JsonProperty("load_date_time")
private String loadDateTime;

@Schema(title = "创建时间")
@JsonProperty("create_time")
private String createTime;

}