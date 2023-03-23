import request from '@/utils/request'
//知识库-查询
export const knowledgelibraryList = (page, query) => {
  return request({
    url: '/safety/safe/knowledgelibrary/list',
    method: 'get',
    params: {
      ...page,
      ...query,
    }
  })
};
//新增
export const knowledgelibraryInsert = (params) => {
  return request({
    url: '/safety/safe/knowledgelibrary/insert',
    method: 'post',
    data: { ...params }
  })
};
//修改
export const knowledgelibraryUpdate = (params) => {
  return request({
    url: '/safety/safe/knowledgelibrary/update',
    method: 'post',
    data: { ...params }
  })
};
//删除
export const knowledgelibraryDelete = (ids) => {
  return request({
    url: '/safety/safe/knowledgelibrary/delete',
    method: 'post',
    data: ids
  })
};

//知识库-附件列表
export const fileList = (params) => {
  return request({
    url: '/safety/safe/knowledgelibrary/file/list',
    method: 'get',
    params
  })
};
