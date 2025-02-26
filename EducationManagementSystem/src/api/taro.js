import request from "@/utils/request.js";

export const getTaroInfoApI = ({pageNum, pageSize, taroId, taroName, facultyName}) => {
    return request({
        url: '/api/taro/search',
        params: {
            pageNum,
            pageSize,
            taroId,
            taroName,
            facultyName
        }
    });
};

export const updateTaroApI = (form) => {
    return request.put(`/api/taro/${form.id}`, form);
}

export const deleteTaroApI = (id) => {
    return request.delete(`/api/taro/${id}`);
}

export const batchDeleteTaroApI = (ids) => {
    return request.post(`/api/taro/deleteBatch`, ids);
}

export const saveTaroApI = (form) => {
    console.log(form);
    return request.post('/api/taro', form);
}

export const getTaroCheckApI = (facultyName) => {
    return request.get(`/api/taro/check?facultyName=${facultyName}`);
}

