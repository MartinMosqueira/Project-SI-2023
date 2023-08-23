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
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      contactos: []
    };
  },
  mounted() {
    this.fetchContactos();
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
    }
  }
};
</script>
