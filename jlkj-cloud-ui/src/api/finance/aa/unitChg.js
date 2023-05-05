import request from '@/utils/request'

// 查询计量单位转换列表
export function listChg(query) {
  return request({
    url: '/finance/chg/list',
    method: 'get',
    params: query
  })
}

// 查询计量单位转换详细
export function getChg(id) {
  return request({
    url: '/finance/chg/' + id,
    method: 'get'
  })
}

// 新增计量单位转换
export function addChg(data) {
  return request({
    url: '/finance/chg',
    method: 'post',
    data: data
  })
}

// 修改计量单位转换
export function updateChg(data) {
  return request({
    url: '/finance/chg',
    method: 'put',
    data: data
  })
}

// 删除计量单位转换
export function delChg(id) {
  return request({
    url: '/finance/chg/' + id,
    method: 'delete'
  })
}
