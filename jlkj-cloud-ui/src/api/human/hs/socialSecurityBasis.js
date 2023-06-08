import request from '@/utils/request'

// 查询社保公积金标准核定列表
export function listSocialSecurityBasis(query) {
  return request({
    url: '/human/socialSecurityBasis/list',
    method: 'get',
    params: query
  })
}

// 查询社保公积金标准核定详细
export function getSocialSecurityBasis(id) {
  return request({
    url: '/human/socialSecurityBasis/' + id,
    method: 'get'
  })
}

// 新增社保公积金标准核定
export function addSocialSecurityBasis(data) {
  return request({
    url: '/human/socialSecurityBasis',
    method: 'post',
    data: data
  })
}

// 修改社保公积金标准核定
export function updateSocialSecurityBasis(data) {
  return request({
    url: '/human/socialSecurityBasis',
    method: 'put',
    data: data
  })
}

// 删除社保公积金标准核定
export function delSocialSecurityBasis(id) {
  return request({
    url: '/human/socialSecurityBasis/' + id,
    method: 'delete'
  })
}
