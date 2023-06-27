<script lang="ts">

import {defineComponent} from "vue";
import Widget from "@/component/common/Widget.vue";
import store from "@/store";

declare let L2Dwidget: any

export default defineComponent({
  name: 'Live2d',
  props: {
    fixed: Boolean,
  },

  components: {
    Widget,
  },

  data() {
    return {
      fixedWidgetCache: store.state.fixedWidgetCache,
      modelNameList: [
        'chitose',
        // 'epsilon2_1',
        // 'gf',
        'haru/01',
        'haru/02',
        'haruto',
        'hibiki',
        'hijiki',
        'izumi',
        'koharu',
        'miku',
        'ni-j',
        'nico',
        // 'nietzsche',
        'nipsilon',
        'nito',
        'shizuku',
        'tororo',
        'tsumiki',
        'unitychan',
        'wanko',
        'z16'
      ],
      live2d: {
        model: {
          name: 'chitose',
          jsonPath: "https://unpkg.com/live2d-widget-model-tororo/assets/tororo.model.json",
          scale: 1 // 比例
        },
        display: {
          position: "right", // 看板娘的表现位置
          width: 200, // 模型的宽度
          height: 300, // 模型的高度
          hOffset: 0,
          vOffset: 0
        },
        mobile: {
          show: true, // 是否在移动端展示
          scale: 0.5  // 移动端模型比例
        },
        react: {
          opacityDefault: 0.7,
          opacityOnHover: 0.2
        },
        dialog: {
          enable: true, //是否开启对话框
          script: {
            //每20s，显示一言（调用一言Api返回的句子）
            'every idle 20s': '$hitokoto$',
            //触摸到class='star'对象
            'hover .star': '星星在天上而你在我心里 (*/ω＼*)',
            //触摸到身体
            'tap body': '害羞⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄',
            //触摸到头部
            'tap face': '~~'
          }
        },
      },
      isOpenDialog: false
    }
  },

  methods: {
    getModelUrl(modelName: string) {
      return `https://unpkg.com/live2d-widget-model-${modelName}/assets/${modelName.replace(/\//, "")}.model.json`
    },

    openDialog() {
      this.isOpenDialog = true
    },

    saveAndReload() {
      this.live2d.model.jsonPath = this.getModelUrl(this.live2d.model.name)
      this.fixedWidgetCache.set(this.$options.name, {
        status: this.live2d
      })
      location.reload()
    },

    async bindDrag() {
      let timerId =  setInterval(() => {
        const widgetDiv = document.getElementById("live2d-widget")
        const widgetEle = document.getElementById("Live2d-Widget")
        console.log(1)
        if (widgetDiv && widgetEle) {
          widgetDiv.style.position = 'relative'
          widgetEle.appendChild(widgetDiv)
          clearInterval(timerId)
        }
      }, 200)
      setTimeout(() => {
        clearInterval(timerId)
      }, 5000)
    }
  },

  created() {
    if (!this.fixed) {
      return
    }
    let widgetCache = this.fixedWidgetCache[this.$options.name]
    if (widgetCache && widgetCache.status) {
      this.live2d = widgetCache.status
    }

    // 引入远程js
    let scriptEl = document.createElement("script")
    scriptEl.type = 'text/javascript'
    scriptEl.src = 'https://l2dwidget.js.org/lib/L2Dwidget.min.js'
    document.body.appendChild(scriptEl)

    // let scriptEl1 = document.createElement("script")
    // scriptEl1.type = 'text/javascript'
    // scriptEl1.src = 'https://blog-static.cnblogs.com/files/liuzhou1/L2Dwidget.0.min.js'
    // document.body.appendChild(scriptEl1)

    scriptEl.onload = () => {
      L2Dwidget.init(this.live2d);
      this.bindDrag()
    }
  },
})
</script>

<template>
  <Widget id="Live2d-Widget" :name="this.$options.name" :fixed="fixed"
          :menu-operate="{
      '设置Live2d': openDialog
    }"
  >
<!--    <div class="operate-btn">-->
<!--      <font-awesome-icon :icon="['far', 'star']"-->
<!--                         class="btn-icon"/>-->
<!--      <span class="btn-text">LIVE</span>-->
<!--      <span class="btn-text2">2D</span>-->
<!--    </div>-->
    <el-dialog v-model="isOpenDialog" width="30%" center append-to-body>
      <el-form v-model="live2d"
               label-position="right"
      >
        <el-form-item label="选择模型">
          <el-select style="width: 100%" v-model="live2d.model.name">
            <el-option v-for="name in modelNameList" :key="name"
                       :label="name" :value="name"/>
          </el-select>
        </el-form-item>
        <el-form-item label="比例（0.0-1.0）">
          <el-input v-model="live2d.model.scale"></el-input>
        </el-form-item>
<!--        <el-form-item label="模型位置">-->
<!--          <el-select v-model="live2d.display.position">-->
<!--            <el-option label="右边" value="right"/>-->
<!--            <el-option label="左边" value="left"/>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item label="模型宽度(像素px)">
          <el-input v-model="live2d.display.width"></el-input>
        </el-form-item>
        <el-form-item label="模型高度(像素px)">
          <el-input v-model="live2d.display.height"></el-input>
        </el-form-item>
<!--        <el-form-item label="模型水平偏移(像素px)">-->
<!--          <el-input v-model="live2d.display.hOffset"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="模型垂直偏移(像素px)">-->
<!--          <el-input v-model="live2d.display.vOffset"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item>
          <el-button
              class="submit-btn"
              type="primary"
              @click="saveAndReload"
          >保存
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </Widget>
</template>

<style scoped lang="scss">
.operate-btn {
  color: #8ab9ff;
  position: relative;
  animation: float 3s ease-in-out infinite;
}

.btn-icon {
  width: 60px;
  height: 60px;
}

@keyframes float {
  0% {
    transform: translateY(-10px);
  }
  50% {
    transform: translateY(0px);
  }
  100% {
    transform: translateY(-10px);
  }
}

.btn-text,
.btn-text2 {
  position: absolute;
  font-family: '扁桃体';
}

.btn-text {
  top: 35%;
  left: 32%;
  font-size: 10px;
}

.btn-text2 {
  top: 51%;
  left: 38%;
  font-size: 4px;
}

.submit-btn {
  width: 100%;
  margin-top: 5%;
  margin-left: 10%;
  margin-right: 10%;
}
</style>
