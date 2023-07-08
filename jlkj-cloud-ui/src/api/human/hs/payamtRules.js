import request from '@/utils/request'

// 查询应付抛帐设定列表
export function listPayamtRules(query) {
  return request({
    url: '/human/payamtRules/list',
    method: 'get',
    params: query
  })
}

// 查询应付抛帐设定详细
export function getPayamtRules(id) {
  return request({
    url: '/human/payamtRules/' + id,
    method: 'get'
  })
}

// 新增应付抛帐设定
export function addPayamtRules(data) {
  return request({
    url: '/human/payamtRules/save',
    method: 'post',
    data: data
  })
}

// 修改应付抛帐设定
export function updatePayamtRules(data) {
  return request({
    url: '/human/payamtRules',
    method: 'put',
    data: data
  })
}

// 删除应付抛帐设定
export function delPayamtRules(id) {
  return request({
    url: '/human/payamtRules/' + id,
    method: 'delete'
  })
}
