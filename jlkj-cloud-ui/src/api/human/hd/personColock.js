import request from '@/utils/request'

// 查询人员卡钟列表
export function listPersonColock(query) {
  return request({
    url: '/human/personColock/list',
    method: 'get',
    params: query
  })
}

// 查询人员卡钟详细
export function getPersonColock(id) {
  return request({
    url: '/human/personColock/' + id,
    method: 'get'
  })
}

// 新增人员卡钟
export function addPersonColock(data) {
  return request({
    url: '/human/personColock',
    method: 'post',
    data: data
  })
}

// 修改人员卡钟
export function updatePersonColock(data) {
  return request({
    url: '/human/personColock',
    method: 'put',
    data: data
  })
}

// 删除人员卡钟
export function delPersonColock(id) {
  return request({
    url: '/human/personColock/' + id,
    method: 'delete'
  })
}
