import request from '@/utils/request'

// 查询部门资料变更版本列表
export function listDeptversion(query) {
  return request({
    url: '/human/deptVersion/list',
    method: 'get',
    params: query
  })
}

// 查询部门资料变更版本详细
export function getDeptversion(id) {
  return request({
    url: '/human/deptVersion/' + id,
    method: 'get'
  })
}

// 新增部门资料变更版本
export function addDeptversion(data) {
  return request({
    url: '/human/deptVersion',
    method: 'post',
    data: data
  })
}

// 修改部门资料变更版本
export function updateDeptversion(data) {
  return request({
    url: '/human/deptVersion',
    method: 'put',
    data: data
  })
}

// 删除部门资料变更版本
export function delDeptVersion(id) {
  return request({
    url: '/human/deptversion/' + id,
    method: 'delete'
  })
}
