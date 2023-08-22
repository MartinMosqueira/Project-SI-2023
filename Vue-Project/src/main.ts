import './assets/main.css'

import { createApp } from 'vue';
import App from './App.vue';
import store from './store'; // Importa el store directamente, no necesitas useStore
import axios from 'axios';
import router from './router';

const app = createApp(App);

// Agrega el interceptor para las solicitudes de Axios
axios.interceptors.request.use(
    config => {
        const token = store.getters.getToken; // Obtiene el token del store
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

app.use(store); // Usa el store de Vuex en la aplicación
app.use(router);
app.mount('#app');

