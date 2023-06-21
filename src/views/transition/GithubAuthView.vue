<!-- eslint-disable indent -->
<script lang="ts">
import {defineComponent} from "vue";
import store from "@/store";
import {loginByGithub} from "@/api/authApi";
import {ElMessage} from "element-plus";
import Loading from "@/component/common/Loading.vue";

export default defineComponent({
  name: "GithubAuthView",
  async created() {
    const state = store.state;
    const code = state.curUrl.param.get("code");
    if (!store.state.user.isLogin && code) {
      const res = await loginByGithub(code);
      if (res) {
        state.user.isLogin = true;
        localStorage.setItem("token", res.token);
        localStorage.setItem("email", res.email);
        localStorage.setItem("username", res.name);
        localStorage.setItem("userId", res.id);
        localStorage.setItem("avatar", res.avatar);
        // location.reload();
        state.curUrl.param.set("menu", "User");
        ElMessage.success("Github登录成功，正在跳转至主页");
        return setTimeout(() => {
          return this.$router.push("/home");
        }, 3000);
      }
    }
    ElMessage.error("Github登录失败");
    setTimeout(() => {
      return this.$router.push("/home");
    }, 3000);
  },
  components: {Loading},
});
</script>
<template>
  <Loading></Loading>
</template>
<style></style>
