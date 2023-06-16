import request from '@/utils/request'
// 查询凭证维护-明细列表
export function listDetailSteelSteel(query) {
  return request({
    url: '/finance/detailSteel/listDetailIfSteel',
    method: 'get',
    params: query
  })
}
// 查询凭证维护-明细列表
export function listCalNumberDetailIfSteel(query) {
  return request({
    url: '/finance/detailSteel/listNumberDetailIfSteel',
    method: 'get',
    params: query
  })
}
