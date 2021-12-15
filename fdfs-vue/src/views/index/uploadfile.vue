<template>
    <div class='root'>
        是否开放
        <template>
            <el-radio v-model="radio" :label="false">私有</el-radio>
            <el-radio v-model="radio" :label="true">开放</el-radio>
        </template>
        <div class="upfile">
            <el-upload
                    class="upload-demo"
                    ref="upload"
                    :action="$store.state.url+'addres'"
                    :with-credentials="true"
                    :on-preview="handlePreview"
                    :on-success="success"
                    :on-remove="handleRemove"
                    :multiple="true"
                    :data="{radio:radio,fileList:JSON.stringify(fileList)}"
                    :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器
                </el-button>
            </el-upload>
        </div>
        <el-dialog
                title="设置标题"
                :visible.sync="centerDialogVisible"
                width="30%"
                center>
            <div v-for="(i,index) in fileList" :key=index>
                <span>{{i.name}}</span>
                <el-input v-model="i.value" placeholder="请输入标题 默认文件名字"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
  <el-button @click="centerDialogVisible = false">取 消</el-button>
  <el-button type="primary" @click="submitfile">提 交</el-button>
</span>
        </el-dialog>
    </div>
</template>

<script>
    import {post} from "@/utils/req.js"

    export default {

        name: 'uploadfile',

        data() {
            return {
                centerDialogVisible: false,
                radio: true,
                fileList: []
            };
        },

        methods: {

            success(response, file, fileList) {
                if (response.status == 401) {
                    this.$router.push({name: 'login', params: {status: 401}});
                } else if(response.status == 200) {
                    this.$message.success("上传成功!");
                }
                else{
                    this.$message.error("上传失败!");
                }
            },
            submitfile() {
                this.centerDialogVisible = false

                this.$refs.upload.submit();
                this.fileList = [];
            },
            submitUpload() {
                this.fileList = [];
                this.$refs.upload.uploadFiles.forEach(element => {
                    if(element.status==="ready")this.fileList.push({name: element.name, value: ""})
                });
                this.centerDialogVisible = true
            },
            handleRemove(file, fileList) {
                //   console.log(file, fileList);
            },
            handlePreview(file) {
                //   console.log(file);
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
    .root {
    }
</style>
