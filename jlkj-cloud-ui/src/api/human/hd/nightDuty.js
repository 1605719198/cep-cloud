import request from '@/utils/request'

// 查询夜班查询列表
export function listNightDuty(query) {
  return request({
    url: '/human/nightDuty/list',
    method: 'get',
    params: query
  })
}

// 查询夜班查询详细
export function getNightDuty(id) {
  return request({
    url: '/human/nightDuty/' + id,
    method: 'get'
  })
}

// 新增夜班查询
export function addNightDuty(data) {
  return request({
    url: '/human/nightDuty',
    method: 'post',
    data: data
  })
}

// 修改夜班查询
export function updateNightDuty(data) {
  return request({
    url: '/human/nightDuty',
    method: 'put',
    data: data
  })
}

// 删除夜班查询
export function delNightDuty(id) {
  return request({
    url: '/human/nightDuty/' + id,
    method: 'delete'
  })
}
