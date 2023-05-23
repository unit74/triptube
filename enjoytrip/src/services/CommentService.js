import Api from "@/services/Api";

export default {
  getCommentByVideoId(filters) {
    return Api().get(`/api/v1/public/trips/comments/${filters}`);
  },
  createComment(data) {
    return Api().post(`/api/v1/private/trips/comments/${data.contentId}`, { text: data.text });
  },
  updateComment(commentId, text) {
    return Api().put(`/api/v1/private/trips/comments/${commentId}`, { text });
  },
  deleteById(commentId) {
    return Api().delete(`/api/v1/private/trips/comments/${commentId}`);
  },
};
