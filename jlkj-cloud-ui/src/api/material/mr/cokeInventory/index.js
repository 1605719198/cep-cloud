import request from "@/utils/request";
// 查询物料焦场库存
export const materialsCokeStockList = (page, query) => {
  return request({
    url: '/material/stock/materialsCokeStock/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//调整
export const materialsCokeStockAdjust = (params) => {
  return request({
    url: '/material/stock/materialsCokeStock/adjust',
    method: 'post',
    data: { ...params }
  })
};
//查询物料焦场库存调整记录
export const materialsCokeStockHistoryList = (page, id) => {
  return request({
    url: '/material/stock/materialsCokeStock/materialsCokeStockHistoryList',
    method: 'get',
    params: {
      ...page,
      id,
    }
  })
};
//新增
export const saveMaterials = (params) => {
  return request({
    url: '/material/stock/materialsCokeStock/addMaterialsCokeStock',
    method: 'post',
    data: { ...params }
  })
};

// 查询物料焦场库存 - 详情 - 焦炭入库
export const getCokePutStorageList = (page, query) => {
  return request({
    url: '/material/stock/materialsCokeStock/getCokePutStorageList',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
// 查询物料焦场库存 - 详情 - 焦炭出库
export const getCokeOutStorageList = (page, query) => {
  return request({
    url: '/material/stock/materialsCokeStock/getCokeOutStorageList',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
