<template>
<div>
  <div class="select-box">
    <el-form :inline="true" :model="userPageForm" class="select-form">
      <el-form-item label="用户名">
        <el-input v-model="userPageForm.username" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item label="角色">
        <el-select v-model="userPageForm.roleId" placeholder="Activity zone">
          <el-option label="全部" value=""/>
          <el-option v-for="role in roleList.list" :key="role.id" :label="role.roleName" :value="role.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="page">查询</el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>

<script>
import {ListRoleResp, PageUserReq, PageUserResp} from "@/type/User";
import {listRole} from "@/api/roleApi";
import {pageUser} from "@/api/userApi";

export default {
  name: "RoleView",

  data(){
    return{
      userPageForm:new PageUserReq(),
      userPageData:new PageUserResp(),
      roleList: new ListRoleResp()
    }
  },

  mounted() {
    listRole().then(resp=>{
      this.roleList = resp.data
    })
  },

  methods:{
    page(){
      pageUser(this.userPageForm).then(resp=>{
        this.userPageData = resp.data
      })
    }
  }

}
</script>

<style scoped>

</style>