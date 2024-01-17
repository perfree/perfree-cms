export function page(data) {
    return axios.post('/api/plugins/page', data);
}