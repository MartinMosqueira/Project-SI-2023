<template>
  <div>
    <input v-model="username" placeholder="Username" />
    <input v-model="password" type="password" placeholder="Password" />
    <button @click="login">Login</button>
  </div>
</template>

<script>
import store from "../store";
import axios from "axios";

export default {
  name: "Login",
  data() {
    return {
      username: "",
      password: ""
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post("http://localhost:8080/auth/login", {
          username: this.username,
          password: this.password
        });

        const token = response.data.token;

        // Almacenar el token en Vuex
        await store.dispatch("updateToken", token);

        // Redirecciona a la página de perfil después del inicio de sesión exitoso
        this.$router.push("/home");

        // Luego redireccionar a la página de perfil u otra
      } catch (error) {
        console.error("Error al iniciar sesión:", error);
      }
    }
  }
};
</script>
