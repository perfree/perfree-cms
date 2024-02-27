export function getAllAttachConfig() {
    return axios.get('/api/attachConfig/getAll');
}