<template id="menu_item">
  <li :class="{active: (item.type===0 && index === 0)}">
    <a v-if="item.type === 0" href="javascript:;">
      <i v-if="item.icon != null" :class="item.icon"></i>
      <span>{{item.name}}</span>
      <i class="fa fa-angle-left pull-right"></i>
    </a>
    <ul v-if="item.type === 0" class="treeview-menu">
      <menu-item :item="item" :index="index" v-for="(item, index) in item.list"></menu-item>
    </ul>
    <a v-if="item.type === 1" :href="'#'+item.url">
      <i v-if="item.icon != null" :class="item.icon"></i>
      <i v-else class="fa fa-circle-o"></i> {{item.name}}
    </a>
  </li>
</template>

<script>
  import utils from '@/common/utils'

  export default {
    name: 'menu_item',
    data: function () {
      return {
        item: {
          type: 0,
          name: '',
          icon: null,
          list: [],
          url: ''
        }
      }
    },
    props: {
      item:Object
    },
    //  页面加载之前
    created() {
    },
    //  页面加载后,只有页面加载之后才能获取到相应的DOM节点
    mounted() {
    },
    methods: {}

  }
</script>

<style>
  /*@import "../../../node_modules/materialize-css/dist/css/materialize.css";*/
</style>
