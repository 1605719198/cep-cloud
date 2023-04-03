import request from '@/utils/request'

// 查询倒班人员法定假日加班资料列表
export function listHolidayOvertime(query) {
  return request({
    url: '/human/holidayOvertime/list',
    method: 'get',
    params: query
  })
}
