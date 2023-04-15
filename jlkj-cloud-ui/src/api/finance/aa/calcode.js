import request from '@/utils/request'

// 查询核算项目-内容维护列表
export function listCalcode(query) {
  return request({
    url: '/finance/calcode/list',
    method: 'get',
    params: query
  })
}

// 查询核算项目-内容维护详细
export function getCalcode(id) {
  return request({
    url: '/finance/calcode/' + id,
    method: 'get'
  })
}

// 新增核算项目-内容维护
export function addCalcode(data) {
  return request({
    url: '/finance/calcode',
    method: 'post',
    data: data
  })
}

// 修改核算项目-内容维护
export function updateCalcode(data) {
  return request({
    url: '/finance/calcode',
    method: 'put',
    data: data
  })
}

// 删除核算项目-内容维护
export function delCalcode(id) {
  return request({
    url: '/finance/calcode/' + id,
    method: 'delete'
  })
}

// 状态修改
export function changeUserStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/finance/calcode/changeStatus',
    method: 'put',
    data: data
  })
}
