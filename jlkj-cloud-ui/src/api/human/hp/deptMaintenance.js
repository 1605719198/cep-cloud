import request from '@/utils/request'

// 查询部门资料维护列表
export function listDeptmaintenance(query) {
  return request({
    url: '/human/deptMaintenance/list',
    method: 'get',
    params: query
  })
}

// 查询部门资料变更版本列表
export function listDeptversion(query) {
  return request({
    url: '/human/deptMaintenance/deptVersionlist',
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

// 查询部门资料维护详细
export function getVersion(id) {
  return request({
    url: '/human/deptMaintenance/getVersion/' + id,
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

// 复制部门资料维护
export function copySysDept(data) {
  return request({
    url: '/human/deptMaintenance/copySysDept',
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
//获取部门树形选择器
export function treeselect(query) {
  return request({
    url: '/human/deptMaintenance/treeselect',
    method: 'get',
    params: query
  })
}

//获取部门岗位树形选择器
export function deptpostTree(query) {
  return request({
    url: '/human/deptMaintenance/deptpostTree',
    method: 'get',
    params: query
  })
}

//获取公司列表
export function selectCompany() {
  return request({
    url: '/human/deptMaintenance/selectCompany',
    method: 'get',
  })
}

//通过员工工号查询一级机构
export function queryFirstdeptByPerson(empId){
  return request({
    url: 'human/deptMaintenance/queryFirstDeptByPerson/' + empId,
    method: 'get'
  })
}

//通过机构ID查询一级机构
export function queryFirstdeptByDept(deptId){
  return request({
    url: 'human/deptMaintenance/queryFirstDeptByDept/' + deptId,
    method: 'get'
  })
}

//通过岗位id查用户
export function queryPersonByPost(query){
  return request({
    url:'human/personnel/base/getPostLevelEmployee',
    method:'get',
    params:query
  })
}

