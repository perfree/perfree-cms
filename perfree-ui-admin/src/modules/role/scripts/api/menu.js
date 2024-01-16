export function list(data) {
    return axios.post('/api/menu/list', data);
}

export function get(id) {
    return axios.get('/api/menu/get?id=' + id);
}

export function addOrUpdate(data) {
    return axios.post('/api/menu/addOrUpdate', data);
}

export function del(id) {
    return axios.delete('/api/menu/del?id=' + id);
}

