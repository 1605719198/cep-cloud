import request from '@/utils/request'

// 查询题库列表
export function listBank(query) {
  return request({
    url: '/human/bank/list',
    method: 'get',
    params: query
  })
}

// 查询题库详细
export function getBank(id) {
  debugger
  return request({

    url: '/human/bank/' + id,
    method: 'get'
  })
}

// 新增题库
export function addBank(data) {
  return request({
    url: '/human/bank',
    method: 'post',
    data: data
  })
}

// 修改题库
export function updateBank(data) {
  return request({
    url: '/human/bank',
    method: 'put',
    data: data
  })
}

// 删除题库
export function delBank(id) {
  return request({
    url: '/human/bank/' + id,
    method: 'delete'
  })
}
// 查询题库  下拉框
export function getBankSelect(compId) {
  return request({
    url: '/human/bank/listByCompId/'+ compId,
    method: 'get'
  })
}
