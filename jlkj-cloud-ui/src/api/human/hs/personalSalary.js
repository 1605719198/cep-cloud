import request from '@/utils/request'

// 查询薪资核定列表
export function listPersonalSalary(query) {
  return request({
    url: '/human/personalSalary/list',
    method: 'get',
    params: query
  })
}

// 查询薪资核定详细
export function getPersonalSalary(id) {
  return request({
    url: '/human/personalSalary/' + id,
    method: 'get'
  })
}

// 新增薪资核定
export function addPersonalSalary(data) {
  return request({
    url: '/human/personalSalary',
    method: 'post',
    data: data
  })
}

// 修改薪资核定
export function updatePersonalSalary(data) {
  return request({
    url: '/human/personalSalary',
    method: 'put',
    data: data
  })
}

// 删除薪资核定
export function delPersonalSalary(id) {
  return request({
    url: '/human/personalSalary/' + id,
    method: 'delete'
  })
}

// 查询公司薪资支付银行编码，名称ID
export function getSalaryBank(compId) {
  return request({
    url: '/human/personalSalary/getBank/' + compId,
    method: 'get'
  })
}

//查询公司薪酬项目
export function getPayFormation(compId) {
  return request({
    url: '/human/personalSalary/getPayFormation/' + compId,
    method: 'get',
  })
}


