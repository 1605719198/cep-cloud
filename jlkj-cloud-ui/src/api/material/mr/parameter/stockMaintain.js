import request from "@/utils/request";
//料仓列表查询
export const listSilos = (page, query) => {
  return request({
    url: '/material/parameter/listSilos',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};

//查询-列表-料仓-下拉
export const listSilosBox = (params) => {
  return request({
    url: '/material/parameter/listSilosBox',
    method: 'get',
    params
  })
};
//新增
export const saveSilo = (params) => {
  return request({
    url: '/material/parameter/saveSilo',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const updateSilo = (data) => {
  return request({
    url: '/material/parameter/updateSilo',
    method: 'put',
    data: data
  })
};
//删除
export const deleteSilos = (ids) => {
  return request({
    url: '/material/parameter/deleteSilos',
    method: 'delete',
    data: ids
  })
};
