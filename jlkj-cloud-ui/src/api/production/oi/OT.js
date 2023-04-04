//出炉实绩测温纪录
import request from "@/utils/request";
//查
export const sel = (params) => {
    return request({
        url:'/product/plan/getProductionOutputPerformanceTemperaturePage',
        method:'get',
        params: {
            ...params
        },
    });
}
