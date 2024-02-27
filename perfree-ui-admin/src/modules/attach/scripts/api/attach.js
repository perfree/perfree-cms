export function page(data) {
    return axios.post('/api/attach/page', data);
}

export function getAllAttachGroup() {
    return axios.get('/api/attach/getAllAttachGroup');
}

export function assignRoleMenu(data) {
    return axios.post('/api/role/assignRoleMenu', data);
}

export function getRole(id) {
    return axios.get('/api/role/get?id=' + id);
}

export function addOrUpdate(data){
    return axios.post('/api/role/addOrUpdate', data);
}

export function del(id) {
    return axios.delete('/api/role/del?id=' + id);
}