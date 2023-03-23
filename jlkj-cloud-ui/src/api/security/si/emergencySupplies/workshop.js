import request from '@/utils/request'
//车间明细台账-查询列表
export const workshopaccList = (page, query) => {
  return request({
    url: '/safety/safe/workshopacc/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const workshopaccInsert = (params) => {
  return request({
    url: '/safety/safe/workshopacc/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const workshopaccUpdate = (params) => {
  return request({
    url: '/safety/safe/workshopacc/update',
    method: 'post',
    data: { ...params }
  })
};

//车间明细台账-详情
export const workshopaccInfo = (params) => {
  return request({
    url: '/safety/safe/workshopacc/info',
    method: 'get',
    params
  })
};

//删除
export const workshopaccDelete = (ids) => {
  return request({
    url: '/safety/safe/workshopacc/delete',
    method: 'post',
    data: ids
  })
};

//删除
export const workshopaccBatchDelete = (data) => {
  return request({
    url: '/safety/safe/workshopacc/batch/delete',
    method: 'post',
    data
  })
};

//车间明细台账-工段列表
export const workshopaccSectionlist = (params) => {
  return request({
    url: '/safety/safe/workshopacc/sectionlist',
    method: 'get',
    params
  })
};
