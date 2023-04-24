import request from '@/utils/request'

// 查询员工请假记录列表
export function listPersonHoliday(query) {
  return request({
    url: '/human/personHoliday/list',
    method: 'get',
    params: query
  })
}

// 查询员工请假记录详细
export function getPersonHoliday(id) {
  return request({
    url: '/human/personHoliday/' + id,
    method: 'get'
  })
}

// 新增员工请假记录
export function addPersonHoliday(data) {
  return request({
    url: '/human/personHoliday',
    method: 'post',
    data: data
  })
}

// 修改员工请假记录
export function updatePersonHoliday(data) {
  return request({
    url: '/human/personHoliday',
    method: 'put',
    data: data
  })
}

// 删除员工请假记录
export function delPersonHoliday(id) {
  return request({
    url: '/human/personHoliday/' + id,
    method: 'delete'
  })
}
