import request from '@/utils/request'

// 查询人员卡钟明细列表
export function listPersonColockDetail(query) {
  return request({
    url: '/human/personColockDetail/list',
    method: 'get',
    params: query
  })
}

// 查询人员卡钟明细详细
export function getPersonColockDetail(id) {
  return request({
    url: '/human/personColockDetail/' + id,
    method: 'get'
  })
}
// 通过卡钟查员工
export function getColockPerson(macId) {
  return request({
    url: '/human/personColockDetail/listPerson/' + macId,
    method: 'get'
  })
}

// 新增人员卡钟明细
export function addPersonColockDetail(data) {
  return request({
    url: '/human/personColockDetail',
    method: 'post',
    data: data
  })
}

// 修改人员卡钟明细
export function updatePersonColockDetail(data) {
  return request({
    url: '/human/personColockDetail',
    method: 'put',
    data: data
  })
}

// 删除人员卡钟明细
export function delPersonColockDetail(id) {
  return request({
    url: '/human/personColockDetail/' + id,
    method: 'delete'
  })
}
