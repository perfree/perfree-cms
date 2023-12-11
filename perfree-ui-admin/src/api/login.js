import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/api/login',
    method: 'post',
    headers: {
      isToken: false
    },
    data: data
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/api/userInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/api/logout',
    headers: {
      isToken: false
    },
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/api/captchaImage',
    method: 'post',
    timeout: 20000
  })
}