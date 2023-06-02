import request from '@/utils/request'

// 查询报支类别-公司级-设定主档列表
export function listReimbItemComp(query) {
  return request({
    url: '/finance/reimbItemComp/list',
    method: 'get',
    params: query
  })
}

// 查询报支类别-公司级-设定主档详细
export function getReimbItemComp(id) {
  return request({
    url: '/finance/reimbItemComp/' + id,
    method: 'get'
  })
}

// 新增报支类别-公司级-设定主档
export function addReimbItemComp(data) {
  return request({
    url: '/finance/reimbItemComp',
    method: 'post',
    data: data
  })
}

// 修改报支类别-公司级-设定主档
export function updateReimbItemComp(data) {
  return request({
    url: '/finance/reimbItemComp',
    method: 'put',
    data: data
  })
}

// 删除报支类别-公司级-设定主档
export function delReimbItemComp(id) {
  return request({
    url: '/finance/reimbItemComp/' + id,
    method: 'delete'
  })
}


// 批量新增报支类别-公司级
export function addBatchData(data) {
  return request({
    url: '/finance/reimbItemComp/batch',
    method: 'post',
    data: data
  })
}

// 状态修改
export function changeStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/finance/reimbItemComp/changeStatus',
    method: 'put',
    data: data
  })
}
