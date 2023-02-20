import request from '@/utils/request'

// 分页查询停机原因
export function queryDataByParams(params) {
  return request({
    url: '/equip/equipment/mgmp3/queryDataByParams',
    method: 'get',
    params: params
  })
}
