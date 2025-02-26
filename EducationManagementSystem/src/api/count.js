import request from '@/utils/request.js';

export const countAPI = () => {
  return request({
    url: '/api/count/stats'
  });
};
export const count2API = (choice) => {
  return request.get(`/api/count/stats2?choice=${choice}`);
};

export const searchAPI = (tid) => {
  return request.get(`/api/count/search?tid=${tid}`);
};
export const count3API = (sid) => {
  return request.get(`/api/count/stats3?sid=${sid}`);
};
