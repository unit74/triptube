import Api from "@/services/Api";

export default {
  checkFeeling(contentId) {
    return Api().get(`/api/v1/private/trips/reactions/${contentId}`);
  },
  createFeeling(params) {
    console.log(params);
    return Api().post("/api/v1/private/trips/reactions", {
      contentId: params.contentId,
      type: params.type,
    });
  },
  deleteFeeling(contentId) {
    return Api().delete(`/api/v1/private/trips/reactions/${contentId}`);
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
