import request from '@/utils/request'


// 获取全部树节点
export function getTreeTestNode() {
  return request({
    url: `/finance/finance/manufacturer/ManufacturerTreeList`,
    method: 'get',
  })
}


