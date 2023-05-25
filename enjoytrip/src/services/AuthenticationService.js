import Api from "@/services/Api";

export default {
  signIn(credentials) {
    return Api().post("/api/v1/public/users/login", credentials);
  },
  signUp(data) {
    return Api().post("/api/v1/public/users/register", data);
  },
  signOut() {
    return Api().post("/api/v1/private/users/logout");
  },
  updateUserDetails(data) {
    return Api().put("/api/v1/private/users/name", data);
  },
  uploadUserProfile(data) {
    console.log("uploadUserAvatar");
    console.log(data);
    return Api().post("/api/v1/private/users/profiles", data, { headers: { "Content-Type": "multipart/form-data" } });
  },
  updatePassword(data) {
    return Api().put("/api/v1/private/users/password", data);
  },
  me(token) {
    //필요없을듯
    console.log(token);
    return Api().post("/api/v1/public/user/me", {
      headers: { Authorization: `Bearer ${token}` },
    });
  },
};
