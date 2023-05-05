import request from '@/utils/request'

// 查询计量单位维护列表
export function listUnit(query) {
  return request({
    url: '/finance/unit/list',
    method: 'get',
    params: query
  })
}

// 查询计量单位维护详细
export function getUnit(id) {
  return request({
    url: '/finance/unit/' + id,
    method: 'get'
  })
}

// 新增计量单位维护
export function addUnit(data) {
  return request({
    url: '/finance/unit',
    method: 'post',
    data: data
  })
}

// 修改计量单位维护
export function updateUnit(data) {
  return request({
    url: '/finance/unit',
    method: 'put',
    data: data
  })
}

// 删除计量单位维护
export function delUnit(id) {
  return request({
    url: '/finance/unit/' + id,
    method: 'delete'
  })
}
