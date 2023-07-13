import request from '@/utils/request'

// 查询报支类别公司设定明细档列表
export function listReimbItemCompDetail(query) {
  return request({
    url: '/finance/reimbItemCompDetail/list',
    method: 'get',
    params: query
  })
}
// 预付款调查询方法
export function selectDetailNoList(query) {
  return request({
    url: `/finance/reimbItemCompDetail/selectDetailNoList`,
    method: 'get',
    params: query
  })
}
// 查询报支类别公司设定明细档详细
export function getReimbItemCompDetail(id) {
  return request({
    url: '/finance/reimbItemCompDetail/' + id,
    method: 'get'
  })
}

// 新增报支类别公司设定明细档
export function addReimbItemCompDetail(data) {
  return request({
    url: '/finance/reimbItemCompDetail',
    method: 'post',
    data: data
  })
}

// 修改报支类别公司设定明细档
export function updateReimbItemCompDetail(data) {
  return request({
    url: '/finance/reimbItemCompDetail',
    method: 'put',
    data: data
  })
}

// 删除报支类别公司设定明细档
export function delReimbItemCompDetail(id) {
  return request({
    url: '/finance/reimbItemCompDetail/' + id,
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
    url: '/finance/reimbItemCompDetail/changeStatus',
    method: 'put',
    data: data
  })
}
