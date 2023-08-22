<template>
  <header v-if="authenticated">
    <nav>
      <RouterLink v-if="authenticated" to="/home">Mensajes</RouterLink>
      <RouterLink v-if="authenticated" to="/contactos">Contactos</RouterLink>
      <RouterLink v-if="authenticated" to="/siguiendo">Siguiendo</RouterLink>
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
