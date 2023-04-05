package com.jlkj.product.oi.vo.productioncokeovens;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-每日推焦炉数维护-账务统计
 *
 * @author sudeyou
 * @since 2022-11-01 10:10:47
 */
@Data
@Schema(description = "VO类-每日推焦炉数维护-账务统计")
public class ListProductionCokeOvensAccountVO {

    /**
     * 资料产生时间
     */
    @Schema(description = "资料产生时间")
    private String castDate;

    /**
     * 产量全焦（一级）
     */
    @Schema(description = "产量全焦（一级）")
    private BigDecimal totalCoOutputA;

    /**
     * 产量冶金焦（一级）
     */
    @Schema(description = "产量冶金焦（一级）")
    private BigDecimal ferroCoOutputA;

    /**
     * 产量全焦（准一级）
     */
    @Schema(description = "产量全焦（准一级）")
    private BigDecimal totalCoOutputB;

    /**
     * 产量冶金焦（准一级）
     */
    @Schema(description = "产量冶金焦（准一级）")
    private BigDecimal ferroCoOutputB;

    /**
     * 产量全焦（二级）
     */
    @Schema(description = "产量全焦（二级）")
    private BigDecimal totalCoOutputC;

    /**
     * 产量冶金焦（二级）
     */
    @Schema(description = "产量冶金焦（二级）")
    private BigDecimal ferroCoOutputC;

    /**
     * 产量焦丁
     */
    @Schema(description = "产量焦丁")
    private BigDecimal nutCoOutput;

    /**
     * 产量焦粉
     */
    @Schema(description = "产量焦粉")
    private BigDecimal coFinesOutput;

    /**
     * 库存量全焦（一级）
     */
    @Schema(description = "库存量全焦（一级）")
    private BigDecimal totalCoStoreA;

    /**
     * 库存量冶金焦（一级）
     */
    @Schema(description = "库存量冶金焦（一级）")
    private BigDecimal ferroCoStoreA;

    /**
     * 库存量全焦（准一级）
     */
    @Schema(description = "库存量全焦（准一级）")
    private BigDecimal totalCoStoreB;

    /**
     * 库存量冶金焦（准一级）
     */
    @Schema(description = "库存量冶金焦（准一级）")
    private BigDecimal ferroCoStoreB;

    /**
     * 库存量全焦（二级）
     */
    @Schema(description = "库存量全焦（二级）")
    private BigDecimal totalCoStoreC;

    /**
     * 库存量冶金焦（二级）
     */
    @Schema(description = "库存量冶金焦（二级）")
    private BigDecimal ferroCoStoreC;

    /**
     * 库存量焦丁
     */
    @Schema(description = "库存量焦丁")
    private BigDecimal nutCoStore;

    /**
     * 库存量焦粉
     */
    @Schema(description = "库存量焦粉")
    private BigDecimal coFinesStore;

    /**
     * 库存量水捞焦粉
     */
    @Schema(description = "库存量水捞焦粉")
    private BigDecimal waterCoFinesStore;

    /**
     * 库存量脱硫石膏
     */
    @Schema(description = "库存量脱硫石膏")
    private BigDecimal sulgaoStore;

    /**
     * 计划出炉数
     */
    @Schema(description = "计划出炉数")
    private BigDecimal planStoveNumber;

    /**
     * 实际出炉数
     */
    @Schema(description = "实际出炉数")
    private BigDecimal realStoveNumber;

    /**
     * 干熄炉数（一级）
     */
    @Schema(description = "干熄炉数（一级）")
    private BigDecimal dryStoveNumberA;

    /**
     * 湿熄炉数（一级）
     */
    @Schema(description = "湿熄炉数（一级）")
    private BigDecimal moistStoveNumberA;

    /**
     * 干熄炉数（准一级）
     */
    @Schema(description = "干熄炉数（准一级）")
    private BigDecimal dryStoveNumberB;

    /**
     * 湿熄炉数（准一级）
     */
    @Schema(description = "湿熄炉数（准一级）")
    private BigDecimal moistStoveNumberB;

    /**
     * 干熄炉数（二级）
     */
    @Schema(description = "干熄炉数（二级）")
    private BigDecimal dryStoveNumberC;

    /**
     * 湿熄炉数（二级）
     */
    @Schema(description = "湿熄炉数（二级）")
    private BigDecimal moistStoveNumberC;
}

