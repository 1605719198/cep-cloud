import request from '@/utils/request'
// 查询凭证维护-明细列表
export function listDetailIfSteel(query) {
  return request({
    url: '/finance/generalLedger/listDetailIfSteel',
    method: 'get',
    params: query
  })
}
// 查询凭证维护-明细列表
export function listCalNumberDetailIfSteel(query) {
  return request({
    url: '/finance/generalLedger/listNumberDetailIfSteel',
    method: 'get',
    params: query
  })
}
