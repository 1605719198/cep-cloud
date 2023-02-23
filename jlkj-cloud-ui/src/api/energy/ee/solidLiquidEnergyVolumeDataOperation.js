import request from "@/utils/request";

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/energy/energy/solid/liquid/query',
  method: 'get',
  params:data
});
// 更新资料
export function refreshInfo(data) {
  return request({
    url: '/energy/energy/solid/liquid/refresh',
    method: 'put',
    data: data
  })
}

// 抛送MES
export function autoSendInfo(data) {
  return request({
    url: '/energy/energy/solid/liquid/autoSend',
    method: 'post',
    data: data
  })
}
//查询能源代码下拉选单
export function queryDropDownInfo(data) {
  return request({
    url: '/energy/energy/solid/liquid/queryDropDownMenu',
    method: 'get',
    data: data
  })
}
