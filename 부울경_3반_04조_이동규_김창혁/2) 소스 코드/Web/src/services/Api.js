import axios from 'axios';

export default () => {
  const axiosInstance = axios.create({
    // baseURL: "http://192.168.203.120:8080",
    baseURL: 'http://localhost:8080',

    headers: {
      'Content-Type': 'application/json',
    },
  });

  const token = localStorage.getItem('token');
  if (token) {
    axiosInstance.defaults.headers.common.Authorization = `${token}`;
  }

  axiosInstance.interceptors.response.use(
    (response) => response,
    async (error) => {
      if (error.response.status === 401) {
        try {
          const response = await axios.post('/api/v1/private/users/reissue');
          localStorage.setItem('token', response.data.data);
          axiosInstance.defaults.headers.common.Authorization = `${response.data.data}`;
          return axios(error.config);
        } catch (error) {
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          location.reload();
        }
      } else if (error.response.status === 403) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        location.reload();
      }
      return Promise.reject(error);
    }
  );

  return axiosInstance;
};
// import axios from "axios";

// export default () => {
//   const axiosInstance = axios.create({
//     baseURL: "http://192.168.203.120:8080",
//     // baseURL: "http://192.168.203.119:8080",

//     headers: {
//       "Content-Type": "application/json",
//     },
//   });

//   const token = localStorage.getItem("token");
//   if (token) {
//     axiosInstance.defaults.headers.common.Authorization = `${token}`;
//   }

//   axiosInstance.interceptors.response.use(
//     (response) => response,
//     (error) => {
//       if (error.response.status === 401 || error.response.status === 403) {
//         // if (error.response.status === 401) {
//         localStorage.removeItem("token");
//         localStorage.removeItem("user");
//         location.reload();
//       }
//       return Promise.reject(error);
//     }
//   );

//   return axiosInstance;
// };
// export default axios.create({
//   baseURL: "http://localhost:8080",
//   headers: {
//      "Content-Type": "application/json",
//   },
//   withCredentials: true,
// });
