import request from '@/utils/request'

// 查询补休统计列表
export function listComptimeStatistics(query) {
  return request({
    url: '/human/comptimeStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询补休统计详细
export function getComptimeStatistics(id) {
  return request({
    url: '/human/comptimeStatistics/' + id,
    method: 'get'
  })
}

// 新增补休统计
export function addComptimeStatistics(data) {
  return request({
    url: '/human/comptimeStatistics',
    method: 'post',
    data: data
  })
}

// 修改补休统计
export function updateComptimeStatistics(data) {
  return request({
    url: '/human/comptimeStatistics',
    method: 'put',
    data: data
  })
}

// 删除补休统计
export function delComptimeStatistics(id) {
  return request({
    url: '/human/comptimeStatistics/' + id,
    method: 'delete'
  })
}
