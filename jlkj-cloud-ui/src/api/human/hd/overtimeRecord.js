import request from '@/utils/request'

// 查询加班记录列表
export function listOvertimeRecord(query) {
  return request({
    url: '/human/overtimeRecord/list',
    method: 'get',
    params: query
  })
}

// 查询加班记录详细
export function getOvertimeRecord(id) {
  return request({
    url: '/human/overtimeRecord/' + id,
    method: 'get'
  })
}

// 新增加班记录
export function addOvertimeRecord(data) {
  return request({
    url: '/human/overtimeRecord',
    method: 'post',
    data: data
  })
}

// 送出加班数据
export function sendOvertimeRecord(data) {
  return request({
    url: '/human/overtimeRecord/sendOvertimeRecord',
    method: 'post',
    data: data
  })
}

// 修改加班记录
export function updateOvertimeRecord(data) {
  return request({
    url: '/human/overtimeRecord',
    method: 'put',
    data: data
  })
}

// 删除加班记录
export function delOvertimeRecord(id) {
  return request({
    url: '/human/overtimeRecord/' + id,
    method: 'delete'
  })
}
