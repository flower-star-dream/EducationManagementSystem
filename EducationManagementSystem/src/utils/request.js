import axios from "axios";
import router from "../router";

// let baseURL = "https://star.south.takin.cc/"; // 内网穿透
let baseURL = "http://localhost:8080/";
const backupURL = "http://localhost:8080/";

function createRequestInstance(baseURL) {
  return axios.create({
    baseURL: baseURL,
    timeout: 5000,
    headers: {
      'Content-Type': 'application/json'
    }
  });
}

let request = createRequestInstance(baseURL);

// 请求拦截器
request.interceptors.request.use(config => {
  // times = 0;
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, error => {
  // 处理请求错误
  return Promise.reject(error);
});

// 响应拦截器
request.interceptors.response.use(
  function (response) {
    // 对响应数据做点什么
    return response;
  },
  function (error) {
    if (error.request.timeout==5000 && error.code == "ECONNABORTED" && error.message.indexOf("timeout") != -1) {
      console.log("连接超时，切换到备用服务器");
      request = createRequestInstance(backupURL);
      error.config.baseURL = backupURL;
      return request(error.config);
    }
    if (error.response && error.response.status === 403) {
      // 处理403错误
      console.error("403错误：", error);
      router.push("/login");
    }
    return Promise.reject(error);
  }
);
export default request;
