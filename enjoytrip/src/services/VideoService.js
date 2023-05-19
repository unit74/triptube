import Api from "@/services/Api";

export default {
  getAll(data, params) {
    console.log(data, params);
    // console.log("Api.get(`/videos`)");
    // console.log(Api.get(`/videos`));
    return Api().get(`/api/v1/trip`);
  },
  getHotplace() {
    console.log();
    // console.log("Api.get(`/videos`)");
    // console.log(Api.get(`/videos`));
    return Api().get(`/api/v1/trip/hot`);
  },
  async getById(id) {
    return Api().get(`/api/v1/trip/${id}`);
  },
  uploadVideo(data, optional) {
    console.log("uploadVideo");
    return Api().post("videos", data, optional);
  },
  updateVideo(id, data) {
    console.log("updateVideo");

    return Api().put(`videos/${id}`, data);
  },
  updateViews(id) {
    console.log("updateViews");

    return Api().put(`videos/${id}/views`);
  },
  uploadThumbnail(id, data) {
    console.log("uploadThumbnail");

    return Api().put(`videos/${id}/thumbnails`, data);
  },
  deleteById(id) {
    console.log("deleteById");

    return Api().delete(`videos/${id}`);
  },
};
