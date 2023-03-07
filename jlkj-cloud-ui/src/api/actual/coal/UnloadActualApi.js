/**
 * 卸车实绩api
 */
import request from "@/utils/request";

/**
 * 获取卸车实绩的列表数据
 * @param params 获取数据时候的查询参数
 * @returns {*} 返回的数据
 */
export const getUnloadList = (params) => {
  console.log(params)
  return request({
    url : "/api/neplatform/production/performance/listMaterialUnloadingPerformance",
    method:'get',
    params
  })
}

