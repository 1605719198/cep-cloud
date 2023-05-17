import request from '@/utils/request'

// 查询计量单位分组列表
export function listUnitGroup(query) {
  return request({
    url: '/finance/unitGroup/list',
    method: 'get',
    params: query
  })
}
// 查询会计公司清单(下拉选单用)
export function select1() {
  return request({
    url: '/finance/unitGroup/select1',
    method: 'get',
  })
}

// 查询计量单位分组详细
export function getUnitGroup(id) {
  return request({
    url: '/finance/unitGroup/' + id,
    method: 'get'
  })
}

// 新增计量单位分组
export function addUnitGroup(data) {
  return request({
    url: '/finance/unitGroup',
    method: 'post',
    data: data
  })
}

// 修改计量单位分组
export function updateUnitGroup(data) {
  return request({
    url: '/finance/unitGroup',
    method: 'put',
    data: data
  })
}

// 删除计量单位分组
export function delUnitGroup(id) {
  return request({
    url: '/finance/unitGroup/' + id,
    method: 'delete'
  })
}
