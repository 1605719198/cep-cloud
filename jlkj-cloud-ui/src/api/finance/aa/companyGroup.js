import request from '@/utils/request'

// 查询集团管理-会计公司维护列表
export function listCompanyGroup(query) {
  return request({
    url: '/finance/companyGroup/list',
    method: 'get',
    params: query
  })
}

// 查询集团管理-会计公司维护详细
export function getCompanyGroup(id) {
  return request({
    url: '/finance/companyGroup/' + id,
    method: 'get'
  })
}

// 新增集团管理-会计公司维护
export function addCompanyGroup(data) {
  return request({
    url: '/finance/companyGroup',
    method: 'post',
    data: data
  })
}

// 修改集团管理-会计公司维护
export function updateCompanyGroup(data) {
  return request({
    url: '/finance/companyGroup',
    method: 'put',
    data: data
  })
}

// 删除集团管理-会计公司维护
export function delCompanyGroup(id) {
  return request({
    url: '/finance/companyGroup/' + id,
    method: 'delete'
  })
}


// 查询会计公司清单(下拉选单用)
export function selectCompanyList() {
  return request({
    url: '/finance/companyGroup/companyList',
    method: 'get',
  })
}
