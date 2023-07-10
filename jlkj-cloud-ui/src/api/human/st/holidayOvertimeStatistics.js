import request from '@/utils/request'

// 查询倒班人员法定假日加班统计列表
export function listHolidayOvertimeStatistics(query) {
  return request({
    url: '/human/holidayOvertimeStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询倒班人员法定假日加班统计详细
export function getHolidayOvertimeStatistics(id) {
  return request({
    url: '/human/holidayOvertimeStatistics/' + id,
    method: 'get'
  })
}

// 新增倒班人员法定假日加班统计
export function addHolidayOvertimeStatistics(data) {
  return request({
    url: '/human/holidayOvertimeStatistics',
    method: 'post',
    data: data
  })
}

// 修改倒班人员法定假日加班统计
export function updateHolidayOvertimeStatistics(data) {
  return request({
    url: '/human/holidayOvertimeStatistics',
    method: 'put',
    data: data
  })
}

// 删除倒班人员法定假日加班统计
export function delHolidayOvertimeStatistics(id) {
  return request({
    url: '/human/holidayOvertimeStatistics/' + id,
    method: 'delete'
  })
}
