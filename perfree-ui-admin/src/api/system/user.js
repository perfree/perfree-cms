import request from '@/utils/request'

export function getAllUser() {
  return request({
    url: '/system/user/getAll',
    method: 'get'
  })
}
