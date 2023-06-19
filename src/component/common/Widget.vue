<script lang="ts">
import {defineComponent, PropType} from "vue";
import store from "../../store";
import {ElMessage, ElMessageBox} from "element-plus";
import {MenuOperate} from "@/type/component/MenuOperate";
import ContextMenu from "@/component/common/ContextMenu";

export default defineComponent({
  name: "ToolCard",

  props: {
    name: {
      type: String,
      required: true,
    },
    fixed: Boolean,
    menuOperate: Object as PropType<MenuOperate>,
  },

  data() {
    return {
      initial: true,
      opacity: 0,
      state: store.state,
      menu: {
        isOpen: false,
        top: 0,
        left: 0
      }
    };
  },
  methods: {
    addFixedToolCard() {
      const fixedToolCard = this.state.fixedToolCard;
      if (fixedToolCard[this.name]) {
        ElMessage.error(`已经添加了，请勿重复添加`);
      } else {
        const position = {top: 32, left: 32};
        fixedToolCard[this.name] = position;
        localStorage[`ToolCard:${this.name}`] = JSON.stringify(position);
      }
    },

    deleteFixedToolCard() {
      const fixedToolCard = this.state.fixedToolCard;
      if (fixedToolCard[this.name]) {
        ElMessageBox.confirm("确定要移除吗？").then(() => {
          delete fixedToolCard[this.name];
          delete localStorage[`ToolCard:${this.name}`];
        })
      }
    },

    isFixed(): boolean {
      const fixedToolCard = this.state.fixedToolCard;
      return fixedToolCard[this.name] !== undefined;
    },

    openMenu(e: PointerEvent) {
      if (!this.fixed) {
        return;
      }
      e.preventDefault();

      ContextMenu.open({
        ...this.menuOperate,
        删除: this.deleteFixedToolCard
      }, e)

      // // 窗口高度
      // let winWidth = window.innerWidth;
      // let winHeight = window.innerHeight
      // // 鼠标点击位置
      // let posX = e.pageX
      // let posY = e.pageY
      // // 菜单宽高
      // let menu: any = e.target
      // let menuWidth = menu.getBoundingClientRect().width
      // let menuHeight = menu.getBoundingClientRect().height
      //
      // let fixedPos = this.state.fixedToolCard[this.name]
      // if (!fixedPos) return;
      //
      // // 菜单显示位置
      // // let posLeft = posX + menuWidth - fixedPos.top >= winWidth
      // //     ? posX - menuWidth - fixedPos.left
      // //     : posX - fixedPos.left;
      // // let posTop = posY + menuHeight - fixedPos.top >= winHeight
      // //     ? posY - menuHeight - fixedPos.top
      // //     : posY - fixedPos.top;
      // this.menu.left = posX - fixedPos.left
      // this.menu.top = posY - fixedPos.top
      // this.menu.isOpen = true
    },
  },

  mounted() {
    document.addEventListener("click", () => {
      ContextMenu.close()
    })
  }
});
</script>

<template>
  <div>
    <!--suppress TypeScriptValidateTypes -->
    <div @contextmenu="openMenu">
      <slot/>
    </div>

    <!--    右键菜单列表-->
    <!--suppress TypeScriptValidateTypes -->
    <el-card class="right-click-menu" v-if="fixed && menu.isOpen"
             :style="{top: menu.top + 'px', left: menu.left + 'px'}"
             :body-style=" { padding: '2px' }"
    >
      <li class="menu-item" v-for="(key, operate) in menuOperate" :key="key">
        <div class="menu-item-content" @click="operate">
          {{ key }}
        </div>
      </li>
      <li class="menu-item">
        <div class="menu-item-content" @click="deleteFixedToolCard">
          移除
        </div>
      </li>
    </el-card>
  </div>

</template>

<style scoped lang="scss">
//卡片背景
.bg-div {
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: 100;
  background-color: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
}

//右边的操作按钮图标
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
