import request from '@/utils/request'

//根据工号查询异动信息主表
export function queryNewPostNameAndChangeDetail(data) {
  return request({
    url: '/human/employeeTurnover/queryNewPostNameAndChangeDetail',
    method: 'get',
    params: data
  })
}

//添加员工异动信息作业数据
export function addEmployeeTurnover(data) {
  return request({
    url: '/human/employeeTurnover/addEmployeeTurnover',
    method: 'post',
    data: data
  })
}

//修改异动信息作业数据
export function updateEmployeeTurnover(data) {
  return request({
    url: '/human/employeeTurnover/updateEmployeeTurnover',
    method: 'post',
    data: data
  })
}

//删除员工入职作业
export function delEmployeeTurnover(uuid,empNo) {
  return request({
    url: `/human/employeeTurnover/delEmployeeTurnover?uuid=${uuid}&&empNo=${empNo}`,
    method: 'delete'
  })
}

