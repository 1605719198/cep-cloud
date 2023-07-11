import request from '@/utils/request'

// 查询年度排班统计列表
export function listSchedulingStatistics(query) {
  return request({
    url: '/human/schedulingStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询年度排班统计详细
export function getSchedulingStatistics(id) {
  return request({
    url: '/human/schedulingStatistics/' + id,
    method: 'get'
  })
}

// 新增年度排班统计
export function addSchedulingStatistics(data) {
  return request({
    url: '/human/schedulingStatistics',
    method: 'post',
    data: data
  })
}

// 修改年度排班统计
export function updateSchedulingStatistics(data) {
  return request({
    url: '/human/schedulingStatistics',
    method: 'put',
    data: data
  })
}

// 删除年度排班统计
export function delSchedulingStatistics(id) {
  return request({
    url: '/human/schedulingStatistics/' + id,
    method: 'delete'
  })
}
