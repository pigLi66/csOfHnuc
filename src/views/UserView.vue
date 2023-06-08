//
登录页不往父组件传递任何信息，登录/登出只会操作cookie后刷新页面以重加载父组件，父组件自行判断cookie状态
// 登录页接受三个参数，父组件从cookie获取的用户id、用户名、登录名 //
用户id用于判定按钮展示"登录"还是用户名，用户名用于在页面上展示，登录名用于传入子组件，便于修改用户信息（如改密码）时减少填写量

<script lang="ts">
import LoginCard from "@/component/LoginCard.vue";
import store from "@/store";
import { ElMessage } from "element-plus";
import { Md5 } from "ts-md5";
import { defineComponent } from "vue";
import * as authApi from "../api/authApi";

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const api = {
  userLogin: "/login/userLogin",
  userLoginSalt: "/login/userLoginSalt",
};
export default defineComponent({
  name: "login",
  data() {
    return {
      state: store.state,

      userState: store.state.user,
    };
  },
  methods: {
    isLogin() {
      return store.state.user.isLogin;
    },
    md5It(str: string) {
      str = Md5.hashStr(str);
      return str;
    },

    logout() {
      this.$cookies.remove("email");
      this.$cookies.remove("username");
      this.$cookies.remove("userId");
      ElMessage.success("退出成功");
      location.reload();
    },
  },
  created() {},
  async mounted() {
    const code = this.state.curUrl.param.get("code");
    console.log(code);
    if (!store.state.user.isLogin && code) {
      const res = await authApi.loginByGithub(code);
      if (res) {
        this.state.user.isLogin = true;
        const userState = store.state.user;
        userState.isLogin = true;
        userState.userId = res.id;
        userState.username = res.name;
        userState.avatar = res.avatar;
        userState.email = res.email;
        this.$cookies.set("email", userState.email);
        this.$cookies.set("username", userState.username);
        this.$cookies.set("userId", userState.userId);
        this.$cookies.set("avatar", userState.avatar);
        // location.reload();
        ElMessage.success("登录成功");
      }
    }
  },
  components: { LoginCard },
});
</script>
<template>
  <div style="height: 100vh; width: 100vw;">
    <div class="view">
      <LoginCard v-if="!state.user.isLogin" style="top: 15vh"></LoginCard>
      <el-card v-if="state.user.isLogin"> 你好 </el-card>
    </div>
  </div>
</template>
<style scoped lang="scss">
.view {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-title {
  margin-bottom: 3vh;
  text-align: center;
  width: 100%;
  color: white;
  text-shadow: 2px 2px 10px #ececec;
}

.login-component {
  position: absolute;
  left: calc(50% - 12.5vw);
  top: calc(50% - 30vh);
  width: 25vw;
  height: 50vh;
}

.login-form-row {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-button {
  width: 9vw;
}

.other-way-login-row {
  color: black;
  margin-top: 3vh;

  .other-way-login-button {
    border-color: rgba(255, 255, 255, 0);
  }
}
</style>
