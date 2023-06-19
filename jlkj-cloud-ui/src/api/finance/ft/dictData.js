import request from '@/utils/request'

// 查询选单内容设定列表
export function listDictData(query) {
  return request({
    url: '/finance/dictData/list',
    method: 'get',
    params: query
  })
}

// 查询选单内容设定详细
export function getDictData(uuid) {
  return request({
    url: '/finance/dictData/' + uuid,
    method: 'get'
  })
}

// 新增选单内容设定
export function addDictData(data) {
  return request({
    url: '/finance/dictData',
    method: 'post',
    data: data
  })
}

// 修改选单内容设定
export function updateDictData(data) {
  return request({
    url: '/finance/dictData',
    method: 'put',
    data: data
  })
}

// 删除选单内容设定
export function delDictData(uuid) {
  return request({
    url: '/finance/dictData/' + uuid,
    method: 'delete'
  })
}
