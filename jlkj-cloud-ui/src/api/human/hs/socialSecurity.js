import request from '@/utils/request'

// 查询社保公积金缴费比例设定列表
export function listSocialSecurity(query) {
  return request({
    url: '/human/socialSecurity/list',
    method: 'get',
    params: query
  })
}

// 查询社保公积金缴费比例设定详细
export function getSocialSecurity(id) {
  return request({
    url: '/human/socialSecurity/' + id,
    method: 'get'
  })
}

// 新增社保公积金缴费比例设定
export function addSocialSecurity(data) {
  return request({
    url: '/human/socialSecurity',
    method: 'post',
    data: data
  })
}

// 修改社保公积金缴费比例设定
export function updateSocialSecurity(data) {
  return request({
    url: '/human/socialSecurity',
    method: 'put',
    data: data
  })
}

// 删除社保公积金缴费比例设定
export function delSocialSecurity(id) {
  return request({
    url: '/human/socialSecurity/' + id,
    method: 'delete'
  })
}
