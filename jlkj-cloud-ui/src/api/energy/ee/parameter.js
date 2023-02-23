import request from "@/utils/request";

//--------------------生产信息配置--------------------//

//生产信息配置-查询-分页
export const getEnergyInformationConfigurationPage = (page, query) => {
  return request({
    url: "/energy/energyinformationconfiguration/getEnergyInformationConfigurationPage",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};
//生产信息配置-新增
export const insertEnergyInformationConfiguration = (params) => {
  return request({
    url: "/energy/energyinformationconfiguration/insertEnergyInformationConfiguration",
    method: "post",
    data: { ...params },
  });
};
//生产信息配置-修改
export const updateEnergyInformationConfiguration = (params) => {
  return request({
    url: "/energy/energyinformationconfiguration/updateEnergyInformationConfiguration",
    method: "put",
    data: { ...params },
  });
};
//生产信息配置-删除
export const deleteEnergyInformationConfiguration = (params) => {
  return request({
    url: "/energy/energyinformationconfiguration/deleteEnergyInformationConfiguration",
    method: "delete",
    data: { ...params },
  });
};

// 查询成本中心设定列表
export function listFinancetest(data) {
  return request({
    url: '/api/neplatform/fin/finance/cost/costCenter/queryAllInterface',
    method: 'get',
    params: data
  })
}

// 查询下拉选单
export function queryEngyIds(data) {
  return request({
    url: '/energy/energy/code/queryDropDownMenuZh',
    method: 'get',
    data: data
  })
}
