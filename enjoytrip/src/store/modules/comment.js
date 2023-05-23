import Vue from "vue";
import Vuex from "vuex";
import CommentService from "@/services/CommentService";

Vue.use(Vuex);

export default {
  state: {
    comments: [],
    commentUpdateCheck: [],
  },
  getters: {
    getComments: (state) => {
      return state.comments;
    },
    getCommentUpdateCheck: (state) => {
      return state.commentUpdateCheck;
    },
  },
  mutations: {
    setComments(state, comments) {
      state.comments = comments;
    },
    setCommentUpdateCheck(state, comments) {
      console.log("comments");
      console.log(comments);
      const length = comments.data.length;
      console.log("length");
      console.log(length);
      state.commentUpdateCheck = new Array(length).fill(false);
    },
    addComment(state, comment) {
      // console.log('hello', comment)
      state.comments.data.unshift(comment);
      state.commentUpdateCheck.unshift(false);
      // console.log(state.comments.data)
    },
  },
  actions: {
    setComments({ commit }, videoId) {
      return new Promise((resolve, reject) => {
        CommentService.getCommentByVideoId(videoId)
          .then((comments) => {
            commit("setComments", comments.data);
            commit("setCommentUpdateCheck", comments.data);

            resolve(comments);
          })
          .catch((err) => reject(err));
      });
    },
    addComment({ commit }, comment) {
      commit("addComment", comment);
    },
  },
};
