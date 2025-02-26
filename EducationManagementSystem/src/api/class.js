import request from "@/utils/request.js";

export const getClassInfoApI = ({pageNum, pageSize, classId, className, majorName}) => {
    return request({
        url: '/api/class/search',
        params: {
            pageNum,
            pageSize,
            classId,
            className,
            majorName
        }
    });
};

export const updateClassApI = (form) => {
    return request.put(`/api/class/${form.id}`, form);
}

export const deleteClassApI = (id) => {
    return request.delete(`/api/class/${id}`);
}

export const batchDeleteClassApI = (ids) => {
    return request.post(`/api/class/deleteBatch`, ids);
}

export const saveClassApI = (form) => {
    console.log(form);
    return request.post('/api/class', form);
}

export const getClassCheckApI = (majorName) => {
    return request.get(`/api/class/check?majorName=${majorName}`);
}

