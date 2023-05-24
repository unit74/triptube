import Api from "@/services/Api";

export default {
  getSubscribedChannels(subscriberId) {
    return Api().get("subscriptions/channels", {
      params: {
        subscriberId,
        select: "channelId",
      },
    });
  },
  getSubscribers(limit) {
    return Api().get("subscriptions/subscribers", {
      params: {
        limit,
      },
    });
  },
  checkSubscription(contentId) {
    return Api().get(`/api/v1/private/trips/libraries/${contentId}`);
  },
  createSubscription(contentId) {
    return Api().post("/api/v1/private/trips/libraries", { contentId });
  },
  deleteLibrary(libraryId) {
    return Api().delete(`/api/v1/private/trips/libraries/${libraryId}`);
  },
  getSaveAttractions(page) {
    return Api().get("/api/v1/private/trips/attractions/libraries", {
      params: {
        page,
      },
    });
  },
};
