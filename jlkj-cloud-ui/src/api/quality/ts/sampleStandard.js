import request from '@/utils/request'

// 新增
export function addInfo(data) {
  return request({
    url: '/quality/chemicalTest/highAndLowStandards/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateInfo(data) {
  return request({
    url: '/quality/chemicalTest/highAndLowStandards/update',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(sampleClass) {
  return request({
    url: `/quality/chemicalTest/highAndLowStandards/delete?sampleClass=${sampleClass}`,
    method: 'delete'
  })
}

// 删除详细信息
export function delInfoA(elementIndex, sampleClass) {
  return request({
    url: `/quality/chemicalTest/highAndLowStandards/deleteA?sampleClass=${sampleClass}&elementIndex=${elementIndex}`,
    method: 'delete'
  })
}

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/quality/chemicalTest/highAndLowStandards/queryList',
  method: 'post',
  data: data
});

// 根据条件查询信息
export const queryInfoYS = (data) => request({
  url: '/quality/chemicalTest/elementId/queryList',
  method: 'post',
  data: data
});

// 根据条件查询信息
export const queryInfoClass = (data) => request({
  url: '/quality/chemicalTest/sampleBasicData/queryList',
  method: 'post',
  data: data
});

// 根据条件查询信息
export const query = (sampleClass) => request({
  url: `/quality/chemicalTest/highAndLowStandards/query?sampleClass=${sampleClass}`,
  method: 'get',
});
