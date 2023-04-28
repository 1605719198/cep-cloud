import request from '@/utils/request'

// 查询年休假天数设定列表
export function listHolidayYear(query) {
  return request({
    url: '/human/holiday/year/list',
    method: 'get',
    params: query
  })
}

// 查询员工年休假信息
export function yearHoliday(query) {
  return request({
    url: '/human/holiday/year/year',
    method: 'get',
    params: query
  })
}

// 查询年休假天数设定详细
export function getHoliday(id) {
  return request({
    url: '/human/holiday/year/' + id,
    method: 'get'
  })
}

// 新增年休假天数设定
export function addHoliday(data) {
  return request({
    url: '/human/holiday/year',
    method: 'post',
    data: data
  })
}

// 修改年休假天数设定
export function updateHoliday(data) {
  return request({
    url: '/human/holiday/year',
    method: 'put',
    data: data
  })
}

// 删除年休假天数设定
export function delHoliday(id) {
  return request({
    url: '/human/holiday/year/' + id,
    method: 'delete'
  })
}
