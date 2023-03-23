package com.jlkj.system.vo.humanresourcepersonnel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-获取人员签章
 *
 * @author sudeyou
 * @since 2022-10-19 10:38:36
 */
@Data
@Schema(description = "VO类-获取人员签章")
public class InfoHumanresourcePersonnelSignatureVO {
    /**
     * 签章图片
     */
    @Schema(description = "签章图片")
    @JsonProperty("signature")
    private String signature;

}

