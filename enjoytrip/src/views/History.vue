<template>
  <div id="history" class="fill-height">
    <v-container fluid class="pt-0 pb-0 fill-height">
      <v-row class="fill-height">
        <v-col cols="12" sm="12" order="2" order-sm="2" order-md="1" order-lg="1" md="7" lg="7" class="pt-8 pl-8">
          <h1 class="title font-weight-medium pb-5">{{ historyType }}</h1>
          <template v-if="historyType == 'Watch History'">
            <template v-if="histories.length <= 0 && !loading">
              <p class="text-center body-1">No watch history yet.</p>
            </template>
            <section>
              <div v-for="(history, i) in loading ? 12 : histories" :key="i" class="mb-5">
                <v-skeleton-loader
                  v-if="history.attractionInfo"
                  class="mx-auto"
                  type="list-item-avatar-three-line"
                  :loading="loading"
                  tile
                  large
                  @click="movePage(history.attractionInfo.contentId)"
                >
                  <v-card
                    class="card"
                    v-if="history.attractionInfo.contentId"
                    tile
                    flat
                    @mouseover="isHovering = true"
                    @mouseout="isHovering = false"
                    :hover="isHovering"
                  >
                    <v-row no-gutters>
                      <v-col class="mx-auto" cols="3" sm="3" md="5" lg="5">
                        <v-img
                          v-if="history.attractionInfo.firstImage"
                          max-width="400"
                          max-height="200"
                          class="align-center"
                          :src="`${history.attractionInfo.firstImage}`"
                        >
                        </v-img>
                        <v-img v-else :src="require(`@/assets/logo.png`)" max-width="400" max-height="200" style="border-radius: 5%"></v-img>
                      </v-col>
                      <v-col>
                        <div class="ml-2">
                          <v-card-title class="pl-2 pt-0 subtitle-1 font-weight-bold d-flex justify-space-between" style="line-height: 1">
                            {{ history.attractionInfo.title }}

                            <v-btn text @click="deleteHistory(history.historyId)">
                              <v-icon>mdi-close</v-icon>
                            </v-btn>
                          </v-card-title>

                          <v-card-subtitle class="pl-2 pt-2 pb-0" style="line-height: 1">
                            {{ history.attractionInfo.title }}<v-icon>mdi-circle-small</v-icon>{{ history.attractionInfo.readcount }} views
                          </v-card-subtitle>
                          <v-card-subtitle class="pl-2 pt-2 pb-0">
                            {{ history.attractionInfo.addr1 }}
                          </v-card-subtitle>
                          <v-card-subtitle class="pl-2 pt-2 pb-0">
                            {{ dateFormatter(history.updatedAt) }}
                          </v-card-subtitle>
                        </div>
                      </v-col>
                    </v-row>
                  </v-card>
                </v-skeleton-loader>
              </div>
              <infinite-loading :identifier="infiniteId" @infinite="getHistories">
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
            </section>
          </template>
          <template v-else-if="historyType == 'Search History'">
            <template v-if="histories.length <= 0 && !loading">
              <p class="text-center body-1">No search history yet.</p>
            </template>
            <div>
              <div v-for="(history, i) in loading ? 12 : histories" :key="i">
                <v-skeleton-loader class="mx-auto" type="list-item-two-line" :loading="loading" tile large>
                  <v-card class="mt-2 card d-flex pl-4" flat @mouseover="isHovering = true" @mouseout="isHovering = false" :hover="isHovering">
                    <v-card-text class="pl-0 py-0">
                      <v-card-title class="pl-0 black--text text--lighten-5 subtitle-1 font-weight-medium">{{ history.searchText }}</v-card-title>
                      <v-card-subtitle class="pl-0 pb-0">{{ dateFormatter(history.updatedAt) }}</v-card-subtitle>
                    </v-card-text>
                    <v-card-actions
                      ><v-btn text class="grey--text" fab @click="deleteHistory(history.historyId)">
                        <v-icon>mdi-close</v-icon>
                      </v-btn></v-card-actions
                    >
                  </v-card>
                </v-skeleton-loader>
              </div>
              <infinite-loading :identifier="infiniteId" @infinite="getHistories">
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
            </div>
          </template>
          <template v-else>
            <template v-if="histories.length <= 0 && !loading">
              <p class="text-center body-1">No search history yet.</p>
            </template>
            <div>
              <div v-for="(history, i) in loading ? 12 : histories" :key="i">
                <v-skeleton-loader
                  class="mx-auto"
                  type="list-item-two-line"
                  :loading="loading"
                  tile
                  large
                  @click="movePage(history.contentId)"
                  style="cursor:Pointer"
                >
                  <v-card class="mt-2  card d-flex pl-2" flat @mouseover="isHovering = true" @mouseout="isHovering = false" :hover="isHovering">
                    <v-list-item-avatar size="50">
                      <v-img
                        v-if="currentUser.profilePhotoUrl !== 'no-photo.jpg'"
                        class="elevation-6"
                        :src="`${defaultProfileUrl + currentUser.profilePhotoUrl}`"
                      ></v-img>
                      <v-avatar v-else color="red">
                        <span class="white--text headline "> {{ currentUser.name.split("")[0].toUpperCase() }}</span>
                      </v-avatar>
                    </v-list-item-avatar>
                    <v-list-item-content>
                      <div class="d-flex mb-0">
                        <v-list-item-title class="font-weight-medium caption mb-0 d-flex"
                          >{{ currentUser.name }}
                          <span class="pl-2 font-weight-light grey--text"> {{ dateFormatter(history.createdAt) }}</span>
                        </v-list-item-title>
                      </div>
                      <v-list-item-subtitle class="mt-2 black--text text--darken-4 caption">{{ history.text }}</v-list-item-subtitle>
                    </v-list-item-content>
                    <v-btn text @click="deleteHistory(history)">
                      <v-icon>mdi-close</v-icon>
                    </v-btn>
                    <!-- <v-card-text class="pl-0 py-0">
                      <v-card-title class="pl-0 black--text text--lighten-5 subtitle-1 font-weight-medium">{{ history.text }}</v-card-title>
                      <v-card-subtitle class="pl-0 pb-0">{{ dateFormatter(history.updatedAt) }}</v-card-subtitle>
                    </v-card-text> -->

                    <!-- <v-card-actions
                      ><v-btn text class="grey--text" fab @click="deleteHistory(history.historyId)">
                        <v-icon>mdi-close</v-icon>
                      </v-btn>
                    </v-card-actions> -->
                  </v-card>
                </v-skeleton-loader>
              </div>
            </div>
          </template>
        </v-col>
        <v-col
          cols="12"
          sm="12"
          order="1"
          order-sm="1"
          order-md="2"
          order-lg="2"
          md="5"
          lg="5"
          :class="['pa-0', { 'fill-height': $vuetify.breakpoint.name.smAndDown ? true : false }]"
        >
          <v-card flat id="card-radiobox" class="fill-height grey lighten-4 pa-10">
            <v-radio-group v-model="historyType">
              <p class="title font-weight-regular pl-5 mb-2">History Type</p>
              <v-list class=" grey lighten-4">
                <v-list-item-group>
                  <template v-for="(item, i) in items">
                    <v-divider :key="i"></v-divider>
                    <v-list-item active-class="grey lighten-4" :key="`item-${i}`" class="py-2" @click="clickItem(item)">
                      <v-list-item-content>
                        <v-list-item-title v-text="item"></v-list-item-title>
                      </v-list-item-content>

                      <v-list-item-action>
                        <v-radio :key="item" :value="item"></v-radio>
                      </v-list-item-action>
                    </v-list-item>
                  </template>
                </v-list-item-group>
              </v-list>
              <div>
                <v-btn v-if="historyType !== 'Comment History' && historyType !== 'Reply History'" text :loading="clearLoading" @click="clearHistory"
                  >Clear all {{ historyType }}</v-btn
                >
                <!-- <v-btn text>Pause {{ historyType }}</v-btn>
                <v-btn text>Manage all activity</v-btn> -->
              </div>
            </v-radio-group>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <v-snackbar v-model="snackbar">
      {{ deleteMessage }}
      <v-btn color="white" text @click="snackbar = false" icon>
        <v-icon>mdi-close-circle</v-icon>
      </v-btn>
    </v-snackbar>
  </div>
</template>

<script>
import moment from "moment";
import { mapGetters } from "vuex";

import HistoryService from "@/services/HistoryService";
import InfiniteLoading from "vue-infinite-loading";

export default {
  data: () => ({
    loading: false,
    loaded: false,
    errored: false,
    snackbar: false,
    deleteMessage: "",
    items: ["Watch History", "Search History", "Comment History", "Reply History"],
    historyType: "Watch History",
    histories: [],
    page: 1,
    infiniteId: +new Date(),
    clearLoading: false,
    moveCheck: true,
    isHovering: false,
  }),
  computed: {
    ...mapGetters(["currentUser", "getUrl", "defaultProfileUrl"]),
  },
  methods: {
    async getHistories($state) {
      this.errored = false;
      if (!this.loaded) {
        this.loading = true;
      }

      // console.log("this.historyType");
      // console.log(this.historyType);
      const params = {
        page: this.page,
        type: this.historyType === "Watch History" ? "VISIT" : "SEARCH",
      };

      let histories;
      if (this.historyType == "Reply History") {
        histories = await HistoryService.getReplies()
          .catch((err) => {
            this.errored = true;
            console.log(err);
          })
          .finally(() => {
            this.loading = false;
          });
      } else if (this.historyType == "Comment History") {
        histories = await HistoryService.getComments()
          .catch((err) => {
            this.errored = true;
            console.log(err);
          })
          .finally(() => {
            this.loading = false;
          });
      } else {
        histories = await HistoryService.getAll(params)
          .catch((err) => {
            this.errored = true;
            console.log(err);
          })
          .finally(() => {
            this.loading = false;
          });
      }
      if (!histories) return;

      if (histories.data.data.length) {
        this.page += 1;

        this.histories.push(...histories.data.data);
        if ($state) {
          $state.loaded();
        }

        console.log(this.histories);

        this.loaded = true;
      } else {
        if ($state) {
          $state.complete();
        }
      }
    },
    async clearHistory() {
      this.clearLoading = true;

      const type = this.historyType === "Watch History" ? "VISIT" : "SEARCH";

      await HistoryService.deleteAll(type)
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          this.histories = [];

          this.clearLoading = false;
          this.deleteMessage = `${this.historyType} Cleared Successfully`;
          this.snackbar = true;
        });
    },
    async deleteHistory(id) {
      console.log("delete");
      console.log(this.histories, id);
      this.moveCheck = false;
      if (this.historyType == "Comment History") {
        this.histories = this.histories.filter((history) => history.commentId.toString() !== id.commentId.toString());
        await HistoryService.deleteComment(id.commentId)
          .catch((err) => {
            console.log(err);
          })
          .finally(() => {
            this.deleteMessage = "History Deleted Successfully";
            this.snackbar = true;
            this.moveCheck = true;
          });
      } else if (this.historyType == "Reply History") {
        this.histories = this.histories.filter((history) => history.replyId.toString() !== id.replyId.toString());
        await HistoryService.deleteReply(id.replyId)
          .catch((err) => {
            console.log(err);
          })
          .finally(() => {
            this.deleteMessage = "History Deleted Successfully";
            this.snackbar = true;
            this.moveCheck = true;
          });
      } else {
        this.histories = this.histories.filter((history) => history.historyId.toString() !== id.toString());
        await HistoryService.deleteById(id)
          .catch((err) => {
            console.log(err);
          })
          .finally(() => {
            this.deleteMessage = "History Deleted Successfully";
            this.snackbar = true;
            this.moveCheck = true;
          });
      }
    },
    clickItem(item) {
      this.historyType = item;
      this.page = 1;
      this.histories = [];
      this.infiniteId += 1;
    },
    dateFormatter(date) {
      return moment(date).fromNow();
    },
    movePage(id) {
      if (this.moveCheck) this.$router.push(`/Watch/${id}`);
    },
  },
  components: {
    InfiniteLoading,
  },
  watch: {
    historyType() {
      if (this.historyType == "Comment History" || this.historyType == "Reply History") this.getHistories();
    },
  },
};
</script>

<style lang="scss" scoped></style>
