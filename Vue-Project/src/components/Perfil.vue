<template>
  <div class="profile-container">
    <h2 class="section-heading">Mi perfil</h2>
    <form @submit.prevent="actualizarUsuario" class="form">
      <label for="username">Username:</label>
      <input v-model="usuario.username" id="username" class="input-field" />

      <label for="email">Email:</label>
      <input v-model="usuario.email" id="email" class="input-field" />

      <label for="fechaNacimiento">Fecha de Nacimiento:</label>
      <input v-model="usuario.fechaNacimiento" id="fechaNacimiento" class="input-field" />

      <label for="telefono">Teléfono:</label>
      <input v-model="usuario.telefono" id="telefono" class="input-field" />

      <button type="submit" class="action-button">Actualizar</button>
    </form>

    <h2 class="section-heading">Seguridad</h2>
    <form @submit.prevent="actualizarPassword" class="form">
      <label for="rol">Rol:</label>
      <span id="rol">{{ usuario.rol }}</span>
      <label for="password">Contraseña:</label>
      <input v-model="password" type="password" id="password" class="input-field" />
      <button type="submit" class="action-button">Actualizar</button>
    </form>

    <h2 class="section-heading">Mi Red</h2>
    <ul class="user-list">
      <li v-for="contacto in contactos" :key="contacto.username" class="user-item">
        <p>Username: {{ contacto.username }}</p>
        <p>Email: {{ contacto.email }}</p>
        <button @click="eliminarContacto(contacto.id)" class="action-button">Eliminar</button>
      </li>
    </ul>

    <h2 class="section-heading">Siguiendo</h2>
    <ul class="user-list">
      <li v-for="seguido in seguidos" :key="seguido.username" class="user-item">
        <p>Username: {{ seguido.username }}</p>
        <p>Email: {{ seguido.email }}</p>
        <button @click="eliminarSeguido(seguido.id)" class="action-button">Eliminar</button>
      </li>
    </ul>

    <button @click="eliminarCuenta" class="delete-account-button">Eliminar Cuenta</button>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";
import store from "@/store";
import router from "@/router";

export default {
  name: "Perfil",
  computed: {
    ...mapGetters(["getToken"]) // Usa el getter getToken del Vuex Store
  },
  data() {
    return {
      usuario: {},
      password: "",
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
    actualizarPassword() {
      const config = {
        headers: {
          Authorization: `Bearer ${this.getToken}`
        }
      };

      axios.put("http://localhost:8080/usuario/update/password", { password: this.password }, config)
          .then(response => {
            console.log("Contraseña actualizada:", response.data);
          })
          .catch(error => {
            console.error("Error al actualizar la contraseña:", error);
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
    },
    eliminarCuenta(){
      axios.delete("http://localhost:8080/usuario/delete")
          .then(response => {
            store.dispatch('updateToken', '');
            localStorage.removeItem('token');
            router.push('/');

            console.log("Cuenta eliminada con éxito");
          })
          .catch(error => {
            console.error("Error al eliminar la cuenta:", error);
          });
    }
  }
};
</script>

<style scoped>
.profile-container {
  margin: 20px;
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.section-heading {
  color: #4267B2;
  margin-bottom: 10px;
}

.form {
  display: flex;
  flex-direction: column;
}

.form label {
  font-weight: bold;
}

.input-field {
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 95%;
}

.action-button {
  background-color: #4267B2;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  width: 10%;
}

.action-button:hover {
  background-color: #365899;
}

.user-list {
  list-style-type: none;
  padding: 0;
}

.user-item {
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #e9ebee;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.delete-account-button {
  background-color: #d9534f;
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  width: 10%;
}
</style>