import request from "@/utils/request";


//--------------------通用--------------------//
//获取指标项列表
export const getProductionTargetItem = (params) => {
  return request({
    url: '/product/plan/getProductionTargetItem',
    method: 'get',
    params: {...params}
  })
};


//--------------------年指标计划查询--------------------//
//年指标计划查询
export const listYearProductionTargetPlans = (page, query) => {
  return request({
    url: '/product/plan/listYearProductionTargetPlans',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增年生产指标计划
export const saveYearProductionTargetPlan = (params) => {
  return request({
    url: '/product/plan/saveYearProductionTargetPlan',
    method: 'post',
    data: {...params}
  })
};
//删除年计划
export const deleteYearProductionTargetPlan = (params) => {
  return request({
    url: '/product/plan/deleteYearProductionTargetPlan',
    method: 'post',
    data: {...params}
  })
};
//查询月生产指标计划
export const listMonthProductionTargetPlans = (page, query) => {
  return request({
    url: '/product/plan/listMonthProductionTargetPlans',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//查询单条月生产指标计划
export const getMonthProductionTargetPlan = (page, query) => {
  return request({
    url: '/product/plan/getMonthProductionTargetPlan',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//修改月指标计划
export const updateMonthProductionTargetPlan = (params) => {
  return request({
    url: '/product/plan/updateMonthProductionTargetPlan',
    method: 'post',
    data: {...params}
  })
};
//查询日生产指标计划
export const listDateProductionTargetPlans = (page, query) => {
  return request({
    url: '/product/plan/listDateProductionTargetPlans',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};

//获取能源代码
export const getEnergyCodeList = () => {
  return request({
    url:"/product/plan/listEnergyCode",
    method:"get",
  });
};

export const listMonthEnergyPlans = (params) => {
  return request({
    url:"/product/plan/listMonthEnergyPlans",
    method:"get",
    params
  });
};




