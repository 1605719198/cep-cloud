import request from '@/utils/request'

// 查询员工请假统计列表
export function listPersonHolidayStatistics(query) {
  return request({
    url: '/human/personHolidayStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询员工请假统计详细
export function getPersonHolidayStatistics(id) {
  return request({
    url: '/human/personHolidayStatistics/' + id,
    method: 'get'
  })
}

// 新增员工请假统计
export function addPersonHolidayStatistics(data) {
  return request({
    url: '/human/personHolidayStatistics',
    method: 'post',
    data: data
  })
}

// 修改员工请假统计
export function updatePersonHolidayStatistics(data) {
  return request({
    url: '/human/personHolidayStatistics',
    method: 'put',
    data: data
  })
}

// 删除员工请假统计
export function delPersonHolidayStatistics(id) {
  return request({
    url: '/human/personHolidayStatistics/' + id,
    method: 'delete'
  })
}
