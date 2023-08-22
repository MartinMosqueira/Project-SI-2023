<template>
  <div>
    <p>Mi perfil</p>
    <p>Username: {{ usuario.username }}</p>
    <p>Email: {{ usuario.email }}</p>
    <p>Fecha de Nacimiento: {{ usuario.fechaNacimiento }}</p>
    <p>Teléfono: {{ usuario.telefono }}</p>
    <p>Rol: {{ usuario.rol }}</p>
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
            console.log(this.getToken);
          })
          .catch(error => {
            console.error("Error al obtener los datos del usuario:", error);
          });
    }
  }
};
</script>
