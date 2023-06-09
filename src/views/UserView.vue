<!-- eslint-disable space-before-function-paren -->
<script lang="ts">
import LoginCard from "@/component/LoginCard.vue";
import store from "@/store";
import { Md5 } from "ts-md5";
import { logout } from "@/api/authApi";
import { defineComponent } from "vue";
import { ElMessageBox } from "element-plus";

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const api = {
  userLogin: "/login/userLogin",
  userLoginSalt: "/login/userLoginSalt",
};
export default defineComponent({
  name: "UserView",
  components: { LoginCard },
  data() {
    return {
      state: store.state,
      userState: store.state.user,
      localStorage,
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
      ElMessageBox.confirm("确定退出账号吗？").then(async () => {
        await logout();
        location.reload();
      });
    },
  },
  mounted() {
    window.scrollTo(0, 0);
  },
});
</script>
<template>
  <div class="view">
    <LoginCard v-if="!state.user.isLogin"></LoginCard>
    <el-card v-if="state.user.isLogin" class="user-card-view">
      <el-container>
        <!-- 左侧的用户个人界面 -->
        <el-aside width="200px">
          HELLO
          <el-divider />
          <el-avatar
            shape="circle"
            :size="70"
            :src="localStorage.getItem('avatar')"
          />
          <div class="main-info">{{ localStorage.getItem("username") }}</div>
          <div class="main-info email-info">
            {{ localStorage.getItem("email") }}
          </div>
          <!-- 用户信息设置 -->
          <el-row style="justify-content: center; margin-top: 30px">
            <el-button class="user-info-icon-button" circle>
              <font-awesome-icon :icon="['fas', 'gear']" size="xl" />
            </el-button>
            <!-- 消息提醒 -->
            <el-button class="user-info-icon-button" circle>
              <el-badge :value="1" class="item" type="primary">
                <font-awesome-icon :icon="['fas', 'bell']" size="xl" />
              </el-badge>
            </el-button>
            <!-- 登出 -->
            <el-button class="user-info-icon-button" circle @click="logout()">
              <font-awesome-icon
                :icon="['fas', 'right-from-bracket']"
                size="xl"
              />
            </el-button>
          </el-row>
        </el-aside>
        <!-- 右侧主要内容 -->
        <el-main>建设中，还不知道用来做什么</el-main>
      </el-container>
      <div class="user-info"></div>
    </el-card>
  </div>
</template>
<style scoped lang="scss">
.view {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.user-card-view {
  width: 93vw;
  height: 100vh;
  background: radial-gradient(ellipse at bottom, #253241 0%, #171a27 100%);
}

.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  left: 0px;
  border: var(--el-text-color-primary);
}

.main-info {
  margin-top: 20px;
  font-size: large;
}

.email-info {
  color: #919599;
  font-size: small;
}

.user-info-icon-button {
  background-color: rgba(39, 43, 48, 0);
  border-color: rgba(255, 255, 255, 0);
  color: var(--card-border-color);
}
</style>
