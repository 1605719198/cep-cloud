import request from '@/utils/request'

// 查询自动结转规则-执行记录列表
export function listRecord(query) {
  return request({
    url: '/finance/record/list',
    method: 'get',
    params: query
  })
}
// 查询自动结转规则-执行记录列表
export function listRecordCompanyId(query) {
  return request({
    url: '/finance/record/listCompanyId',
    method: 'get',
    params: query
  })
}
// 查询自动结转规则-执行记录详细
export function getRecord(id) {
  return request({
    url: '/finance/record/' + id,
    method: 'get'
  })
}

// 新增自动结转规则-执行记录
export function addRecord(data) {
  return request({
    url: '/finance/record',
    method: 'post',
    data: data
  })
}
// 执行自动结转规则-执行记录
export function addExecute(data) {
  return request({
    url: '/finance/record/addExecute',
    method: 'post',
    data: data
  })
}

// 取消修改自动结转规则-执行记录
export function updateCancel(data) {
  return request({
    url: '/finance/record/updateCancel',
    method: 'put',
    data: data
  })
}

// 修改自动结转规则-执行记录
export function updateRecord(data) {
  return request({
    url: '/finance/record',
    method: 'put',
    data: data
  })
}

// 删除自动结转规则-执行记录
export function delRecord(id) {
  return request({
    url: '/finance/record/' + id,
    method: 'delete'
  })
}
