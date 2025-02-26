// 封装所有和用户相关的接口函数
import request from '@/utils/request.js'

export const userInfoAPI = (token) => {
  return request({
    url: '/api/users',
    params: {
      token
    }
  })
}

export const updateUserAPI = (form) => {
  return request.put(`/api/users/update/${form.id}`, form)
}

export const getUsersAPI = ({ pageNum, pageSize, name}) => {
  return request({
    url: '/api/users/search',
    params: {
      pageNum,
      pageSize,
      name,
    },
  });
};

export const deleteUserAPI = (id) => {
  return request.delete(`/api/users/delete/${id}`)
}

export const batchDeleteUsersAPI = (ids) => {
  return request.post('/api/users/deleteBatch', ids)
}

export const updateUserPasswordAPI = ({ userId, oldPassword, newPassword }) => {
  return request.post(`/api/users/password`, {
    userId,
    oldPassword,
    newPassword
  })
}

