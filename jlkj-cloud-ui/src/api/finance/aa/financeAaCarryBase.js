import request from '@/utils/request'

// 查询自动结转规则-基本资料-集团列表
export function listFinanceAaCarryBase(query) {
  return request({
    url: '/finance/financeAaCarryBase/list',
    method: 'get',
    params: query
  })
}

// 查询自动结转规则-基本资料-集团详细
export function getFinanceAaCarryBase(id) {
  return request({
    url: '/finance/financeAaCarryBase/' + id,
    method: 'get'
  })
}

// 新增自动结转规则-基本资料-集团
export function addFinanceAaCarryBase(data) {
  return request({
    url: '/finance/financeAaCarryBase',
    method: 'post',
    data: data
  })
}

// 修改自动结转规则-基本资料-集团
export function updateFinanceAaCarryBase(data) {
  return request({
    url: '/finance/financeAaCarryBase',
    method: 'put',
    data: data
  })
}

// 删除自动结转规则-基本资料-集团
export function delFinanceAaCarryBase(id) {
  return request({
    url: '/finance/financeAaCarryBase/' + id,
    method: 'delete'
  })
}
