import request from '@/utils/request'

// 查询社保公积金缴费公司维护列表
export function listSocialSecurityCompany(query) {
  return request({
    url: '/human/socialSecurityCompany/list',
    method: 'get',
    params: query
  })
}

// 查询社保公积金缴费公司维护详细
export function getSocialSecurityCompany(id) {
  return request({
    url: '/human/socialSecurityCompany/' + id,
    method: 'get'
  })
}

// 新增社保公积金缴费公司维护
export function addSocialSecurityCompany(data) {
  return request({
    url: '/human/socialSecurityCompany/save',
    method: 'post',
    data: data
  })
}

// 修改社保公积金缴费公司维护
export function updateSocialSecurityCompany(data) {
  return request({
    url: '/human/socialSecurityCompany',
    method: 'put',
    data: data
  })
}

// 删除社保公积金缴费公司维护
export function delSocialSecurityCompany(id) {
  return request({
    url: '/human/socialSecurityCompany/' + id,
    method: 'delete'
  })
}
