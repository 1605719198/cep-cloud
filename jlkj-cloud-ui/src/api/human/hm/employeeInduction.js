import request from '@/utils/request'

//获取员工入职作业查询列表
export function queryEmployeeInduction(data) {
  return request({
    url: '/human/employeeInduction/getList',
    method: 'get',
    params: data
  })
}

//添加员工入职作业数据
export function addEmployeeInduction(data) {
  return request({
    url: '/human/employeeInduction/addEmployeeInduction',
    method: 'post',
    data: data
  })
}

//查询员工入职作业详细数据
export function queryEmployeeInductionByUuid(uuid) {
  return request({
    url: `/human/employeeInduction/queryEmployeeInductionByUuid?uuid=${uuid}`,
    method: 'get'
  })
}

//删除员工入职作业
export function delEmployeeInduction(uuid,empNo) {
  return request({
    url: `/human/employeeInduction/delEmployeeInduction?uuid=${uuid}&&empNo=${empNo}`,
    method: 'delete'
  })
}

//删除员工入职作业明细
export function delEmployeeInductionDetail(uuid) {
  return request({
    url: `/human/employeeInduction/delEmployeeInductionDetail?uuid=${uuid}`,
    method: 'delete'
  })
}

//修改员工入职作业数据
export function updateEmployeeInduction(data) {
  return request({
    url: '/human/employeeInduction/updateEmployeeInduction',
    method: 'post',
    data: data
  })
}

