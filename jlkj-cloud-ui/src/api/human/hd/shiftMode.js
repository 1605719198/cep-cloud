import request from '@/utils/request'

// 查询轮班方式数据列表
export function listShiftMode(query) {
  return request({
    url: '/human/shiftMode/list',
    method: 'get',
    params: query
  })
}

// 查询轮班方式数据列表
export function listShiftModeAll(query) {
  return request({
    url: '/human/shiftMode/listAll',
    method: 'get',
    params: query
  })
}

// 查询轮班方式数据详细
export function getShiftMode(id) {
  return request({
    url: '/human/shiftMode/' + id,
    method: 'get'
  })
}

// 新增轮班方式数据
export function addShiftMode(data) {
  return request({
    url: '/human/shiftMode',
    method: 'post',
    data: data
  })
}

// 修改轮班方式数据
export function updateShiftMode(data) {
  return request({
    url: '/human/shiftMode',
    method: 'put',
    data: data
  })
}

// 删除轮班方式数据
export function delShiftMode(id) {
  return request({
    url: '/human/shiftMode/' + id,
    method: 'delete'
  })
}
