import axios from 'axios'
import { getToken, updateToken } from './keycloak'

const api = axios.create({
  baseURL: '/api/v1',
  headers: {
    'Content-Type': 'application/json'
  }
})

api.interceptors.request.use(async (config) => {
  try {
    await updateToken(30) // Refresh token if it expires in less than 30 seconds
    const token = getToken()
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
  } catch (error) {
    console.error('Failed to update token', error)
  }
  return config
}, (error) => {
  return Promise.reject(error)
})

export default api
