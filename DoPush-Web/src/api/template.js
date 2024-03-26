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

export const upload = (file) => {
  const formData = new FormData()
  formData.append('file', file.raw)
  return request.post('/messageTemplate/upload', formData, {
    headers: {
      'ContentType': 'multipart/form-data'
    }
  })
}

export const start = (id) => {
  return request({
    url: '/messageTemplate/start/' + id,
    method: 'post'
  })
}

export const stop = (id) => {
  return request({
    url: '/messageTemplate/stop/' + id,
    method: 'post'
  })
}
