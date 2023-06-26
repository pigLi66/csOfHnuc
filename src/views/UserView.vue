<!-- eslint-disable space-before-function-paren -->
<script lang="ts">
import LoginCard from "@/component/LoginCard.vue";
import store from "@/store";
import {Md5} from "ts-md5";
import {logout} from "@/api/authApi";
import {defineComponent} from "vue";
import {ElMessageBox, FormInstance} from "element-plus";
import CustomCardListItem from "@/component/scrollbar/CustomWidgetItem.vue";

export interface AddCardForm {
  title: string;
  desc: string;
  html: string;
}

export default defineComponent({
  name: "UserView",
  components: {LoginCard, CustomCardListItem},
  data() {
    return {
      state: store.state,
      userState: store.state.user,
      localStorage,
      addDialogVisible: false,
      addCardForm: {
        title: "",
        desc: "",
        html: "",
      } as AddCardForm,
      rules: {
        title: [{required: true, message: "请输入卡片名称", trigger: "blur"}],
        desc: [{required: true, message: "请输入卡片描述", trigger: "blur"}],
      },
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
        location.reload()
      });
    },

    async addCustomCard() {
      await (this.$refs.registerFormRef as FormInstance).validate(
          (isValid: boolean) => {
            if (isValid) {
              // todo 添加到服务器，服务器返回了id
            }
          }
      );
    },
  },
  mounted() {
    window.scrollTo(0, 0);
  },
});
</script>
<template>
  <div class="view">
    <!--    登录的那个滑动卡片-->
    <LoginCard v-if="!state.user.isLogin"></LoginCard>
    <!--    登录之后的用户详情-->
    <el-card v-if="state.user.isLogin" class="user-card-view">
      <el-container>
        <!-- 左侧的用户个人界面 -->
        <el-aside width="200px">
          <span style="font-family: '扁桃体'">HELLO</span>
          <el-divider/>
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
              <font-awesome-icon :icon="['fas', 'gear']" size="xl"/>
            </el-button>
            <!-- 消息提醒 -->
            <el-button class="user-info-icon-button" circle>
              <el-badge :value="1" class="item" type="primary">
                <font-awesome-icon :icon="['fas', 'bell']" size="xl"/>
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
        <el-main>
          <el-row style="margin-bottom: 20px">
            <el-col :span="20">
              <span class="custom-card-title">我的卡片</span>
            </el-col>
            <el-col :span="4">
              <el-row>
                <!-- 帮助按钮 -->
                <el-button class="blank-bg custom-card-menu-btn-help" circle>
                  <font-awesome-icon :icon="['far', 'circle-question']"/>
                </el-button>
                <!-- 新增卡片按钮 -->
                <el-button
                    class="blank-bg custom-card-menu-btn-add"
                    circle
                    @click="addDialogVisible = true"
                >
                  <font-awesome-icon :icon="['fas', 'plus']"/>
                </el-button>
              </el-row>
            </el-col>
          </el-row>
          <el-scrollbar height="80vh">
            <CustomCardListItem
                name="MoyuCard"
                title="摸鱼卡片"
                desc="摸鱼的卡片"
            >
            </CustomCardListItem>
            <CustomCardListItem
                name="LeetcodeCard"
                title="力扣每日一题"
                desc="力扣每日一题"
            >
            </CustomCardListItem>
          </el-scrollbar>
        </el-main>
      </el-container>
      <div class="user-info"></div>
    </el-card>
    <el-dialog
        v-model="addDialogVisible"
        title="新增卡片"
        width="30%"
        style="color: black"
    >
      <template #header>
        <div>新增自定义卡片</div>
      </template>
      <el-form ref="addFormRef" :model="addCardForm" :rules="rules" status-icon>
        <el-form-item prop="title">
          <el-input placeholder="卡片名称" v-model="addCardForm.title">
          </el-input>
        </el-form-item>
        <el-form-item prop="desc">
          <el-input
              placeholder="卡片描述"
              v-model="addCardForm.desc"
          ></el-input>
        </el-form-item>
        <el-from-item prop="html">
          <el-input
              placeholder="卡片内容/HTML"
              autosize
              type="textarea"
              v-model="addCardForm.html"
          ></el-input>
        </el-from-item>
        <el-form-item>
          <el-button type="primary">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
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
  //background: radial-gradient(ellipse at bottom, #253241 0%, #171a27 100%);
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
  font-weight: bolder;
}

.email-info {
  color: #919599;
  font-size: small;
  font-style: italic;
  font-weight: bolder;
}

.user-info-icon-button {
  background-color: rgba(39, 43, 48, 0);
  border-color: rgba(255, 255, 255, 0);
  color: var(--card-border-color);
}

.main-container {
  padding-top: 0px;
}

.custom-card-menu-btn-help,
.custom-card-menu-btn-add,
.custom-card-menu-btn-edit {
  color: white;
  border-color: rgba(255, 255, 255, 0);
}

.custom-card-menu-btn-help {
  background-color: #919599;
}

.custom-card-menu-btn-add {
  background-color: var(--el-color-success);
}

.custom-card-menu-btn-edit {
  background-color: var(--el-color-primary);
}

.custom-card-menu-btn-help:hover,
.custom-card-menu-btn-add:hover,
.custom-card-menu-btn-edit:hover {
  border-radius: 30%;
  transform: rotate(1turn) scale(1.3);
  transition: all 0.5s;
}

.table-row {
  background-color: rgba(255, 255, 255, 0);
  border-color: rgba(255, 255, 255, 0);
}

// *:hover {
//   background-color: rgba(255, 255, 255, 0);
//   border-color: rgba(255, 255, 255, 0);
// }
</style>
