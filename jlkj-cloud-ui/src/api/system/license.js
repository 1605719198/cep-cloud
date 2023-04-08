import request from '@/utils/request'

// 查询服务器硬件信息
export function getServerInfos(query) {
  return request({
    url: '/system/license/getServerInfos',
    method: 'get',
    params: query
  })
}
// 生成License 授权信息
export function generateLicense(data) {
  return request({
    url: '/system/license/generateLicense',
    method: 'post',
    data: data
  })
}
