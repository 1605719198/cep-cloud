//出炉实绩测温纪录
import request from "@/utils/request";
//查
export const sel = (params) => {
    return request({
        url:'/api/neplatform/production/plan/getProductionOutputPerformanceTemperaturePage',
        method:'get',
        params: {
            ...params
        },
    });
}
