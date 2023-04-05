import request from '@/utils/request'

// 查询服务器硬件信息
export function getServerInfos(query) {
  return request({
    url: '/system/license/getServerInfos',
    method: 'get',
    params: query
  })
}

