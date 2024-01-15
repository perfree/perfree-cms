export function list(data) {
    return axios.post('/api/menu/list', data);
}