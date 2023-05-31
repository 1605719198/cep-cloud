import request from '@/utils/request'

// 查询报支管理-报支类别集团设定明细档列表
export function listReimbDetail(query) {
  return request({
    url: '/finance/reimbDetail/list',
    method: 'get',
    params: query
  })
}

// 查询报支管理-报支类别集团设定明细档详细
export function getReimbDetail(id) {
  return request({
    url: '/finance/reimbDetail/' + id,
    method: 'get'
  })
}

// 新增报支管理-报支类别集团设定明细档
export function addReimbDetail(data) {
  return request({
    url: '/finance/reimbDetail',
    method: 'post',
    data: data
  })
}

// 修改报支管理-报支类别集团设定明细档
export function updateReimbDetail(data) {
  return request({
    url: '/finance/reimbDetail',
    method: 'put',
    data: data
  })
}

// 删除报支管理-报支类别集团设定明细档
export function delReimbDetail(id) {
  return request({
    url: '/finance/reimbDetail/' + id,
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
    url: '/finance/reimbDetail/changeStatus',
    method: 'put',
    data: data
  })
}
