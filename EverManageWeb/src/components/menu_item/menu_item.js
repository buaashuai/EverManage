// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Menu_item from '../menu_item.vue'

//生成菜单
var menuItem = Vue.extend({
  name: 'menu-item',
  props: {item: {}, index: 0},
  template: '<Menu_item/>',

});
//注册菜单组件
// Vue.component('menuItem', menuItem);
