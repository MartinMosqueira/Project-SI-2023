<template>
  <div class="register-container">
    <form @submit.prevent="register" class="register-form">
      <label class="register-label">Username</label>
      <input v-model="username" type="text" class="register-input"/>

      <label class="register-label">Email</label>
      <input v-model="email" type="email" class="register-input"/>

      <label class="register-label">Fecha de Nacimiento</label>
      <input v-model="fechaNacimiento" type="date" class="register-input"/>

      <label class="register-label">Password</label>
      <input v-model="password" type="password" class="register-input"/>

      <button type="submit" class="register-button">Registrarse</button>
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

        await store.dispatch("updateToken", token);

        this.$router.push("/home");

      } catch (error) {
        console.error("Error al iniciar sesión:", error);
      }
    }
  },
}
</script>

<style>
.register-container {
  max-width: 300px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.register-label {
  font-size: 14px;
  margin-bottom: 5px;
}

.register-input {
  width: calc(100% - 20px);
  padding: 10px;
  margin-bottom: 10px;
  box-sizing: border-box;
}

.register-button {
  width: 100%;
  padding: 10px;
  background-color: #1877f2;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.register-button:hover {
  background-color: #1565c0;
}
</style>
