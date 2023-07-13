import request from '@/utils/request'

// 查询考试分类列表
export function listExamType(query) {
  return request({
    url: '/human/questions/examtype/list',
    method: 'get',
    params: query
  })
}

// 查询考试分类详细
export function getExamtype(typeId) {
  return request({
    url: '/human/questions/examtype/' + typeId,
    method: 'get'
  })
}

// 查询考试分类详细
export function typeTreeSelect() {
  return request({
    url: '/human/questions/examtype/typetreeselect',
    method: 'get'
  })
}

// 新增考试分类
export function addExamtype(data) {
  debugger
  return request({
    url: '/human/questions/examtype',
    method: 'post',
    data: data
  })
}

// 修改考试分类
export function updateExamtype(data) {
  debugger
  return request({
    url: '/human/questions/examtype',
    method: 'put',
    data: data
  })
}

// 删除考试分类
export function delExamtype(typeId) {
  return request({
    url: '/human/questions/examtype/' + typeId,
    method: 'delete'
  })
}

// 导出考试分类
export function exportExamtype(query) {
  return request({
    url: '/human/questions/examtype/export',
    method: 'get',
    params: query
  })
}
