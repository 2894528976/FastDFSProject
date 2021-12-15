<template>
    <div class='root'>
        <sf @searchFile="searchFile" :selectData="sd"></sf>
        <cf v-on:getpage="getFiles" :pages="pages" :navigat="navigatepageNums" :filedata="filedata" :loading="loading"
            :pagesize="pagesize" :currentPage="currentPage" :total="total"></cf>
    </div>
</template>
<script>
    import {get} from "@/utils/req.js"
    import cf from "@/components/catFile.vue"
    import sf from "@/components/searchFile.vue"

    export default {
        name: 'catfile',
        data() {
            return {
                sd:[{
            label:"用户名",value:1
           
         },{ label:"文件名",value:2}],
                total: 0,
                currentPage: 1,
                pagesize: 18,
                pages: 0,
                search: {
                    text: "",
                    radio: 0,
                    current: 1,
                    size: 18,
                },
                filedata: [],
                loading: true,
                navigatepageNums: [],
            };
        },
        methods: {
            searchFile(text, radio) {
                if (text === "") { //空搜索 返回所有文件的分页 如果已经为当前分页就不进行axios
                    if (this.text === "") return;
                    //进行所有文件分页查询
                    this.getFiles(this.currentPage, this.pagesize)
                }
                this.search.text = text;
                this.search.radio = radio;

                //请求es搜索
                let data = {
                    current: this.search.current,
                    size: this.search.size,
                    value: this.search.text,
                    type: this.search.radio,
                    open: true,
                }
                // console.log(data)
                get({url: "/addres/search", params: data})
                    .then(data => {
                        // console.log("传递父组件", data)
                        this.pages = data.data.obj.pages;
                        this.filedata = data.data.obj.list;
                        this.total = data.data.obj.total
                        this.currentPage = data.data.obj.pageNum
                        this.pagesize = data.data.obj.pageSize
                        this.navigatepageNums = data.data.obj.navigatepageNums
                        this.loading = false;

                    }).catch(err => {
                    this.loading = false;
                    this.$message.error({message: "获取文件异常", duration: 800})

                })
            },
            getFiles(current, size) {
                if (this.search.text !== "") {
                    //  searchFile(text,radio){
                    // console.log("不为空 进行es搜索")
                 this.search.current = current;
                  this.search.size = size;
                    this.searchFile(this.search.text, this.search.radio);
                    return;
                }
                var da = {
                    url: "addres/public",
                    params: {
                        current,
                        size
                    }
                }
                return get(da)
                    .then(data => {
                        // console.log("传递父组件", data)
                        this.pages = data.data.obj.pages;
                        this.filedata = data.data.obj.list;
                        this.total = data.data.obj.total
                        this.currentPage = data.data.obj.pageNum
                        this.pagesize = data.data.obj.pageSize
                        this.navigatepageNums = data.data.obj.navigatepageNums
                        this.loading = false;

                    }).catch(err => {
                        this.loading = false;
                        this.$message.error({message: "获取文件异常", duration: 800})

                    })
            }

        },
        components: {
            cf, sf
        },

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
