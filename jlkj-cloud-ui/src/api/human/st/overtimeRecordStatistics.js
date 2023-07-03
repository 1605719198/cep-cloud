import request from '@/utils/request'

// 查询加班统计列表
export function listOvertimeRecordStatistics(query) {
  return request({
    url: '/human/overtimeRecordStatistics/list',
    method: 'get',
    params: query
  })
}

// 查询加班统计详细
export function getOvertimeRecordStatistics(id) {
  return request({
    url: '/human/overtimeRecordStatistics/' + id,
    method: 'get'
  })
}

// 新增加班统计
export function addOvertimeRecordStatistics(data) {
  return request({
    url: '/human/overtimeRecordStatistics',
    method: 'post',
    data: data
  })
}

// 修改加班统计
export function updateOvertimeRecordStatistics(data) {
  return request({
    url: '/human/overtimeRecordStatistics',
    method: 'put',
    data: data
  })
}

// 删除加班统计
export function delOvertimeRecordStatistics(id) {
  return request({
    url: '/human/overtimeRecordStatistics/' + id,
    method: 'delete'
  })
}
