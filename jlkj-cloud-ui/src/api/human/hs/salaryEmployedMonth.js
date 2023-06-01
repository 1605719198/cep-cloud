import request from '@/utils/request'

// 查询员工受雇月维护列表
export function listSalaryEmployedMonth(query) {
  return request({
    url: '/human/salaryEmployedMonth/list',
    method: 'get',
    params: query
  })
}

// 查询员工受雇月维护详细
export function getSalaryEmployedMonth(id) {
  return request({
    url: '/human/salaryEmployedMonth/' + id,
    method: 'get'
  })
}

// 新增员工受雇月维护
export function addSalaryEmployedMonth(data) {
  return request({
    url: '/human/salaryEmployedMonth/save',
    method: 'post',
    data: data
  })
}

// 修改员工受雇月维护
export function updateSalaryEmployedMonth(data) {
  return request({
    url: '/human/salaryEmployedMonth',
    method: 'put',
    data: data
  })
}

// 删除员工受雇月维护
export function delSalaryEmployedMonth(id) {
  return request({
    url: '/human/salaryEmployedMonth/' + id,
    method: 'delete'
  })
}
