import request from "@/utils/request";

//生产实绩-上煤记录-查询-分页
export const getProductionConveyingCoalRecordPage = (page, query) => {
  return request({
    url: "/api/neplatform/production/productionconveyingcoalrecord/getProductionConveyingCoalRecordPage",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};

//获取配合配煤计划信息
export const getProductionPlanConfigCokeDetailInfoByWarehouseNumber = (params) => {
  return request({
    url: "/api/neplatform/production/plan/getProductionPlanConfigCokeDetailInfoByWarehouseNumber",
    method: "get",
    params
  });
};
//生产实绩-上记录新增
export const insertProductionConveyingCoalRecord = (params) => {
  return request({
    url: "/api/neplatform/production/productionconveyingcoalrecord/insertProductionConveyingCoalRecord",
    method: "post",
    data: { ...params },
  });
};
//生产实绩-上煤记录-删除
export const deleteProductionConveyingCoalRecord = (params) => {
  return request({
    url: "/api/neplatform/production/productionconveyingcoalrecord/deleteProductionConveyingCoalRecord",
    method: "delete",
    data: { ...params },
  });
};

//获取配合配煤计划信息
export const getPlanConfigCoke = (params) => {
  return request({
    url: "/api/neplatform/production/manual_config/getPlanConfigCoke",
    method: "get",
    params
  });
};
