// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Login from './login.vue'
import router from '@/components/router/index_router'
import Menu_item from '@/components/menu_item.vue'

Vue.config.productionTip = false;

// utils.loadResource(['common']);

new Vue({
  el: '#app',
  router,
  template: '<Login/>',
  components: {Login}
})
