import Api from "@/services/Api";

export default {
  checkFeeling(data) {
    return Api().post("feelings/check", data);
  },
  createFeeling(data) {
    return Api().post("/api/v1/private/trip/reaction", data);
  },
  getLikedVideos(page) {
    return Api().get("feelings/videos", {
      params: {
        page,
        limit: 12,
      },
    });
  },
};
