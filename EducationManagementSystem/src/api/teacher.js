import request from "@/utils/request.js";

export const getTeacherInfoApI = ({pageNum, pageSize, teacherId, teacherName, teacherGender, teacherAge, taroName}) => {
    return request({
        url: '/api/teacher/search',
        params: {
            pageNum,
            pageSize,
            teacherId,
            teacherName,
            teacherGender,
            teacherAge,
            taroName
        }
    });
};

export const getOneTeacherInfoApI = (teacherId) => {
    return request.get(`/api/teacher/searchOne?teacherId=${teacherId}`);
};

export const updateTeacherApI = (form) => {
    return request.put(`/api/teacher/update/${form.id}`, form);
}

export const deleteTeacherApI = (id) => {
    return request.delete(`/api/teacher/delete/${id}`);
}

export const batchDeleteTeacherApI = (ids) => {
    return request.post(`/api/teacher/deleteBatch`, ids);
}

export const saveTeacherApI = (form) => {
    console.log(form);
    return request.post('/api/teacher', form);
}

export const getTeacherCheckApI = (taroName) => {
    return request.get(`/api/teacher/check?taroName=${taroName}`);
}

