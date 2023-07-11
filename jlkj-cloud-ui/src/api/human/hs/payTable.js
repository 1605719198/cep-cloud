import request from '@/utils/request'

// 查询公司级薪资项目维护列表
export function listPayTable(query) {
  return request({
    url: '/human/payTable/list',
    method: 'get',
    params: query
  })
}

// 新增公司级薪资项目维护
export function addPayTable(data) {
  return request({
    url: '/human/payTable',
    method: 'post',
    data: data
  })
}

// 删除公司级薪资项目维护
export function delPayTable(uuids) {
  return request({
    url: `/human/payTable/delPayTable?uuid=${uuids}`,
    method: 'delete'
  })
}

// 查询公司级薪资项目维护列表
export function listPayTableFormula(query) {
  return request({
    url: '/human/payTable/listFormula',
    method: 'get',
    params: query
  })
}

// 新增公司级薪资项目维护
export function addPayTableFormula(data) {
  return request({
    url: '/human/payTable/addPayTableFormula',
    method: 'post',
    data: data
  })
}
