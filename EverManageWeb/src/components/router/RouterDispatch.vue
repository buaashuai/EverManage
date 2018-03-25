<template>
  <iframe v-bind:src="getPagePath" style="width:100%;min-height:200px;overflow:visible;background:#fff;" scrolling="yes"
          frameborder="0" id="external-frame"></iframe>
</template>

<script>
  export default {
    name: 'RouterDispatch',
    data() {
      return {
        //缓存页面路径
        page_path: 'login.html',
        //页面容器,用于存放需要跳转页面的路径
        page_list: {
          'index': 'index.html',
          'login': 'login.html',
          'about': 'sys/sys_about.html',
          'sys/schedule/schedule_job': 'sys/schedule/schedule_job.html',
          'sys/schedule/schedule_log': 'sys/schedule/schedule_log.html',
          'sys/sys_about': 'sys/sys_about.html',
          'sys/sys_config': 'sys/sys_config.html',
          'sys/sys_log': 'sys/sys_log.html',
          'sys/sys_menu': 'sys/sys_menu.html',
          'sys/sys_oss': 'sys/sys_oss.html',
          'sys/sys_role': 'sys/sys_role.html',
          'sys/sys_user': 'sys/sys_user.html',
        }
      }
    },
    computed: {
      //获取页面路径
      getPagePath() {
        if (this.page_list[this.$route.params.page_path]) {
          this.page_path = this.page_list[this.$route.params.page_path]
        }
        else if (this.$route.params.page_path == '!') {
          return this.page_path
        }
        else {
          this.page_path = this.page_list['login']
        }
        return this.page_path
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  iframe {
    padding: 3px;
  }
</style>
