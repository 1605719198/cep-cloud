/**
 * 焦炭出厂实绩Api
 */

import request from "@/utils/request";

/*
  获取焦炭出厂实绩数据列表
 */
export const getProductionCokeFactoryPerformancePage = (params) => {
    return request({
        url: "/product/plan/getProductionCokeFactoryPerformancePage",
        method: "get",
        params
    });
}

//获取物料代码 00：煤   01：焦炭
export const getMaterialsCodeListByType = (type) => {
  return request({
    url:'/api/neplatform/material/parameter/getMaterialsCodeListByType?mtrlType='+ type,
    method:'get'
  });
}
