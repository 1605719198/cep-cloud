import request from '@/utils/request'

// 根据条件查询信息
export const queryInfo = (data) => request({
  url: '/quality/chemicalTest/instrumentBasicData/queryList',
  method: 'post',
  data: data
});

// 新增
export function addInfo(data) {
  return request({
    url: '/quality/chemicalTest/instrumentBasicData/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateInfo(data) {
  return request({
    url: '/quality/chemicalTest/instrumentBasicData/update',
    method: 'post',
    data: data
  })
}

// 删除
export function delInfo(ids) {
  return request({
    url: `/quality/chemicalTest/instrumentBasicData/delete?equipNo=${ids}`,
    method: 'delete'
  })
}
