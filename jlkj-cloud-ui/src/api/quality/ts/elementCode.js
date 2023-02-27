import request from '@/utils/request'

// 新增
export function addInfo(data) {
  return request({
    url: '/quality/chemicalTest/elementId/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateInfo(data) {
  return request({
    url: '/quality/chemicalTest/elementId/update',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(ids) {
  return request({
    url: `/quality/chemicalTest/elementId/delete?elementIndex=${ids}`,
    method: 'delete'
  })
}

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/quality/chemicalTest/elementId/queryList',
  method: 'post',
  data: data
});
