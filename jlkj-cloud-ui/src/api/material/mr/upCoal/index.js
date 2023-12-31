import request from "@/utils/request";
// 物料卸货实绩查询
export const listMaterialLoadingPerformance = (page, query) => {
  return request({
    url: '/product/performance/listMaterialLoadingPerformance',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};

