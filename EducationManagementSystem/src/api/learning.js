import request from "@/utils/request.js";

export const getLearningInfoApI = ({pageNum, pageSize, affiliateId, sid, cid, sName, cName, learningType, score, usertype}) => {
    return request({
        url: '/api/learning/search',
        params: {
            pageNum,
            pageSize,
            affiliateId,
            sid,
            cid,
            sName,
            cName,
            learningType,
            score,
            usertype
        }
    });
};

export const updateLearningApI = (form) => {
    return request.put(`/api/learning/${form.sid}/${form.cid}`, form);
}

export const deleteLearningApI = (sid,cid) => {
    return request.delete(`/api/learning/${sid}/${cid}`);
}

export const batchDeleteLearningApI = (sids,cids) => {
  const payload = {
        sids: sids,
        cids: cids
  };
  return request.post(`/api/learning/deleteBatch`, payload);
}

export const saveLearningApI = (form) => {
    console.log(form);
    return request.post('/api/learning', form);
}

export const getLearningCheckStudentApI = (sName) => {
    return request.get(`/api/learning/checkStudent?sName=${sName}`);
}

export const getLearningCheckCourseApI = (cName) => {
    return request.get(`/api/learning/checkCourse?cName=${cName}`);
}


