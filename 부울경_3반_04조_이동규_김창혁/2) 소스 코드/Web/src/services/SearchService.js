import Api from "@/services/Api";

export default {
  search(params) {
    console.log(params);

    return Api().get(`/api/v1/public/trips/attractions/searches`, { params: params });
  },
  getSidos() {
    return Api().get("/api/v1/public/trips/categories/sidos");
  },

  getGuguns(sido) {
    console.log(sido);

    return Api().get(`/api/v1/public/trips/categories/guguns`, { params: { sido: sido } });
  },

  getContents() {
    return Api().get("/api/v1/public/trips/categories/contents");
  },
};
