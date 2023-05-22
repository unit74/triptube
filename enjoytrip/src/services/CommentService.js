import Api from "@/services/Api";

export default {
  getCommentByVideoId(filters) {
    console.log(filters);
    // return Api().get(`comments/${filters}/videos`);
  },
  createComment(data) {
    return Api().post("comments", data);
  },
  deleteById(id) {
    return Api().delete(`comments/${id}`);
  },
};
