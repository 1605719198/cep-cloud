import request from "@/utils/request";

//生产看板-焦炭日产量
export const getCokeOutPutAnalysisByNowDay = () => {
  return request({
    url:'/api/neplatform/production/plan/dataBoard/getCokeOutputAnalysisByNowDay',
    method:'get'
  });
}

//生产看板-本年度-焦炭产量分析-分类汇总
export const getCokeOutputAnalysis = () => {
  return request({
    url:'/api/neplatform/production/plan/dataBoard/getCokeOutputAnalysis',
    method:'get'
  });
}


//生产看板-原煤库存
export const getCoalStock = () => {
  return request({
    url:'/api/neplatform/material/stock/materialsCoalStock/getCoalStock',
    method:'get'
  });
}

//生产看板-焦炭库存
export const getCokeStock = (params) => {
  return request({
    url:'/api/neplatform/material/stock/materialsCokeStock/getCokeStock',
    method:'get',
    params
  });
}

