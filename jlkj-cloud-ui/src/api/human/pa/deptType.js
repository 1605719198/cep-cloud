import request from '@/utils/request'

// 查询人员类别考评项目设定主档列表
export function listDeptType(query) {
  return request({
    url: '/human/deptType/list',
    method: 'get',
    params: query
  })
}

// 查询人员类别考评项目设定明细档列表
export function listDeptTypeDetail(query) {
  return request({
    url: '/human/deptType/listDeptTypeDetail',
    method: 'get',
    params: query
  })
}

// 新增人员类别考评项目设定主档
export function addDeptType(data) {
  return request({
    url: '/human/deptType',
    method: 'post',
    data: data
  })
}

// 修改人员类别考评项目设定主档
export function updateDeptType(data) {
  return request({
    url: '/human/deptType',
    method: 'put',
    data: data
  })
}

// 删除人员类别考评项目设定主档
export function delDeptType(data) {
  debugger
  return request({
    url: '/human/deptType/delDeptType',
    method: 'post',
    data: data
  })
}

// 新增KPI指标明细档
export function addDeptTypeDetail(data) {
  return request({
    url: '/human/deptType/addDeptTypeDetail',
    method: 'post',
    data: data
  })
}
