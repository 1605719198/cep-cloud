/**
 * 焦炉系数记录Api
 */

import request from "@/utils/request";

/*
  获取焦炉系数数据列表
 */
export const getProductionCoefficientRecord = (params) => {
    return request({
        url: "/product/coefficientRecord/getProductionCoefficientRecord",
        method: "get",
        params
    });
}
