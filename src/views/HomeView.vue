<!-- eslint-disable indent -->
<script lang="ts">
import SearchView from "@/views/SearchView.vue";
import ToolView from "@/views/ToolView.vue";
import UserView from "@/views/UserView.vue";
import {HTML5Backend} from "react-dnd-html5-backend";
import {defineComponent} from "vue";
import {DndProvider} from "vue3-dnd";
import store from "@/store";
import SettingView from "@/views/SettingView.vue";

type MenuButton = "Home" | "User" | "Tool" | "Setting";

function strIsMenuType(str: string): str is MenuButton {
  return (
      str === "Home" || str === "User" || str === "Tool" || str === "Setting"
  );
}

export default defineComponent({
  name: "HomeView",
  components: {
    DndProvider,
    SearchView,
    ToolView: ToolView,
    UserView: UserView,
    SettingView,
  },

  data() {
    return {
      initial: true,
      HTML5Backend: HTML5Backend,
      state: store.state,

      // 菜单相关数据
      curMenu: "Home" as MenuButton,
      mouseHover: false,
      slideWidth: 0,
      bgSlide: {
        top: 6,
        height: 68,
      },
      broderSlide: {
        top: 10,
        height: 50,
        opacity: 0,
      },
      openMenu: false,
      localStorage,
      value1: false,
    };
  },

  methods: {
    /**
     *
     * @param e 点击事件
     */
    selectMenu(e: any, curMenu: MenuButton) {
      const target = e.currentTarget;
      const top = target.offsetTop;
      const height = target.offsetHeight;
      this.bgSlide.top = top;
      this.bgSlide.height = height;
      this.curMenu = curMenu;
      console.log(curMenu);
    },
    hoverMenuButton(e: any) {
      const target = e.currentTarget;
      const top = target.offsetTop;
      const height = target.offsetHeight;
      this.broderSlide.top = top;
      this.broderSlide.height = height;

      this.broderSlide.opacity = 1;
    },
    endHoverMenuButton() {
      this.broderSlide.opacity = 0;
      this.mouseHover = false;
    },
    hoverMenu() {
      this.openMenu = true;
    },
    endHoverMenu() {
      this.openMenu = false;
    },
  },

  mounted() {
    window.scrollTo(0, 0);
    document.documentElement.scrollTop = 0;
    // document.body.style.setProperty("--el-text-color-primary", "#FFFFFF");


    this.state.user.isLogin = localStorage.getItem("token") !== null;

    const param = this.state.curUrl.param;
    const menuParam = param.get("menu");
    this.curMenu = menuParam && strIsMenuType(menuParam) ? menuParam : "Home";
  },
});
</script>

<template>
  <DndProvider
      :backend="HTML5Backend"
      style="top: 100px; background-color: white"
  >
    <el-affix :offset="120">
      <div :class="{ menu: true, 'menu-open': openMenu }">
        <div id="nav-1">
          <ul class="nav">
            <li
                :class="{ slide1: true }"
                :style="{
                top: bgSlide.top + 'px',
                height: bgSlide.height + 'px',
              }"
            ></li>
            <li
                :class="{ slide2: true, squeeze: mouseHover }"
                :style="{
                top: broderSlide.top + 'px',
                height: broderSlide.height + 'px',
                opacity: broderSlide.opacity,
              }"
            ></li>
            <li>
              <a
                  class="active"
                  @click="selectMenu($event, 'Home')"
                  @mouseover="hoverMenuButton($event)"
                  @mouseout="endHoverMenuButton()"
              >
                <el-icon size="25">
                  <Search/>
                </el-icon
                >
              </a>
            </li>
            <li>
              <a
                  @click="selectMenu($event, 'User')"
                  @mouseover="hoverMenuButton($event)"
                  @mouseout="endHoverMenuButton()"
              >
                <el-icon size="25">
                  <User/>
                </el-icon
                >
              </a>
            </li>
            <li>
              <a
                  @click="selectMenu($event, 'Setting')"
                  @mouseover="hoverMenuButton($event)"
                  @mouseout="endHoverMenuButton()"
              >
                <el-icon size="25">
                  <Setting/>
                </el-icon
                >
              </a>
            </li>
            <li>
              <a
                  @click="selectMenu($event, 'Tool')"
                  @mouseover="hoverMenuButton($event)"
                  @mouseout="endHoverMenuButton()"
              >
                <el-icon size="25">
                  <CopyDocument/>
                </el-icon
                >
              </a>
            </li>
          </ul>
        </div>
      </div>
      <div
          style="
          position: absolute;
          height: 40vh;
          width: 50px;
          opacity: 0;
          left: 0px;
          top:30vh;
          z-index: 30;
        "
          @mouseover="hoverMenu()"
          @mouseout="endHoverMenu()"
      >
        1
      </div>
    </el-affix>
    <SearchView :class="{ 'hidden-view': curMenu !== 'Home' }"></SearchView>
    <UserView :class="{ 'hidden-view': curMenu !== 'User' }"></UserView>
    <ToolView :class="{ 'hidden-view': curMenu !== 'Tool' }"></ToolView>
    <SettingView :class="{ 'hidden-view': curMenu !== 'Setting' }">
    </SettingView>
  </DndProvider>
</template>

<style lang="scss">
.hidden-view {
  display: none;
}

.menu {
  left: -40px;
  position: absolute;
  top: 30vh;
  height: 100%;
  width: 50px;
  z-index: 50;
}

.menu:hover {
  transform: translateX(40px);
  transition: all 0.4s cubic-bezier(0.23, 1, 0.32, 1.05);
}

.menu-open {
  transform: translateX(40px);
  transition: all 0.4s cubic-bezier(0.23, 1, 0.32, 1.05);
}

#nav-1 .nav {
  position: relative;
  border: none;
  border-radius: 10em;
  // display: flex;
  list-style: none;
  background: #f5f5f5;
  box-shadow: 20px 40px 50px #00000044;
  padding: 5px;
  vertical-align: middle;
}

#nav-1 .nav li {
  margin: 0px;
}

#nav-1 .nav li a {
  position: relative;
  // padding: 0.6em 2em;
  // padding: 5px 5px 20px 20px;
  padding-top: 20px;
  padding-bottom: 20px;
  font-size: 16px;
  border: none;
  color: #333;
  display: inline-block;
  text-decoration: none;
  z-index: 104;
}

#nav-1 .slide1,
#nav-1 .slide2 {
  position: absolute;
  width: 40px;
  border-radius: 10em;
  transition: all 0.4s cubic-bezier(0.23, 1, 0.32, 1.05);
}

#nav-1 .nav .slide1 {
  background-color: var(--el-color-primary-light-3);
  z-index: 33;
}

#nav-1 .nav .slide2 {
  opacity: 0;
  background: #fff;
  border: 1px solid #8ab9ff;
  z-index: 32;
}

.squeeze {
  transition: all 1.5s;
  -webkit-transform: scale(0.9);
  transform: scale(0.9);
}
</style>
