import request from '@/utils/request'

// 根据条件查询信息
export const queryBoilerInfo = (data) => request({
  url: '/quality/chemicalTest/experimentRecord/queryListBoiler',
  method: 'get',
  params: data
});
