<template>
  <div class="login-box">
    <el-form
        ref="loginFormRef"
        :model="loginForm"
        status-icon
        :rules="rules"
        label-width="60px"
        class="demo-ruleForm"
    >
      <h2>LOG IN</h2>
      <el-form-item label="账号" prop="username" class="input-box">
        <el-input v-model="loginForm.username" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
            v-model="loginForm.password"
            type="password"
            autocomplete="off"
        />
      </el-form-item>

      <el-form-item>
        <el-button class="login-button" type="primary" @click="submit">
          登 录
        </el-button>
        <el-button class="login-button" @click="reset">
          重 置
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import * as loginApi from '@/request/loginApi'
import {FormInstance} from "element-plus";
import {LoginData} from "@/type/Login";
import store from "@/store";
import router from "@/router";

interface ApiLoginResp {
  id: number
  token: string
}

export default defineComponent({
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
      (this.$refs['loginFormRef'] as FormInstance).validate((isValid: boolean) => {
        if (isValid) {
          loginApi.login(this.loginForm).then((res) => {
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

<style lang="scss" scoped>

.login-box {
  width: 100%;
  height: 100%;
  background-color: #1f2029;
  overflow: hidden; /*父元素也会受到子元素影响，所以要屏蔽掉*/
  text-align: center;

  .demo-ruleForm {
    width: 400px;
    margin: 200px auto;
    background: #2a2b38;
    padding: 30px; /*外边距*/
    border-radius: 20px;
  }

  .el-input {
    height: 40px;
    -el-text-color-regular: #c4c3ca;
    --el-fill-color-blank: #1f2029;
    --el-border-color: #1f2029;
    --el-color-primary: #535568;
    --el-text-color-disabled: #535568;
    --el-border: #535568;
    --el-input-hover-border-color: #434458;
  }

  .el-input__inner {
    background: #2a2b38;
  }

  .login-button {
    width: 45%;
    color: #535568;
    font-weight: bolder;
    margin-top: 40px;
    height: 40px;
    --el-color-primary: #ffeba7;
    --el-color-primary-dark-2: #c3ab80;
    --el-color-primary-light-3: #dcc593;
  }

  h2 {
    color: #c4c3ca;
    font-weight: bolder;
    margin-bottom: 40px;
  }
}

</style>