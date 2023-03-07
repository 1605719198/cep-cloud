import request from "@/utils/request";
export const getproductionHandoverStockVerify = (page, query) => {
    return request({
        url: "/api/neplatform/production/productionHandoverStockVerify/list",
        method: "get",
        params: {
            ...page,
            ...query,
        },
    });
};
export const updateproductionHandoverStockVerify = (data) => {
    return request({
        url: "/api/neplatform/production/productionHandoverStockVerify/update",
        method: "post",
        data: data,

    })
};
