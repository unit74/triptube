<template>
  <div>
    <!-- <div v-if="!comments.length">
      <p>No comment yet, leave a comment</p>
    </div> -->
    <div v-for="(comment, i) in loading ? 4 : comments" :key="comment.commentId">
      <v-skeleton-loader type="list-item-avatar-two-line" :loading="loading" :id="'a' + `${comment.commentId}`" v-show="!comment.commentUpdateCheck">
        <v-card class="transparent" flat>
          <v-list-item three-line class="pl-0 mt-2">
            <v-list-item-avatar v-if="typeof comment.user !== 'undefined'" size="50">
              <v-img
                v-if="comment.user.profilePhotoUrl !== 'no-photo.jpg'"
                class="elevation-6"
                :src="`${defaultProfileUrl + comment.user.profilePhotoUrl}`"
              ></v-img>
              <v-avatar v-else color="red">
                <span class="white--text headline "> {{ comment.user.name.split('')[0].toUpperCase() }}</span>
              </v-avatar>
            </v-list-item-avatar>
            <v-list-item-content>
              <div class="d-flex mb-0">
                <v-list-item-title v-if="comment.user" class="font-weight-medium caption mb-0 d-flex"
                  >{{ comment.user.name }}
                  <span class="pl-2 font-weight-light grey--text"> {{ dateFormatter(comment.createdAt) }}</span>
                </v-list-item-title>
                <v-menu bottom left>
                  <template v-slot:activator="{ on }">
                    <v-btn icon v-on="on">
                      <v-icon>mdi-dots-vertical</v-icon>
                    </v-btn>
                  </template>

                  <v-list v-if="isAuthenticated">
                    <v-list-item @click="updateTextField(comment.user.email, comment.text, i)">
                      <v-list-item-title><v-icon>mdi-update</v-icon> Update</v-list-item-title>
                    </v-list-item>
                    <v-list-item @click="deleteComment(comment.commentId, comment.user)">
                      <v-list-item-title><v-icon>mdi-trash-can-outline</v-icon> Delete</v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>
              </div>
              <v-list-item-subtitle class="mt-n2 black--text text--darken-4 caption">{{ comment.text }}</v-list-item-subtitle>

              <div>
                <v-btn text small :ripple="false" @click.stop="showReply(`${'reply' + comment.commentId}`)">Reply</v-btn>
              </div>
              <div class="d-none" :ref="`${'reply' + comment.commentId}`">
                <v-list-item three-line class="pl-0">
                  <v-list-item-avatar v-if="typeof comment.user !== 'undefined'" class="mt-2" size="40">
                    <v-avatar v-if="!isAuthenticated" color="primary">
                      <v-icon class="white--text">mdi-account</v-icon>
                    </v-avatar>
                    <template v-else>
                      <v-img
                        v-if="currentUser.profilePhotoUrl !== 'no-photo.jpg'"
                        class="elevation-6"
                        :src="`${defaultProfileUrl + currentUser.profilePhotoUrl}`"
                      ></v-img>
                      <v-avatar v-else color="red">
                        <span class="white--text headline "> {{ currentUser.name.split('')[0].toUpperCase() }}</span>
                      </v-avatar>
                    </template>
                  </v-list-item-avatar>
                  <v-list-item-content class="align-self-auto mt-0 pt-0">
                    <v-form :ref="`form${comment.commentId}`">
                      <v-text-field
                        :ref="`${'input' + comment.commentId}`"
                        class="pt-0 mt-0 body-2"
                        placeholder="Add a public comment..."
                        @click="clickTextField"
                        :value="repliesInput[`input${comment.commentId}`]"
                      >
                      </v-text-field>
                    </v-form>
                    <div :ref="comment.commentId + 'btns'" class="d-inline-block text-right" v-if="isAuthenticated">
                      <v-btn text @click="hideReply(comment.commentId)" small>Cancel</v-btn>
                      <v-btn
                        class="blue darken-3 white--text"
                        depressed
                        tile
                        small
                        :loading="btnLoading && i == index"
                        @click="
                          index = i;
                          addReply($event, comment.commentId);
                        "
                        >Reply</v-btn
                      >
                    </div>
                  </v-list-item-content>
                </v-list-item>
                <!-- </v-list-item-action> -->
              </div>
              <v-expansion-panels>
                <v-expansion-panel class="transparent elevation-0">
                  <v-expansion-panel-header v-if="comment.replies && comment.replies.length > 0" class="blue--text text--darken-4 py-0"
                    >{{ comment.replies.length }} replies</v-expansion-panel-header
                  >
                  <v-expansion-panel-content>
                    <v-list-item three-line class="pl-0 mt-2" v-for="(reply, j) in comment.replies" :key="reply.replyId">
                      <v-list-item-avatar v-if="typeof reply !== 'undefined'" size="50">
                        <v-img
                          v-if="reply.user.profilePhotoUrl !== 'no-photo.jpg'"
                          class="elevation-6"
                          :src="`${defaultProfileUrl + reply.user.profilePhotoUrl}`"
                        ></v-img>
                        <v-avatar v-else color="red">
                          <span class="white--text headline "> {{ reply.user.name.split('')[0].toUpperCase() }}</span>
                        </v-avatar>
                      </v-list-item-avatar>
                      <v-list-item-content v-show="!reply.replyUpdateCheck">
                        <div class="d-flex mb-0">
                          <v-list-item-title v-if="reply.user.name" class="font-weight-medium caption mb-0 d-flex"
                            >{{ reply.user.name }}
                            <span class="pl-2 font-weight-light grey--text"> {{ dateFormatter(reply.createdAt) }}</span>
                          </v-list-item-title>
                          <v-menu bottom left v-if="isAuthenticated">
                            <template v-slot:activator="{ on }">
                              <v-btn icon v-on="on">
                                <v-icon>mdi-dots-vertical</v-icon>
                              </v-btn>
                            </template>

                            <v-list>
                              <v-list-item @click="replyUpdateTextField(i, j, reply.user.email, reply.text)">
                                <v-list-item-title><v-icon>mdi-update</v-icon> Update</v-list-item-title>
                              </v-list-item>
                              <v-list-item @click="deleteReply(comment.commentId, reply.replyId)">
                                <v-list-item-title><v-icon>mdi-trash-can-outline</v-icon> Delete</v-list-item-title>
                              </v-list-item>
                            </v-list>
                          </v-menu>
                        </div>
                        <v-list-item-subtitle class="mt-n2 black--text text--darken-4 caption">{{ reply.text }}</v-list-item-subtitle>
                      </v-list-item-content>
                      <v-list-item-content v-show="reply.replyUpdateCheck">
                        <div class="d-flex mb-0">
                          <v-list-item-title v-if="reply.user.name" class="font-weight-medium caption mb-0 d-flex"
                            >{{ reply.user.name }}
                            <span class="pl-2 font-weight-light grey--text"> {{ dateFormatter(reply.createdAt) }}</span>
                          </v-list-item-title>
                          <v-menu bottom left v-if="isAuthenticated">
                            <template v-slot:activator="{ on }">
                              <v-btn icon v-on="on">
                                <v-icon>mdi-dots-vertical</v-icon>
                              </v-btn>
                            </template>
                          </v-menu>
                        </div>
                        <v-text-field v-model="updateText" @click="clickTextField2"> </v-text-field>
                        <div v-if="showCommentBtns" class="d-inline-block text-right">
                          <v-btn text @click="finishReply(i, j)">Cancel</v-btn>
                          <v-btn
                            class="blue darken-3 white--text"
                            depressed
                            tile
                            :loading="loading"
                            :disabled="comment === ''"
                            @click="updateReply(i, j, reply.replyId)"
                            >Comment</v-btn
                          >
                        </div>
                      </v-list-item-content>
                    </v-list-item>
                  </v-expansion-panel-content>
                </v-expansion-panel>
              </v-expansion-panels>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-skeleton-loader>
      <!-------------------------여기가 업데이트------------------------------------------------------>
      <v-skeleton-loader type="list-item-avatar-two-line" :loading="loading" :id="'b' + `${comment.commentId}`" v-show="comment.commentUpdateCheck">
        <v-card class="transparent" flat>
          <v-list-item three-line class="pl-0 mt-2">
            <v-list-item-avatar v-if="typeof comment.user !== 'undefined'" size="50">
              <v-img
                v-if="comment.user.profilePhotoUrl !== 'no-photo.jpg'"
                class="elevation-6"
                :src="`${defaultProfileUrl + comment.user.profilePhotoUrl}`"
              ></v-img>
              <v-avatar v-else color="red">
                <span class="white--text headline "> {{ comment.user.name.split('')[0].toUpperCase() }}</span>
              </v-avatar>
            </v-list-item-avatar>
            <v-list-item-content>
              <div class="d-flex mb-0">
                <v-list-item-title v-if="comment.user" class="font-weight-medium caption mb-0 d-flex"
                  >{{ comment.user.name }}
                  <span class="pl-2 font-weight-light grey--text"> {{ dateFormatter(comment.createdAt) }}</span>
                </v-list-item-title>
                <v-menu bottom left>
                  <template v-slot:activator="{ on }">
                    <v-btn icon v-on="on">
                      <v-icon>mdi-dots-vertical</v-icon>
                    </v-btn>
                  </template>
                </v-menu>
              </div>
              <v-text-field v-model="updateText" @click="clickTextField2"> </v-text-field>
              <div v-if="showCommentBtns" class="d-inline-block text-right">
                <v-btn text @click="finishUpdate(i)">Cancel</v-btn>
                <v-btn
                  class="blue darken-3 white--text"
                  depressed
                  tile
                  :loading="loading"
                  :disabled="comment === ''"
                  @click="updateComment(comment.commentId, i)"
                  >Comment</v-btn
                >
              </div>
            </v-list-item-content>
          </v-list-item>
        </v-card>
      </v-skeleton-loader>
    </div>
    <v-snackbar v-model="snackbar">
      Comment deleted successfully
      <v-btn color="white" text @click="snackbar = false" icon>
        <v-icon>mdi-close-circle</v-icon>
      </v-btn>
    </v-snackbar>
    <v-snackbar v-model="snackbarFail">
      Comment deleted fail
      <v-btn color="white" text @click="snackbarFail = false" icon>
        <v-icon>mdi-close-circle</v-icon>
      </v-btn>
    </v-snackbar>
    <v-snackbar v-model="updateFail">
      Comment {{ updateFailMessage }}
      <v-btn color="white" text @click="updateFail = false" icon>
        <v-icon>mdi-close-circle</v-icon>
      </v-btn>
    </v-snackbar>
  </div>
</template>

<script>
import moment from 'moment';
import { mapGetters } from 'vuex';

import CommentService from '@/services/CommentService';
import ReplyService from '@/services/ReplyService';
export default {
  props: {
    attractionId: {
      type: Number,
      required: true,
    },
  },
  data: function() {
    return {
      repliesInput: {},
      comments: this.$store.getters.getComments.data,
      commentsLength: false,
      index: -1,
      btnLoading: false,
      url: process.env.VUE_APP_URL,
      snackbar: false,
      loading: false,
      snackbarFail: false,
      showCommentBtns: false,
      updateText: '',
      usingUpdate: false,
      updateFail: false,
      updateFailMessage: '',
    };
  },
  computed: {
    ...mapGetters(['isAuthenticated', 'currentUser', 'defaultProfileUrl']),
  },
  methods: {
    async getComments() {
      this.loading = true;
      const comments = await this.$store
        .dispatch('setComments', this.attractionId)
        .catch((err) => console.log(err))
        .finally(() => (this.loading = false));
      // console.log(this.loading)
      if (!comments) return;

      this.comments = this.$store.getters.getComments.data;

      // console.log(this.comments.length)
      // this.loading = false
      // console.log(this.$store.getters.getComments.data)
    },

    updateTextField(email, commentText, i) {
      console.log(email);
      console.log(this.currentUser.email);

      if (this.currentUser.email !== email) {
        this.updateFailMessage = '작성자가 동일 하지 않습니다.';
        this.updateFail = true;
        return;
      } else if (this.usingUpdate) {
        this.updateFailMessage = '이미 업데이트 사용 중 입니다.';
        this.updateFail = true;
        return;
      }
      console.log('updateTextField');
      this.usingUpdate = true;

      console.log(i);
      console.log(this.comments[i]);
      this.comments[i].commentUpdateCheck = true;
      this.updateText = commentText;
    },
    replyUpdateTextField(i, j, email, replyText) {
      console.log(email);
      console.log(this.currentUser.email);

      if (this.currentUser.email !== email) {
        this.updateFailMessage = '작성자가 동일 하지 않습니다.';
        this.updateFail = true;
        return;
      } else if (this.usingUpdate) {
        this.updateFailMessage = '이미 업데이트 사용 중 입니다.';
        this.updateFail = true;
        return;
      }
      console.log('updateTextField');
      this.usingUpdate = true;

      console.log(i);
      console.log(this.comments[i]);
      this.comments[i].replies[j].replyUpdateCheck = true;
      this.updateText = replyText;
    },
    async updateComment(commentId, i) {
      console.log(i);
      if (!this.isAuthenticated) return;
      const result = await CommentService.updateComment(commentId, this.updateText).catch((err) => {
        console.log(err);
      });

      if (result.data.success) {
        this.comments[i].text = this.updateText;
      }

      this.finishUpdate(i);
      if (!result.data.success) {
        this.snackbarFail = true;
        return;
      }
    },
    async updateReply(i, j, replyId) {
      console.log(i);
      if (!this.isAuthenticated) return;
      const result = await ReplyService.updateReply(replyId, this.updateText).catch((err) => {
        console.log(err);
      });

      if (result.data.success) {
        this.comments[i].replies[j].text = this.updateText;
      }

      this.finishReply(i, j);
      if (!result.data.success) {
        this.snackbarFail = true;
        return;
      }
    },
    async deleteComment(commentId) {
      if (!this.isAuthenticated) return;
      // this.$store.dispatch('deleteComment', id)

      const result = await CommentService.deleteById(commentId).catch((err) => {
        console.log(err);
      });
      if (!result.data.success) {
        this.snackbarFail = true;
        return;
      }

      this.comments = this.comments.filter((comment) => comment.commentId.toString() !== commentId.toString());

      this.snackbar = true;

      await this.$store.dispatch('setComments', this.attractionId).catch((err) => console.log(err));
      this.comments = this.$store.getters.getComments.data;
      this.$emit('attractionCommentLength');
    },
    async addReply(event, id) {
      if (!this.isAuthenticated) return;
      if (this.$refs[`input${id}`][0].$refs.input.value == '') return;

      this.btnLoading = true;
      // console.log((event.target.loading = true))
      this.$refs[`form${id}`][0].reset();
      // console.log(this.$refs[`input${id}`][0].$refs.input.value)

      const reply = await ReplyService.createReply(id, this.$refs[`input${id}`][0].$refs.input.value)
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          this.btnLoading = false;
          // this.$store.dispatch('setComments', this.attractionId)
        });
      reply.data.data.userId = this.$store.getters.currentUser;
      // this.$store.dispatch('addComment', reply.data.data)
      // console.log(this.$store.getters.getComments.data)
      let comment = this.comments.find((comment) => comment.commentId.toString() === id.toString());
      // console.log(comment)
      if (!comment.replies) {
        // console.log('1')
        // comment.replies = []
        comment.replies.push(reply.data.data);
      } else {
        // console.log('2')
        comment.replies.unshift(reply.data.data);
        // this.comments
        //   .find((comment) => comment.commentId === id)
        //   .replies.unshift(reply.data.data)
      }

      // console.log(
      //   this.$store.getters.getComments.data.find(
      //     (comment) => comment.commentId === id
      //   )
      // )
      // this.comments
      //   .find((comment) => comment.commentId === id)
      //   .replies.unshift(reply.data.data)
    },
    async deleteReply(commentId, replyId) {
      if (!this.isAuthenticated) return;

      const result = await ReplyService.deleteById(replyId).catch((err) => {
        console.log(err);
      });
      if (!result.data.success) {
        this.snackbarFail = true;
        return;
      }

      this.comments = this.comments.filter((comment) => comment.commentId.toString() !== commentId.toString());

      this.snackbar = true;

      await this.$store.dispatch('setComments', this.attractionId).catch((err) => console.log(err));
      this.comments = this.$store.getters.getComments.data;
    },
    clickTextField() {
      if (!this.isAuthenticated) return this.$router.push('/signin');
    },
    clickTextField2() {
      if (!this.isAuthenticated) return this.$router.push('/signin');
      this.showCommentBtns = true;
    },
    showReply(id) {
      this.$refs[id][0].classList.toggle('d-none');
    },
    hideReply(id) {
      this.$refs[`form${id}`][0].reset();
      this.$refs['reply' + id][0].classList.toggle('d-none');
    },
    dateFormatter(date) {
      return moment(date).fromNow();
    },
    finishUpdate(i) {
      this.updateText = '';
      this.usingUpdate = false;
      this.comments[i].commentUpdateCheck = false;
    },
    finishReply(i, j) {
      this.updateText = '';
      this.usingUpdate = false;
      this.comments[i].replies[j].replyUpdateCheck = false;
    },
  },

  mounted() {
    this.getComments();
    console.log('mounted()');
  },
};
</script>

<style lang="scss">
.v-application--is-ltr .v-expansion-panel-header__icon {
  margin-left: initial;
}

.v-expansion-panel::before {
  box-shadow: initial;
}
</style>
