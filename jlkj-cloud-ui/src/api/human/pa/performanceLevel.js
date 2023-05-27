import request from '@/utils/request'

// 查询绩效系数设定列表
export function listPerformanceLevel(query) {
  return request({
    url: '/human/performanceLevel/list',
    method: 'get',
    params: query
  })
}

// 查询绩效系数设定详细
export function getPerformanceLevel(uuid) {
  return request({
    url: '/human/performanceLevel/' + uuid,
    method: 'get'
  })
}

// 新增绩效系数设定
export function addPerformanceLevel(data) {
  return request({
    url: '/human/performanceLevel',
    method: 'post',
    data: data
  })
}


// 删除绩效系数设定
export function delPerformanceLevel(uuid) {
  return request({
    url: '/human/performanceLevel/' + uuid,
    method: 'delete'
  })
}
