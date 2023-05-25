import Api from "@/services/Api";

export default {
  createReply(commentId, text) {
    console.log("commentId");
    console.log(commentId);
    return Api().post(`/api/v1/private/trips/replies/${commentId}`, { text });
  },
  updateReply(replyId, text) {
    return Api().put(`/api/v1/private/trips/replies/${replyId}`, { text });
  },
  deleteById(replyId) {
    return Api().delete(`/api/v1/private/trips/replies/${replyId}`);
  },
};
