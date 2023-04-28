import request from '@/utils/request'

// 查询集团管理-自动结转规则-规则设置列表
export function listFinanceAaCarryRules(query) {
  return request({
    url: '/finance/financeAaCarryRules/list',
    method: 'get',
    params: query
  })
}

// 查询集团管理-自动结转规则-规则设置详细
export function getFinanceAaCarryRules(id) {
  return request({
    url: '/finance/financeAaCarryRules/' + id,
    method: 'get'
  })
}

// 新增集团管理-自动结转规则-规则设置
export function addFinanceAaCarryRules(data) {
  return request({
    url: '/finance/financeAaCarryRules',
    method: 'post',
    data: data
  })
}

// 修改集团管理-自动结转规则-规则设置
export function updateFinanceAaCarryRules(data) {
  return request({
    url: '/finance/financeAaCarryRules',
    method: 'put',
    data: data
  })
}

// 删除集团管理-自动结转规则-规则设置
export function delFinanceAaCarryRules(id) {
  return request({
    url: '/finance/financeAaCarryRules/' + id,
    method: 'delete'
  })
}
