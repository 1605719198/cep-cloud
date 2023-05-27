import request from '@/utils/request'

// 查询各公司薪资支付银行维护列表
export function listPayBank(query) {
  return request({
    url: '/human/payBank/list',
    method: 'get',
    params: query
  })
}

// 新增各公司薪资支付银行维护
export function addPayBank(data) {
  return request({
    url: '/human/payBank',
    method: 'post',
    data: data
  })
}

// 删除各公司薪资支付银行维护
export function delPayBank(uuid) {
  return request({
    url: '/human/payBank/' + uuid,
    method: 'delete'
  })
}
