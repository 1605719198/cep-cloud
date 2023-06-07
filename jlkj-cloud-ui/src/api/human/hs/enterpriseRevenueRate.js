import request from '@/utils/request'

// 查询企业效益系数设定列表
export function listEnterpriseRevenueRate(query) {
  return request({
    url: '/human/enterpriseRevenueRate/list',
    method: 'get',
    params: query
  })
}

// 查询企业效益系数设定详细
export function getEnterpriseRevenueRate(id) {
  return request({
    url: '/human/enterpriseRevenueRate/' + id,
    method: 'get'
  })
}

// 新增企业效益系数设定
export function addEnterpriseRevenueRate(data) {
  return request({
    url: '/human/enterpriseRevenueRate',
    method: 'post',
    data: data
  })
}

// 修改企业效益系数设定
export function updateEnterpriseRevenueRate(data) {
  return request({
    url: '/human/enterpriseRevenueRate',
    method: 'put',
    data: data
  })
}

// 删除企业效益系数设定
export function delEnterpriseRevenueRate(id) {
  return request({
    url: '/human/enterpriseRevenueRate/' + id,
    method: 'delete'
  })
}
