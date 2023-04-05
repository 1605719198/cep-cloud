import request from "@/utils/request";
export const getproductionHandoverStockVerify = (page, query) => {
    return request({
        url: "/product/productionHandoverStockVerify/list",
        method: "get",
        params: {
            ...page,
            ...query,
        },
    });
};
export const updateproductionHandoverStockVerify = (data) => {
    return request({
        url: "/product/productionHandoverStockVerify/update",
        method: "post",
        data: data,

    })
};
