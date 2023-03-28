import request from '@/utils/request'

// 查询机构参数列表
export function listOrganizationSetting(query) {
  return request({
    url: '/human/organizationSetting/list',
    method: 'get',
    params: query
  })
}

// 查询机构参数详细
export function getOrganizationSetting(id) {
  return request({
    url: '/human/organizationSetting/' + id,
    method: 'get'
  })
}

// 新增机构参数
export function addOrganizationSetting(data) {
  return request({
    url: '/human/organizationSetting',
    method: 'post',
    data: data
  })
}

// 修改机构参数
export function updateOrganizationSetting(data) {
  return request({
    url: '/human/organizationSetting',
    method: 'put',
    data: data
  })
}

// 删除机构参数
export function delOrganizationSetting(id) {
  return request({
    url: '/human/organizationSetting/' + id,
    method: 'delete'
  })
}
