import request from "@/utils/request";
//储位维护列表查询
export const listStorageSpaces = (page, query) => {
  return request({
    url: '/material/parameter/listStorageSpaces',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};

//下拉-列表-储位
export const listMaterialsStorageSpacesBox = (params) => {
  return request({
    url: '/material/parameter/listMaterialsStorageSpacesBox',
    method: 'get',
    params
  })
};
//新增
export const saveStorageSpaces = (params) => {
  return request({
    url: '/material/parameter/saveStorageSpaces',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const updateStorageSpaces = (data) => {
  return request({
    url: '/material/parameter/updateStorageSpaces',
    method: 'put',
    data: data
  })
};
//删除
export const deleteSilos = (ids) => {
  return request({
    url: '/material/parameter/delStorageSpaces',
    method: 'delete',
    data: ids
  })
};
