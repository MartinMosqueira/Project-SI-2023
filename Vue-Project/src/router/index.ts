import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Perfil from '../components/Perfil.vue'
import Contactos from '../components/Contactos.vue'
import Siguiendo from '../components/Siguiendo.vue'
import Login from '../components/Login.vue'
import store from "../store";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView,
      beforeEnter: (to, from, next) => {
        const token = store.getters.getToken;
        if (token) {
          // El usuario tiene un token válido, permite el acceso
          next();
        } else {
          // Redirige al inicio de sesión si el usuario no tiene un token válido
          next("/");
        }
      },
    },
    {
      path: '/contactos',
      name: 'contactos',
      component: Contactos,
      beforeEnter: (to, from, next) => {
        const token = store.getters.getToken;
        if (token) {
          // El usuario tiene un token válido, permite el acceso
          next();
        } else {
          // Redirige al inicio de sesión si el usuario no tiene un token válido
          next("/");
        }
      },
    },
    {
      path: '/siguiendo',
      name: 'siguiendo',
      component: Siguiendo,
      beforeEnter: (to, from, next) => {
        const token = store.getters.getToken;
        if (token) {
          // El usuario tiene un token válido, permite el acceso
          next();
        } else {
          // Redirige al inicio de sesión si el usuario no tiene un token válido
          next("/");
        }
      },
    },
    {
      path: '/perfil',
      name: 'perfil',
      component: Perfil,
      beforeEnter: (to, from, next) => {
        const token = store.getters.getToken;
        if (token) {
          // El usuario tiene un token válido, permite el acceso
          next();
        } else {
          // Redirige al inicio de sesión si el usuario no tiene un token válido
          next("/");
        }
      },
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
      beforeEnter: (to, from, next) => {
        const token = store.getters.getToken;
        if (token) {
          // El usuario tiene un token válido, permite el acceso
          next();
        } else {
          // Redirige al inicio de sesión si el usuario no tiene un token válido
          next("/");
        }
      },
    }
  ]
})

export default router
