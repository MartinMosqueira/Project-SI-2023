<template>
  <div class="message-container">
    <h2 style="color: #4267B2;">Publicar Mensaje</h2>
    <div class="message-inputs">
      <textarea v-model="mensaje.mensaje" placeholder="¿Qué estás pensando?"></textarea>
      <input v-model="mensaje.tags" placeholder="Añade etiquetas, separadas por comas" />
      <button @click="publicarMensaje" class="action-button">Publicar</button>
    </div>

    <h2 style="color: #4267B2;">Mis Mensajes</h2>
    <ul>
      <li v-for="mensaje in mensajes" :key="mensaje.id" class="message-item">
        <div class="message-header">
          <p class="message-username">{{ mensaje.username }}</p>
          <p class="message-date">{{ mensaje.fecha }}</p>
        </div>
        <p class="message-content">{{ mensaje.mensaje }}</p>
        <p class="message-tags">Tags: {{ mensaje.tags }}</p>
        <div class="message-actions" v-if="mensaje.idUsuario === usuarioActual">
          <router-link :to="`/editar-mensaje/${mensaje.id}`" class="action-link">Editar</router-link>
          <a @click="eliminarMensaje(mensaje.id)" class="action-link">Eliminar</a>
        </div>
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
    },
    eliminarMensaje(idMensaje){
      axios.delete(`http://localhost:8080/mensajeMuro/delete/${idMensaje}`)
          .then(response => {
            this.mensajes = this.mensajes.filter(mensaje => mensaje.id !== idMensaje);
          })
          .catch(error => {
            console.error("Error al eliminar el mensaje:", error);
          });
    }
  }
};
</script>


<style scoped>
.message-container {
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.message-inputs {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

.message-inputs textarea {
  width: 95%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  resize: vertical;
}

.message-inputs input {
  width: 95%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.message-item {
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.action-button {
  width: 10%;
  background-color: #4267B2;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
}

.action-link {
  color: #4267B2;
  text-decoration: none;
  margin-left: 10px;
  cursor: pointer;
}

.action-link:hover {
  text-decoration: underline;
}

.message-item {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #e9ebee;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
}

.message-username {
  font-weight: bold;
  margin: 0;
}

.message-date {
  margin: 0;
  color: #606770;
}

.message-content {
  margin: 0;
  font-size: 16px;
}

.message-tags {
  margin: 0;
  font-size: 14px;
  color: #606770;
}

.message-actions {
  display: flex;
}

.message-actions .action-link {
  color: #1877f2;
  text-decoration: none;
  margin-left: 10px;
  cursor: pointer;
}

.message-actions .action-link:hover {
  text-decoration: underline;
}
</style>