import request from '@/utils/request'

// 新增创建考试
export function saveData(data) {
    return request({
      url: '/human/examtask/save',
      method: 'post',
      data: data
    })
  }
