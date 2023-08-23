<template>
  <div>
    <h2>Siguiendo</h2>
    <ul>
      <li v-for="seguido in seguidos" :key="seguido.username">
        <p>Username: {{ seguido.username }}</p>
        <p>Email: {{ seguido.email }}</p>
        <button @click="eliminarSeguido(seguido.id)">Eliminar</button>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      seguidos: []
    };
  },
  mounted() {
    this.fetchSeguido();
  },
  methods: {
    fetchSeguido() {
      axios.get("http://localhost:8080/usuario/get/seguidos")
          .then(response => {
            this.seguidos = response.data;
          })
          .catch(error => {
            console.error("Error al obtener los seguidos:", error);
          });
    },
    eliminarSeguido(id) {
      if (id) {
        axios.delete(`http://localhost:8080/usuario/delete/seguido/${id}`)
            .then(response => {
              // Elimina el contacto de la lista de contactos
              this.seguidos = this.seguidos.filter(seguidos => seguidos.id !== id);
            })
            .catch(error => {
              console.error("Error al eliminar el seguido:", error);
            });
      } else {
        console.error("ID de seguido no válido:", id);
      }
    }
  }
};
</script>
