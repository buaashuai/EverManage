;(function () {
  'use strict';
//jqGrid的配置信息
  $.jgrid.defaults.width = 1000;
  $.jgrid.defaults.responsive = true;
  $.jgrid.defaults.styleUI = 'Bootstrap';

//工具集合Tools
  window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
  var url = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
  };
  T.p = url;

  //请求前缀
  var baseURL = "/EverManage/";

//登录token
  var token = localStorage.getItem("token");
  if (token == 'null') {
    console.log('token=' + token)
    parent.location.href = baseURL + 'login.html';
  }
  console.error('token=' + token)

//jquery全局配置
  $.ajaxSetup({
    dataType: "json",
    contentType: "application/json",
    cache: false,
    headers: {
      "token": token
    },
    xhrFields: {
      withCredentials: true
    },
    complete: function (xhr) {
      //token过期，则跳转到登录页面
      if (xhr.responseJSON.code == 401) {
        console.error('token 过期')
        parent.location.href = baseURL + 'login.html';
      }
    }
  });

  //jqgrid全局配置
  $.extend($.jgrid.defaults, {
    ajaxGridOptions: {
      headers: {
        "token": token
      }
    }
  });

  function hasPermission(permission) {
    if (window.parent.permissions.indexOf(permission) > -1) {
      return true;
    } else {
      return false;
    }
  }

//重写alert
  window.alert = function (msg, callback) {
    parent.layer.alert(msg, function (index) {
      parent.layer.close(index);
      if (typeof(callback) === "function") {
        callback("ok");
      }
    });
  }

//重写confirm式样框
  window.confirm = function (msg, callback) {
    parent.layer.confirm(msg, {btn: ['确定', '取消']},
      function () {//确定事件
        if (typeof(callback) === "function") {
          callback("ok");
        }
      });
  }


}());
