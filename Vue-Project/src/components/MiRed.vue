<template>
  <div>
    <h2>Contactos</h2>
    <ul>
      <li v-for="contacto in contactos" :key="contacto.username">
        <p>Username: {{ contacto.username }}</p>
        <p>Email: {{ contacto.email }}</p>
        <button @click="eliminarContacto(contacto.id)">Eliminar</button>
      </li>
    </ul>
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
      contactos: [],
      seguidos: []
    };
  },
  mounted() {
    this.fetchContactos();
    this.fetchSeguido();
  },
  methods: {
    fetchContactos() {
      axios.get("http://localhost:8080/usuario/get/contactos")
          .then(response => {
            this.contactos = response.data;
          })
          .catch(error => {
            console.error("Error al obtener los contactos:", error);
          });
    },
    eliminarContacto(id) {
      if (id) {
        axios.delete(`http://localhost:8080/usuario/delete/contacto/${id}`)
            .then(response => {
              // Elimina el contacto de la lista de contactos
              this.contactos = this.contactos.filter(contacto => contacto.id !== id);
            })
            .catch(error => {
              console.error("Error al eliminar el contacto:", error);
            });
      } else {
        console.error("ID de contacto no válido:", id);
      }
    },
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
