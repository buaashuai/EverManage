<template>
  <div class="wrapper" id="app" v-cloak>
    <header class="main-header">
      <a @click="changeNavTitle('about')" href="#/sys@sys_about" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>EM</b></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">
          <img src="./assets/img/side-nev_head.jpg" style="width: 35px;">
          <b>管理系统孵化器</b>
        </span>
      </a>
      <!-- Header Navbar: style can be found in header.less -->
      <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
          <span class="sr-only">Toggle navigation</span>
        </a>
        <div style="float:left;color:#fff;padding:15px 10px;">代码的搬运工</div>
        <div class="navbar-custom-menu">
          <ul class="nav navbar-nav">
            <li><a href="javascript:;" @click="updatePassword"><i class="fa fa-lock"></i> &nbsp;修改密码</a></li>
            <li><a href="javascript:;" @click="logout"><i class="fa fa-sign-out"></i> &nbsp;退出系统</a></li>
          </ul>
        </div>
      </nav>
    </header>

    <!-- =============================================== -->

    <!-- Left side column. contains the sidebar -->
    <aside class="main-sidebar">
      <!-- sidebar: style can be found in sidebar.less -->
      <section class="sidebar">
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
          <li class="header">导航菜单</li>

          <!-- vue生成的菜单 -->
          <menu-item :item="item" :index="index" v-for="(item, index) in menuList"></menu-item>
        </ul>
      </section>
      <!-- /.sidebar -->
    </aside>
    <!-- =============================================== -->
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <ol class="breadcrumb" id="nav_title" style="position:static;float:none;">
          <li class="active"><i class="fa fa-home" style="font-size:20px;position:relative;top:2px;left:-3px;"></i>
            &nbsp; 首页
          </li>
          <li class="active">{{navTitle}}</li>
        </ol>
      </section>

      <!-- Main content -->
      <section class="content" style="background:#fff;">
        <!--<iframe scrolling="yes" frameborder="0"-->
        <!--style="width:100%;min-height:200px;overflow:visible;background:#fff;" :src="main"></iframe>-->
        <router-view></router-view>
      </section>

      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <footer class="main-footer">
      <div class="pull-right hidden-xs">
        Version 1.0.0
      </div>
      Copyright &copy; 2099 <a href="" target="_blank">wangshuai</a> All Rights Reserved
    </footer>

    <!-- Add the sidebar's background. This div must be placed
         immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>

    <!-- 修改密码 -->
    <div id="passwordLayer" style="display: none;">
      <form class="form-horizontal">
        <div class="form-group">
          <div class="form-group">
            <div class="col-sm-2 control-label">账号</div>
            <span class="label label-success" style="vertical-align: bottom;">{{user.username}}</span>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">原密码</div>
            <div class="col-sm-10">
              <input type="password" class="form-control" v-model="password" placeholder="原密码"/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">新密码</div>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="newPassword" placeholder="新密码"/>
            </div>
          </div>
        </div>
      </form>
    </div>

  </div>
</template>

<script>
  import utils from '@/common/utils'

  var baseURL = "http://localhost:8080/EverManage/";

  export default {
    name: 'app',
    data: function () {
      return {
        user: {},
        menuList: {},
        main: "main.html",
        password: '',
        newPassword: '',
        navTitle: "欢迎页"
      }
    },
    //  页面加载之前
    created() {
      this.getMenuList();
      this.getUser();
    },
    //  页面加载后,只有页面加载之后才能获取到相应的DOM节点
    mounted() {
      var url = window.location.hash;
      $(".treeview-menu li").removeClass("active");
      var ele = $("a[href='" + url + "']");
      ele.parents("li").addClass("active");
      this.navTitle = ele.text();
    },
    updated: function () {
    },
    methods: {
      getMenuList: function () {
        $.getJSON(baseURL + "sys/menu/nav", function (r) {
          this.menuList = r.menuList;
          window.permissions = r.permissions;
        });
      },
      getUser: function () {
        $.getJSON(baseURL + "sys/user/info", function (r) {
          this.user = r.user;
        });
      },
      updatePassword: function () {
        layer.open({
          type: 1,
          skin: 'layui-layer-molv',
          title: "修改密码",
          area: ['550px', '270px'],
          shadeClose: false,
          content: jQuery("#passwordLayer"),
          btn: ['修改', '取消'],
          btn1: function (index) {
            var data = "password=" + this.password + "&newPassword=" + this.newPassword;
            $.ajax({
              type: "POST",
              url: baseURL + "sys/user/password",
              data: data,
              dataType: "json",
              success: function (r) {
                if (r.code == 200) {
                  layer.close(index);
                  layer.alert('修改成功', function () {
                    location.reload();
                  });
                } else {
                  layer.alert(r.msg);
                }
              }
            });
          }
        });
      },
      logout: function () {
        $.ajax({
          type: "POST",
          url: baseURL + "sys/logout",
          dataType: "json",
          success: function (r) {
            //删除本地token
            localStorage.removeItem("token");
            //跳转到登录页面
            location.href = baseURL + 'login.html';
          }
        });
      },
      donate: function () {
        layer.open({
          type: 2,
          title: false,
          area: ['806px', '467px'],
          closeBtn: 1,
          shadeClose: false,
          content: ['http://cdn.everManage.io/donate.jpg', 'no']
        });
      },
      changeNavTitle: function (event) {
        $(".treeview-menu li").removeClass("active");
        var ele;
        if (utils.isString(event)) {
          ele = $("#default");
        } else {
          ele = $(event.target).closest("li");
        }
        this.navTitle = ele.find('a').text();
        ele.addClass("active");
      }

    }

  }
</script>

<style>
  /*@import "../../../node_modules/materialize-css/dist/css/materialize.css";*/

  #app {
  }
</style>
