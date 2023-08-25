<template>
  <div>
    <h2>Buscar y Agregar Usuarios</h2>
    <input v-model="searchText" placeholder="Buscar usuario por nombre" />
    <button @click="buscarUsuario">Buscar</button>

    <ul>
      <li v-for="usuario in usuarios" :key="usuario.id">
        {{ usuario.username }}
        <button @click="agregarContacto(usuario.id)">Agregar Contacto</button>
        <button @click="agregarSeguido(usuario.id)">Agregar Seguido</button>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      searchText: "",
      usuarios: []
    };
  },
  methods: {
    buscarUsuario() {
      if (this.searchText) {
        axios.get(`http://localhost:8080/usuario/find/${this.searchText}`)
            .then(response => {
              console.log(response.data);
              this.usuarios.push(response.data);
              console.log(this.usuarios);
            })
            .catch(error => {
              console.error("Error al buscar el usuario:", error);
            });
      }
    },
    agregarContacto(idUsuario) {
      axios.post(`http://localhost:8080/usuario/add/contacto/${idUsuario}`)
          .then(response => {
            // Actualizar la lista de usuarios después de agregar
            this.usuarios = this.usuarios.filter(usuario => usuario.id !== idUsuario);
          })
          .catch(error => {
            console.error("Error al agregar el usuario:", error);
          });
    },
    agregarSeguido(idUsuario) {
      axios.post(`http://localhost:8080/usuario/add/seguido/${idUsuario}`)
          .then(response => {
            // Actualizar la lista de usuarios después de agregar como seguido
            this.usuarios = this.usuarios.filter(usuario => usuario.id !== idUsuario);
          })
          .catch(error => {
            console.error("Error al agregar como seguido:", error);
          });
    }
  }
};
</script>
