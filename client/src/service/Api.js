import axios from 'axios';

// initializing the axios instance with custom configs. can be seperated another file
const api = axios.create({
    withCredentials: true,
    headers: {},
    baseURL: "http://localhost:8080/api",
});


export const BackendApi = {
    calculate: async function (data) {
        const response = await api.request({
            url: '/calculate-score',
            method: "POST",
            data:data
        })

        return response.data
    },
}
