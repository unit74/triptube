import Api from "@/services/Api";

export default {
  getAll(params) {
    console.log(params);
    return Api().get("/api/v1/private/trips/histories", { params: params });
  },
  // createHistory(data) {
  //   return Api().post("histories", data);
  // },
  deleteById(historyId) {
    return Api().delete(`/api/v1/private/trips/histories/${historyId}`);
  },
  deleteAll(type) {
    return Api().delete(`histories/${type}/all`);
  },
};
