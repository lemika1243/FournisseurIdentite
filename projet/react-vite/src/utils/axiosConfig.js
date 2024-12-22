// src/utils/axiosConfig.js
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';



export const isTokenExpired = (token) => {
  try {
    const decoded = jwtDecode(token);
    return decoded.exp < Date.now() / 1000;
  } catch (error) {
    return true;
  }
};

// Configuration de base d'Axios
export const setupAxios = (navigate) => {
  axios.defaults.baseURL = import.meta.env.VITE_API_URL;


  axios.interceptors.request.use(function (config) {
    const token = localStorage.getItem('token');
    
    if (token) {
      if (isTokenExpired(token)) {
        localStorage.removeItem('token');
        navigate('/');
        throw new axios.Cancel('Token expiré. Redirection...');
      }
      
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    
    config.headers['X-Binarybox-Api-Key'] = import.meta.env.VITE_API_KEY;
    
    return config;
  }, function (error) {
    return Promise.reject(error);
  });

  axios.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response && error.response.status === 401) {
        localStorage.removeItem('token');
        navigate('/');
      }
      return Promise.reject(error);
    }
  );
};



