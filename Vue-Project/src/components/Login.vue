<template>
  <div class="main-container">
    <div class="left-column">
      <h1 class="title">Microblogging</h1>
      <p class="description">Proyecto de ingeniería de software</p>
    </div>
    <div class="right-column">
      <div class="login-container">
        <h2 class="login-header">Inicio de sesión</h2>
        <input v-model="username" class="login-input" placeholder="Nombre de Usuario" />
        <input v-model="password" class="login-input" type="password" placeholder="Contraseña" />
        <button @click="login" class="login-button">Iniciar sesión</button>
        <p class="login-register-text">¿No tienes cuenta? <a @click="register" class="login-register-link">Regístrate</a></p>
      </div>
    </div>
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
      password: "",
      isPointer: false
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

      } catch (error) {
        console.error("Error al iniciar sesión:", error);
      }
    },
    register() {
      this.$router.push("/register");
    }
  }
};
</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: space-between;
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.left-column {
  flex: 1;
  padding: 20px;
  text-align: left;
}

.right-column {
  flex: 1;
  padding: 20px;
  text-align: left;
}

.login-container {
  max-width: 300px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.login-header {
  font-size: 24px;
  margin-bottom: 10px;
}

.login-input {
  width: calc(100% - 20px);
  padding: 10px;
  margin-bottom: 10px;
  box-sizing: border-box;
}

.login-button {
  width: 100%;
  padding: 10px;
  background-color: #1877f2;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.login-button:hover {
  background-color: #1565c0;
}

.login-register-text {
  font-size: 14px;
}

.login-register-link {
  color: #1877f2;
  cursor: pointer;
}

.login-register-link:hover {
  text-decoration: underline;
}

.title {
  font-size: 36px;
  margin-bottom: 10px;
}

.description {
  font-size: 16px;
}
</style>