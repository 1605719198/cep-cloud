import request from '@/utils/request'

// 查询人员离职信息列表
export function listEmployeeLeave(query) {
  return request({
    url: '/human/employeeLeave/list',
    method: 'get',
    params: query
  })
}

// 查询人员离职信息详细
export function getEmployeeLeave(uuid) {
  return request({
    url: '/human/employeeLeave/' + uuid,
    method: 'get'
  })
}

// 新增人员离职信息
export function addEmployeeLeave(data) {
  return request({
    url: '/human/employeeLeave',
    method: 'post',
    data: data
  })
}

// 修改人员离职信息
export function updateEmployeeLeave(data) {
  return request({
    url: '/human/employeeLeave',
    method: 'put',
    data: data
  })
}

// 删除人员离职信息
export function delEmployeeLeave(uuid) {
  return request({
    url: '/human/employeeLeave/' + uuid,
    method: 'delete'
  })
}
