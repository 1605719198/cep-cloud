import request from '@/utils/request'

// 查询年休假统计列表
export function listYearHolidayStatistics(query) {
  return request({
    url: '/human/yearHolidayStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询年休假统计详细
export function getYearHolidayStatistics(id) {
  return request({
    url: '/human/yearHolidayStatistics/' + id,
    method: 'get'
  })
}

// 新增年休假统计
export function addYearHolidayStatistics(data) {
  return request({
    url: '/human/yearHolidayStatistics',
    method: 'post',
    data: data
  })
}

// 修改年休假统计
export function updateYearHolidayStatistics(data) {
  return request({
    url: '/human/yearHolidayStatistics',
    method: 'put',
    data: data
  })
}

// 删除年休假统计
export function delYearHolidayStatistics(id) {
  return request({
    url: '/human/yearHolidayStatistics/' + id,
    method: 'delete'
  })
}
