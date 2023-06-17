import request from '@/utils/request'
// 查询凭证维护-明细列表
export function listDetailIfSteel(query) {
  return request({
    url: '/finance/accountSteel/listDetailIfSteel',
    method: 'get',
    params: query
  })
}

