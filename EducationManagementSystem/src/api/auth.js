import request from '@/utils/request.js'

export const loginAPI = (form) => {
  return request.post('/api/auth/login', form)
}

export const registerAPI = (form) => {
  return request.post('/api/auth/register', form)
}

export const checkAffiliateIdAPI = (usertype, affiliateId) => {
  const params = {
    usertype,
    affiliateId
  }
  return request.post('/api/auth/checkAffiliateId', params)
}
