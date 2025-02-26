import request from "@/utils/request.js";

export const getLearnTeacherInfoApI = ({pageNum, pageSize, sid, cid, tName, cName, learningType}) => {
    return request({
        url: '/api/learnTeacher/search',
        params: {
            pageNum,
            pageSize,
            sid,
            cid,
            tName,
            cName,
            learningType
        }
    });
};

