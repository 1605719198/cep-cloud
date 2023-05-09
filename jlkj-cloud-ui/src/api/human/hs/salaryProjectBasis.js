import request from '@/utils/request'

// 查询集团级薪资项目输入维护列表
export function listSalaryProjectBasis(query) {
  return request({
    url: '/human/salaryProjectBasis/list',
    method: 'get',
    params: query
  })
}

// 查询集团级薪资项目输入维护详细
export function getSalaryProjectBasis(id) {
  return request({
    url: '/human/salaryProjectBasis/' + id,
    method: 'get'
  })
}

// 新增集团级薪资项目输入维护
export function addSalaryProjectBasis(data) {
  return request({
    url: '/human/salaryProjectBasis',
    method: 'post',
    data: data
  })
}

// 修改集团级薪资项目输入维护
export function updateSalaryProjectBasis(data) {
  return request({
    url: '/human/salaryProjectBasis',
    method: 'put',
    data: data
  })
}

//获取出勤树结构数据
export function treeselect(query) {
  return request({
    url: '/human/salaryProjectBasis/treeselect',
    method: 'get',
    params: query
  })
}

// 删除集团级薪资项目输入维护
export function delSalaryProjectBasis(id) {
  return request({
    url: '/human/salaryProjectBasis/' + id,
    method: 'delete'
  })
}
