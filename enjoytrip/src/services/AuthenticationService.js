import Api from "@/services/Api";

export default {
  signIn(credentials) {
    return Api().post("/api/v1/public/user/login", credentials);
  },
  signUp(data) {
    return Api().post("/api/v1/public/user/register", data);
  },
  updateUserDetails(data) {
    return Api().put("auth/updatedetails", data);
  },
  uploadUserAvatar(data) {
    return Api().put("auth/avatar", data);
  },
  updatePassword(data) {
    return Api().put("auth/updatepassword", data);
  },
  me(token) {
    //필요없을듯
    console.log(token);
    return Api().post("/api/v1/public/user/me", {
      headers: { Authorization: `Bearer ${token}` },
    });
  },
};
