import request from '@/utils/request'

// 新增个人信息设置
export function addCard(data) {
  return request({
    url: '/finance/card',
    method: 'post',
    data: data
  })
}


