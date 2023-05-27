import request from '@/utils/request'

// 新增个人信息设置
export function addCard(data) {
  return request({
    url: '/finance/card',
    method: 'post',
    data: data
  })
}

// 查询个人信息详细
export function queryCard(userNo) {
  return request({
    url: '/finance/card/' + userNo,
    method: 'get'
  })
}


