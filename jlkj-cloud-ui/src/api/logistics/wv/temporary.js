import request from '@/utils/request'
// 根据单号查询临时用车申请
export function queryTempDayData(params) {
  return request({
    url: '/logistics/logistics/temp/day/queryTempDayDataByApplyNo',
    method: 'get',
    params: params
  })
}
// 查询运输实际数据
export const queryTransportActual = (id) => request({
  url: `/logistics/logistics/temp/day/queryTransportActual?logicId=${id}`,
  method: 'get',
});
