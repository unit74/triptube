import Api from "@/services/Api";

export default {
  createReply(contentId, text) {
    return Api().post(`/api/v1/private/trips/replies/${contentId}`, { text });
  },
  deleteById(id) {
    return Api().delete(`replies/${id}`);
  },
};
