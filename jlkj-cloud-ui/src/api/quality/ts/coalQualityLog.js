import request from '@/utils/request'

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/quality/chemicalTest/experimentRecord/queryList',
  method: 'get',
  params: data
});
