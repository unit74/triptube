import Api from "@/services/Api";

export default {
  search(params) {
    console.log(params);

    return Api().get(`/api/v1/public/trips/attractions/searches`, { params: params });
  },
};
