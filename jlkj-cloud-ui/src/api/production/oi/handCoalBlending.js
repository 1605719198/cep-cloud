import request from "@/utils/request";

//手动配煤-储煤塔列表查询
export const getTowerList = (params) => {
  return request({
    url: "/product/manual_config/getTowerList",
    method: "get",
    params,
  });
};

//手动配煤-根据配煤开始时间，查询前置数据
export const getLastConfigPlan = (params) => {
  return request({
    url: "/product/manual_config/getLastConfigPlan",
    method: "get",
    params,
  });
};

//手动配煤-新增
export const addManualConfigCoal = (params) => {
  return request({
    url: "/product/manual_config/addManualConfigCoal",
    method: "post",
    data: { ...params },
  });
};

//手动配煤-储煤塔配煤详细列表查询
export const getTowerBlendDetailList = (page, query) => {
  return request({
    url: "/product/manual_config/getTowerBlendDetailList",
    method: "get",
    params: {
      ...page,
      ...query,
    },
  });
};

//手动配煤-储煤塔配煤记录对应详细配煤仓配煤列表查询
export const getBlendWareDetailList = (params) => {
  return request({
    url: "/product/manual_config/getBlendWareDetailList",
    method: "get",
    params,
  });
};


//删除-储煤塔配煤详细列表
export const delManualConfigCoal = (params) => {
  return request({
    url: "/product/manual_config/delManualConfigCoal",
    method: "post",
    data: { ...params },
  });
};
