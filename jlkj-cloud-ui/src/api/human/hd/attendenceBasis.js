import request from '@/utils/request'

// 查询员工出勤基本资料维护列表
export function listAttendenceBasis(query) {
  return request({
    url: '/human/attendenceBasis/list',
    method: 'get',
    params: query
  })
}

// 查询员工出勤基本资料维护详细
export function getAttendenceBasis(id) {
  return request({
    url: '/human/attendenceBasis/' + id,
    method: 'get'
  })
}

// 新增员工出勤基本资料维护
export function addAttendenceBasis(data) {
  return request({
    url: '/human/attendenceBasis',
    method: 'post',
    data: data
  })
}

// 修改员工出勤基本资料维护
export function updateAttendenceBasis(data) {
  return request({
    url: '/human/attendenceBasis',
    method: 'put',
    data: data
  })
}

// 删除员工出勤基本资料维护
export function delAttendenceBasis(id) {
  return request({
    url: '/human/attendenceBasis/' + id,
    method: 'delete'
  })
}

//获取出勤树结构数据
export function treeselect(query) {
  return request({
    url: '/human/attendenceBasis/treeselect',
    method: 'get',
    params: query
  })
}

//获取基础选单
export function getBasisOptions(code) {
  return request({
    url: '/human/attendenceBasis/getBasisOptions/'+ code,
    method: 'get',
  })
}
