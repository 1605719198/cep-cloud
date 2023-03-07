import request from "@/utils/request";

//--------------------指标项维护--------------------//
//查询指标项
export const listProductionTargetItem = (page, query) => {
  return request({
    url: "/api/neplatform/production/plan/listProductionTargetItem",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};


//生产管理-产量跟踪-查询-获取动态列表
export const getProductionYieldAnalysisColList = (params) => {
  return request({
    url: "/api/neplatform/production/yield/getProductionYieldAnalysisColList",
    method: "get",
    params,
  });
};


//新增指标项
export const addProductionTargetItem = (params) => {
  return request({
    url: "/api/neplatform/production/plan/addProductionTargetItem",
    method: "post",
    data: { ...params },
  });
};
//修改指标项
export const editProductionTargetItem = (params) => {
  return request({
    url: "/api/neplatform/production/plan/editProductionTargetItem",
    method: "post",
    data: { ...params },
  });
};
//查询物料代码
export const materialCodeList = () => {
  return request({
    url: "/api/neplatform/production/plan/listMaterialCode",
    method: "get",
  });
}

//删除指标项
export const delProductionTargetItem = (params) => {
  return request({
    url: "/api/neplatform/production/plan/delProductionTargetItem",
    method: "post",
    data: { ...params },
  });
};
//--------------------焦炉单孔产量--------------------//

//查询焦炉单孔产量
export const listProductionSingleHoleYield = (page, query) => {
  return request({
    url: "/api/neplatform/production/plan/listProductionSingleHoleYield",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//新增焦炉单孔产量
export const addProductionSingleHoleYield = (params) => {
  return request({
    url: "/api/neplatform/production/plan/addProductionSingleHoleYield",
    method: "post",
    data: { ...params },
  });
};
//修改焦炉单孔产量
export const editProductionSingleHoleYield = (params) => {
  return request({
    url: "/api/neplatform/production/plan/editProductionSingleHoleYield",
    method: "post",
    data: { ...params },
  });
};
//删除焦炉单孔产量
export const delProductionSingleHoleYield = (params) => {
  return request({
    url: "/api/neplatform/production/plan/delProductionSingleHoleYield",
    method: "post",
    data: { ...params },
  });
};
//--------------------K值标准--------------------//

//查询K值标准
export const listCokeOvenParameterStandards = (page, query) => {
  return request({
    url: "/api/neplatform/production/plan/listCokeOvenParameterStandards",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//新增K值标准
export const addCokeOvenParameterStandard = (params) => {
  return request({
    url: "/api/neplatform/production/plan/saveCokeOvenParameterStandard",
    method: "post",
    data: { ...params },
  });
};
//修改K值标准
export const editCokeOvenParameterStandard = (params) => {
  return request({
    url: "/api/neplatform/production/plan/updateCokeOvenParameterStandard",
    method: "post",
    data: { ...params },
  });
};
//删除K值标准
export const delCokeOvenParameterStandard = (params) => {
  return request({
    url: "/api/neplatform/production/plan/deleteCokeOvenParameterStandard",
    method: "post",
    data: { ...params },
  });
};
//--------------------日志项名称--------------------//

//查询日志项名称
export const productionLogsNameList = (page, query) => {
  return request({
    url: "/api/neplatform/production/productionLogsName/list",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//新增
export const productionLogsNameSave = (params) => {
  return request({
    url: "/api/neplatform/production/productionLogsName/save",
    method: "post",
    data: { ...params },
  });
};
//修改
export const productionLogsNameUpdate = (params) => {
  return request({
    url: "/api/neplatform/production/productionLogsName/update",
    method: "post",
    data: { ...params },
  });
};
//删除日志项名称
export const productionLogsNameDelete = (params) => {
  return request({
    url: "/api/neplatform/production/productionLogsName/delete",
    method: "post",
    data: { ...params },
  });
};
//日志项名称下拉列表
export const productionLogsNameSelectAll = (params) => {
  return request({
    url: "/api/neplatform/production/productionLogsName/selectAll",
    method: "get",
    params,
  });
};

//--------------------生产信息配置--------------------//

//生产信息配置-查询-分页
export const getProductionInformationConfigurationPage = (page, query) => {
  return request({
    url: "/api/neplatform/production/productioninformationconfiguration/getProductionInformationConfigurationPage",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//生产信息配置-新增
export const insertProductionInformationConfiguration = (params) => {
  return request({
    url: "/api/neplatform/production/productioninformationconfiguration/insertProductionInformationConfiguration",
    method: "post",
    data: { ...params },
  });
};
//生产信息配置-修改
export const updateProductionInformationConfiguration = (params) => {
  return request({
    url: "/api/neplatform/production/productioninformationconfiguration/updateProductionInformationConfiguration",
    method: "put",
    data: { ...params },
  });
};
//生产信息配置-删除
export const deleteProductionInformationConfiguration = (params) => {
  return request({
    url: "/api/neplatform/production/productioninformationconfiguration/deleteProductionInformationConfiguration",
    method: "delete",
    data: { ...params },
  });
};

//--------------------每日推焦炉数维护--------------------//

//每日推焦炉数维护-查询-分页
export const getProductionCokeOvensPage = (page, query) => {
  return request({
    url: "/api/neplatform/production/productioncokeovens/getProductionCokeOvensPage",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//每日推焦炉数维护-新增
export const insertProductionCokeOvens = (params) => {
  return request({
    url: "/api/neplatform/production/productioncokeovens/insertProductionCokeOvens",
    method: "post",
    data: { ...params },
  });
};
//每日推焦炉数维护-修改
export const updateProductionCokeOvens = (params) => {
  return request({
    url: "/api/neplatform/production/productioncokeovens/updateProductionCokeOvens",
    method: "put",
    data: { ...params },
  });
};
//每日推焦炉数维护-抛送ERP
export const confirmProductionCokeOvens = (params) => {
  return request({
    url: "/api/neplatform/production/productioncokeovens/confirmProductionCokeOvens",
    method: "put",
    data: { ...params },
  });
};
//每日推焦炉数维护-删除
export const deleteProductionCokeOvens = (params) => {
  return request({
    url: "/api/neplatform/production/productioncokeovens/deleteProductionCokeOvens",
    method: "delete",
    data: { ...params },
  });
};

//--------------------焦棚产量维护--------------------//

//焦棚产量维护-查询-分页
export const getProductionCokeShedOutputPage = (page, query) => {
  return request({
    url: "/api/neplatform/production/productioncokeshedoutput/getProductionCokeShedOutputPage",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//焦棚产量维护-新增
export const insertProductionCokeShedOutput = (params) => {
  return request({
    url: "/api/neplatform/production/productioncokeshedoutput/insertProductionCokeShedOutput",
    method: "post",
    data: { ...params },
  });
};
//焦棚产量维护-修改
export const updateProductionCokeShedOutput = (params) => {
  return request({
    url: "/api/neplatform/production/productioncokeshedoutput/updateProductionCokeShedOutput",
    method: "put",
    data: { ...params },
  });
};
//焦棚产量维护-抛送ERP
export const confirmProductionCokeShedOutput = (params) => {
  return request({
    url: "/api/neplatform/production/productioncokeshedoutput/confirmProductionCokeShedOutput",
    method: "put",
    data: { ...params },
  });
};
//焦棚产量维护-删除
export const deleteProductionCokeShedOutput = (params) => {
  return request({
    url: "/api/neplatform/production/productioncokeshedoutput/deleteProductionCokeShedOutput",
    method: "delete",
    data: { ...params },
  });
};

//--------------------炼焦煤消耗维护--------------------//

//炼焦煤消耗维护-查询-分页
export const getProductionCoalConsumePage = (page, query) => {
  return request({
    url: "/api/neplatform/production/productioncoalconsume/getProductionCoalConsumePage",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//炼焦煤消耗维护-新增
export const insertProductionCoalConsume = (params) => {
  return request({
    url: "/api/neplatform/production/productioncoalconsume/insertProductionCoalConsume",
    method: "post",
    data: { ...params },
  });
};
//炼焦煤消耗维护-修改
export const updateProductionCoalConsume = (params) => {
  return request({
    url: "/api/neplatform/production/productioncoalconsume/updateProductionCoalConsume",
    method: "put",
    data: { ...params },
  });
};
//炼焦煤消耗维护-抛送ERP
export const confirmProductionCoalConsume = (params) => {
  return request({
    url: "/api/neplatform/production/productioncoalconsume/confirmProductionCoalConsume",
    method: "put",
    data: { ...params },
  });
};
//炼焦煤消耗维护-删除
export const deleteProductionCoalConsume = (params) => {
  return request({
    url: "/api/neplatform/production/productioncoalconsume/deleteProductionCoalConsume",
    method: "delete",
    data: { ...params },
  });
};
//--------------------练习--------------------//
//查询指标项
// export const listProductionpractice = (page, query) => {
//   return request({
//     url: "/api/neplatform/production/plan/listProductionpractice",
//     method: "get",
//     params: {
//       ...page,
//       ...query,
//     },
//   });
// };
// //新增指标项
// export const addProductionpractice = (params) => {
//   return request({
//     url: "/api/neplatform/production/plan/addProductionpractice",
//     method: "post",
//     data: { ...params },
//   });
// };
// //修改指标项
// export const editProductionpractice = (params) => {
//   return request({
//     url: "/api/neplatform/production/plan/editProductionpractice",
//     method: "post",
//     data: { ...params },
//   });
// };
// //删除指标项
// export const delProductionpractice = (params) => {
//   return request({
//     url: "/api/neplatform/production/plan/delProductionpractice",
//     method: "post",
//     data: { ...params },
//   });
// };
