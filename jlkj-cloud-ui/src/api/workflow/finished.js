import request from '@/utils/request'

// 撤回任务
export function revokeProcess(data) {
  return request({
    url: '/flowable/workflow/task/revokeProcess',
    method: 'post',
    data: data
  })
}
