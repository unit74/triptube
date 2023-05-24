import Vue from "vue";
import Vuex from "vuex";
import CommentService from "@/services/CommentService";

Vue.use(Vuex);

export default {
  state: {
    comments: [],
  },
  getters: {
    getComments: (state) => {
      return state.comments;
    },
  },
  mutations: {
    setComments(state, comments) {
      for (var i = 0; i < comments.data.length; i++) {
        comments.data[i].commentUpdateCheck = false;
        console.log(comments.data[i]);

        for (var j = 0; j < comments.data[i].replies.length; j++) {
          comments.data[i].replies[j].replyUpdateCheck = false;
        }
      }
      console.log("setComments comments");
      console.log(comments);
      state.comments = comments;
    },

    addComment(state, comment) {
      // console.log('hello', comment)

      comment.commentUpdateCheck = false;
      for (var j = 0; comment.replies.length; j++) {
        comment.replies[j].replyUpdateCheck = false;
      }
      state.comments.data.unshift(comment);
      // console.log(state.comments.data)
    },
  },
  actions: {
    setComments({ commit }, videoId) {
      return new Promise((resolve, reject) => {
        CommentService.getCommentByVideoId(videoId)
          .then((comments) => {
            commit("setComments", comments.data);

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
