import request from '@/utils/request'

// 查询活动信息列表
export function listActivic(query) {
  return request({
    url: '/system/activic/list',
    method: 'get',
    params: query
  })
}

// 查询活动信息详细
export function getActivic(activicId) {
  return request({
    url: '/system/activic/' + activicId,
    method: 'get'
  })
}

// 新增活动信息
export function addActivic(data) {
  return request({
    url: '/system/activic',
    method: 'post',
    data: data
  })
}

// 修改活动信息
export function updateActivic(data) {
  return request({
    url: '/system/activic',
    method: 'put',
    data: data
  })
}

// 删除活动信息
export function delActivic(activicId) {
  return request({
    url: '/system/activic/' + activicId,
    method: 'delete'
  })
}
