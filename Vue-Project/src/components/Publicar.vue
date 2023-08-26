<template>
    <div>
      <h2>Publicar Mensaje</h2>
      <input v-model="mensaje.mensaje" placeholder="Mensaje" />
      <input v-model="mensaje.tags" placeholder="Tags" />
      <button @click="publicarMensaje">Publicar</button>
    </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      mensaje: {
        mensaje: "",
        tags: ""
      }
    };
  },
  methods: {
    publicarMensaje() {
      axios.post("http://localhost:8080/mensajeMuro/create", this.mensaje)
          .then(response => {
            console.log("Mensaje publicado con éxito:", response.data);
            // Restablecer los valores de las propiedades del mensaje
            this.mensaje.mensaje = "";
            this.mensaje.tags = "";
          })
          .catch(error => {
            console.error("Error al publicar el mensaje:", error);
          });
    }
  }
};
</script>
