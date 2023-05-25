import request from '@/utils/request'

// 查询各公司薪给输入列表
export function listPaySheetInput(query) {
  return request({
    url: '/human/paySheetInput/list',
    method: 'get',
    params: query
  })
}

// 查询各公司薪给输入详细
export function getPaySheetInput(uuid) {
  return request({
    url: '/human/paySheetInput/' + uuid,
    method: 'get'
  })
}

// 新增各公司薪给输入
export function addPaySheetInput(data) {
  return request({
    url: '/human/paySheetInput',
    method: 'post',
    data: data
  })
}

// 修改各公司薪给输入
export function updatePaySheetInput(data) {
  return request({
    url: '/human/paySheetInput',
    method: 'put',
    data: data
  })
}

// 删除各公司薪给输入
export function delPaySheetInput(uuid) {
  return request({
    url: '/human/paySheetInput/' + uuid,
    method: 'delete'
  })
}
