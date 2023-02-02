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
      <h2>测试系统</h2>
      <el-form-item label="账号" prop="username">
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
          登录
        </el-button>
        <el-button class="login-button" @click="reset">
          重置
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
import * as loginApi from '@/request/api'
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
    }

    // login(): void {
    //   (this.$refs['loginFormRef'] as FormInstance).validate((valid: boolean) => {
    //     if (valid) {
    //       loginApi.login({
    //         method: 'POST',
    //         path: '/api/user/login',
    //         data: {
    //           "username": this.loginForm.username,
    //           "password": this.loginForm.password
    //         }
    //       })
    //       model.service({
    //
    //       }).then((resp: IResp<ApiLoginResp>) => {
    //         const {code, data} = resp
    //         if (code === StateCode.success) {
    //           console.log(data)
    //           store.state.token = data.token
    //           router.push({path: "/msg"})
    //         }
    //       })
    //     }
    //   })
    // }
  }
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
    width: 500px;
    margin: 200px auto;
    background: #ffffff;
    padding: 30px; /*外边距*/
    border-radius: 20px;
  }

  .login-button {
    width: 48%;
  }

  h2 {
    margin-bottom: 40px;
  }
}
</style>