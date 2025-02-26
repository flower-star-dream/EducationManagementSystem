import request from "@/utils/request.js";

export const getTeachStuInfoApI = ({pageNum, pageSize, tid, sid, cid, sName, cName, className}) => {
    return request({
        url: '/api/teachStu/search',
        params: {
            pageNum,
            pageSize,
            tid,
            sid,
            cid,
            sName,
            cName,
            className
        }
    });
};

