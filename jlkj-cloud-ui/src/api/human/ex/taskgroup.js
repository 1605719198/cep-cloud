import request from '@/utils/request'

// 查询任务分组列表
export function listTaskgroup(query) {
  return request({
    url: '/human/taskgroup/list',
    method: 'get',
    params: query
  })
}

// 查询任务分组详细
export function getTaskgroup(examCode) {
  return request({
    url: '/human/taskgroup/' + examCode,
    method: 'get'
  })
}

// 新增任务分组
export function addTaskgroup(data) {
  return request({
    url: '/human/taskgroup',
    method: 'post',
    data: data
  })
}

// 修改任务分组
export function updateTaskgroup(data) {
  return request({
    url: '/human/taskgroup/update',
    method: 'post',
    data: data
  })
}

// 修改任务分组
export function batchUpdateTaskgroup(data) {
  return request({
    url: '/human/taskgroup/batchupdate',
    method: 'post',
    data: data
  })
}

// 删除任务分组
export function delTaskgroup(examCode) {
  return request({
    url: '/human/taskgroup/delete/' + examCode,
    method: 'get'
  })
}

// 导出任务分组
export function exportTaskgroup(query) {
  return request({
    url: '/human/taskgroup/export',
    method: 'get',
    params: query
  })
}
