import request from '@/utils/request'

// 查询关账执行记录列表
export function listRecord(query) {
  return request({
    url: '/finance/recordClosing/list',
    method: 'get',
    params: query
  })
}

// 查询关账执行记录详细
export function getRecord(id) {
  return request({
    url: '/finance/recordClosing/' + id,
    method: 'get'
  })
}

// 新增关账执行记录
export function addRecord(data) {
  return request({
    url: '/finance/recordClosing',
    method: 'post',
    data: data
  })
}
// 执行关账执行记录
export function addClosingExecute(data) {
  return request({
    url: '/finance/recordClosing/addClosingExecute',
    method: 'post',
    data: data
  })
}
// 修改关账执行记录
export function updateRecord(data) {
  return request({
    url: '/finance/recordClosing',
    method: 'put',
    data: data
  })
}

// 删除关账执行记录
export function delRecord(id) {
  return request({
    url: '/finance/recordClosing/' + id,
    method: 'delete'
  })
}
