import request from '@/utils/request'

// 查询抛帐规则维护列表
export function listAccountRules(query) {
  return request({
    url: '/human/accountRules/list',
    method: 'get',
    params: query
  })
}

// 查询抛帐规则维护详细
export function getAccountRules(id) {
  return request({
    url: '/human/accountRules/' + id,
    method: 'get'
  })
}

// 新增抛帐规则维护
export function addAccountRules(data) {
  return request({
    url: '/human/accountRules',
    method: 'post',
    data: data
  })
}


// 删除抛帐规则维护
export function delAccountRules(id) {
  return request({
    url: '/human/accountRules/' + id,
    method: 'delete'
  })
}

// 查询薪资资料树结构
export function listAccountRulesTree(compId) {
  return request({
    url: '/human/accountRules/listTree/'+compId,
    method: 'get',

  })
}

