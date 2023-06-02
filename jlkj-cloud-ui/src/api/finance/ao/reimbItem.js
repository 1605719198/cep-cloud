import request from '@/utils/request'

// 查询报支管理-报支类别集团设定主档列表
export function listReimbItem(query) {
  return request({
    url: '/finance/reimbItem/list',
    method: 'get',
    params: query
  })
}

// 查询报支管理-报支类别集团设定主档详细
export function getReimbItem(id) {
  return request({
    url: '/finance/reimbItem/' + id,
    method: 'get'
  })
}

// 新增报支管理-报支类别集团设定主档
export function addReimbItem(data) {
  return request({
    url: '/finance/reimbItem',
    method: 'post',
    data: data
  })
}

// 修改报支管理-报支类别集团设定主档
export function updateReimbItem(data) {
  return request({
    url: '/finance/reimbItem',
    method: 'put',
    data: data
  })
}

// 删除报支管理-报支类别集团设定主档
export function delReimbItem(id) {
  return request({
    url: '/finance/reimbItem/' + id,
    method: 'delete'
  })
}

// 状态修改
export function changeStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/finance/reimbItem/changeStatus',
    method: 'put',
    data: data
  })
}

export function listItemAndDetail(companyId) {
  return request({
    url: '/finance/reimbItem/itemList/' + companyId,
    method: 'get'
  })
}
