import request from "@/utils/request.js";

export const getCourseInfoApI = ({pageNum, pageSize, courseId, courseName, taroName}) => {
    return request({
        url: '/api/course/search',
        params: {
            pageNum,
            pageSize,
            courseId,
            courseName,
            taroName
        }
    });
};

export const updateCourseApI = (form) => {
    return request.put(`/api/course/${form.id}`, form);
}

export const deleteCourseApI = (id) => {
    return request.delete(`/api/course/${id}`);
}

export const batchDeleteCourseApI = (ids) => {
    return request.post(`/api/course/deleteBatch`, ids);
}

export const saveCourseApI = (form) => {
    console.log(form);
    return request.post('/api/course', form);
}

export const getCourseCheckApI = (taroName) => {
    return request.get(`/api/course/check?taroName=${taroName}`);
}

