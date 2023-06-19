import request from '@/utils/request'

// 查询个人绩效主档列表
export function listDeptEvaluationQuery(query) {
  return request({
    url: '/human/deptEvaluationQuery/list',
    method: 'get',
    params: query
  })
}

// 查询个人绩效主档详细
export function getDeptEvaluationQuery(id) {
  return request({
    url: '/human/deptEvaluationQuery/' + id,
    method: 'get'
  })
}

// 新增个人绩效主档
export function addDeptEvaluationQuery(data) {
  return request({
    url: '/human/deptEvaluationQuery',
    method: 'post',
    data: data
  })
}

// 修改个人绩效主档
export function updateDeptEvaluationQuery(data) {
  return request({
    url: '/human/deptEvaluationQuery',
    method: 'put',
    data: data
  })
}

// 删除个人绩效主档
export function delDeptEvaluationQuery(id) {
  return request({
    url: '/human/deptEvaluationQuery/' + id,
    method: 'delete'
  })
}
