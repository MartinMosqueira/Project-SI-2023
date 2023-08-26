<template>
  <div>
    <h2>Mis Mensajes</h2>
    <ul>
      <li v-for="mensaje in mensajes" :key="mensaje.id">
        <p>Username: {{ mensaje.username }}</p>
        <p>Fecha: {{ mensaje.fecha }}</p>
        <p>Mensaje: {{ mensaje.mensaje }}</p>
        <p>Tags: {{ mensaje.tags }}</p>
        <router-link v-if="mensaje.idUsuario === usuarioActual" :to="`/editar-mensaje/${mensaje.id}`">Editar</router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      mensajes: [],
      usuarioActual: null
    };
  },
  mounted() {
    this.fetchMensajes();
    this.fetchUsuarioActual();
  },
  methods: {
    fetchMensajes() {
      axios.get("http://localhost:8080/mensajeMuro/get/all")
          .then(response => {
            this.mensajes = response.data;
          })
          .catch(error => {
            console.error("Error al obtener los mensajes:", error);
          });
    },
    fetchUsuarioActual() {
      axios.get("http://localhost:8080/usuario/get/")
          .then(response => {
            console.log(response.data);
            this.usuarioActual = response.data.id;
          })
          .catch(error => {
            console.error("Error al obtener el usuario actual:", error);
          });
    },
    editarMensaje(mensajeId) {
      // Implementa la lógica para editar el mensaje con el ID mensajeId
    }
  }
};
</script>
