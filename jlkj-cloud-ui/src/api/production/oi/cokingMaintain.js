import request from "@/utils/request";
//--------------------油品消耗--------------------//
//查询
export const productionReportOilConsumeList = (page, query) => {
  return request({
    url: "/product/productionReportOilConsume/list",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//新增
export const productionReportOilConsumeSave = (params) => {
  return request({
    url: "/product/productionReportOilConsume/save",
    method: "post",
    data: { ...params },
  });
};
//修改
export const productionReportOilConsumeUpdate = (params) => {
  return request({
    url: "/product/productionReportOilConsume/update",
    method: "post",
    data: { ...params },
  });
};
//删除
export const productionReportOilConsumeDelete = (params) => {
  return request({
    url: "/product/productionReportOilConsume/delete",
    method: "post",
    data: { ...params },
  });
};

//--------------------辅料消耗--------------------//
//查询
export const productionReportExcipientConsumeList = (page, query) => {
  return request({
    url: "/product/productionReportExcipientConsume/list",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//新增
export const productionReportExcipientConsumeSave = (params) => {
  return request({
    url: "/product/productionReportExcipientConsume/save",
    method: "post",
    data: { ...params },
  });
};
//修改
export const productionReportExcipientConsumeUpdate = (params) => {
  return request({
    url: "/product/productionReportExcipientConsume/update",
    method: "post",
    data: { ...params },
  });
};
//删除
export const productionReportExcipientConsumeDelete = (params) => {
  return request({
    url: "/product/productionReportExcipientConsume/delete",
    method: "post",
    data: { ...params },
  });
};

//--------------------煤场卸车情况--------------------//
//查询
export const productionReportUnloadingList = (page, query) => {
  return request({
    url: "/product/productionReportUnloading/list",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//新增
export const productionReportUnloadingSave = (params) => {
  return request({
    url: "/product/productionReportUnloading/save",
    method: "post",
    data: { ...params },
  });
};
//修改
export const productionReportUnloadingUpdate = (params) => {
  return request({
    url: "/product/productionReportUnloading/update",
    method: "post",
    data: { ...params },
  });
};
//删除
export const productionReportUnloadingDelete = (params) => {
  return request({
    url: "/product/productionReportUnloading/delete",
    method: "post",
    data: { ...params },
  });
};

//--------------------吊装费用--------------------//
//查询
export const productionReportLiftingCostsList = (page, query) => {
  return request({
    url: "/product/productionReportLiftingCosts/list",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//新增
export const productionReportLiftingCostsSave = (params) => {
  return request({
    url: "/product/productionReportLiftingCosts/save",
    method: "post",
    data: { ...params },
  });
};
//修改
export const productionReportLiftingCostsUpdate = (params) => {
  return request({
    url: "/product/productionReportLiftingCosts/update",
    method: "post",
    data: { ...params },
  });
};
//删除
export const productionReportLiftingCostsDelete = (params) => {
  return request({
    url: "/product/productionReportLiftingCosts/delete",
    method: "post",
    data: { ...params },
  });
};

//--------------------物流费用--------------------//
//查询
export const productionReportLogisticsCostsList = (page, query) => {
  return request({
    url: "/product/productionReportLogisticsCosts/list",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//新增
export const productionReportLogisticsCostsSave = (params) => {
  return request({
    url: "/product/productionReportLogisticsCosts/save",
    method: "post",
    data: { ...params },
  });
};
//修改
export const productionReportLogisticsCostsUpdate = (params) => {
  return request({
    url: "/product/productionReportLogisticsCosts/update",
    method: "post",
    data: { ...params },
  });
};
//删除
export const productionReportLogisticsCostsDelete = (params) => {
  return request({
    url: "/product/productionReportLogisticsCosts/delete",
    method: "post",
    data: { ...params },
  });
};

