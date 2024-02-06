export function pageUser(data) {
    return axios.post('/api/user/page', data);
}

export function getUser(id) {
    return axios.get('/api/user/get?id=' + id);
}

export function addUser(data){
    return axios.post('/api/user/add', data);
}

export function updateUser(data){
    return axios.post('/api/user/update', data);
}
export function delUser(id) {
    return axios.delete('/api/user/del?id=' + id);
}

export function updateUserRole(data) {
    return axios.post('/api/user/updateUserRole', data);
}

export function getUserRole(id) {
    return axios.get('/api/user/getUserRole?id=' + id);
}


export function resetPassword(data) {
    return axios.post('/api/user/resetPassword', data);
}