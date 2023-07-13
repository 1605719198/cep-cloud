import request from '@/utils/request'

// 查询考试分类列表
export const listExamType = (data) => request({
  url: '/human/examtype/list',
  method: 'get',
  params: data
});


// 查询考试分类详细
export function getExamtype(typeId) {
  return request({
    url: '/human/examtype/' + typeId,
    method: 'get'
  })
}

// 查询考试分类详细
export function typeTreeSelect() {
  return request({
    url: '/human/examtype/typetreeselect',
    method: 'get'
  })
}

// 新增考试分类
export function addExamtype(data) {
  return request({
    url: '/human/examtype/add',
    method: 'post',
    data: data
  })
}

// 修改考试分类
export function updateExamtype(data) {
  return request({
    url: '/human/examtype',
    method: 'put',
    data: data
  })
}

// 删除考试分类
export function delExamtype(typeId) {
  return request({
    url: '/human/examtype/' + typeId,
    method: 'delete'
  })
}

// 导出考试分类
export function exportExamtype(query) {
  return request({
    url: '/human/examtype/export',
    method: 'get',
    params: query
  })
}
