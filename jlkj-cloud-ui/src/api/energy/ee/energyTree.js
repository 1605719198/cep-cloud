import request from "@/utils/request";

// 能介资料 - 下拉列表
export function selectEnergyTree() {
  return request({
    url: '/energy/energy/mediated/data/select',
    method: 'get'
  })
}
