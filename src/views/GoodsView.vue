<template>
  <div class="top-box">
    <div class="select-box">
      <el-form :inline="true" :model="pageForm" class="select-form">
        <el-form-item label="关键字">
          <el-input v-model="pageForm.keyword" placeholder="请输入关键字"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="page">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="data-box">
      <el-scrollbar>
        <el-table :data="pageData.list" border style="width: 100%">
          <el-table-column prop="title" label="商品名称" width="120" align="center"/>
          <el-table-column prop="amount" label="价格" width="80" align="center"/>
          <el-table-column prop="desc" label="描述"/>
        </el-table>
      </el-scrollbar>
    </div>
    <div class="page-box">
      <el-pagination
          class="text-right mt15"
          background
          layout="total, prev, pager, next, sizes"
          :page-sizes="[5, 10, 15, 20, 30]"
          v-model:page-size="pageForm.pageSize"
          v-model:current-page="pageForm.pageNum"
          :total="pageData.total"
      />
    </div>
  </div>

</template>

<script>
import {PageGoodsReq, PageGoodsResp} from "@/type/Goods";
import {goodsPage} from "@/api/goodsApi";

export default {
  name: "GoodsView",

  data() {
    return {
      pageForm: new PageGoodsReq(),
      pageData: new PageGoodsResp()
    }
  },

  mounted() {
    this.page()
  },

  watch: {
    'pageForm.pageNum'(newVar, oldVar){
      this.page()
    },
    'pageForm.pageSize'(newVar, oldVar){
      this.page()
    }
  },

  methods: {
    page() {
      goodsPage(this.pageForm).then(res => {
        this.pageData = res
      })
    }
  }
}
</script>

<style lang="scss" scoped>


.top-box {
  width: 100%;
  height: 100%;
  overflow: hidden; /*父元素也会受到子元素影响，所以要屏蔽掉*/

  padding: 0px; /*外边距*/

  .select-form {
    width: 50%;
  }

  .select-box {
    height: 5vh;
  }

  .data-box {
    text-align: center;
    height: calc(100vh - 80px - 40px - 5vh - 5vh - 10px);
  }

  .page-box {
    height: 5vh;
    margin-top: 10px;
  }

  .page-size {
    width: 80px;

  }
}

</style>