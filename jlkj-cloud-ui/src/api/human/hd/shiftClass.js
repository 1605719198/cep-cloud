import request from '@/utils/request'

// 查询班别数据列表
export function listShiftClass(query) {
  return request({
    url: '/human/shiftClass/list',
    method: 'get',
    params: query
  })
}

// 查询班别数据详细
export function getShiftClass(id) {
  return request({
    url: '/human/shiftClass/' + id,
    method: 'get'
  })
}

// 新增班别数据
export function addShiftClass(data) {
  return request({
    url: '/human/shiftClass',
    method: 'post',
    data: data
  })
}

// 修改班别数据
export function updateShiftClass(data) {
  return request({
    url: '/human/shiftClass',
    method: 'put',
    data: data
  })
}

// 删除班别数据
export function delShiftClass(id) {
  return request({
    url: '/human/shiftClass/' + id,
    method: 'delete'
  })
}
