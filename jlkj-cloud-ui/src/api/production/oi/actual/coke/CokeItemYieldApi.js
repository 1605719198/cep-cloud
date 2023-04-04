/**
 * 焦炭分项产量Api
 */

import request from "@/utils/request";

/**
 * 查询
 */
export const getCokeItemYieldList = (params) => {
  return request({
    url:"/product/performance/cokeitemyield/getCokeItemYieldList",
    method:"get",
    params
  });
}

/**
 * 查询
 */
export const getCokeItemYieldList2 = (params) => {
  return request({
    url:"/product/performance/cokeitemyield/getCokeItemYieldList2",
    method:"get",
    params
  });
}


/**
 * 新增
 */
export const insertCokeItemYieldList = (params) => {
  return request({
    url:"/product/performance/cokeitemyield/insertCokeItemYieldList",
    method:"post",
    data:{...params}
  });
}

/**
 * 修改
 */
export const updateCokeItemYieldList = (params) => {
  return request({
    url:"/product/performance/cokeitemyield/updateCokeItemYieldList",
    method:"post",
    data:{...params}
  });
}

/**
 * 删除
 */

export const deleteCokeItemYieldList = (params) => {
  return request({
    url:"/product/performance/cokeitemyield/deleteCokeItemYieldList",
    method:"get",
    params
  });
}
