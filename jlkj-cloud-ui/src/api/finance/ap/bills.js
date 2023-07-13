import request from '@/utils/request'

// 查询发票信息列表
export function listBills(query) {
  return request({
    url: '/finance/ap/bills/list',
    method: 'get',
    params: query
  })
}

// 查询发票信息详细
export function getBills(id) {
  return request({
    url: '/finance/ap/bills/' + id,
    method: 'get'
  })
}

// 新增发票信息
export function addBills(data) {
  return request({
    url: '/finance/ap/bills',
    method: 'post',
    data: data
  })
}

// 修改发票信息
export function updateBills(data) {
  return request({
    url: '/finance/ap/bills',
    method: 'put',
    data: data
  })
}

// 删除发票信息
export function delBills(id) {
  return request({
    url: '/finance/ap/bills/' + id,
    method: 'delete'
  })
}
