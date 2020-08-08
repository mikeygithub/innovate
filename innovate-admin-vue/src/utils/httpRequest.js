import Vue from 'vue'
import axios from 'axios'
import router from '@/router'
import qs from 'qs'
import merge from 'lodash/merge'
import { clearLoginInfo } from '@/utils'

const http = axios.create({
  timeout: 10000 * 600,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
})

/**
 * 请求拦截
 */
http.interceptors.request.use(config => {
  config.headers['token'] = Vue.cookie.get('token') // 请求头带上token
  return config
}, error => {
  return Promise.reject(error)
})

/**
 * 响应拦截
 */
http.interceptors.response.use(response => {
  if (response.data && response.data.code === 401) { // 401, token失效
    console.log('token失效')
    clearLoginInfo()
    // router.push({ name: 'index' })
    router.push({ name: 'login' })
  }
  return response
}, error => {
  return Promise.reject(error)
})

/**
 * 请求地址处理
 * @param {*} actionName action方法名称
 */
http.adornUrl = (actionName) => {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl) + actionName
}

/**
 * get请求参数处理
 * @param {*} params 参数对象
 * @param {*} openDefultParams 是否开启默认参数?
 */
http.adornParams = (params = {}, openDefultParams = true) => {
  var defaults = {
    't': new Date().getTime()
  }
  return openDefultParams ? merge(defaults, params) : params
}

/**
 * post请求数据处理
 * @param {*} data 数据对象
 * @param {*} openDefultdata 是否开启默认数据?
 * @param {*} contentType 数据格式
 *  json: 'application/json; charset=utf-8'
 *  form: 'application/x-www-form-urlencoded; charset=utf-8'
 */
http.adornData = (data = {}, openDefultdata = true, contentType = 'json') => {
  var defaults = {
    't': new Date().getTime()
  }
  data = openDefultdata ? merge(defaults, data) : data
  return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
}

/**
 * 文件上传
 * @type {AxiosInstance}
 */

const httpFile = axios.create({
  timeout: 1000 * 3000,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  },
  responseType: 'blob'
})

/**
 * 请求拦截
 */
httpFile.interceptors.request.use(config => {
  config.headers['token'] = Vue.cookie.get('token') // 请求头带上token
  return config
}, error => {
  return Promise.reject(error)
})

// httpFile.interceptors.response.use(
//   response => {
//     if (response.config && response.config.responseType === 'blob') {
//       // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet这里表示xlsx类型
//       const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
//       let filename = 'excel.xls'
//       if ('download' in document.createElement('a')) {
//         const downloadElement = document.createElement('a')
//         let href = ''
//         if (window.URL) href = window.URL.createObjectURL(blob)
//         else href = window.webkitURL.createObjectURL(blob)
//         downloadElement.href = href
//         downloadElement.download = filename
//         document.body.appendChild(downloadElement)
//         downloadElement.click()
//         if (window.URL) window.URL.revokeObjectURL(href)
//         else window.webkitURL.revokeObjectURL(href)
//         document.body.removeChild(downloadElement)
//       } else {
//         navigator.msSaveBlob(blob, filename)
//       }
//       return
//     }
//     return Promise.resolve(response.data)
//     /* const { status, config } = response;
//     const { onlyHandleSuccess } = config;
//     if(!onlyHandleSuccess) return Promise.resolve(response.data);
//     else {
//         const { success, data, errorCode, errorMsg } = response.data;
//         if(success) return Promise.resolve(data);
//         else ElementUI.Message.error(errorMsg);
//     } */
//
//     /* if(status >= 200 && status < 300) {
//         return Promise.resolve(data);
//     }
//     return Promise.reject(response); */
//   },
//   error => {
//     const { response } = error
//     let message = '异常', status
//     if (!response) message = '请求超时...'
//     else {
//       message = response.statusText
//       status = response.status
//     }
//     return Promise.reject({ message, status })
//   }
// )

/**
 * 请求地址处理
 * @param {*} actionName action方法名称
 */
httpFile.adornUrl = (actionName) => {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl) + actionName
}

/**
 * get请求参数处理
 * @param {*} params 参数对象
 * @param {*} openDefultParams 是否开启默认参数?
 */
httpFile.adornParams = (params = {}, openDefultParams = true) => {
  var defaults = {
    't': new Date().getTime()
  }
  return openDefultParams ? merge(defaults, params) : params
}

/**
 * post请求数据处理
 * @param {*} data 数据对象
 * @param {*} openDefultdata 是否开启默认数据?
 * @param {*} contentType 数据格式
 *  json: 'application/json; charset=utf-8'
 *  form: 'application/x-www-form-urlencoded; charset=utf-8'
 */
httpFile.adornData = (data = {}, openDefultdata = true, contentType = 'json') => {
  var defaults = {
    't': new Date().getTime()
  }
  data = openDefultdata ? merge(defaults, data) : data
  return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
}

export {http, httpFile}
