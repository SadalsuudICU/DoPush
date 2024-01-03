import request from '@/utils/request'

export const templateList = (data) => {
  return request({
    url: '/messageTemplate/list',
    method: 'get',
    params: {
      ...data
    }
  })
}

export const batchDelete = (ids) => {
  return request({
    url: '/messageTemplate/delete/' + ids,
    method: 'delete'
  })
}

export const test = (data) => {
  return request({
    url: '/messageTemplate/test',
    method: 'post',
    data: {
      ...data
    }
  })
}

export const save = (data) => {
  return request({
    url: '/messageTemplate/save',
    method: 'post',
    data: {
      ...data
    }
  })
}
