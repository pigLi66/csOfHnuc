使用方法类似于ElMessage
可以通过引入types/component/ContextMenu.ts来直接调用右键的菜单
菜单内容还比较简单，只支持显示文字，不支持显示其他图标（当然，emoji还是可以的）
<script lang="ts">
import {defineComponent, ref} from "vue";
import {MenuOperate} from "@/type/component/MenuOperate";
import {ElCard} from "element-plus";

export default defineComponent({

  // 这个组件 是动态创建的，所以要引入全局组件，不然就报错了
  components: {
    ElCard,
  },

  setup() {
    const menuAttr = ref({
      isOpen: false,
      top: 0,
      left: 0
    })
    let menuOperate = ref<MenuOperate>({})
    const open = (menu: MenuOperate, e: PointerEvent) => {
      menuOperate.value = menu
      console.log(menu, menuOperate.value)
      e.preventDefault()
      // 窗口高度
      let winWidth = window.innerWidth;
      let winHeight = window.innerHeight
      // 鼠标点击位置
      let posX = e.pageX
      let posY = e.pageY
      // 菜单宽高
      let target: any = e.target
      let menuWidth = target.getBoundingClientRect().width
      let menuHeight = target.getBoundingClientRect().height

      // 菜单显示位置
      let posLeft = posX + menuWidth >= winWidth
          ? posX - menuWidth
          : posX
      let posTop = posY + menuHeight >= winHeight
          ? posY - menuHeight
          : posY

      menuAttr.value = {
        left: posLeft,
        top: posTop,
        isOpen: true
      }
    }

    const close = () => {
      menuAttr.value.isOpen = false
    }

    return {
      menuAttr,
      menuOperate,
      open,
      close
    }
  },
});
</script>

<template>
  <div>
    <!--    右键菜单列表-->
    <!--suppress TypeScriptValidateTypes -->
    <el-card class="right-click-menu" v-if=" menuAttr.isOpen"
             :style="{top: menuAttr.top + 'px', left: menuAttr.left + 'px', zIndex:999}"
             :body-style=" { padding: '2px' }"
    >
      <li class="menu-item" v-for="(operate, key) in menuOperate" :key="key">
        <div class="menu-item-content" @click="operate">
          {{ key }}
        </div>
      </li>
    </el-card>
  </div>

</template>

<style scoped lang="scss">

//右键菜单
.right-click-menu {
  width: 100px;
  max-width: 200px;
  padding: 1px;
  border-radius: 5%;
  position: absolute;
  z-index: 999;
  background-color: rgba(255, 255, 255, 0);
  backdrop-filter: blur(10px);
}

.right-click-menu li {
  background-color: white;
  width: 105%;
  list-style-type: none; // 去掉列表前缀
  font-weight: 10;
  background-color: rgba(255, 255, 255, 0);
}

//右键菜单里的每一项
.menu-item {
  background: #3c3c3c;
  border-radius: 0;
  border-left: 5px solid var(--gradient-color); // 菜单左边框的值用的菜单聚焦时的颜色
  margin-bottom: 1px;
  position: relative;
  transition: .5s;
  display: inline-block;
  margin-left: -5px;
  vertical-align: top;
  background-color: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
}

//.menu-item:hover {
//  color: white;
//}

.menu-item:nth-child(1n+0) {
  --accent-color: #ffc5b4;
  --gradient-color: #ffeab0;
}

.menu-item:nth-child(1n+1) {
  --accent-color: #d8ffb6;
  --gradient-color: #beeeff;
}

.menu-item:nth-child(1n+2) {
  --accent-color: #fff8dc;
  --gradient-color: #fcdce4;
}

.menu-item:last-child {
  --accent-color: #ffdbdb;
  --gradient-color: #ffbdbd;
}

.menu-item:after {
  background: #c7c7c7;
  content: "";
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  transition: .5s;
  max-width: 0;
  overflow: hidden;
}

.menu-item:focus,
.menu-item:focus-within,
.menu-item:hover {
  z-index: 100;
  color: white;
}

.menu-item:hover:after,
.menu-item:focus:after,
.menu-item:focus-within:after {
  //background: linear-gradient(to left, var(--accent-color), var(--gradient-color));
  max-width: 110%;
  background: linear-gradient(to bottom, var(--accent-color), var(--gradient-color));
  border-radius: 3px;
  top: 0px;
  bottom: 0px;
  left: 0px;
  right: 0px;
}

.menu-item-content {
  color: #545454;
  display: flex;
  justify-content: center;
  position: relative;
  text-decoration: none;
  z-index: 1;
  width: 100%;
  background-color: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(5px);
}

.menu-item-content:focus {
  outline: none;
  position: relative;
}

.menu-item-content:focus:after {
  content: "";
  background: #FBFBFB;
  position: absolute;
  bottom: 5px;
  left: 15px;
  height: 3px;
  width: 0;
  transition: .5s;
  transition-delay: .2s;
}

.menu-item:focus .menu-item-content:before,
.menu-item:focus-within .menu-item-content:before {
  transform: rotate(-180deg);
}


</style>
