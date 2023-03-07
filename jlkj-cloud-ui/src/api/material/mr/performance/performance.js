import request from "@/utils/request";
//原料检斤计划列表查询
export const listMaterialsWeighingPerformance = (page, query) => {
  return request({
    url: '/material/listMaterialsWeighingPerformance',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
