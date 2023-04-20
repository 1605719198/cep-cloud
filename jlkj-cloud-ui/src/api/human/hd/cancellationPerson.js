import request from '@/utils/request'

// 查询人事注销记录列表
export function listCancellationPerson(query) {
  return request({
    url: '/human/cancellationPerson/list',
    method: 'get',
    params: query
  })
}

// 查询人事注销记录详细
export function getCancellationPerson(id) {
  return request({
    url: '/human/cancellationPerson/' + id,
    method: 'get'
  })
}

// 新增人事注销记录
export function addCancellationPerson(data) {
  return request({
    url: '/human/cancellationPerson',
    method: 'post',
    data: data
  })
}

// 修改人事注销记录
export function updateCancellationPerson(data) {
  return request({
    url: '/human/cancellationPerson',
    method: 'put',
    data: data
  })
}

// 删除人事注销记录
export function delCancellationPerson(id) {
  return request({
    url: '/human/cancellationPerson/' + id,
    method: 'delete'
  })
}
