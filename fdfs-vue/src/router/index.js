import Vue from 'vue'
import VueRouter from 'vue-router'
import home from "@/views/index/index.vue"
import login from "@/views/login/login.vue"
import myfile from "@/views/index/myfile.vue"
import uploadfile from "@/views/index/uploadfile.vue"
import catfile from "@/views/index/catfile.vue"
import myvideo from "@/views/index/myvideo.vue"
Vue.use(VueRouter)
const routes = [
{
  path: '/login.html',
  name: 'login',
  component: login
},
{
  path: '/myvideo',
  name: 'myvideo',
  component: myvideo
},
  {
    path: '/',
    name: 'Home',
    component: home,
    children:[
      {
        path: 'uploadfile',
        name: 'uploadfile',
        component: uploadfile 
      },
      {
        path: '/',
        name: 'catfile',
        component: catfile 
      },
      {
        path: 'myfile',
        name: 'myfile',
        component: myfile 
      },
    ]
  },
]

const router = new VueRouter({
  routes
})
router.beforeEach((to,from,next)=>{
  if(to.path=="/login.html"){
    next();
  }else{
  if(sessionStorage.getItem("username")){
    next()
  }else{
  next('/login.html')}}
})
export default router
