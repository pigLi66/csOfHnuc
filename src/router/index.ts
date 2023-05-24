import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router"
import store from "@/store"
import HomeView from "@/views/HomeView.vue"
import * as path from "path"

const routes: Array<RouteRecordRaw> = [
  {
    // redirect 表示当路径是'/'时，默认跳转到路径 login 对应的页面。
    path: "/",
    redirect: "/login",
  },
  // {
  //     path: '/login',
  //     name: 'login',
  //     component: () => import("@/views/NewLoginView.vue"),
  // },
  // {
  //     path: '/home',
  //     name: 'home',
  //     component: () => HomeView,
  //     // 配置子路由
  //     children: [
  //         {
  //             path: "/goods",
  //             name: "goods",
  //             meta: {
  //                 isShow: true,
  //                 title: "商品列表"
  //             },
  //             component: import("@/views/GoodsView.vue")
  //         },
  //         {
  //             path: "/user",
  //             name: "user",
  //             meta: {
  //                 isShow: true,
  //                 title: "用户列表"
  //             },
  //             component: import("@/views/UserView.vue")
  //         },
  //     ]
  // },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

// 挂载路由导航守卫，to准备到哪去，from从哪里来，next下一步去哪
// router.beforeEach((to, from, next) => {
//     // 放行登录
//     if (to.path === '/login') {
//         return next()
//     }
//     // 检查token，没有token直接去登录界面
//     // const token = store.state.token;
//     // if (!token) {
//     //     return next('/login')
//     // } else {
//     //     return next()
//     // }
// })

export default router
