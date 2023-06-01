import request from '@/utils/request'
// 查询凭证维护-明细列表
export function listDetailIfSteel(query) {
  return request({
    url: '/finance/accountSteel/listDetailIfSteel',
    method: 'get',
    params: query
  })
}
// 查询凭证维护-明细列表
export function listNumberDetailIfSteel(query) {
  return request({
    url: '/finance/accountSteel/listNumberDetailIfSteel',
    method: 'get',
    params: query
  })
}
// 查询凭证维护-明细列表
export function listCalNumberDetailIfSteel(query) {
  return request({
    url: '/finance/accountSteel/listCalNumberDetailIfSteel',
    method: 'get',
    params: query
  })
}
// 查询凭证维护-明细列表
export function listCalDetailIfSteel(query) {
  return request({
    url: '/finance/accountSteel/listCalDetailIfSteel',
    method: 'get',
    params: query
  })
}
