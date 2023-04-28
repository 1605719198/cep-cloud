import request from '@/utils/request'

// 查询假日设定列表
export function listHolidayTable(query) {
  return request({
    url: '/human/holidayTable/list',
    method: 'get',
    params: query
  })
}

// 查询时间段内假日设定
export function getHoliday(query) {
  return request({
    url: '/human/holidayTable/getHolidayTable',
    method: 'get',
    params: query
  })
}

// 查询假日设定详细
export function getHolidayTable(id) {
  return request({
    url: '/human/holidayTable/' + id,
    method: 'get'
  })
}

// 新增假日设定
export function addHolidayTable(data) {
  return request({
    url: '/human/holidayTable',
    method: 'post',
    data: data
  })
}

// 新增假日设定
export function addYearHolidayTable(data) {
  return request({
    url: '/human/holidayTable/addYear',
    method: 'post',
    data: data
  })
}

// 修改假日设定
export function updateHolidayTable(data) {
  return request({
    url: '/human/holidayTable',
    method: 'put',
    data: data
  })
}

// 删除假日设定
export function delHolidayTable(id) {
  return request({
    url: '/human/holidayTable/' + id,
    method: 'delete'
  })
}


