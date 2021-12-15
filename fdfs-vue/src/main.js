import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import elementui from "element-ui"
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.bundle.js'
Vue.use(elementui)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
