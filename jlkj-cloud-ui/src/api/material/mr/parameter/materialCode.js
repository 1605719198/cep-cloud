import request from "@/utils/request";
//物料代码列表查询
export const listMaterials = (page, query) => {
  return request({
    url: '/material/parameter/listMaterials',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//查询-列表-物料代码-下拉
export const listMaterialsBox = (params) => {
  return request({
    url: '/material/parameter/listMaterialsBox',
    method: 'get',
    params
  })
};
//查询-列表-物料代码-指定物料类别id
export const listMaterialsBoxByCataId = (params) => {
  return request({
    url: '/material/parameter/listMaterialsBoxByCataId',
    method: 'get',
    params
  })
};
//查询-列表-物料代码-下拉（煤）
export const listMaterialsBoxM = (params) => {
  return request({
    url: '/material/parameter/listMaterialsBoxM',
    method: 'get',
    params
  })
};
//查询-列表-物料代码-下拉（焦）
export const listMaterialsBoxJ = (params) => {
  return request({
    url: '/material/parameter/listMaterialsBoxJ',
    method: 'get',
    params
  })
};
//新增
export const saveMaterials = (params) => {
  return request({
    url: '/material/parameter/saveMaterials',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const updateMaterials = (data) => {
  return request({
    url: '/material/parameter/updateMaterials',
    method: 'put',
    data: data
  })
};
//删除
export const delMaterialsCode = (ids) => {
  return request({
    url: '/material/parameter/delMaterialsCode',
    method: 'delete',
    data: ids
  })
};



//查询物料小煤种
export const listSmallMaterials = (page, query) => {
  return request({
    url: '/material/materialsCodeSmall/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//修改物料小煤种
export const updateSmallMaterials = (data) => {
  return request({
    url: '/material/materialsCodeSmall/update',
    method: 'post',
    data: data
  })
};

//查询-列表-物料小煤种-下拉
export const getMaterialsCodeSmallDic = (params) => {
  return request({
    url: '/material/materialsCodeSmall/getMaterialsCodeSmallDic',
    method: 'get',
    params
  })
};


