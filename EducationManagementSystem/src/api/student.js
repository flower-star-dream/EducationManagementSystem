import request from "@/utils/request.js";

export const getStudentInfoApI = ({pageNum, pageSize, studentId, studentName, studentGender, studentAge, className}) => {
    return request({
        url: '/api/student/search',
        params: {
            pageNum,
            pageSize,
            studentId,
            studentName,
            studentGender,
            studentAge,
            className
        }
    });
};

export const getOneStudentInfoApI = (studentId) => {
    return request.get(`/api/student/searchOne?studentId=${studentId}`);
};

export const updateStudentApI = (form) => {
    return request.put(`/api/student/update/${form.id}`, form);
}

export const deleteStudentApI = (id) => {
    return request.delete(`/api/student/delete/${id}`);
}

export const batchDeleteStudentApI = (ids) => {
    return request.post(`/api/student/deleteBatch`, ids);
}

export const saveStudentApI = (form) => {
    return request.post('/api/student', form);
}

export const getStudentCheckApI = (className) => {
    return request.get(`/api/student/check?className=${className}`);
}

