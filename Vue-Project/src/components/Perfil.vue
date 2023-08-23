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
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex"; // Importa mapGetters desde vuex

export default {
  name: "Perfil",
  computed: {
    ...mapGetters(["getToken"]) // Usa el getter getToken del Vuex Store
  },
  data() {
    return {
      usuario: {} // Aquí se almacenarán los datos del usuario
    };
  },
  mounted() {
    this.fetchUsuario();
  },
  methods: {
    fetchUsuario() {
      // Configura las cabeceras para incluir el token de acceso
      const config = {
        headers: {
          Authorization: `Bearer ${this.getToken}` // Utiliza el token del Vuex Store
        }
      };

      // Realiza la solicitud GET al endpoint del backend con las cabeceras configuradas
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
            // Actualización exitosa, podrías mostrar un mensaje de éxito
            console.log("Usuario actualizado:", response.data);
          })
          .catch(error => {
            console.error("Error al actualizar el usuario:", error);
          });
    }
  }
};
</script>
