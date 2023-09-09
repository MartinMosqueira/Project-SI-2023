<template>
  <div>
    <form @submit.prevent="register">
      <label>Username</label>
      <input v-model="username" type="text" />

      <label>Email</label>
      <input v-model="email" type="email" />

      <label>Fecha de Nacimiento</label>
      <input v-model="fechaNacimiento" type="date" />

      <label>Password</label>
      <input v-model="password" type="password" />

      <button type="submit">Registrarse</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import store from "@/store";

export default {
  name: "Register",
  data() {
    return {
      username: "",
      email: "",
      fechaNacimiento: "",
      password: ""
    };
  },
  methods: {
    async register() {
      try {
        const response = await axios.post("http://localhost:8080/auth/register", {
          username: this.username,
          email: this.email,
          fechaNacimiento: this.fechaNacimiento,
          password: this.password
        });

        const token = response.data.token;

        // Almacenar el token en Vuex
        await store.dispatch("updateToken", token);

        // Redirecciona a la página de perfil después del inicio de sesión exitoso
        this.$router.push("/home");

      } catch (error) {
        console.error("Error al iniciar sesión:", error);
      }
    }
  },
}
</script>
