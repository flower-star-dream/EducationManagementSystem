import request from "@/utils/request.js";

export const getMajorInfoApI = ({pageNum, pageSize, majorId, majorName, facultyName}) => {
    return request({
        url: '/api/major/search',
        params: {
            pageNum,
            pageSize,
            majorId,
            majorName,
            facultyName
        }
    });
};

export const updateMajorApI = (form) => {
    return request.put(`/api/major/${form.id}`, form);
}

export const deleteMajorApI = (id) => {
    return request.delete(`/api/major/${id}`);
}

export const batchDeleteMajorApI = (ids) => {
    return request.post(`/api/major/deleteBatch`, ids);
}

export const saveMajorApI = (form) => {
    console.log(form);
    return request.post('/api/major', form);
}

export const getMajorCheckApI = (facultyName) => {
    return request.get(`/api/major/check?facultyName=${facultyName}`);
}

