// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './index.vue'
import router from '@/components/router/index_router'
import Menu_item from '@/components/menu_item.vue'

Vue.config.productionTip = false;

// utils.loadResource(['common']);

new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App, Menu_item}
})

//iframe自适应
$(window).on('resize', function () {
  var $content = $('.content');
  $content.height($(this).height() - 120);
  $content.find('iframe').each(function () {
    $(this).height($content.height());
  });
}).resize();
