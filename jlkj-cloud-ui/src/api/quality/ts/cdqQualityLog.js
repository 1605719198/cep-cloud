import request from '@/utils/request'

// 根据条件查询信息
export const queryCdqInfo = (data) => request({
  url: '/quality/chemicalTest/experimentRecord/queryListCdq',
  method: 'get',
  params: data
});
