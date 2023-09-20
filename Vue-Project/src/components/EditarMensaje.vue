<template>
  <div class="edit-message-container">
    <h2 class="edit-message-heading">Editar Mensaje</h2>
    <form @submit.prevent="editarMensaje" class="edit-message-form">
      <label for="edit-message">Mensaje:</label>
      <textarea v-model="nuevoMensaje" id="edit-message" class="edit-message-textarea">{{ mensajeActual.mensaje }}</textarea>

      <label for="edit-tags">Tags:</label>
      <input v-model="nuevosTags" type="text" id="edit-tags" class="edit-tags-input"/>

      <button type="submit" class="edit-message-button">Guardar Cambios</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      mensajeActual: {},
      nuevoMensaje: "",
      nuevosTags: ""
    };
  },
  mounted() {
    this.cargarMensaje();
  },
  methods: {
    cargarMensaje() {
      const idMensaje = this.$route.params.id;
      const url = `http://localhost:8080/mensajeMuro/get/mensaje/${idMensaje}`;

      axios.get(url)
          .then(response => {
            this.mensajeActual = response.data;
            this.nuevoMensaje = response.data.mensaje;
            this.nuevosTags = response.data.tags;
          })
          .catch(error => {
            console.error("Error al cargar el mensaje:", error);
          });
    },
    editarMensaje() {
      const idMensaje = this.$route.params.id;
      const url = `http://localhost:8080/mensajeMuro/update/${idMensaje}`;

      const data = {
        mensaje: this.nuevoMensaje,
        tags: this.nuevosTags
      };

      axios.put(url, data)
          .then(response => {
            console.log("Mensaje editado con éxito");
            this.$router.push("/home");
          })
          .catch(error => {
            console.error("Error al editar el mensaje:", error);
          });
    }
  }
};
</script>

<style scoped>
.edit-message-container {
  margin: 20px;
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.edit-message-heading {
  color: #4267B2;
  margin-bottom: 10px;
}

.edit-message-form {
  display: flex;
  flex-direction: column;
}

.edit-message-form label {
  font-weight: bold;
}

.edit-message-textarea {
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  resize: vertical;
}

.edit-tags-input {
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.edit-message-button {
  background-color: #4267B2;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
}

.edit-message-button:hover {
  background-color: #365899;
}
</style>
