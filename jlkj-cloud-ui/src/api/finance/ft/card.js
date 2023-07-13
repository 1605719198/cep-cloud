import request from '@/utils/request'

//固定资产卡片挑选清单
export function selectCardList(data) {
  return request({
    url: '/finance/ft/card/selectCardList',
    params:data,
    method: 'get'
  })
}
