<template>
  <div class="login-box" id="login" v-cloak>
    <div class="login-logo">
      <b>人人快速开发平台</b>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
      <p class="login-box-msg">管理员登录</p>
      <div v-if="error" class="alert alert-danger alert-dismissible">
        <h4 style="margin-bottom: 0px;"><i class="fa fa-exclamation-circle"></i> {{errorMsg}}</h4>
      </div>
      <div class="form-group has-feedback">
        <input type="text" class="form-control" v-model="username" placeholder="账号">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" v-model="password" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="text" class="form-control" v-model="captcha" @keyup.enter="login" placeholder="验证码">
        <span class="glyphicon glyphicon-warning-sign form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <img alt="如果看不清楚，请单击图片刷新！" class="pointer" :src="src" @click="refreshCode">
        &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" @click="refreshCode">点击刷新</a>
      </div>

      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="button" class="btn btn-primary btn-block btn-flat" @click="login">登录</button>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.social-auth-links -->

    </div>
    <!-- /.login-box-body -->
  </div>
</template>

<script>
  import utils from '@/common/utils'

  var baseURL = "http://localhost:8080/EverManage/";

  export default {
    name: 'login',
    data: function () {
      return {
        username: '',
        password: '',
        captcha: '',
        error: false,
        errorMsg: '',
        src: baseURL + 'captcha.jpg'
      }
    },
    beforeCreate: function () {
      if (self != top) {
        top.location.href = self.location.href;
      }
    },
    //  页面加载之前
    created() {
    },
    //  页面加载后,只有页面加载之后才能获取到相应的DOM节点
    mounted() {
    },
    updated: function () {
    },
    methods: {
      refreshCode: function () {
        this.src = baseURL + "captcha.jpg?t=" + $.now();
      },
      login: function () {
        var data = {
          username: this.username,
          password: this.password,
          captcha: this.captcha
        };
        var data2 = "username=" + this.username + "&password=" + this.password + "&captcha=" + this.captcha;

        console.log('data=' + JSON.stringify(data));
        console.log(data);
        var self = this;
        $.ajax({
          type: "POST",
          url: baseURL + "sys/login",
          data: JSON.stringify(data),
          dataType: "json",
          success: function (r) {
            console.log(r);
            if (r.code == 200) {//登录成功
              localStorage.setItem("token", r.token);
              parent.location.href = 'index.html';
            } else {
              self.error = true;
              self.errorMsg = r.msg;
              self.refreshCode();
            }
          }
        });
      }

    }

  }
</script>

<style>
  /*@import "../../../node_modules/materialize-css/dist/css/materialize.css";*/

  #app {
  }
</style>
