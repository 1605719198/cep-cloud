import request from '@/utils/request'

// 查询集团管理-自动结转规则-规则设置列表
export function listFinanceAaCarryRulesCorp(query) {
  return request({
    url: '/finance/financeAaCarryRulesCorp/list',
    method: 'get',
    params: query
  })
}

// 查询集团管理-自动结转规则-规则设置详细
export function getFinanceAaCarryRulesCorp(id) {
  return request({
    url: '/finance/financeAaCarryRulesCorp/' + id,
    method: 'get'
  })
}

// 新增集团管理-自动结转规则-规则设置
export function addFinanceAaCarryRulesCorp(data) {
  return request({
    url: '/finance/financeAaCarryRulesCorp',
    method: 'post',
    data: data
  })
}

// 修改集团管理-自动结转规则-规则设置
export function updateFinanceAaCarryRulesCorp(data) {
  return request({
    url: '/finance/financeAaCarryRulesCorp',
    method: 'put',
    data: data
  })
}

// 删除集团管理-自动结转规则-规则设置
export function delFinanceAaCarryRulesCorp(id) {
  return request({
    url: '/finance/financeAaCarryRulesCorp/' + id,
    method: 'delete'
  })
}
