import request from '@/utils/request'

// 查询班次数据列表
export function listShiftCode(query) {
  return request({
    url: '/human/shiftCode/list',
    method: 'get',
    params: query
  })
}

// 查询班次数据详细
export function getShiftCode(id) {
  return request({
    url: '/human/shiftCode/' + id,
    method: 'get'
  })
}

// 新增班次数据
export function addShiftCode(data) {
  return request({
    url: '/human/shiftCode',
    method: 'post',
    data: data
  })
}

// 修改班次数据
export function updateShiftCode(data) {
  return request({
    url: '/human/shiftCode',
    method: 'put',
    data: data
  })
}

// 删除班次数据
export function delShiftCode(id) {
  return request({
    url: '/human/shiftCode/' + id,
    method: 'delete'
  })
}



