import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'

const routes: Array<RouteRecordRaw> = [
    {
        path:"/",
        redirect:"/login"
    },
    {
        path: '/login',
        name: 'login',
        component: () => import("@/views/LoginPage.vue"),
    },
    {
        path: '/home',
        name: 'home',
        component: () => import("@/components/MainContent.vue")
    }, {
        path: "/msg",
        name: "msg",
        component: ()=>import("@/views/MsgList.vue")
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
