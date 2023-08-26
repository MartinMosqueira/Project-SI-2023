<template>
  <header v-if="authenticated">
    <nav>
      <RouterLink v-if="authenticated" to="/home">Notificaciones</RouterLink>
      <RouterLink v-if="authenticated" to="/publicar">Publicar</RouterLink>
      <RouterLink v-if="authenticated" to="/red">MiRed</RouterLink>
      <RouterLink v-if="authenticated" to="/buscar">Buscar</RouterLink>
      <RouterLink v-if="authenticated" to="/perfil">Tu perfil</RouterLink>
      <RouterLink v-if="authenticated" to="/about">About</RouterLink>
    </nav>
  </header>
</template>

<script>
import { computed } from 'vue';
import store from '../store';

export default {
  name: 'Header',
  setup() {
    const authenticated = computed(() => !!store.getters.getToken);
    return { authenticated };
  },
};
</script>
