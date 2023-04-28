import request from '@/utils/request'

// 查询假别参数设定列表
export function listHolidaysetting(query) {
  return request({
    url: '/human/holidaysetting/list',
    method: 'get',
    params: query
  })
}

// 查询公司假别参数设定
export function getCompHolidaySetting(query) {
  return request({
    url: '/human/holidaysetting/getSetting',
    method: 'get',
    params: query
  })
}

// 查询假别参数设定详细
export function getHolidaysetting(id) {
  return request({
    url: '/human/holidaysetting/' + id,
    method: 'get'
  })
}

// 新增假别参数设定
export function addHolidaysetting(data) {
  return request({
    url: '/human/holidaysetting',
    method: 'post',
    data: data
  })
}

// 复制假别参数设定
export function copyHolidaysetting(data) {
  return request({
    url: '/human/holidaysetting/copyholidaysetting',
    method: 'post',
    data: data
  })
}

// 修改假别参数设定
export function updateHolidaysetting(data) {
  return request({
    url: '/human/holidaysetting',
    method: 'put',
    data: data
  })
}

// 删除假别参数设定
export function delHolidaysetting(id) {
  return request({
    url: '/human/holidaysetting/' + id,
    method: 'delete'
  })
}
