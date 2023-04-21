import request from '@/utils/request'

// 查询现金流量代码列表
export function listCode(query) {
  return request({
    url: '/finance/code/list',
    method: 'get',
    params: query
  })
}

// 查询现金流量代码详细
export function getCode(id) {
  return request({
    url: '/finance/code/' + id,
    method: 'get'
  })
}

// 新增现金流量代码
export function addCode(data) {
  return request({
    url: '/finance/code',
    method: 'post',
    data: data
  })
}
// 获取全部树节点FinanceProduct
export function getTreeNode() {
  return request({
    url: '/finance/code/tree',
    method: 'get',
  })
}
// 修改现金流量代码
export function updateCode(data) {
  return request({
    url: '/finance/code',
    method: 'put',
    data: data
  })
}

// 删除现金流量代码
export function delCode(id) {
  return request({
    url: '/finance/code/' + id,
    method: 'delete'
  })
}
