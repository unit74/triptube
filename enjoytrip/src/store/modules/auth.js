import AuthenticationService from "@/services/AuthenticationService";

const state = {
  url: process.env.VUE_APP_URL,
  token: localStorage.getItem("token") || null,
  user: JSON.parse(localStorage.getItem("user")) || null,
  // isUserLoggedIn: localStorage.getItem('token') || false
  isUserLoggedIn: localStorage.getItem("token") || false,
  // logoUrl: "http://192.168.203.120:8080/api/v1/public/users/profiles/3.png",
  logoUrl: "http://192.168.203.120:8080/api/v1/public/images/logo",
  noImgUrl: "http://192.168.203.120:8080/api/v1/public/images/no-images",
  defaultProfileUrl: "http://192.168.203.120:8080/api/v1/public/users/profiles/",

  // logoUrl: "http://192.168.203.119:8080/api/v1/public/images/logo",
  // noImgUrl: "http://192.168.203.119:8080/api/v1/public/images/no-images",
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
  logoUrl: (state) => {
    return state.logoUrl;
  },
  noImgUrl: (state) => {
    return state.noImgUrl;
  },
  defaultProfileUrl: (state) => {
    return state.defaultProfileUrl;
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
          localStorage.setItem("user", JSON.stringify({ name: data.data.name, profilePhotoUrl: data.data.profilePhotoUrl, email: data.data.email }));
          commit("SET_USER_DATA", data.data);
          commit("SET_TOKEN", data.data.token);
          resolve(data);
        })
        .catch((err) => reject(err));
    });
  },
  signIn({ commit }, credentials) {
    return new Promise((resolve, reject) => {
      AuthenticationService.signIn(credentials)
        .then(({ data }) => {
          localStorage.setItem("token", data.data.token);
          localStorage.setItem("user", JSON.stringify({ name: data.data.name, profilePhotoUrl: data.data.profilePhotoUrl, email: data.data.email }));
          commit("SET_USER_DATA", data.data);
          commit("SET_TOKEN", data.data.token);

          resolve(data);
        })
        .catch((err) => reject(err));
    });
  },
  updateUserInfo({ commit }, credentials) {
    return new Promise((resolve) => {
      localStorage.setItem("user", JSON.stringify(credentials));
      commit("SET_USER_DATA", credentials);
      resolve(credentials);
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
    return new Promise((reject) => {
      AuthenticationService.signOut()
        .catch((err) => reject(err))
        .finally(() => {
          localStorage.removeItem("token");
          localStorage.removeItem("user");

          commit("CLEAR_AUTH");
        });
    });
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
