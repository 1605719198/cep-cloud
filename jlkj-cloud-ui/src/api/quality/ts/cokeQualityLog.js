import request from '@/utils/request'

// 根据条件查询信息
export const queryMcInfo = (data) => request({
  url: '/quality/chemicalTest/experimentRecord/queryListMc',
  method: 'get',
  params: data
});
