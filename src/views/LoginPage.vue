<template>
  <div class="login">
    <h1>Bot Login</h1>
    <input v-model="id" type="text" name="u" placeholder="Username" required="required"/>
    <input v-model="pwd" type="password" name="p" placeholder="Password" required="required"/>
    <button @click="login" type="submit" class="btn btn-primary btn-block btn-large">登录</button>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import router from "@/router";
import * as model from '@/model/index'
import {IResp, StateCode} from "@/model";
import store from '../store'

interface ApiLoginResp {
  id: number
  token: string
}

export default defineComponent({
  data() {
    return {
      id: "",
      pwd: ""
    }
  },

  methods: {
    login(): void {
      // console.log(this.id + this.pwd)
      model.service({
        method: 'POST',
        path: '/api/user/login',
        data: JSON.stringify({
          "id": this.id,
          "pwd": this.pwd
        })
      }).then((resp: IResp<ApiLoginResp>) => {
        const {code, data} = resp
        if (code === StateCode.success) {
          console.log(data)
          store.state.token = data.token
          router.push({path: "/msg"})
        }
      })
    }
  }
})
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Open+Sans");

.btn {
  display: inline-block;
  *display: inline;
  *zoom: 1;
  padding: 4px 10px 4px;
  margin-bottom: 0;
  font-size: 13px;
  line-height: 18px;
  color: #333333;
  text-align: center;
  text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
  vertical-align: middle;
  background-color: #f5f5f5;
  background-image: -moz-linear-gradient(to top, #ffffff, #e6e6e6);
  background-image: -ms-linear-gradient(to top, #ffffff, #e6e6e6);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#e6e6e6));
  background-image: -webkit-linear-gradient(to top, #ffffff, #e6e6e6);
  background-image: -o-linear-gradient(to top, #ffffff, #e6e6e6);
  background-image: linear-gradient(to top, #ffffff, #e6e6e6);
  background-repeat: repeat-x;
  filter: progid:dximagetransform.microsoft.gradient(startColorstr=#ffffff, endColorstr=#e6e6e6, GradientType=0);
  border-color: #e6e6e6 #e6e6e6 #e6e6e6;
  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
  border: 1px solid #e6e6e6;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  *margin-left: .3em;
}

.btn:hover, .btn:active, .btn:active, .btn:disabled, .btn[disabled] {
  background-color: #e6e6e6;
}

/*表示按钮大一点*/
.btn-large {
  padding: 9px 14px;
  font-size: 15px;
  line-height: normal;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
}

.btn:hover {
  color: #333333;
  text-decoration: none;
  background-color: #e6e6e6;
  /*background-image: none;*/
  /*background-position: 0 -50px;*/
  /*-webkit-transition: background-position 0.1s linear;*/
  /*-moz-transition: background-position 0.1s linear;*/
  /*-ms-transition: background-position 0.1s linear;*/
  /*-o-transition: background-position 0.1s linear;*/
  /*transition: background-position 0.1s linear;*/
  background-image: linear-gradient(to top, #4a77d4, rgba(255, 255, 255, 0));
}

.btn-primary, .btn-primary:hover {
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
  color: #ffffff;
}

.btn-primary:active {
  color: rgba(255, 255, 255, 0.75);
}

.btn-primary {
  background-color: #4a77d4;
  background-image: -moz-linear-gradient(to top, #6eb6de, #4a77d4);
  background-image: -ms-linear-gradient(to top, #6eb6de, #4a77d4);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6eb6de), to(#4a77d4));
  background-image: -webkit-linear-gradient(to top, #6eb6de, #4a77d4);
  background-image: -o-linear-gradient(to top, #6eb6de, #4a77d4);
  background-image: linear-gradient(to top, #6eb6de, #4a77d4);
  background-repeat: repeat-x;
  filter: progid:dximagetransform.microsoft.gradient(startColorstr=#6eb6de, endColorstr=#4a77d4, GradientType=0);
  border: 1px solid #3762bc;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.4);
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.5);
}

.btn-primary:hover, .btn-primary:active, .btn-primary:active, .btn-primary:disabled, .btn-primary[disabled] {
  filter: none;
  background-color: #4a77d4;
}

/*这个表示块状按钮*/
.btn-block {
  width: 100%;
  display: block;
}

.login {
  position: absolute;
  top: 50%;
  left: 50%;
  margin: -150px 0 0 -150px;
  width: 300px;
  height: 300px;
}

.login h1 {
  color: #fff;
  text-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  letter-spacing: 1px;
  text-align: center;
}

input {
  width: 100%;
  margin-bottom: 10px;
  background: rgba(0, 0, 0, 0.3);
  outline: none;
  padding: 10px;
  font-size: 13px;
  color: #fff;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(0, 0, 0, 0.3);
  border-radius: 4px;
  box-shadow: inset 0 -5px 45px rgba(100, 100, 100, 0.2), 0 1px 1px rgba(255, 255, 255, 0.2);
  -webkit-transition: box-shadow .5s ease;
  -moz-transition: box-shadow .5s ease;
  -o-transition: box-shadow .5s ease;
  -ms-transition: box-shadow .5s ease;
  transition: box-shadow .5s ease;
}

input:focus {
  box-shadow: inset 0 -5px 45px rgba(100, 100, 100, 0.4), 0 1px 1px rgba(255, 255, 255, 0.2);
}

</style>