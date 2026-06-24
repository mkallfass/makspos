import { createRouter, createWebHistory } from 'vue-router'
import OrderFormView from '../views/OrderFormView.vue'
import StatisticsView from '@/views/StatisticsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      redirect: '/order-form'
    },
    {
      path: '/order-form',
      name: 'order-form',
      component: OrderFormView
    },
    {
      path: '/statistics',
      name: 'statistics',
      component: StatisticsView
    }
  ]
})

export default router
