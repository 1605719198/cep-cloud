import request from '@/utils/request'

// 查询餐饮消费明细列表
export function listRestaurantDetail(query) {
  return request({
    url: '/human/restaurantDetail/list',
    method: 'get',
    params: query
  })
}

