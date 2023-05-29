import request from '@/utils/request'

// 查询各公司薪资发放说明列表
export function listSalaryExplain(query) {
  return request({
    url: '/human/salaryExplain/list',
    method: 'get',
    params: query
  })
}

// 查询各公司薪资发放说明详细
export function getSalaryExplain(uuid) {
  return request({
    url: '/human/salaryExplain/' + uuid,
    method: 'get'
  })
}

// 保存各公司薪资发放说明
export function saveSalaryExplain(data) {
  return request({
    url: '/human/salaryExplain/save',
    method: 'post',
    data: data
  })
}


// 删除各公司薪资发放说明
export function delSalaryExplain(uuid) {
  return request({
    url: '/human/salaryExplain/' + uuid,
    method: 'delete'
  })
}
