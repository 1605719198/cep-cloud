//焦炭产量纪录
import request from "@/utils/request";
//查
export const sel = (params) => {
    return request({
        url:'/product/cokeRecords/getProductionCokeRecords',
        method:'get',
        params: {
            ...params
        },
    });
}
