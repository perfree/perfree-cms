export function page(data) {
    return axios.post('/api/attach/page', data);
}

export function getAllAttachGroup() {
    return axios.get('/api/attach/getAllAttachGroup');
}

export function update(data) {
    return axios.put('/api/attach/update', data);
}

export function getAttach(id) {
    return axios.get('/api/attach/get?id=' + id);
}

export function del(id) {
    return axios.delete('/api/attach/del?id=' + id);
}

export function download(id) {
    return axios.delete('/api/role/del?id=' + id);
}