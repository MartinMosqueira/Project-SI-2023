import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Perfil from '../components/Perfil.vue'
import Contactos from '../components/Contactos.vue'
import Siguiendo from '../components/Siguiendo.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/contactos',
      name: 'contactos',
      component: Contactos
    },
    {
      path: '/siguiendo',
      name: 'siguiendo',
      component: Siguiendo
    },
    {
      path: '/perfil',
      name: 'perfil',
      component: Perfil
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    }
  ]
})

export default router
