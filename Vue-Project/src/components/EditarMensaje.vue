<template>
  <div>
    <h2>Editar Mensaje</h2>
    <form @submit.prevent="editarMensaje">
      <label>Mensaje:</label>
      <textarea v-model="nuevoMensaje">{{ mensajeActual.mensaje }}</textarea>

      <label>Tags:</label>
      <input v-model="nuevosTags" type="text" />

      <button type="submit">Guardar Cambios</button>
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
          })
          .catch(error => {
            console.error("Error al editar el mensaje:", error);
          });
    }
  }
};
</script>
