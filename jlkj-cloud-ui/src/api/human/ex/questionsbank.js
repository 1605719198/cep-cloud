import request from '@/utils/request'

// 查询题库管理列表
export function listQuestionsbank(query) {
  return request({
    url: '/human/questionsbank/list',
    method: 'get',
    params: query
  })
}

// 查询题库管理列表
export function listQuestionsbankBycode(codes) {
  return request({
    url: '/human/questionsbank/arraylist/' + codes,
    method: 'get'
  })
}

// 查询题库管理详细
export function getQuestionsbank(bankCode) {
  return request({
    url: '/human/questionsbank/' + bankCode,
    method: 'get'
  })
}

// 新增题库管理
export function addQuestionsbank(data) {
  return request({
    url: '/human/questionsbank',
    method: 'post',
    data: data
  })
}

// 修改题库管理
export function updateQuestionsbank(data) {
  return request({
    url: '/human/questionsbank',
    method: 'put',
    data: data
  })
}

// 删除题库管理
export function delQuestionsbank(bankCode) {
  return request({
    url: '/human/questionsbank/' + bankCode,
    method: 'delete'
  })
}

// 导出题库管理
export function exportQuestionsbank(query) {
  return request({
    url: '/human/questionsbank/export',
    method: 'get',
    params: query
  })
}

// 新增
export function uploadPhoto(bankcode, data) {
  return request({
    url: '/human/questionsbank/uploadphoto/' + bankcode,
    method: 'post',
    data: data
  })
}
