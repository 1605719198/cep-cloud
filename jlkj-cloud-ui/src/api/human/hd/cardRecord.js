import request from '@/utils/request'

// 查询刷卡记录列表
export function listCardRecord(query) {
  return request({
    url: '/human/cardRecord/list',
    method: 'get',
    params: query
  })
}
