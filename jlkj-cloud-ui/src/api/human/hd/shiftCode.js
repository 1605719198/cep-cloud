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

// 查询班次信息
export function queryShiftCode(query) {
  return request({
    url: '/human/shiftCode/queryShiftCode' ,
    method: 'get',
    params: query
  })
}

// 查询人员班次数据详细
export function getShiftCodeByPerson(data) {
  return request({
    url: '/human/shiftCode/getShiftData',
    method: 'post',
    params: data
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



