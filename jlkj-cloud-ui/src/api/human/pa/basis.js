import request from '@/utils/request'

// 查询员工出勤基本资料维护列表
export function listBasis(query) {
  return request({
    url: '/human/basis/list',
    method: 'get',
    params: query
  })
}

// 查询员工出勤基本资料维护详细
export function getBasis(id) {
  return request({
    url: '/human/basis/' + id,
    method: 'get'
  })
}

// 新增员工出勤基本资料维护
export function addBasis(data) {
  return request({
    url: '/human/basis',
    method: 'post',
    data: data
  })
}

// 修改员工出勤基本资料维护
export function updateBasis(data) {
  return request({
    url: '/human/basis',
    method: 'put',
    data: data
  })
}

// 删除员工出勤基本资料维护
export function delBasis(id) {
  return request({
    url: '/human/basis/' + id,
    method: 'delete'
  })
}

//获取出勤树结构数据
export function treeselect(query) {
  return request({
    url: '/human/basis/treeselect',
    method: 'get',
    params: query
  })
}

//获取基础选单
export function getPerformanceOptions(data) {
  return request({
    url: '/human/basis/getBasisOptions',
    method: 'get',
    params: data
  })
}
