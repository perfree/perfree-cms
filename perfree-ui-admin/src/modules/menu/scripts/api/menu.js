export function list(data) {
    return axios.post('/api/menu/page', data);
}

export function get(id) {
    return axios.get('/api/menu/get?id=' + id);
}

export function add(data) {
    return axios.post('/api/menu/add', data);
}

export function update(data) {
    return axios.post('/api/menu/update', data);
}

export function del(id) {
    return axios.delete('/api/menu/del?id=' + id);
}

