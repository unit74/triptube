// import AuthenticationService from "@/services/AuthenticationService";

// const state = {
//   url: process.env.VUE_APP_URL,
//   token: localStorage.getItem("token") || null,
//   user: JSON.parse(localStorage.getItem("user")) || null,
//   // isUserLoggedIn: localStorage.getItem('token') || false
//   isUserLoggedIn: localStorage.getItem("token") || false,
// };

// const getters = {
//   getUrl: (state) => {
//     return state.url;
//   },
//   isAuthenticated: (state) => {
//     return state.isUserLoggedIn;
//   },
//   getToken: (state) => {
//     return state.token;
//   },
//   currentUser: (state) => {
//     return state.user;
//   },
// };

// const mutations = {
//   SET_TOKEN(state, token) {
//     state.token = token;
//     if (token) {
//       state.isUserLoggedIn = true;
//     } else {
//       state.isUserLoggedIn = false;
//     }
//   },
//   SET_USER_DATA(state, data) {
//     state.user = data;
//   },
//   CLEAR_AUTH(state) {
//     state.token = null;
//     state.user = null;
//     state.isUserLoggedIn = false;
//   },
// };

// const actions = {
//   signUp({ commit }, credentials) {
//     return new Promise((resolve, reject) => {
//       AuthenticationService.signUp(credentials)
//         .then(({ data }) => {
//           commit("SET_TOKEN", data.data.token);
//           localStorage.setItem("token", data.data.token);
//           resolve(data);
//         })
//         .catch((err) => reject(err));
//     });
//   },
//   signIn({ commit }, credentials) {
//     console.log("로그인");
//     return new Promise((resolve, reject) => {
//       AuthenticationService.signIn(credentials)
//         .then(({ data }) => {
//           localStorage.setItem("token", data.data.token);
//           commit("SET_TOKEN", data.data.token);

//           resolve(data);
//         })
//         .catch((err) => reject(err));
//     });
//   },
//   getCurrentUser({ commit }, token) {
//     return new Promise((resolve, reject) => {
//       AuthenticationService.me(token)
//         .then(({ data }) => {
//           localStorage.setItem("user", JSON.stringify(data.data));
//           commit("SET_USER_DATA", data.data);
//           resolve(data);
//         })
//         .catch((err) => reject(err));
//     });
//   },
//   signOut({ commit }) {
//     localStorage.removeItem("token");
//     localStorage.removeItem("user");
//     commit("CLEAR_AUTH");
//   },
// };

// export default {
//   state,
//   getters,
//   mutations,
//   actions,
// };
import AuthenticationService from "@/services/AuthenticationService";

const state = {
  url: process.env.VUE_APP_URL,
  token: localStorage.getItem("token") || null,
  user: JSON.parse(localStorage.getItem("user")) || null,
  // isUserLoggedIn: localStorage.getItem('token') || false
  isUserLoggedIn: localStorage.getItem("token") || false,
};

const getters = {
  getUrl: (state) => {
    return state.url;
  },
  isAuthenticated: (state) => {
    return state.isUserLoggedIn;
  },
  getToken: (state) => {
    return state.token;
  },
  currentUser: (state) => {
    return state.user;
  },
};

const mutations = {
  SET_TOKEN(state, token) {
    state.token = token;
    if (token) {
      state.isUserLoggedIn = true;
    } else {
      state.isUserLoggedIn = false;
    }
  },
  SET_USER_DATA(state, data) {
    state.user = data;
  },
  CLEAR_AUTH(state) {
    state.token = null;
    state.user = null;
    state.isUserLoggedIn = false;
  },
};

const actions = {
  signUp({ commit }, credentials) {
    return new Promise((resolve, reject) => {
      AuthenticationService.signUp(credentials)
        .then(({ data }) => {
          localStorage.setItem("token", data.data.token);
          localStorage.setItem("user", JSON.stringify({ name: data.data.name, profilePhotoUrl: data.data.profilePhotoUrl }));
          commit("SET_USER_DATA", data.data);
          commit("SET_TOKEN", data.data.token);
          resolve(data);
        })
        .catch((err) => reject(err));
    });
  },
  signIn({ commit }, credentials) {
    console.log("로그인");
    return new Promise((resolve, reject) => {
      AuthenticationService.signIn(credentials)
        .then(({ data }) => {
          localStorage.setItem("token", data.data.token);
          localStorage.setItem("user", JSON.stringify({ name: data.data.name, profilePhotoUrl: data.data.profilePhotoUrl }));
          commit("SET_USER_DATA", data.data);
          commit("SET_TOKEN", data.data.token);

          resolve(data);
        })
        .catch((err) => reject(err));
    });
  },
  getCurrentUser({ commit }, token) {
    //필요 없을듯?
    console.log(token);
    return new Promise((resolve, reject) => {
      AuthenticationService.me(token)
        .then(({ data }) => {
          // 기존엔 여기서 user 정보를 저장했지만 뭘 받아야 할지 몰라 로그인 부분으로 올렸다
          commit("SET_USER_DATA", data.data);
          resolve(data);
        })
        .catch((err) => reject(err));
    });
  },
  signOut({ commit }) {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    commit("CLEAR_AUTH");
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
