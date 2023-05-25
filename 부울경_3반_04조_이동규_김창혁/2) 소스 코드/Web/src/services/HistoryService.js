import Api from "@/services/Api";

export default {
  getAll(params) {
    console.log(params);
    return Api().get("/api/v1/private/trips/attractions/histories", { params: params });
  },
  // createHistory(data) {
  //   return Api().post("histories", data);
  // },
  getComments() {
    return Api().get("/api/v1/private/trips/comments");
  },
  getReplies() {
    return Api().get("/api/v1/private/trips/replies");
  },
  deleteById(historyId) {
    return Api().delete(`/api/v1/private/trips/histories/${historyId}`);
  },
  deleteAll(type) {
    return Api().delete(`/api/v1/private/trips/histories`, { params: { type } });
  },
  deleteReply(replyId) {
    return Api().delete(`/api/v1/private/trips/replies/${replyId}`);
  },
  deleteComment(commentId) {
    return Api().delete(`/api/v1/private/trips/comments/${commentId}`);
  },
};
