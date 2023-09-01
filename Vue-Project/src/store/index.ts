import { createStore } from "vuex";

const store = createStore({
    state: {
        token: "" // Inicializar el token como una cadena vacía
    },
    mutations: {
        setToken(state, token) {
            state.token = token;
            localStorage.setItem('token', token); // Almacenar el token en LocalStorage
        }
    },
    actions: {
        updateToken({ commit }, token) {
            commit("setToken", token);
        }
    },
    getters: {
        getToken: state => state.token
    }
});

export default store;
