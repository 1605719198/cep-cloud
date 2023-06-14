import request from '@/utils/request'

// 查询报支类别公司细项列表
export function listItemDetailCompany(query) {
  return request({
    url: '/finance/itemDetailCompany/list',
    method: 'get',
    params: query
  })
}

// 查询报支类别公司细项详细
export function getItemDetailCompany(id) {
  return request({
    url: '/finance/itemDetailCompany/' + id,
    method: 'get'
  })
}

// 新增报支类别公司细项
export function addItemDetailCompany(data) {
  return request({
    url: '/finance/itemDetailCompany',
    method: 'post',
    data: data
  })
}

// 修改报支类别公司细项
export function updateItemDetailCompany(data) {
  return request({
    url: '/finance/itemDetailCompany',
    method: 'put',
    data: data
  })
}

// 删除报支类别公司细项
export function delItemDetailCompany(id) {
  return request({
    url: '/finance/itemDetailCompany/' + id,
    method: 'delete'
  })
}

// 复制集团表数据
export function copyToComp(data) {
  return request({
    url: '/finance/itemComp/copyToComp',
    method: 'post',
    data: data
  })
}
