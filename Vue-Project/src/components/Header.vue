<template>
  <header v-if="authenticated">
    <nav>
      <RouterLink v-if="authenticated" to="/home">Notificaciones</RouterLink>
      <RouterLink v-if="authenticated" to="/buscar">Buscar</RouterLink>
      <RouterLink v-if="authenticated" to="/perfil">Tu perfil</RouterLink>
      <RouterLink v-if="authenticated" to="/about">About</RouterLink>
      <button @click="logout">Logout</button>
    </nav>
  </header>
</template>

<script>
import { computed } from 'vue';
import store from '../store';
import router from "@/router";

export default {
  name: 'Header',
  setup() {
    const authenticated = computed(() => !!store.getters.getToken);
    const logout = () => {
      // Limpia el token en Vuex
      store.dispatch('updateToken', '');

      // Limpia el token en localStorage
      localStorage.removeItem('token');

      // Redirige al usuario a la página de inicio de sesión
      router.push('/');
    };
    return { authenticated, logout };
  },
};
</script>
