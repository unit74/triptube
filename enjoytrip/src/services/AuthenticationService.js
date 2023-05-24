import Api from "@/services/Api";

export default {
  signIn(credentials) {
    return Api().post("/api/v1/public/users/login", credentials);
  },
  signUp(data) {
    return Api().post("/api/v1/public/users/register", data);
  },
  updateUserDetails(data) {
    return Api().put("/api/v1/private/users", data);
  },
  uploadUserAvatar(data) {
    console.log("uploadUserAvatar");
    console.log(data);
    return Api().put("auth/avatar", data);
  },
  updatePassword(data) {
    return Api().put("/api/v1/private/users", data);
  },
  me(token) {
    //필요없을듯
    console.log(token);
    return Api().post("/api/v1/public/user/me", {
      headers: { Authorization: `Bearer ${token}` },
    });
  },
};
