<script lang="ts">
import store from "@/store";
import { LoginData } from "@/type/Auth";
import { ElMessage, FormInstance } from "element-plus";
import { defineComponent } from "vue";
import * as authApi from "../api/authApi";

export default defineComponent({
  name: "LoginCard",
  data() {
    return {
      visible: false,
      loading: false,
      isSignUp: false,

      userState: store.state.user,
      loginForm: new LoginData(),
      rules: {
        email: [
          { required: true, message: "请输入账户邮箱", trigger: "blur" },
          {
            pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
            message: "请填写正确的邮箱地址",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "密码的长度在3到20之间",
            trigger: "blur",
          },
        ],
        name: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            max: 30,
            message: "用户名长度需小于30",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    toGithubLoginPage() {
      window.location.href = `https://github.com/login/oauth/authorize?client_id=${process.env.VUE_APP_GITHUB_APP_ID}&scope=read:user user:email`;
    },
    async login() {
      await (this.$refs.loginFormRef as FormInstance).validate(
        (isValid: boolean) => {
          if (isValid) {
            console.log(isValid);
            authApi.login(this.loginForm).then((res) => {
              // 设置全局状态
              const userState = store.state.user;
              userState.isLogin = true;
              userState.userId = res.id;
              userState.username = res.name;
              userState.avatar = res.avatar;
              userState.email = res.email;
              this.visible = false;
              this.$cookies.set("email", userState.userId);
              this.$cookies.set("username", userState.username);
              this.$cookies.set("userId", userState.userId);
              this.$cookies.set("avatar", userState.avatar);
              // location.reload();
              ElMessage.success("登录成功");
            });
          }
        }
      );
    },
  },
});
</script>

<template>
  <div
    :class="{ container: true, 'right-panel-active': isSignUp }"
    id="container"
  >
    <div class="form-container sign-up-container">
      <el-form ref="loginFormRef" :model="loginForm" :rules="rules" status-icon>
        <h1 style="margin-bottom: 10%">创建账户</h1>
        <el-form-item prop="email">
          <el-input
            size="large"
            v-model="loginForm.email"
            placeholder="名称"
            @keyup.enter="login()"
          >
            <template #prefix>
              <font-awesome-icon :icon="['far', 'user']" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input
            size="large"
            v-model="loginForm.email"
            placeholder="邮箱"
            @keyup.enter="login()"
          >
            <template #prefix>
              <font-awesome-icon :icon="['far', 'envelope']" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password" class="password-input">
          <el-input
            size="large"
            v-model="loginForm.password"
            placeholder="密码"
            show-password
            @keyup.enter="login()"
          >
            <template #prefix>
              <font-awesome-icon :icon="['fas', 'lock']" />
            </template>
          </el-input>
        </el-form-item>
        <el-button
          :loading="loading"
          class="login-button"
          size="large"
          type="primary"
          @click="login()"
        >
          登录
        </el-button>
      </el-form>
    </div>
    <div class="form-container sign-in-container">
      <el-form ref="loginFormRef" :model="loginForm" :rules="rules" status-icon>
        <h1>登录账户</h1>
        <el-row class="login-form-row other-way-login-row social-container">
          <el-button class="other-way-login-button" circle>
            <font-awesome-icon
              icon="fa-brands fa-github"
              size="2xl"
              @click="toGithubLoginPage()"
            />
          </el-button>
        </el-row>
        <el-form-item prop="email">
          <el-input
            size="large"
            v-model="loginForm.email"
            placeholder="邮箱"
            @keyup.enter="login()"
          >
            <template #prefix>
              <font-awesome-icon :icon="['far', 'envelope']" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password" class="password-input">
          <el-input
            size="large"
            v-model="loginForm.password"
            placeholder="密码"
            show-password
            @keyup.enter="login()"
          >
            <template #prefix>
              <font-awesome-icon :icon="['fas', 'lock']" />
            </template>
          </el-input>
        </el-form-item>
        <el-button
          :loading="loading"
          class="login-button"
          size="large"
          type="primary"
          @click="login()"
        >
          登录
        </el-button>
      </el-form>
    </div>
    <div class="overlay-container">
      <div class="overlay">
        <div class="overlay-panel overlay-left">
          <h1>WELCOME</h1>
          <p>
            我们需要更多的摸鱼<br />
            We need more 摸鱼<br />
            もっと摸鱼が必要です<br />
            Нам нужно больше времени для 摸鱼<br />
          </p>
          <el-button
            class="change-button"
            @click="isSignUp = false"
            plain
            round
            size="large"
            auto-insert-space
          >
            登录
          </el-button>
          <!-- <button class="ghost" id="signIn" @click="isSignUp = false">
            Sign In
          </button> -->
        </div>
        <div class="overlay-panel overlay-right">
          <h1>HELLO FRIEND</h1>
          <p>快来加入摸鱼<s>&nbsp;团伙&nbsp;</s>大家庭</p>
          <!-- <button class="ghost" id="signUp" @click="isSignUp = true">
            Sign Up
          </button> -->
          <el-button
            class="change-button"
            @click="isSignUp = true"
            plain
            round
            size="large"
            auto-insert-space
          >
            注册
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped lang="scss">
* {
  box-sizing: border-box;
}

body {
  background: #222;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  font-family: "Montserrat", sans-serif;
  height: 100vh;
  margin: -20px 0 50px;
}

h1 {
  font-weight: bold;
  margin: 0;
}

h2 {
  text-align: center;
}

p {
  font-size: 14px;
  font-weight: 100;
  line-height: 20px;
  letter-spacing: 0.5px;
  margin: 20px 0 30px;
}

span {
  font-size: 12px;
}

a {
  color: #333;
  font-size: 14px;
  text-decoration: none;
  margin: 15px 0;
}

.login-button {
  width: 100%;
}

.change-button {
  color: white;
  background-color: rgba(0, 0, 0, 0);
  width: 40%;
  border-width: 1px;
}

.other-way-login-row {
  color: black;
  margin-top: 3vh;

  .other-way-login-button {
    border-color: rgba(255, 255, 255, 0);
  }
}

form {
  background-color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 50px;
  height: 100%;
  text-align: center;
  width: 80%;
}

.container {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  position: relative;
  overflow: hidden;
  width: 768px;
  max-width: 100%;
  min-height: 480px;
}

.form-container {
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.6s ease-in-out;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.sign-in-container {
  left: 0;
  width: 50%;
  z-index: 2;
}

.container.right-panel-active .sign-in-container {
  transform: translateX(100%);
}

.sign-up-container {
  left: 0;
  width: 50%;
  opacity: 0;
  z-index: 1;
}

.container.right-panel-active .sign-up-container {
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
  animation: show 0.6s;
}

@keyframes show {
  0%,
  49.99% {
    opacity: 0;
    z-index: 1;
  }

  50%,
  100% {
    opacity: 1;
    z-index: 5;
  }
}

.overlay-container {
  position: absolute;
  top: 0;
  left: 50%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transition: transform 0.6s ease-in-out;
  z-index: 100;
}

.container.right-panel-active .overlay-container {
  transform: translateX(-100%);
}

.overlay {
  background: var(--el-color-primary);
  background: -webkit-linear-gradient(
    to right,
    var(--el-color-primary-light-3),
    var(--el-color-primary)
  );
  background: linear-gradient(
    to right,
    var(--el-color-primary),
    var(--el-color-primary-light-3)
  );
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 0 0;
  color: #ffffff;
  position: relative;
  left: -100%;
  height: 100%;
  width: 200%;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}

.container.right-panel-active .overlay {
  transform: translateX(50%);
}

.overlay-panel {
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 40px;
  text-align: center;
  top: 0;
  height: 100%;
  width: 50%;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}

.overlay-left {
  transform: translateX(-20%);
}

.container.right-panel-active .overlay-left {
  transform: translateX(0);
}

.overlay-right {
  right: 0;
  transform: translateX(0);
}

.container.right-panel-active .overlay-right {
  transform: translateX(20%);
}

.social-container {
  margin: 20px 0;
}

.social-container a {
  border: 1px solid #dddddd;
  border-radius: 50%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  margin: 0 5px;
  height: 40px;
  width: 40px;
}

footer {
  background-color: #222;
  color: #fff;
  font-size: 14px;
  bottom: 0;
  position: fixed;
  left: 0;
  right: 0;
  text-align: center;
  z-index: 999;
}

footer p {
  margin: 10px 0;
}

footer i {
  color: red;
}

footer a {
  color: #3c97bf;
  text-decoration: none;
}
</style>
