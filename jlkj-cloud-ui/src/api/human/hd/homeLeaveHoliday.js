import request from '@/utils/request'

// 查询探亲假天数设定列表
export function listHoliday(query) {
  return request({
    url: '/human/homeLeaveHoliday/list',
    method: 'get',
    params: query
  })
}

// 查询探亲假天数
export function homeLeaveHoliday(query) {
  return request({
    url: '/human/homeLeaveHoliday/home',
    method: 'get',
    params: query
  })
}

// 查询探亲假天数设定详细
export function getHoliday(id) {
  return request({
    url: '/human/homeLeaveHoliday/' + id,
    method: 'get'
  })
}

// 新增探亲假天数设定
export function addHoliday(data) {
  return request({
    url: '/human/homeLeaveHoliday',
    method: 'post',
    data: data
  })
}

// 修改探亲假天数设定
export function updateHoliday(data) {
  return request({
    url: '/human/homeLeaveHoliday',
    method: 'put',
    data: data
  })
}

// 删除探亲假天数设定
export function delHoliday(id) {
  return request({
    url: '/human/homeLeaveHoliday/' + id,
    method: 'delete'
  })
}


