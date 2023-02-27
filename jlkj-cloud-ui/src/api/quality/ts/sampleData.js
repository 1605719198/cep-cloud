import request from '@/utils/request'

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/quality/chemicalTest/sampleBasicData/queryList',
  method: 'post',
  data: data
});

// 新增
export function addInfo(data) {
  return request({
    url: '/quality/chemicalTest/sampleBasicData/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateInfo(data) {
  return request({
    url: '/quality/chemicalTest/sampleBasicData/update',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(sampleClass) {
  return request({
    url: `/quality/chemicalTest/sampleBasicData/delete?sampleClass=${sampleClass}`,
    method: 'delete'
  })
}

// 编辑查询
export function queryTsSampleInfo(sampleClass) {
  return request({
    url: `/quality/chemicalTest/sampleBasicData/query?sampleClass=${sampleClass}`,
    method: 'get'
  })
}

// 根据条件查询信息
export const queryInfoGS = (data) => request({
  url: '/quality/chemicalTest/sampleBasicData/queryTsTable',
  method: 'post',
  data: data
});

// 根据条件查询信息
export const queryDropDownMenu = (data) => request({
  url: '/quality/chemicalTest/sampleBasicData/queryDropDownMenu',
  method: 'get',
  params: data
});
