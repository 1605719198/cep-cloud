import request from '@/utils/request'

// 查询薪资计算结果列表
export function listSalaryResult(query) {
  return request({
    url: '/human/salaryResult/list',
    method: 'get',
    params: query
  })
}

// 查询薪资计算结果详细
export function getSalaryResult(id) {
  return request({
    url: '/human/salaryResult/' + id,
    method: 'get'
  })
}

