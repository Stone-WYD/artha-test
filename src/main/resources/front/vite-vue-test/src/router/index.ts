import { createRouter, createWebHistory } from 'vue-router'
import Yinhun from '../views/ComicContent/Yinhun/index.vue'



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/comic/yinhun',
      name: 'yinhun',
      component: Yinhun,
    },
  ],
})

export default router
