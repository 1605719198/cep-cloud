import request from '@/utils/request'

// 查询薪酬项目列表
export function listProjectPay(query) {
  return request({
    url: '/human/projectPay/list',
    method: 'get',
    params: query
  })
}

// 查询薪酬项目详细
export function getProjectPay(id) {
  return request({
    url: '/human/projectPay/' + id,
    method: 'get'
  })
}

// 新增薪酬项目
export function addProjectPay(data) {
  return request({
    url: '/human/projectPay',
    method: 'post',
    data: data
  })
}

// 修改薪酬项目
export function updateProjectPay(data) {
  return request({
    url: '/human/projectPay',
    method: 'put',
    data: data
  })
}

// 删除薪酬项目
export function delProjectPay(id) {
  return request({
    url: '/human/projectPay/' + id,
    method: 'delete'
  })
}
