import request from "@/utils/request";

// 查询-列表-料仓区域维护
export const listMaterialsSiloArea = (params) => {
  return request({
    url: '/material/storageSpaces/listMaterialsSiloArea',
    method: 'get',
    params: params
  })
};
// 查询-分页-料仓区域历史
export const getPageMaterialsSiloAreaHistory = (params) => {
  return request({
    url: '/material/materialssiloareahistory/getPageMaterialsSiloAreaHistory',
    method: 'get',
    params: params
  })
};

//保存-料仓区域维护
export const saveMaterialsSiloArea = (data) => {
  return request({
    url: '/material/storageSpaces/saveMaterialsSiloArea',
    method: 'put',
    data: data
  })
};
//删除-料仓区域维护
export const deleteMaterialsSiloArea = (data) => {
  return request({
    url: '/material/storageSpaces/deleteMaterialsSiloArea',
    method: 'delete',
    data: data
  })
};

// 查询-列表-料仓-下拉
export const listSilosBox = (params) => {
  return request({
    url: '/material/parameter/listSilosBox',
    method: 'get',
    params: params
  })
};

// 查询-列表-储位
export const listStorageSpacesBySiloId = (params) => {
  return request({
    url: '/material/parameter/listStorageSpacesBySiloId',
    method: 'get',
    params: params
  })
};









