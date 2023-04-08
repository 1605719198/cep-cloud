package com.jlkj.material.mr.dto.materialscoalstock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author zyf
 * @Description
 * @create 2022-09-20 16:41
 */
@Data
public class AddMaterialsCoalStockDTO {

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("materials_id")
    private String materialId;

    @JsonProperty("materials_name")
    private String materialName;

    /**
     * 小煤种代码
     */
    @NotNull(message = "小煤种为必填项")
    @NotEmpty(message = "小煤种不能为空")
    @JsonProperty("materials_small_code")
    private String materialsSmallCode;

    @JsonProperty("materials_small_name")
    private String materialsSmallName;

    @JsonProperty("storage_spaces_id")
    private String storageSpaceId;

    @JsonProperty("storage_spaces_name")
    private String storageSpaceName;

    @JsonProperty("inventory")
    private BigDecimal inventory;

    /**
     * 创建人id
     */
    @NotNull(message = "创建人id为必填项")
    @NotEmpty(message = "创建人id不能为空")
    @Size(max = 36, message = "创建人id长度不能超过36")
    @JsonProperty("modify_user_id")
    private String modifyUserId;

    /**
     * 创建人
     */
    @NotNull(message = "创建人为必填项")
    @NotEmpty(message = "创建人不能为空")
    @Size(max = 50, message = "创建人长度不能超过50")
    @JsonProperty("modify_user_name")
    private String modifyUserName;
}
