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
            <v-col cols="12" sm="12" md="8" lg="8" :style="{ marginTop: scrollNum + 'px' }">
              <v-skeleton-loader type="card-avatar, article, actions" :loading="videoLoading" tile large>
                <div ref="hello">
                  <v-responsive>
                    <v-container class="grey lighten-5" max-height="900">
                      <nearby-map :video="video" :videos="videos" />
                    </v-container>
                  </v-responsive>
                </div>
              </v-skeleton-loader>
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
                        <v-img v-else class="align-center" height="110" :src="noImgUrl"> </v-img>
                        <!-- </v-responsive> -->
                      </v-col>
                      <v-col>
                        <div class="ml-2">
                          <v-card-title class="pl-2 pt-2 subtitle-1 font-weight-bold " style="line-height: 1">
                            {{ video.title }}
                          </v-card-title>

                          <v-card-subtitle class="pl-2 pt-2 pb-0" style="line-height: 1">
                            <!-- {{ video.userId.channelName }}<br /> -->
                            {{ video.readcount }} views
                          </v-card-subtitle>
                          <v-card-subtitle class="pl-2 pt-2 pb-0" style="line-height: 1">
                            {{ video.addr1 }}
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
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import InfiniteLoading from "vue-infinite-loading";

import VideoService from "@/services/VideoService";

import NearbyMap from "@/components/map/NearbyMap.vue";

export default {
  data: () => ({
    loading: false,
    loaded: false,
    errored: false,
    videoLoading: true,
    video: {},
    videos: [],
    page: 1,
    infiniteId: +new Date(),
    details: {},
    scrollNum: 0,
  }),
  computed: {
    ...mapGetters(["currentUser", "getUrl", "isAuthenticated", "noImgUrl"]),
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
      }

      if (!this.isAuthenticated) return;
    },
    async getVideos($state) {
      this.errored = false;
      if (!this.loaded) {
        this.loading = true;
      }
      const videos = await VideoService.getNearbyAttractions(this.$route.params.id, { page: this.page })
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

    play(e) {
      console.log(e);
    },
    actions() {
      this.getVideo();
    },

    scroll() {
      this.scrollNum = window.scrollY || document.documentElement.scrollTop;
      if (window.scrollY > this.$refs.scrollTest.offsetTop) {
        this.isFixed = true;
      } else {
        this.isFixed = false;
      }
    },
  },
  components: {
    InfiniteLoading,
    NearbyMap,
  },
  mounted() {
    this.getVideo(this.$route.params.id);
    document.addEventListener("scroll", this.scroll);
  },
  beforeDestroy() {
    document.removeEventListener("scroll", this.scroll);
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