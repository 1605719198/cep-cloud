import request from '@/utils/request'

// 查询现金流量代码公司级列表
export function listCodecompid(query) {
  return request({
    url: '/finance/codecompid/list',
    method: 'get',
    params: query
  })
}
// 获取全部树节点FinanceProduct
export function getTreeNodeCompId(companyId) {
  return request({
    url: `/finance/codecompid/treeCompanyId?companyId=${companyId}`,
    method: 'get',
  })
}
// 查询现金流量代码公司级详细
export function getCodecompid(id) {
  return request({
    url: '/finance/codecompid/' + id,
    method: 'get'
  })
}

// 新增现金流量代码公司级
export function addCodecompid(data) {
  return request({
    url: '/finance/codecompid',
    method: 'post',
    data: data
  })
}

// 修改现金流量代码公司级
export function updateCodecompid(data) {
  return request({
    url: '/finance/codecompid',
    method: 'put',
    data: data
  })
}

// 删除现金流量代码公司级
export function delCodecompid(id) {
  return request({
    url: '/finance/codecompid/' + id,
    method: 'delete'
  })
}
