package com.jlkj.human.hd.dto;

import lombok.Data;

/**
 * 出差地点
 * @author 266861
 * @date 2023-04-24
 */

@Data
public class TripAddressDTO {
    /**
     * 公司别
     */
    private String compId;

    /**
     * 地址
     */
    private String dicName;

    /**
     * 拼音缩写
     */
    private String dicNo;

    /**
     *地点属性
     */
    private String type;
}
