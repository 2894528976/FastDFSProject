<template>
   <div class='root' v-loading="loading" >
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
  <el-form-item label="用户名">
    <el-input v-model="formInline.user" placeholder="用户名"></el-input>
  </el-form-item>
  <el-form-item label="密码">
       <el-input v-model="formInline.psd" placeholder="密码" type="password"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmit">登录</el-button>
  </el-form-item>
</el-form>
<!-- <button class="btn btn-success btn-lg" @click="test">测试登录</button> -->
</div>
</template>

<script>
   import {post,get} from "@/utils/req.js";
export default {

  name:'login',
  props:["status"],
   data () {
      return {
         loading:false,
 formInline: {
         //  user: 'root',
         //  psd: 'sun12345'
         user: '',
         psd: ''
        }
      };
   },

   methods: {
      test(){
         get({url:"/test"})
         .then(data=>{
      // localStorage.setItem("user",);
      console.log("获取到的数据",data)})
   .catch(err=>{
      console.log("报错了哦~~",err)

   });
      },
      onSubmit(){
         this.loading = true;
   
         let obj = {
            url:"/login",
            params:{
              user: this.formInline.user.trim(),
              psd: this.formInline.psd.trim(),
            }
         }
   post(obj).then(data=>{
      // localStorage.setItem("user",);
      if(data.data.status==200){
   
         sessionStorage.setItem("username",data.data.obj.username);
         this.$message.closeAll();
         
         this.$message.success({message:"登陆成功",duration:800});
         this.loading = false;
         this.$router.push("/")
         
      }
   }).catch(err=>{
      this.loading = false;
      this.$message.error({message:"用户名密码错误,请重新登陆!",duration:800});

   });
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
   if(this.$route.params.status&&this.$route.params.status==401){
      this.$message.warning("信息过期,请重新登录");
   }
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

<style lang='css'>
.root{

}
</style>