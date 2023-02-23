import request from "@/utils/request";
// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/energy/energy/consume/output/query',
  method: 'get',
  params:data
});

// 根据条件查询信息
export const querySumInfo = (data) => request({
  url: '/energy/energy/consume/output/query',
  method: 'get',
  params:data
});

// 查询下拉选单
export function queryEngyIds(data) {
  return request({
    url: '/energy/energy/code/queryDropDownMenu',
    method: 'get',
    data: data
  })
}
