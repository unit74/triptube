import Api from "@/services/Api";

export default {
  search(page, data) {
    console.log(page, data);
    data.page = page;
    const params = {
      searchText: data.searchText,
      page: page,
    };
    return Api().get(`/api/v1/public/trips/attractions/searches`, { params });
  },
};
