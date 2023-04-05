//生产异常台账
import request from "@/utils/request";

// 增加
export const addYa = (params) => {
    return request({
        url:'/product/actualPerformance/addProductionAbnormalLedger',
        method:'post',
        data: { ...params },
    });
}
//删除
export const delYa = (ids) => {
    return request({
        url:'/product/actualPerformance/delProductionAbnormalLedger',
        method:'post',
        data: ids
    });
}
//修改
export const upYa = (params) => {
    return request({
        url:'/product/actualPerformance/updateProductionAbnormalLedger',
        method:'post',
        data: { ...params },
    });
}
//查询
export const selYa = (params) => {
    return request({
        url:'/product/actualPerformance/getProductionAbnormalLedger',
        method:'get',
        params: {
            ...params
        },
    });
}
