<template>
  <header v-if="authenticated" class="header">
    <div class="left-links">
      <RouterLink  class="links" v-if="authenticated" to="/home">Notificaciones</RouterLink>
      <RouterLink class="links" v-if="authenticated" to="/buscar">Buscar</RouterLink>
      <RouterLink class="links" v-if="authenticated" to="/perfil">Tu perfil</RouterLink>
      <RouterLink class="links" v-if="authenticated" to="/about">About</RouterLink>
    </div>
    <div class="right-links">
      <button @click="logout">Logout</button>
    </div>
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

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #4267B2;
  color: white;
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.left-links {
  display: flex;
}

.links {
  margin-right: 20px;
  color: white;
  text-decoration: none;
  font-size: 15px;
}

.right-links button {
  background-color: transparent;
  border: none;
  color: white;
  cursor: pointer;
  font-weight: bold;
}
</style>