<script lang="ts">
import { defineComponent } from "vue"
import store from '../../store'
import { ElMessage } from "element-plus"

export default defineComponent({
  name: "ToolCard",

  props: {
    name: {
      type: String,
      required: true
    },
    fixed: Boolean,
  },

  data() {
    return {
      initial: true,
      opacity: 0,
      state: store.state
    }
  },

  mounted() {
    window.addEventListener("scroll", this.handleScroll)
  },
  beforeUnmount() {
    window.removeEventListener("scroll", this.handleScroll)
  },

  methods: {
    handleScroll() {
      const scrollTop = window.scrollY
      const windowHeight = window.innerHeight
      const documentHeight = document.documentElement.scrollHeight
      const maxScrollTop = documentHeight - windowHeight
      const scrollFraction = scrollTop / maxScrollTop
      const opacity = Math.min(scrollFraction, 1)
      this.opacity = opacity
    },

    addFixedToolCard() {
      const fixedToolCard = this.state.fixedToolCard
      if (fixedToolCard[this.name]) {
        ElMessage.error(`已经添加了，请勿重复添加`)
      } else {
        fixedToolCard[this.name] = { top: 32, left: 32 }
      }
      console.log(fixedToolCard)
    },

    deleteFixedToolCard() {
      const fixedToolCard = this.state.fixedToolCard
      if (fixedToolCard[this.name]) {
        delete fixedToolCard[this.name]
      }
    },

    isFixed():boolean {
      const fixedToolCard = this.state.fixedToolCard
      return fixedToolCard[this.name] !== undefined
    },
  },
})
</script>

<template>
  <div>
    <el-card class="bg-div" :style="{ borderColor: `rgb(200,200,200, ${opacity}` }" shadow="hover">
      <el-button v-if="fixed" class="operate-icon" text circle @click="deleteFixedToolCard">
        <el-icon>
          <Delete />
        </el-icon>
      </el-button>
      <el-button v-if="!fixed && !isFixed()" class="operate-icon" text circle @click="addFixedToolCard">
        <el-icon>
          <CirclePlus />
        </el-icon>
      </el-button>
      <slot />
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.bg-div {
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: -1;
  background-color: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
}

.operate-icon {
  position: absolute;
  top: -10px;
  right: -10px;
  opacity: 0;
  transition: all 0.3s ease;
  height: 10%;
  width: 10%;
  background-color: rgba(255, 255, 255, 0);
  // border-color: rgba(255, 255, 255, 0);
}

.bg-div:hover .operate-icon {
  opacity: 0.7;
  position: absolute;
  transform: translate(-80%, 60%);
  color: var(--el-text-color-primary);
}
</style>
