import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Perfil from '../components/Perfil.vue'
import Login from '../components/Login.vue'
import store from "../store";
import Buscar from "../components/Buscar.vue"
import Publicar from "../components/Publicar.vue"
import EditarMensaje from "../components/EditarMensaje.vue"
import Register from "../components/Register.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/publicar',
      name: 'publicar',
      component: Publicar,
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
      path: '/editar-mensaje/:id',
      name: 'editarmensaje',
      component: EditarMensaje,
      beforeEnter: (to, from, next) => {
        const token = store.getters.getToken;
        if (token) {
          next();
        } else {
          next("/");
        }
      },
    },
    {
      path: '/buscar',
      name: 'buscar',
      component: Buscar,
      beforeEnter: (to, from, next) => {
        const token = store.getters.getToken;
        if (token) {
          next();
        } else {
          next("/");
        }
      },
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView,
      beforeEnter: (to, from, next) => {
        const token = store.getters.getToken;
        if (token) {
          next();
        } else {
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
          next();
        } else {
          next("/");
        }
      },
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
      beforeEnter: (to, from, next) => {
        const token = store.getters.getToken;
        if (token) {
          next();
        } else {
          next("/");
        }
      },
    }
  ]
})

export default router
