import request from '@/utils/request'

export const list = (data) => {
  return request({
    url: '/account/list',
    method: 'get',
    params: {
      ...data
    }
  })
}

export const query = (data) => {
  return request({
    url: '/account/queryByChannelType',
    method: 'get',
    params: {
      ...data
    }
  })
}

export const save = (data) => {
  return request({
    url: '/account/save',
    method: 'post',
    data
  })
}

export const batchDelete = (ids) => {
  return request({
    url: '/account/delete/' + ids,
    method: 'delete'
  })
}

export const accountData = () => {
  return request({
    url: '/account/data',
    method: 'get'
  })
}

