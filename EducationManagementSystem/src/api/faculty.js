import request from "@/utils/request.js";

export const getFacultyInfoApI = ({pageNum, pageSize, facultyId, facultyName}) => {
    return request({
        url: '/api/faculty/search',
        params: {
            pageNum,
            pageSize,
            facultyId,
            facultyName
        }
    });
};

export const updateFacultyApI = (form) => {
    return request.put(`/api/faculty/${form.id}`, form);
}

export const deleteFacultyApI = (id) => {
    return request.delete(`/api/faculty/${id}`);
}

export const batchDeleteFacultyApI = (ids) => {
    return request.post(`/api/faculty/deleteBatch`, ids);
}

export const saveFacultyApI = (form) => {
    console.log(form);
    return request.post('/api/faculty', form);
}

