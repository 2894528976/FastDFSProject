import axios from "axios";
import vue from "vue"
import store from "../store";
import router from "@/router/index.js"
const request = axios.create({
    baseURL: store.state.url,
    withCredentials: true,

    // paramsSerializer: params => {
    //     return qs.stringify(params, { indices: false })
    // }
})
request.interceptors.request.use(req=>{
        //  console.log("拦截前打印",req)
return req;
})
request.interceptors.response.use(resp=>{
    // console.log("拦截后",resp)
    if(resp.data.status==200){
        // console.log("拦截后",resp)
        return resp;
    }
    else if(resp.data.status==401){
        //
        router.push({name:'login',params:{status:401}});
    }
})
export const get =(obj)=> {
    return  request({
        ...obj,
        changeOrigin: true,  //是否跨域
        method:"get",
    })
}
export const post =(obj)=> {
    return  request({
        ...obj,
        changeOrigin: true,  //是否跨域
        method:"post",
    })
}
export const deleted =(obj)=> {
    return  request({
        ...obj,
        changeOrigin: true,  //是否跨域
        method:"delete",
    })
}
export const put =(obj)=> {
    return  request({
        ...obj,
        changeOrigin: true,  //是否跨域
        method:"put",
    })
}