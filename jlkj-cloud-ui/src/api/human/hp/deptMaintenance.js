import request from '@/utils/request'

// 查询部门资料维护列表
export function listDeptmaintenance(query) {
  return request({
    url: '/human/deptMaintenance/list',
    method: 'get',
    params: query
  })
}

// 查询部门资料维护详细
export function getDeptmaintenance(deptId) {
  return request({
    url: '/human/deptMaintenance/' + deptId,
    method: 'get'
  })
}

// 新增部门资料维护
export function addDeptmaintenance(data) {
  return request({
    url: '/human/deptMaintenance',
    method: 'post',
    data: data
  })
}

// 修改部门资料维护
export function updateDeptmaintenance(data) {
  return request({
    url: '/human/deptMaintenance',
    method: 'put',
    data: data
  })
}

// 删除部门资料维护
export function delDeptmaintenance(deptId) {
  return request({
    url: '/human/deptMaintenance/' + deptId,
    method: 'delete'
  })
}
//获取树形选择器
export function treeselect() {
  return request({
    url: '/human/deptMaintenance/treeselect',
    method: 'get'
  })
}
