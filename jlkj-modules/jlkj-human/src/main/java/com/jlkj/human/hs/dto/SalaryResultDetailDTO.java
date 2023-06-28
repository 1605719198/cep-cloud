package com.jlkj.human.hs.dto;

import lombok.Data;

@Data
public class SalaryResultDetailDTO {

    /** 主键 */
    private String id;

    /** 项目名称 */
    private String payTableName;

    /** 项目数值 */
    private String result;

    /** 项目类别 */
    private String type;

    /** 备注说明 */
    private String comm;

}
