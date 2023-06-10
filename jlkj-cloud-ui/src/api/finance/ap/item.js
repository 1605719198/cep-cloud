import request from '@/utils/request'

// 查询报支类别集团设置列表
export function listItem(query) {
  return request({
    url: '/finance/apItem/list',
    method: 'get',
    params: query
  })
}

// 查询报支类别集团设置详细
export function getItem(id) {
  return request({
    url: '/finance/apItem/' + id,
    method: 'get'
  })
}

// 新增报支类别集团设置
export function addItem(data) {
  return request({
    url: '/finance/apItem',
    method: 'post',
    data: data
  })
}

// 修改报支类别集团设置
export function updateItem(data) {
  return request({
    url: '/finance/apItem',
    method: 'put',
    data: data
  })
}

// 删除报支类别集团设置
export function delItem(id) {
  return request({
    url: '/finance/apItem/' + id,
    method: 'delete'
  })
}

export function listItemAndDetail(companyId) {
  return request({
    url: '/finance/apItem/itemList/' + companyId,
    method: 'get'
  })
}
