import request from "@/utils/request";
//入储列表查询
export const listMaterialsStorage = (page, query) => {
  return request({
    url: '/material/listMaterialsStorage',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
