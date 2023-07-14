/**
 * 推焦计划Api
 */

import request from "@/utils/request";

/**
 * 查询
 */
export const getProductionOutputPerformancePlanPage = (page, query) => {
    return request({
        url: "/product/productionoutputperformanceplan/getProductionOutputPerformancePlanPage",
        method: "get",
        params: {
            ...page,
            ...query,
        },
    });
};
