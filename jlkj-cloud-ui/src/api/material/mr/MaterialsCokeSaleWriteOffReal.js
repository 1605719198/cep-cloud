import request from "@/utils/request";

// 焦炭外售销账实际 查询
export const listMaterialsCokeSaleWriteOffReal = (page, query) => {
  return request({
    url: '/material/cokesalewriteoffreal/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
