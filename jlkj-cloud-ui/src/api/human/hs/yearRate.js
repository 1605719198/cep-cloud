import request from '@/utils/request'

// 查询年度奖金比例设定列表
export function listYearRate(query) {
  return request({
    url: '/human/yearRate/list',
    method: 'get',
    params: query
  })
}

// 查询年度奖金比例设定详细
export function getYearRate(id) {
  return request({
    url: '/human/yearRate/' + id,
    method: 'get'
  })
}

// 保存年度奖金比例设定
export function addYearRate(data) {
  return request({
    url: '/human/yearRate',
    method: 'post',
    data: data
  })
}

// 修改年度奖金比例设定
export function updateYearRate(data) {
  return request({
    url: '/human/yearRate',
    method: 'put',
    data: data
  })
}

// 删除年度奖金比例设定
export function delYearRate(id) {
  return request({
    url: '/human/yearRate/' + id,
    method: 'delete'
  })
}
