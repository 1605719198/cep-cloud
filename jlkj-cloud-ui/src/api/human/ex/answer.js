import request from '@/utils/request'

// 查询考试题目答案列表
export function listAnswer(query) {
  return request({
    url: '/human/answer/list',
    method: 'get',
    params: query
  })
}

// 查询考试题目答案详细
export function getAnswer(questionsCode) {
  return request({
    url: '/human/answer/' + questionsCode,
    method: 'get'
  })
}

// 新增考试题目答案
export function addAnswer(data) {
  return request({
    url: '/human/answer/add',
    method: 'post',
    data: data
  })
}

// 修改考试题目答案
export function updateAnswer(data) {
  return request({
    url: '/human/answer/update',
    method: 'post',
    data: data
  })
}

// 删除考试题目答案
export function delAnswer(questionsCode) {
  return request({
    url: '/human/answer/delete/' + questionsCode,
    method: 'get'
  })
}

// 导出考试题目答案
export function exportAnswer(query) {
  return request({
    url: '/human/answer/export',
    method: 'get',
    params: query
  })
}
