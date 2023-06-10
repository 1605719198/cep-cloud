import request from '@/utils/request'

// 查询薪资项目金额追补扣列表
export function listSalaryAdd(query) {
  return request({
    url: '/human/salaryAdd/list',
    method: 'get',
    params: query
  })
}

// 新增薪资项目金额追补扣
export function addSalaryAdd(data) {
  return request({
    url: '/human/salaryAdd',
    method: 'post',
    data: data
  })
}

// 删除薪资项目金额追补扣
export function delSalaryAdd(id) {
  return request({
    url: '/human/salaryAdd/' + id,
    method: 'delete'
  })
}

// 查询导入状态
export function queryStatus(query) {
  return request({
    url: '/human/salaryAdd/queryStatus',
    method: 'get',
    params: query
  })
}
