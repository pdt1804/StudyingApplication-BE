import axios from 'axios';

const ApiManager = axios.create({
  baseURL: 'https://localhost:8080/api',
  responseType: 'json',
  withCredentials: true,
});

export default ApiManager;
