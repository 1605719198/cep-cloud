import request from '@/utils/request'
//工段-查询列表
export const workshopaccSectionList = (page, query) => {
  return request({
    url: '/safety/safe/workshopacc/section/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const workshopaccSectionInsert = (params) => {
  return request({
    url: '/safety/safe/workshopacc/section/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const workshopaccSectionUpdate = (params) => {
  return request({
    url: '/safety/safe/workshopacc/section/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const workshopaccSectionDelete = (ids) => {
  return request({
    url: '/safety/safe/workshopacc/section/delete',
    method: 'post',
    data: ids
  })
};
