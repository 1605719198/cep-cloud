import request from '@/utils/request'

// 查询薪资导入记录
export function queryImportNote(data) {
  return request({
    url: '/human/importNote/query',
    method: 'post',
    data: data
  })
}


