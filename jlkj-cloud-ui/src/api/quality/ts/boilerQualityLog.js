import request from '@/utils/request'

// 根据条件查询信息
export const queryBoilerInfo = (data) => request({
  url: '/api/neplatform/quality/quality/chemicalTest/experimentRecord/queryListBoiler',
  method: 'get',
  params: data
});
