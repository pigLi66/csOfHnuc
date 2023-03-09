<template>
  <div class="login-box page-header min-vh-100"
       :style="{
        backgroundColor: '#777',
        backgroundImage:
          'url(https://images.unsplash.com/photo-1497294815431-9365093b7331?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1950&q=80)'
      }"
       loading="lazy"
  >
    <div class="top-bar">测试系统</div>
    <div class="login-form-box">
      <n-form :ref="loginFormRef" :model="loginForm" :rules="rules"
              label-placement="left"
              show-require-mark="false"
              size="large"
      >
        <div class="form-title-card">
          <div class="form-title">
            <h1>LOG IN</h1>
          </div>
          <div>
            <n-button quaternary circle class="login-icon">
              <template #icon>
                <n-icon size="40px" color="#fff">
                  <github-filled></github-filled>
                </n-icon>
              </template>
            </n-button>
            <n-button quaternary circle class="login-icon">
              <template #icon>
                <n-icon size="40px" color="#fff">
                  <qq-circle-filled></qq-circle-filled>
                </n-icon>
              </template>
            </n-button>
          </div>
        </div>

        <n-form-item class="form-input-item" label="" path="username">
          <n-input v-model:value="loginForm.username"
                   size="large"
                   placeholder="邮箱">
            <template #suffix>
              <n-icon>
                <mail-outlined></mail-outlined>
              </n-icon>
            </template>
          </n-input>
        </n-form-item>
        <n-form-item class="form-input-item" label="" path="password">
          <n-input v-model:value="loginForm.password"
                   size="large"
                   type="password"
                   maxlength="20"
                   show-password-on="mousedown"
                   placeholder="密码"></n-input>
        </n-form-item>
        <n-form-item class="form-input-item">
          <n-switch></n-switch>
          <div style="margin-left: 2%; color: #9799b7">记住我</div>
        </n-form-item>

        <n-button size="large" class="submit-button" type="primary">
          登录
        </n-button>

        <div class="sign-up">
          <p> 没有账号？点击
            <n-button text tag="a" type="primary">
              注册
            </n-button>
          </p>
        </div>
      </n-form>
    </div>
    <div class="footer-bar">
      <div class="creator-info">@created by csOfHnuc team</div>
      <div class="bottom-bar">
        <n-button class="footer-bar-button" round>Github</n-button>
        <n-button class="footer-bar-button" round>Blog</n-button>
        <n-button class="footer-bar-button" round>License</n-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {FormInst, NButton, NForm, NFormItem, NIcon, NInput, NSwitch} from 'naive-ui';
import {LoginData} from "@/type/Login";
import * as loginApi from "@/api/loginApi";
import store from "@/store";
import router from "@/router";
import {GithubFilled, MailOutlined, QqCircleFilled} from "@vicons/antd";


export default defineComponent({
  name: "NewLoginView",

  components: {
    NForm,
    NFormItem,
    NInput,
    NButton,
    NSwitch,
    GithubFilled, QqCircleFilled, MailOutlined,
    NIcon
  },

  data() {
    return {
      loginForm: new LoginData(),

      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 20, message: '账号的长度在3到20之间', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 20, message: '密码的长度在3到20之间', trigger: 'blur'},
        ]
      }
    }
  },


  methods: {

    submit() {
      (this.$refs['loginFormRef'] as FormInst).validate((errors) => {
        if (!errors) {
          console.log(errors)
          loginApi.login(this.loginForm).then((res) => {
            console.log(res.data)
            store.state.token = res.data.token
            router.push("/home")
          })
        }
      })
    },

    reset() {
      this.loginForm.password = ""
      this.loginForm.username = ""
    },
  },


})
</script>

<style scoped lang="scss">

.page-header {
  padding: 0;
  overflow: hidden;
  align-items: center;
  background-size: cover;
  background-position: 50%
}

.min-vh-100 {
  min-height: 100vh !important
}


.login-box {
  width: 100%;
  height: 100%;
  overflow: hidden; /*父元素也会受到子元素影响，所以要屏蔽掉*/
  text-align: center;
  position: relative; //设置为相对定位
}

.login-form-box {
  width: 25vw;
  background-color: white;
  border-radius: 20px;
  position: absolute; //设置为绝对定位
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .form-title-card {
    height: 18vh;
    width: 100%;
    background-image: linear-gradient(195deg, #66bb6a 0%, #18a058 100%);
    border-radius: 20px 20px 0px 0px;
    margin-bottom: 10%;
    text-align: center;


    .form-title {
      //margin-top: 10%;
      padding: 3%;
      text-align: center;
      color: white;
      font-size: larger;
      font-weight: bolder;
    }

    .login-icon {
      margin-left: 10%;
      margin-right: 10%;
    }
  }

  .form-input-item {
    padding-left: 5%;
    padding-right: 5%;
  }

  .submit-button {
    padding-left: 5%;
    padding-right: 5%;
    margin-bottom: 10%;
    width: 90%;
  }

  .sign-up {
    margin-bottom: 5%;
    color: #9799b7;
  }
}

.top-bar {
  color: white;
  margin-top: 1.5%;
  margin-left: 5%;
  font-weight: bolder;
  text-align: left;
}

.footer-bar {
  color: white;
  overflow: hidden;
  margin-top: 87vh;
  text-align: left;
  display: inline-flex;

  .creator-info {

  }

  .bottom-bar {
    padding-left: 70vw;

    .footer-bar-button {
      color: white;
    }
  }

}

</style>