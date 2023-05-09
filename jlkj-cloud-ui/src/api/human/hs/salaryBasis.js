import request from '@/utils/request'

// 查询薪资资料列表
export function listSalaryBasis(query) {
  return request({
    url: '/human/salaryBasis/list',
    method: 'get',
    params: query
  })
}

// 查询薪资资料树结构
export function listSalaryBasisTree(query) {
  return request({
    url: '/human/salaryBasis/listTree',
    method: 'get',
    params: query
  })
}

// 查询薪资资料详细
export function getSalaryBasis(id) {
  return request({
    url: '/human/salaryBasis/' + id,
    method: 'get'
  })
}

// 新增薪资资料
export function addSalaryBasis(data) {
  return request({
    url: '/human/salaryBasis',
    method: 'post',
    data: data
  })
}

// 修改薪资资料
export function updateSalaryBasis(data) {
  return request({
    url: '/human/salaryBasis',
    method: 'put',
    data: data
  })
}

// 删除薪资资料
export function delSalaryBasis(id) {
  return request({
    url: '/human/salaryBasis/' + id,
    method: 'delete'
  })
}
