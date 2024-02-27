export function getCodeImg() {
    return axios.post('/api/captchaImage', {});
}

export function login(data) {
    return axios.post('/api/login', data);
}

export function menuList() {
    return axios.get('/api/menuList');
}

export function userInfo() {
    return axios.get('/api/userInfo');
}

export function getAllOption() {
    return axios.get('/api/getAllOption');
}

