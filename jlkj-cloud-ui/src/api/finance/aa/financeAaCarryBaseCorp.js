import request from '@/utils/request'

// 查询自动结转规则-基本资料-公司列表
export function listFinanceAaCarryBaseCorp(query) {
  return request({
    url: '/finance/financeAaCarryBaseCorp/list',
    method: 'get',
    params: query
  })
}

// 查询自动结转规则-基本资料-公司详细
export function getFinanceAaCarryBaseCorp(id) {
  return request({
    url: '/finance/financeAaCarryBaseCorp/' + id,
    method: 'get'
  })
}

// 新增自动结转规则-基本资料-公司
export function addFinanceAaCarryBaseCorp(data) {
  return request({
    url: '/finance/financeAaCarryBaseCorp',
    method: 'post',
    data: data
  })
}

// 修改自动结转规则-基本资料-公司
export function updateFinanceAaCarryBaseCorp(data) {
  return request({
    url: '/finance/financeAaCarryBaseCorp',
    method: 'put',
    data: data
  })
}

// 删除自动结转规则-基本资料-公司
export function delFinanceAaCarryBaseCorp(id) {
  return request({
    url: '/finance/financeAaCarryBaseCorp/' + id,
    method: 'delete'
  })
}
