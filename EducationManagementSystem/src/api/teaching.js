import request from "@/utils/request.js";

export const getTeachingInfoApI = ({pageNum, pageSize, tid, cid, tName, cName, teachingType}) => {
    return request({
        url: '/api/teaching/search',
        params: {
            pageNum,
            pageSize,
            tid,
            cid,
            tName,
            cName,
            teachingType
        }
    });
};
export const getTeachingInfoApI2 = ({pageNum, pageSize, tid, cid, cName, teachingType}) => {
    return request({
        url: '/api/teaching/search2',
        params: {
            pageNum,
            pageSize,
            tid,
            cid,
            cName,
            teachingType
        }
    });
};

export const updateTeachingApI = (form) => {
    return request.put(`/api/teaching/${form.tid}/${form.cid}`, form);
}

export const deleteTeachingApI = (tid,cid) => {
    return request.delete(`/api/teaching/${tid}/${cid}`);
}

export const batchDeleteTeachingApI = (tids,cids) => {
  const payload = {
        tids: tids,
        cids: cids
  };
  return request.post(`/api/teaching/deleteBatch`, payload);
}

export const saveTeachingApI = (form) => {
    console.log(form);
    return request.post('/api/teaching', form);
}

export const getTeachingCheckTeacherApI = (tName) => {
    return request.get(`/api/teaching/checkTeacher?tName=${tName}`);
}

export const getTeachingCheckCourseApI = (cName) => {
    return request.get(`/api/teaching/checkCourse?cName=${cName}`);
}


