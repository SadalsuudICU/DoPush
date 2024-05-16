import request from '@/utils/request'

export const uploadMaterial = (formData) => {
  return request.post('material/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const queryMaterialList = (data) => {
  return request({
    url: '/material/list',
    method: 'get',
    params: {
      ...data
    }
  })
}

export const batchDelete = (ids) => {
  return request({
    url: '/material/delete/' + ids,
    method: 'delete'
  })
}

export const reference = (id) => {
  return request({
    url: 'material/reference/' + id,
    method: 'post'
  })
}
