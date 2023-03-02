import request from '@/utils/request'

// 查询运输实际数据
export const queryInfo = (params) => request({
  url: '/logistics/logistics/trans/actual/queryActual',
  method: 'get',
  params:{
    ...params
  }
});

// 查询月倒出焦炭比
export function getOutCokeScale(params) {
  return request({
    url: '/logistics/logistics/trans/actual/getOutCokeScale',
    method: 'get',
    params: {
      ...params
    }
  })
}

// 查询月焦炭倒出量(按收货地点)
export function getOutCokeLocation(params) {
  return request({
    url: '/logistics/logistics/trans/actual/getOutCokeLocation',
    method: 'get',
    params: {
      ...params
    }
  })
}

// 查询月焦炭倒出量实际-计划
export function getOutCokeAmount(params) {
  return request({
    url: '/logistics/logistics/trans/actual/getOutCokeAmount',
    method: 'get',
    params: {
      ...params
    }
  })
}

// 查询月焦炭倒出量实际-计划
export function getInCokeAmount(params) {
  return request({
    url: '/logistics/logistics/trans/actual/getInCokeAmount',
    method: 'get',
    params: {
      ...params
    }
  })
}
