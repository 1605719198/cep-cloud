import request from "@/utils/request";

//原煤库存
export const getCoalStock = (params) => {
  return request({
    url:'/material/stock/materialsCoalStock/getCoalStock',
    method:'get',
    params
  });
}

//上煤量
export const getUpCoalWeightByNowDay = (params) => {
  return request({
    url:'/production/plan/dataBoard/getUpCoalWeightByNowDay',
    method:'get',
    params
  });
}

//上煤仓余量
export const getMaterialsCoalWarehouseStock = (params) => {
  return request({
    url:"/production/productionCoalWarehouseStock/getMaterialsCoalWarehouseStock",
    method:"get",
    params
  });
}
