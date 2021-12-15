<template>
   <div class='root'>
       <body>
  <div id="wrapper">
  </div>
</body>
   </div>
</template>

<script>
import Chimee from 'chimee';

export default {
  name:'myvideo',

   data () {
      return {

      };
   },
   props:["url"],
   methods: {},
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
    const chimee = new Chimee('#wrapper');
chimee.on('play', () => console.log('play!!'));
chimee.load('http://120.27.128.225:89/'+this.$route.params.url);
chimee.play(); // play!!
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
/* #wrapper{
   width: 300px;
   height: 300px;
} */
</style>