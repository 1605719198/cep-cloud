import request from '@/utils/request'

// 查询报支类别公司设置列表
export function listItemComp(query) {
  return request({
    url: '/finance/itemComp/list',
    method: 'get',
    params: query
  })
}

// 查询报支类别公司设置详细
export function getItemComp(id) {
  return request({
    url: '/finance/itemComp/' + id,
    method: 'get'
  })
}

// 新增报支类别公司设置
export function addItemComp(data) {
  return request({
    url: '/finance/itemComp',
    method: 'post',
    data: data
  })
}

// 修改报支类别公司设置
export function updateItemComp(data) {
  return request({
    url: '/finance/itemComp',
    method: 'put',
    data: data
  })
}

// 删除报支类别公司设置
export function delItemComp(id) {
  return request({
    url: '/finance/itemComp/' + id,
    method: 'delete'
  })
}

// 批量新增报支类别-公司级
export function addBatchData(data) {
  return request({
    url: '/finance/itemComp/batch',
    method: 'post',
    data: data
  })
}
