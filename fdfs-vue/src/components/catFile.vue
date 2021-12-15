<template>
   <div class='root' 
   v-loading="loading"
   element-loading-text="拼命加载中"
   element-loading-spinner="el-icon-loading"
   element-loading-background="rgba(0, 0, 0, 0.8)">
      <div class="container-fluid">
         <div class="row">
            <!-- 图片框 
               annotation: "video"
            this.$refs.myimage.setAttribute(preview-src-list,['http://120.27.128.225:89/'+data.thumbnail])
              :preview-src-list="['http://120.27.128.225:89/'+i.thumbnail]" 缩略图
            -->
            <!--             <div v-if="i.annotation==='video'"></div> -->

            <div :key="i.id" v-for="i in filedata" class="col-6 col-sm-4 col-md-3 col-lg-2">
               <!-- <div>得到循环{{i}}</div> -->
          
               <div v-if="i.annotation==='video'">
               <div class="container mt-3">
                  <el-image
                  ref="myimage"
                  :src="'http://120.27.128.225:89/'+i.thumbnail" @click.naver="pushdetailed(i)">
                  </el-image>
                     <div class="caption">
                     <p>  此处为文件信息</p>
                        上传者:   
                     </div>
                </div>
               </div> 
               <div v-else-if="i.annotation==='image'">
                  <div class="container mt-3">
                     <el-image
                     :preview-src-list="['http://120.27.128.225:89/'+i.thumbnail]"
                     :src="'http://120.27.128.225:89/'+i.thumbnail">
                     </el-image>
                        <div class="caption">
                        <p>  此处为文件信息</p>
                           上传者:  
                        </div>
                   </div>
               </div>
               
            
            
            </div>
         </div>
      </div>
      <!-- 分页 -->
      <div>
         <!-- <el-pagination
           @size-change="handleSizeChange"
           @current-change="handleCurrentChange"
           :current-page="currentPage"
           :page-sizes="[18, 40, 66, 100]"
           :page-size="pagesize"
           layout="total, sizes, prev, pager, next, jumper"
           :total="total">
         </el-pagination> -->
         <div class="container mt-3">
               <ul class="pagination justify-content-center pagination-sm">
                  <li @click="handleCurrentChange(1)"  :class="{'page-item':true,'disabled':cz===1}"><a 
                     class="page-link" href="#"
                    >首页</a></li>
                  <li @click="handleCurrentChange(currentPage-1)"   :class="{'page-item':true,'disabled':cz===1}"><a 
                   class="page-link" href="#"
                  >上一页</a></li>
               <div v-for="ar in navigat" :key="ar">
                  <li @click="handleCurrentChange(ar)" :class="{'page-item':true,'active':ar===currentPage}"><a class="page-link" href="#">{{ar}}</a></li>
               </div>
                  <li @click="handleCurrentChange(currentPage+1)" :class="{'page-item':true,'disabled':cz===pages}"><a 
                   class="page-link" 
                    href="#">下一页</a></li>
                    <li @click="handleCurrentChange(total)" :class="{'page-item':true,'disabled':cz===pages}"><a 
                     class="page-link" href="#"
                    >尾页</a></li>
                </ul>
             </div>
                  </div>
                  </div>
</template>
<script>
export default {
  name:'catFile',
   data () {
      return {
         ww:true,
         num:18,
         a:0,
         cz : 0,
      };
   },
   props:{navigat:{type:Array},pages:{type:Number},"filedata":{},"loading":{type:Boolean},"pagesize":{type:Number},"currentPage":{type:Number},"total":{type:Number}},
   methods: {
      getfme(){
         this.$emit("getpage",this.cz,this.pagesize)
      },
      // handleSizeChange(val) {
      //    console.log(val)
      //    this.currentPage = val;
      //    this.getfme();
      // },
      handleCurrentChange(val) {
         this.cz = val;
         // console.log(val)
         this.getfme();
      },
      pushdetailed(data){
         //        router.push({name:'login',params:{status:401}});
         //annotation: "video"
         // <!-- 图片框 
            // :preview-src-list="['http://120.27.128.225:89/'+i.thumbnail]" 
   
         if(data.annotation==="video"){
            this.$router.push({
            name:"myvideo",
            params:{
               url:data.url
            }
         });
         }else{

         }
      }

   },
   components: {},

   computed: {

   //    cz: {
	//     get(){
          
   //        let num = this.currentPage+this.a;
	//         return num;
	//     },
	//     set(v) {
   //       this.a = this.a+v;
	//     }
	// },

   },
     watch: {},
//创建之前 创建一个空的vue 所有属性还没有设置
beforeCreate() {

},
//创建后 (属性设置之后)(可以访问数据)
created() {
   // this.cz = this,currentPage;
},
//挂载之前(Vue编译DOM之前) 这里对DOM(Vue所管理的DOM)的操作都不会生效
beforeMount() {
},
//初始化完成 页面呈现的时经过Vue编译的DOM
// 在此过程一般会进行   开启定时器,发送网络请求,订阅消息,绑定自定义事件,登初始化操作
mounted() {

   this.getfme();
},
   //此时数据是新的 但是页面仍然是旧的 (页面和数据未保持同步)
   beforeUpdate() {
},
updated() {
   this.cz = this.currentPage
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
.pagination li span { 
 color: black; 
 padding: 8px 16px; 
} 
.active{
   background-color: slategrey;
}
</style>