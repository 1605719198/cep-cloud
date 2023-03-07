import request from "@/utils/request";


// 焦炭外售发货计划 查询
export const listMaterialsCokeSaleDeliverPlan = (page, query) => {
  return request({
    url: '/material/cokesaledeliverplan/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
