export function page(data) {
    return axios.post('/api/role/page', data);
}

export function getRoleMenus(id) {
    return axios.get('/api/role/getRoleMenus?id=' + id);
}

export function assignRoleMenu(data) {
    return axios.post('/api/role/assignRoleMenu', data);
}