/**
 * 接焦实绩Api
 */

import request from "@/utils/request";

/**
 * 查询
 */
export const getProductionCollectionCdqJarList = (page, query) => {
    return request({
        url: "/product/productionCollectionCdqJar/list",
        method: "get",
        params: {
            ...page,
            ...query,
        },
    });
};
