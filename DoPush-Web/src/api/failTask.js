import request from '@/utils/request'

export const search = (data) => {
  return request({
    url: '/failedTask/list',
    method: 'get',
    params: {
      ...data
    }
  })
}
