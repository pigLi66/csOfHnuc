<template>
  <div>
    <el-container>
      <el-header>
        <el-row :gutter="20">
          <el-col :span="4"><img src="../assets/logo.png" class="logo"></el-col>
          <el-col :span="16"><h2>测试管理系统</h2></el-col>
          <el-col :span="4"><span class="logout">退出登录</span></el-col>
        </el-row>
      </el-header>

      <el-container>
        <el-aside width="200px">
          <el-menu
              active-text-color="#ffd04b"
              background-color="#2a2b38"
              class="el-menu-vertical-demo"
              default-active="2"
              text-color="#fff"
              @open="handleOpen"
              @close="handleClose"
              router
          >
<!--            router开启路由模式，通过el-menu-item的index进行跳转-->
            <el-menu-item :index="item.path" v-for="item in menuItem" :key="item.path">
              <span>{{item.meta.title}}</span>
            </el-menu-item>
          </el-menu>
        </el-aside>

        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts">
import router from "@/router";

export default {
  name: "HomeView",

  data(){
    const items = router.getRoutes().filter(it=>it.meta.isShow)
    return{
      menuItem: items
    }
  },

  methods:{

  }
}


</script>

<style lang="scss" scoped>

.el-header {
  height: 80px;
  background-color: #2a2b38;
  color: white
}

.logo {
  height: 80px;
}

h2 {
  text-align: center;
  height: 80px;
  line-height: 80px;
}

.logout {
  text-align: center;
  height: 80px;
  line-height: 80px;
}

.el-aside{
  background-color: #545c64;
  .el-menu{
    height: calc(100vh - 80px);
  }
}


</style>