// 登录页不往父组件传递任何信息，登录/登出只会操作cookie后刷新页面以重加载父组件，父组件自行判断cookie状态
// 登录页接受三个参数，父组件从cookie获取的用户id、用户名、登录名
// 用户id用于判定按钮展示"登录"还是用户名，用户名用于在页面上展示，登录名用于传入子组件，便于修改用户信息（如改密码）时减少填写量
<template>
    <div class="login-component">
        <el-popover
                class="login-popover"
                placement="bottom"
                width="200"
                trigger="click"
                v-model="visible"
                v-show="isLogin()"
        >
            <template #reference>
                <el-button round>
                    <el-icon><User /></el-icon>
                    登录
                </el-button>
            </template>
            <!--            登录之前显示登录卡片-->
            <h4 style="text-align: center; margin: 10px">LOGIN</h4>
            <el-form
                    ref="loginFormRef"
                    :model="loginForm"
                    :rules="rules"
                    status-icon
            >
                <el-form-item prop="email" class="email-input">
                    <el-input
                            size="small"
                            v-model="loginForm.email"
                            placeholder="邮箱"
                            @keyup.enter="login()"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="password" class="password-input">
                    <el-input
                            size="small"
                            v-model="loginForm.password"
                            placeholder="密码"
                            show-password
                            @keyup.enter="login()"
                    ></el-input>
                </el-form-item>
                <el-form-item style="width: 100%;">
                    <el-button
                        class="login-button"
                        size="small"
                        text
                        type="primary"
                        @click="login()"
                    >
                        注册
                    </el-button>
                    <el-button
                            :loading="loading"
                            class="login-button"
                            size="small"
                            type="primary"
                            @click="login()"
                    >
                        登录
                    </el-button>
                </el-form-item>
            </el-form>
            <!--            <el-button type="text" v-slot="reference">登录</el-button>-->
        </el-popover>

        <!--        登录之后显示操作下拉框-->
        <el-dropdown class="user-popover" trigger="hover" v-show="!isLogin">
            <span class="el-dropdown-link userinfo-inner">{{ userState.username }}</span>
            <el-dropdown-menu>
                <el-dropdown-item @click="consoleClicked"
                >控制台
                </el-dropdown-item
                >
                <el-dropdown-item @click="cloudClicked"
                >网盘 / 图床
                </el-dropdown-item
                >
                <el-dropdown-item divided @click="logout"
                >退出登录
                </el-dropdown-item
                >
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>
<script lang="ts">
import {Md5} from "ts-md5";
import {defineComponent} from "vue";
import {ElMessage, FormInstance} from "element-plus";
import {LoginData} from "@/type/Login";
import store from "@/store";
import * as loginApi from '../api/LoginApi'

const api = {
    userLogin: "/login/userLogin",
    userLoginSalt: "/login/userLoginSalt",
};
export default defineComponent({
    name: "login",
    data() {
        return {
            visible: false,
            loading: false,

            salt: "",
            user: "",
            cloudStatus: 0,

            drawer: {
                title: "",
                size: "",
                visible: false,
                direction: "ttb",
            },

            userState: store.state.user,
            loginForm: new LoginData(),
            rules: {
                email: [
                    {required: true, message: '请输入账户邮箱', trigger: 'blur'},
                    {pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/, message:'请填写正确的邮箱地址', trigger: 'blur' }
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 3, max: 20, message: '密码的长度在3到20之间', trigger: 'blur'},
                ]
            }
        };
    },
    methods: {

        isLogin() {
            return store.state.user.isLogin
        },

        md5It(str: string) {
            str = Md5.hashStr(str);
            return str;
        },

        async login() {
            await (this.$refs['loginFormRef'] as FormInstance).validate((isValid: boolean) => {
                if (isValid) {
                    console.log(isValid)
                    loginApi.login(this.loginForm).then((res) => {
                        let data = res.data
                        console.log(data)
                        // 设置全局状态
                        let state = store.state.user
                        state.isLogin = true
                        state.userId = data.data.userId
                        state.username = data.data.username

                        this.visible = false;

                        this.$cookies.set("email", state.userId);
                        this.$cookies.set("username", state.username);
                        this.$cookies.set("userId", state.userId);
                        location.reload();

                        ElMessage.success("登录成功")
                    })
                }
            })
        },

        logout() {
            this.$cookies.remove("email");
            this.$cookies.remove("username");
            this.$cookies.remove("userId");
            ElMessage.success("退出成功")
            location.reload();
        },

        consoleClicked() {
            this.drawer.title = "控制台";
            this.drawer.size = "500";
            this.drawer.visible = true;
            this.drawer.direction = "ttb";
        },
        cloudClicked() {
            this.drawer.title = "网盘 / 图床";
            this.drawer.size = "50%";
            this.drawer.visible = true;
            this.drawer.direction = "rtl";
            this.cloudStatus = 0;
        },
    },
    created() {
    },
    mounted() {
    },
});
</script>
<style scoped>
.login-component {
    position: absolute;
    right: 0px;
    top: 0px;
    z-index: 99;
    margin-top: 10px;
    margin-right: 20px;
}

.email-input {
    padding-bottom: 3px;
}

.password-input {
    padding-bottom: 3px;
}

.login-button {
    text-align: center;
    width: 45%;
}

.userinfo-inner {
    font-size: 14px;
    cursor: pointer;
    color: #409eff;
}

.user-popover {
    padding-top: 12px;
}
</style>
