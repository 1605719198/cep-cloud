import request from "@/utils/request";
//原料检斤计划列表查询
export const listMaterialsWeighingPlan = (page, query) => {
  return request({
    url: '/material/listMaterialsWeighingPlan',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
