import request from '@/utils/request'

// 查询各公司平均天数设定列表
export function listAverageDays(query) {
  return request({
    url: '/human/averageDays/list',
    method: 'get',
    params: query
  })
}

// 查询各公司平均天数设定详细
export function getAverageDays(uuid) {
  return request({
    url: '/human/averageDays/' + uuid,
    method: 'get'
  })
}

// 新增各公司平均天数设定
export function addAverageDays(data) {
  return request({
    url: '/human/averageDays',
    method: 'post',
    data: data
  })
}

// 修改各公司平均天数设定
export function updateAverageDays(data) {
  return request({
    url: '/human/averageDays',
    method: 'put',
    data: data
  })
}

// 删除各公司平均天数设定
export function delAverageDays(uuid) {
  return request({
    url: '/human/averageDays/' + uuid,
    method: 'delete'
  })
}
