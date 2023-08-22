<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div>
      <nav>
        <RouterLink to="/home">Mensajes</RouterLink>
        <RouterLink to="/contactos">Contactos</RouterLink>
        <RouterLink to="/siguiendo">Siguiendo</RouterLink>
        <RouterLink to="/perfil">Tu perfil</RouterLink>
        <RouterLink to="/about">About</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>
