import request from '@/utils/request'

// 查询夜班统计列表
export function listNightDutyStatistics(query) {
  return request({
    url: '/human/nightDutyStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询夜班统计详细
export function getNightDutyStatistics(id) {
  return request({
    url: '/human/nightDutyStatistics/' + id,
    method: 'get'
  })
}

// 新增夜班统计
export function addNightDutyStatistics(data) {
  return request({
    url: '/human/nightDutyStatistics',
    method: 'post',
    data: data
  })
}

// 修改夜班统计
export function updateNightDutyStatistics(data) {
  return request({
    url: '/human/nightDutyStatistics',
    method: 'put',
    data: data
  })
}

// 删除夜班统计
export function delNightDutyStatistics(id) {
  return request({
    url: '/human/nightDutyStatistics/' + id,
    method: 'delete'
  })
}
