import request from '@/utils/request'

// 新增
export function addInfo(data) {
  return request({
    url: '/quality/chemicalTest/elementTable/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateInfo(data) {
  return request({
    url: '/quality/chemicalTest/elementTable/update',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(formatId) {
  return request({
    url: `/quality/chemicalTest/elementTable/delete?formatId=${formatId}`,
    method: 'delete'
  })
}

// 删除详细信息
export function delInfoA(elementIndex, formatId) {
  return request({
    url: `/quality/chemicalTest/elementTable/deleteA?formatId=${formatId}&elementIndex=${elementIndex}`,
    method: 'delete'
  })
}

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/quality/chemicalTest/elementTable/queryList',
  method: 'post',
  data: data
});

// 根据条件查询信息
export const query = (data) => request({
  url: '/quality/chemicalTest/elementTable/query',
  method: 'post',
  data: data
});

// 根据条件查询信息
export const queryInfoYS = (data) => request({
  url: '/quality/chemicalTest/elementId/queryList',
  method: 'post',
  data: data
});
