import request from '@/utils/request'

// 查询团队绩效主档列表
export function listTeamPerformance(query) {
  return request({
    url: '/human/teamPerformance/list',
    method: 'get',
    params: query
  })
}

// 查询团队绩效明细档列表
export function listTeamPerformanceDetail(query) {
  return request({
    url: '/human/teamPerformance/listTeamPerformanceDetail',
    method: 'get',
    params: query
  })
}

// 新增团队绩效主档
export function addTeamPerformance(data) {
  return request({
    url: '/human/teamPerformance',
    method: 'post',
    data: data
  })
}

// 修改团队绩效明细档
export function updateTeamPerformanceDetail(data) {
  return request({
    url: '/human/teamPerformance',
    method: 'put',
    data: data
  })
}

// 删除团队绩效主档和明细档
export function delTeamPerformance(data) {
  return request({
    url: '/human/teamPerformance/delTeamPerformance',
    method: 'post',
    data: data
  })
}

// 确认
export function updateTeamPerformanceMeritStatus(data) {
  return request({
    url: '/human/teamPerformance/updateTeamPerformanceMeritStatus',
    method: 'post',
    data: data
  })
}
