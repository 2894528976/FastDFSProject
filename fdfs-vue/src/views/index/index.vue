<template>
   <div class='root '>
       <div class="body">
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
  <!-- Brand -->
  <a class="navbar-brand" href="#">
    不仅仅是文件
  </a>
  <!-- Toggler/collapsibe Button -->
  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  <!-- Navbar links -->
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav">
      <li class="nav-item ">
        <span :class="{active: '/' === $route.path,'nav-link':true}" @click="pu('/')" >查看公共文件</span>
      </li>
      <li class="nav-item">
        <span  :class="{active: '/uploadfile' === $route.path,'nav-link':true}" @click="pu('/uploadfile')"  >上传文件</span>
      </li>
      <li class="nav-item">
        <span  :class="{active: '/myfile' === $route.path,'nav-link':true}" @click="pu('/myfile')" >我的文件</span>
      </li>
    </ul>
  </div> 
</nav>
<div class="container-fluid" style="width: 100%;height: 100%;margin:0px;padding: 0px;">
  <router-view/>
</div>
       </div>
   </div>
</template>

<script>
export default {
  name:'index',
   data () {
      return {
        b:"nav-link",
        isActive:this.$route.path,
      };
   },

   methods: {
  
     pu(rou){
       if(rou!==this.$route.path){
        this.isActive = this.$route.path;
      this.$router.push(rou)
       }
     }
   },
   components: {},

   computed: {},
     watch: {},
//创建之前 创建一个空的vue 所有属性还没有设置
beforeCreate() {

},
//创建后 (属性设置之后)(可以访问数据)
created() {
},
//挂载之前(Vue编译DOM之前) 这里对DOM(Vue所管理的DOM)的操作都不会生效
beforeMount() {
},
//初始化完成 页面呈现的时经过Vue编译的DOM
// 在此过程一般会进行   开启定时器,发送网络请求,订阅消息,绑定自定义事件,登初始化操作
mounted() {
},
   //此时数据是新的 但是页面仍然是旧的 (页面和数据未保持同步)
   beforeUpdate() {
},
updated() {
},
//销毁之前 (一般会进行关闭定时器 ,取消订阅消息,解绑自定义事件等收尾消息)
beforeDestroy() {
},
//销毁之后
destroy() {
},
////进入守卫：通过路由规则，进入该组件时被调用
beforeRouteEnter(to, from, next) {
next()
},
//离开守卫 通过路由规则 离开该组件时调用
beforeRouteLeave(to, from, next) {
next()
}
}
</script>
<style lang='css' scoped>
.root{
}
</style>