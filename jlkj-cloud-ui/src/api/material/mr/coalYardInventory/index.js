import request from "@/utils/request";
// 物料卸货实绩查询
export const materialsCoalStockList = (page, query) => {
  return request({
    url: '/material/stock/materialsCoalStock/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//调整
export const adjust = (params) => {
  return request({
    url: '/material/stock/materialsCoalStock/adjust',
    method: 'post',
    data: { ...params }
  })
};
//新增
export const addMaterialsCoalStock = (params) => {
  return request({
    url: '/material/stock/materialsCoalStock/addMaterialsCoalStock ',
    method: 'post',
    data: { ...params }
  })
};
//调整记录
export const materialsCoalStockHistoryList = (page, id) => {
  return request({
    url: '/material/stock/materialsCoalStock/materialsCoalStockHistoryList',
    method: 'get',
    params: {
      ...page,
      id,
    }
  })
};

// 上煤记录
export const getConveyingCoalList = (page, query) => {
  return request({
    url: '/material/stock/materialsCoalStock/getConveyingCoalList',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
// 卸煤记录
export const getHCoalUnloadingList = (page, query) => {
  return request({
    url: '/material/stock/materialsCoalStock/getHCoalUnloadingList',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
