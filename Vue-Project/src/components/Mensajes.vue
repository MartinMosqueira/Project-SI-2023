<template>
  <div>
    <h2>Publicar Mensaje</h2>
    <input v-model="mensaje.mensaje" placeholder="Mensaje" />
    <input v-model="mensaje.tags" placeholder="Tags" />
    <button @click="publicarMensaje">Publicar</button>

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
      usuarioActual: null,
      mensaje: {
        mensaje: "",
        tags: ""
      }
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
            this.usuarioActual = response.data.id;
          })
          .catch(error => {
            console.error("Error al obtener el usuario actual:", error);
          });
    },
    publicarMensaje() {
      axios.post("http://localhost:8080/mensajeMuro/create", this.mensaje)
          .then(response => {
            console.log("Mensaje publicado con éxito:", response.data);
            this.mensaje.mensaje = "";
            this.mensaje.tags = "";
            this.fetchMensajes();
          })
          .catch(error => {
            console.error("Error al publicar el mensaje:", error);
          });
    }
  }
};
</script>
