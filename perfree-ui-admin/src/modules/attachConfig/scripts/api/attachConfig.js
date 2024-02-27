export function page(data) {
    return axios.post('/api/attachConfig/page', data);
}


export function add(data) {
    return axios.post('/api/attachConfig/add', data);
}

export function update(data) {
    return axios.put('/api/attachConfig/update', data);
}

export function getAttachConfig(id) {
    return axios.get('/api/attachConfig/get?id=' + id);
}

export function updateMaster(data){
    return axios.put('/api/attachConfig/updateMaster', data);
}

export function del(id) {
    return axios.delete('/api/attachConfig/del?id=' + id);
}