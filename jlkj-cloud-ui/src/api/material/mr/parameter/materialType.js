import request from "@/utils/request";
//物料类别列表查询
export const listMaterialsCategory = (page, query) => {
  return request({
    url: '/material/parameter/listMaterialsCategory',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//查询-列表-物料类别-下拉
export const listMaterialsCategoryBox = (params) => {
  return request({
    url: '/material/parameter/listMaterialsCategoryBox',
    method: 'get',
    params
  })
};
//新增
export const saveMaterialsCategory = (params) => {
  return request({
    url: '/material/parameter/saveMaterialsCategory',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const updateMaterialsCategory = (data) => {
  return request({
    url: '/material/parameter/updateMaterialsCategory',
    method: 'put',
    data: data
  })
};
//删除
export const deleteMaterialsCategory = (ids) => {
  return request({
    url: '/material/parameter/deleteMaterialsCategory',
    method: 'delete',
    data: ids
  })
};
