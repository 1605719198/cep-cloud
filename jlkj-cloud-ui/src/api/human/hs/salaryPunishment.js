import request from '@/utils/request'

// 查询薪资项目金额追补扣列表
export function listSalaryPunishment(query) {
  return request({
    url: '/human/salaryPunishment/list',
    method: 'get',
    params: query
  })
}

// 新增薪资项目金额追补扣
export function addSalaryPunishment(data) {
  return request({
    url: '/human/salaryPunishment',
    method: 'post',
    data: data
  })
}

// 删除薪资项目金额追补扣
export function delSalaryPunishment(id) {
  return request({
    url: '/human/salaryPunishment/' + id,
    method: 'delete'
  })
}

// 查询导入状态
export function queryStatus(query) {
  return request({
    url: '/human/salaryPunishment/queryStatus',
    method: 'get',
    params: query
  })
}
