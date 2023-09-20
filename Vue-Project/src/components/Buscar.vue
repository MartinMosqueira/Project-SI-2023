<template>
  <div class="search-container">
    <h2 style="color: #4267B2; font-size: 20px;">Buscar y Agregar Usuarios</h2>
    <div class="search">
      <input v-model="searchText" placeholder="Buscar usuario por nombre" />
      <button @click="buscarUsuario">Buscar</button>
    </div>

    <ul>
      <li v-for="usuario in usuarios" :key="usuario.id" class="user-item">
        {{ usuario.username }}
        <button @click="agregarContacto(usuario.id)" class="action-button">Agregar Contacto</button>
        <button @click="agregarSeguido(usuario.id)" class="action-button">Agregar Seguido</button>
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
              this.usuarios.push(response.data);
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
            this.usuarios = this.usuarios.filter(usuario => usuario.id !== idUsuario);
          })
          .catch(error => {
            console.error("Error al agregar como seguido:", error);
          });
    }
  }
};
</script>

<style scoped>
.search-container {
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.search {
  display: flex;
  margin-bottom: 10px;
}

.search-container input {
  flex: 1;
  padding: 5px;
}

.search-container button {
  background-color: #4267B2;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}

.user-item {
  margin-bottom: 10px;
}

.action-button {
  background-color: #4267B2;
  color: white;
  border: none;
  padding: 5px 10px;
  margin-left: 10px;
  cursor: pointer;
}
</style>
