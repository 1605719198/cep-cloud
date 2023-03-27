import request from '@/utils/request'

// 查询员工奖惩信息列表
export function listEmployeeEncourage(query) {
  return request({
    url: '/human/employeeEncourage/list',
    method: 'get',
    params: query
  })
}

// 查询员工奖惩信息详细
export function getEmployeeEncourage(uuid) {
  return request({
    url: '/human/employeeEncourage/' + uuid,
    method: 'get'
  })
}

// 新增员工奖惩信息
export function addEmployeeEncourage(data) {
  return request({
    url: '/human/employeeEncourage',
    method: 'post',
    data: data
  })
}

// 修改员工奖惩信息
export function updateEmployeeEncourage(data) {
  return request({
    url: '/human/employeeEncourage',
    method: 'put',
    data: data
  })
}

// 删除员工奖惩信息
export function delEmployeeEncourage(uuid) {
  return request({
    url: '/human/employeeEncourage/' + uuid,
    method: 'delete'
  })
}
