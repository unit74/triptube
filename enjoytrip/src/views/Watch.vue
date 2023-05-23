<template>
  <div id="watch" ref="watch">
    <v-container fluid>
      <v-row>
        <v-alert prominent class="mx-auto" type="error" v-if="errored">
          <v-row align="center">
            <v-col class="grow">
              <div class="title">Error!</div>
              <div>Something went wrong, but don’t fret — let’s give it another shot.</div>
            </v-col>
            <v-col class="shrink">
              <v-btn @click="actions">Take action</v-btn>
            </v-col>
          </v-row>
        </v-alert>
        <v-col v-else cols="11" class="mx-auto">
          <v-row>
            <v-col cols="12" sm="12" md="8" lg="8">
              <v-skeleton-loader type="card-avatar, article, actions" :loading="videoLoading" tile large>
                <div ref="hello">
                  <v-responsive max-height="450">
                    <v-container class="grey lighten-5">
                      <v-row>
                        <v-col cols="6">
                          <v-img v-if="video.firstImage" :src="`${video.firstImage}`" width="100%" height="350" style="border-radius: 5%"></v-img>
                          <v-img v-else :src="require(`@/assets/logo.png`)" width="100%" height="350" style="border-radius: 5%"></v-img>
                        </v-col>
                        <v-col cols="6">
                          <show-map :video="video" />
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-responsive>

                  <v-card flat tile class="card">
                    <v-card-title class="pl-0 pb-0">{{ video.title }}</v-card-title>
                    <div class="d-flex flex-wrap justify-space-between" id="btns">
                      <v-card-subtitle class="pl-0 pt-0 pb-0 subtitle-1" style="line-height: 2.4em">
                        {{ video.readcount }} views<v-icon>mdi-circle-small</v-icon>{{ dateFormatter(video.createdAt) }}
                      </v-card-subtitle>
                      <v-card-actions class="pt-0 pl-0">
                        <v-btn text @click="createFeeling('like')"
                          ><v-icon :class="`pr-2${feeling !== 'like' ? ' grey--text text--darken-1' : ''}`">mdi-thumb-up</v-icon>{{ video.likes }}</v-btn
                        >

                        <v-btn text @click="createFeeling('dislike')"
                          ><v-icon :class="`pr-2${feeling !== 'dislike' ? ' grey--text text--darken-1' : ''}`">mdi-thumb-down</v-icon>
                          {{ video.dislikes }}</v-btn
                        >
                        <!-- 다운로드 버튼은 어찌 쓰지? -->
                        <v-btn :href="'#'" text class="grey--text text--darken-1"><v-icon>mdi-download</v-icon> Download</v-btn>
                        <!-- <v-btn text class="grey--text text--darken-1"
                          ><v-icon>mdi-share</v-icon> Share</v-btn
                        >
                        <v-btn text class="grey--text text--darken-1"
                          ><v-icon>mdi-playlist-plus</v-icon> Save</v-btn
                        > -->
                      </v-card-actions>
                    </div>
                  </v-card>

                  <v-row class="justify-space-between">
                    <v-col cols="12" sm="6" md="5" lg="5">
                      <v-card class="transparent" flat>
                        <v-list-item three-line>
                          <v-list-item-avatar v-if="typeof video !== 'undefined'" size="50">
                            <img v-if="video.firstImage2" :src="`${video.firstImage2}`" :alt="`${video.title} avatar`" />
                            <v-avatar v-else color="red">
                              <span v-if="video.title" class="white--text headline"> {{ video.title.split("")[0].toUpperCase() }}</span>
                            </v-avatar>
                          </v-list-item-avatar>
                          <v-list-item-content v-if="video" class="align-self-auto">
                            <v-list-item-title class="font-weight-medium mb-1">{{ video.title }}</v-list-item-title>
                            <v-list-item-subtitle>{{ video.addr1 }} </v-list-item-subtitle>
                          </v-list-item-content>
                        </v-list-item>
                      </v-card>
                    </v-col>
                    <v-col cols="12" sm="6" md="4" lg="4">
                      <div class="d-flex justify-end align-center" v-if="typeof video.userId !== 'undefined'">
                        <v-btn
                          v-if="showSubBtn"
                          :class="[
                            { 'red white--text': !subscribed },
                            {
                              'grey grey--text lighten-3 text--darken-3': subscribed,
                            },
                            'mt-6',
                          ]"
                          tile
                          large
                          depressed
                          :loading="subscribeLoading"
                          @click="subscribe"
                          >{{ !subscribed ? "subscribe" : "subscribed" }}</v-btn
                        >
                      </div>
                    </v-col>
                    <v-col class="pl-11" offset="1" cols="11" md="11">
                      <p>
                        {{ truncate ? truncateText(video.overview, 150) : video.overview }}
                      </p>
                      <v-btn text @click="show" class="remove-hover-bg">Show More</v-btn>
                    </v-col>
                  </v-row>
                </div>
              </v-skeleton-loader>

              <v-row>
                <!-- <v-col v-if="video && video.status === 'public'"> -->
                <v-col v-if="video">
                  <p class="mb-0">{{ video.comments }} Comments</p>

                  <AddComment v-if="video.contentId" @videoCommentLength="video.comments++" :videoId="video.contentId" />
                  <CommentList v-if="video.contentId" @videoCommentLength="video.comments--" :videoId="video.contentId" />
                </v-col>
              </v-row>
            </v-col>

            <v-col cols="12" sm="12" md="4" lg="4">
              <hr class="grey--text" />
              <h4 class="mb-3 mt-3">Up next</h4>
              <div v-for="(video, i) in loading ? 12 : videos" :key="i" class="mb-5">
                <v-skeleton-loader class="mx-auto" type="list-item-avatar-three-line" :loading="loading" tile large>
                  <v-card class="card" tile flat v-if="!loading" :to="`/watch/${video.contentId}`">
                    <v-row no-gutters>
                      <v-col class="mx-auto" cols="12" sm="12" md="5" lg="5">
                        <!-- <v-responsive max-height="100%"> -->
                        <v-img v-if="video.firstImage" class="align-center" height="110" :src="`${video.firstImage}`"> </v-img>
                        <v-img v-else class="align-center" height="110" :src="require(`@/assets/logo.png`)"> </v-img>
                        <!-- </v-responsive> -->
                      </v-col>
                      <v-col>
                        <div class="ml-2">
                          <v-card-title class="pl-2 pt-2 subtitle-1 font-weight-bold " style="line-height: 1">
                            {{ video.title }}
                          </v-card-title>

                          <v-card-subtitle class="pl-2 pt-2 pb-0" style="line-height: 1">
                            <!-- {{ video.userId.channelName }}<br /> -->
                            {{ video.readcount }} views<v-icon>mdi-circle-small</v-icon>
                            {{ dateFormatter(video.createdAt) }}
                          </v-card-subtitle>
                        </div>
                      </v-col>
                    </v-row>
                  </v-card>
                </v-skeleton-loader>
              </div>
              <!-- <v-col cols="12" sm="12" md="12" lg="12"> -->
              <infinite-loading :identifier="infiniteId" @infinite="getVideos">
                <div slot="spinner">
                  <v-progress-circular indeterminate color="red"></v-progress-circular>
                </div>
                <div slot="no-results"></div>
                <span slot="no-more"></span>
                <div slot="error" slot-scope="{ trigger }">
                  <v-alert prominent type="error">
                    <v-row align="center">
                      <v-col class="grow">
                        <div class="title">Error!</div>
                        <div>
                          Something went wrong, but don’t fret — let’s give it another shot.
                        </div>
                      </v-col>
                      <v-col class="shrink">
                        <v-btn @click="trigger">Take action</v-btn>
                      </v-col>
                    </v-row>
                  </v-alert>
                </div>
              </infinite-loading>
              <!-- </v-col> -->
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <signin-modal :openModal="signinDialog" :details="details" @closeModal="signinDialog = false" />
  </div>
</template>

<script>
import moment from "moment";
import { mapGetters } from "vuex";
import InfiniteLoading from "vue-infinite-loading";

import VideoService from "@/services/VideoService";
import SubscriptionService from "@/services/SubscriptionService";
import FeelingService from "@/services/FeelingService";
// import HistoryService from "@/services/HistoryService";

import SigninModal from "@/components/SigninModal";
import AddComment from "@/components/comments/AddComment";
import CommentList from "@/components/comments/CommentList";
import ShowMap from "@/components/map/ShowMap";

export default {
  data: () => ({
    loading: false,
    loaded: false,
    errored: false,
    videoLoading: true,
    subscribed: false,
    subscribeLoading: false,
    showSubBtn: true,
    feeling: "",
    video: {},
    videoId: "",
    videos: [],
    page: 1,
    infiniteId: +new Date(),
    truncate: true,
    url: process.env.VUE_APP_URL,
    signinDialog: false,
    details: {},
  }),
  computed: {
    ...mapGetters(["currentUser", "getUrl", "isAuthenticated"]),
  },
  methods: {
    async getVideo(id) {
      this.errored = false;
      this.videoLoading = true;
      this.video = {};
      try {
        const video = await VideoService.getById(id);

        if (!video) return this.$router.push("/");
        this.video = video.data.data;
      } catch (err) {
        this.errored = true;
        console.log(err);
      } finally {
        this.videoLoading = false;
        // this.checkSubscription(this.video.userId._id);
        this.checkFeeling(this.video.contentId);
      }
      // if (this.currentUser && this.currentUser._id === this.video.userId._id) {
      //   this.showSubBtn = false;
      // } else {
      //   this.showSubBtn = true;
      // }

      this.showSubBtn = true;

      if (!this.isAuthenticated) return;
    },
    async getVideos($state) {
      this.errored = false;
      if (!this.loaded) {
        this.loading = true;
      }
      const videos = await VideoService.getAll("public", { page: this.page })
        .catch((err) => {
          console.log(err);
          this.errored = true;
        })
        .finally(() => (this.loading = false));

      if (typeof videos === "undefined") return;

      if (videos.data.data.length) {
        this.page += 1;

        this.videos.push(...videos.data.data);
        if ($state) {
          $state.loaded();
        }

        this.loaded = true;
      } else {
        if ($state) {
          $state.complete();
        }
      }
    },
    async checkSubscription(id) {
      if (!this.isAuthenticated) return;

      this.loading = true;
      const sub = await SubscriptionService.checkSubscription({ userId: id })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          this.loading = false;
        });

      if (!sub) return;

      if (!sub.data.data._id) this.subscribed = false;
      else this.subscribed = true;
    },
    async checkFeeling(id) {
      if (!this.isAuthenticated) return;

      this.loading = true;
      const feeling = await FeelingService.checkFeeling(id)
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          this.loading = false;
        });

      if (!feeling) {
        return;
      }

      console.log(feeling);
      if (feeling.data.data.type === "LIKE") this.feeling = "like";
      else if (feeling.data.data.type === "DISLIKE") this.feeling = "dislike";
      else this.feeling = "";
    },
    async createFeeling(type) {
      if (!this.isAuthenticated) {
        this.signinDialog = true;
        this.details = {
          title: type === "like" ? "Like this video?" : "Don't like this video?",
          text: "Sign in to make your opinion count.",
        };
        return;
      }
      switch (true) {
        case type === "like" && this.feeling === "":
          this.feeling = "like";
          this.video.likes++;
          // console.log('new like')
          break;
        case type === "like" && this.feeling === type:
          this.feeling = "";
          this.video.likes--;
          // console.log('remove like')
          break;
        case type === "like" && this.feeling === "dislike":
          this.feeling = "like";
          this.video.dislikes--;
          this.video.likes++;
          // console.log('change to like')
          break;
        case type === "dislike" && this.feeling === "":
          this.feeling = "dislike";
          this.video.dislikes++;
          // console.log('new dislike')
          break;
        case type === "dislike" && this.feeling === type:
          this.feeling = "";
          this.video.dislikes--;
          // console.log('remove dislike')
          break;
        case type === "dislike" && this.feeling === "like":
          this.feeling = "dislike";
          this.video.likes--;
          this.video.dislikes++;
        // console.log('change to dislike')
      }

      if (this.feeling == "") {
        const feeling = await FeelingService.deleteFeeling(this.video.contentId).catch((err) => {
          console.log(err);
        });

        if (!feeling) return;
      } else {
        const feeling = await FeelingService.createFeeling({
          contentId: this.video.contentId,
          type: this.feeling.toUpperCase(),
        }).catch((err) => {
          console.log(err);
        });

        if (!feeling) return;
      }
    },
    async subscribe() {
      if (!this.isAuthenticated) {
        this.signinDialog = true;
        this.details = {
          title: "Want to subscribe to this channel?",
          text: "Sign in to subscribe to this channel.",
        };
        return;
      }
      this.subscribeLoading = true;
      const sub = await SubscriptionService.createSubscription({
        channelId: this.video.contentId,
      })
        .catch((err) => console.log(err))
        .finally(() => {
          this.subscribeLoading = false;
        });

      if (!sub) return;

      if (!sub.data.data._id) {
        this.subscribed = false;
      } else {
        this.subscribed = true;
      }
    },
    // async updateViews(id) {
    //   const views = await VideoService.updateViews(id).catch((err) => {
    //     console.log(err);
    //   });
    //   if (!views) return;

    //   this.video.readcount++;
    // },
    play(e) {
      console.log(e);
    },
    actions() {
      this.getVideo();
    },
    show(event) {
      if (event.target.innerText === "SHOW MORE") {
        this.truncate = false;
        event.target.innerText = "SHOW LESS";
      } else {
        this.truncate = true;
        event.target.innerText = "SHOW MORE";
      }
    },
    truncateText(string = "", num) {
      if (string.length <= num) {
        return string;
      }
      return string.slice(0, num);
    },
    dateFormatter(date) {
      return moment(date).fromNow();
    },
  },
  components: {
    AddComment,
    CommentList,
    SigninModal,
    ShowMap,
    InfiniteLoading,
  },
  mounted() {
    this.getVideo(this.$route.params.id);
    // if (this.isAuthenticated) this.updateViews(this.$route.params.id);
  },
  beforeRouteUpdate(to, from, next) {
    this.page = 1;
    (this.loading = false), (this.loaded = false), (this.videos = []);
    this.infiniteId += 1;
    this.getVideo(to.params.id);
    next();
  },
};
</script>

<style lang="scss">
video {
  max-width: 100%;
}

.grey-color {
  color: #7f7f7f !important;
}

#btns {
  border-bottom: 1px solid #e0d8d8;
}

button.v-btn.remove-hover-bg {
  background-color: initial !important;
  &:hover {
    background-color: #f9f9f9;
  }
}
</style>
