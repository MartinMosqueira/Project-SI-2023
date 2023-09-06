<template>
  <div>
    <h2>Mi perfil</h2>
    <form @submit.prevent="actualizarUsuario">
      <label>Username</label>
      <input v-model="usuario.username" />

      <label>Email</label>
      <input v-model="usuario.email" />

      <label>Fecha de Nacimiento</label>
      <input v-model="usuario.fechaNacimiento" />

      <label>Teléfono</label>
      <input v-model="usuario.telefono" />

      <label>Password</label>
      <input v-model="usuario.password" />

      <button type="submit">Actualizar</button>
    </form>
    <h2>Mi Red</h2>
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
import { mapGetters } from "vuex";

export default {
  name: "Perfil",
  computed: {
    ...mapGetters(["getToken"]) // Usa el getter getToken del Vuex Store
  },
  data() {
    return {
      usuario: {},
      contactos: [],
      seguidos: []
    };
  },
  mounted() {
    this.fetchUsuario();
    this.fetchContactos();
    this.fetchSeguido();
  },
  methods: {
    fetchUsuario() {
      // Configura las cabeceras para incluir el token de acceso
      const config = {
        headers: {
          Authorization: `Bearer ${this.getToken}` // Utiliza el token del Vuex Store
        }
      };

      axios.get("http://localhost:8080/usuario/get/", config)
          .then(response => {
            this.usuario = response.data;
          })
          .catch(error => {
            console.error("Error al obtener los datos del usuario:", error);
          });
    },
    actualizarUsuario() {
      const config = {
        headers: {
          Authorization: `Bearer ${this.getToken}`
        }
      };

      axios.put("http://localhost:8080/usuario/update", this.usuario, config)
          .then(response => {
            console.log("Usuario actualizado:", response.data);
          })
          .catch(error => {
            console.error("Error al actualizar el usuario:", error);
          });
    },
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
