import request from "@/utils/request";
// 物料卸货实绩查询
export const listMaterialUnloadingPerformance = (page, query) => {
  return request({
    url: '/production/performance/listMaterialUnloadingPerformance',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//物料卸货实绩修改
export const updateMaterialUnloadingPerformance = (params) => {
  return request({
    url: '/production/performance/updateMaterialUnloadingPerformance',
    method: 'post',
    data: { ...params }
  })
};
