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
              <v-skeleton-loader type="card-avatar, article, actions" :loading="attractionLoading" tile large>
                <div ref="hello">
                  <v-responsive max-height="450">
                    <v-container class="grey lighten-5">
                      <v-row>
                        <v-col cols="6">
                          <v-img v-if="attraction.firstImage" :src="`${attraction.firstImage}`" width="100%" height="350" style="border-radius: 5%"></v-img>
                          <v-img v-else :src="noImgUrl" width="100%" height="350" style="border-radius: 5%"></v-img>
                        </v-col>
                        <v-col cols="6">
                          <show-map :attraction="attraction" />
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-responsive>

                  <v-card flat tile class="card">
                    <v-card-title class="pl-0 pb-0">{{ attraction.title }}</v-card-title>
                    <div class="d-flex flex-wrap justify-space-between" id="btns">
                      <v-card-subtitle class="pl-0 pt-0 pb-0 subtitle-1" style="line-height: 2.4em"> {{ attraction.readcount }} views </v-card-subtitle>
                      <v-card-actions class="pt-0 pl-0">
                        <v-btn text @click="createReaction('like')"
                          ><v-icon :class="`pr-2${reaction !== 'like' ? ' grey--text text--darken-1' : ''}`">mdi-thumb-up</v-icon>{{ attraction.likes }}</v-btn
                        >

                        <v-btn text @click="createReaction('dislike')"
                          ><v-icon :class="`pr-2${reaction !== 'dislike' ? ' grey--text text--darken-1' : ''}`">mdi-thumb-down</v-icon>
                          {{ attraction.dislikes }}</v-btn
                        >
                        <v-btn :to="`/nearby/${attraction.contentId}`" text class="grey--text text--darken-1"
                          ><v-icon>mdi-map-marker-distance</v-icon> 주변 관광지</v-btn
                        >
                      </v-card-actions>
                    </div>
                  </v-card>

                  <v-row class="justify-space-between">
                    <v-col cols="12" sm="6" md="5" lg="5">
                      <v-card class="transparent" flat>
                        <v-list-item three-line>
                          <!-- <v-list-item-avatar v-if="typeof attraction !== 'undefined'" size="50"> -->
                          <v-list-item-avatar v-if="attraction !== 'undefined'" size="50">
                            <img v-if="attraction.firstImage2" :src="`${attraction.firstImage2}`" :alt="`${attraction.title} avatar`" />
                            <v-avatar v-else color="red">
                              <span v-if="attraction.title" class="white--text headline"> {{ attraction.title.split('')[0].toUpperCase() }}</span>
                            </v-avatar>
                          </v-list-item-avatar>
                          <v-list-item-content v-if="attraction" class="align-self-auto">
                            <v-list-item-title class="font-weight-medium mb-1">{{ attraction.title }}</v-list-item-title>
                            <v-list-item-subtitle>{{ attraction.addr1 }} </v-list-item-subtitle>
                          </v-list-item-content>
                        </v-list-item>
                      </v-card>
                    </v-col>
                    <v-col cols="12" sm="6" md="4" lg="4">
                      <!-- <div class="d-flex justify-end align-center" v-if="typeof attraction.title !== 'undefined'"> -->
                      <div class="d-flex justify-end align-center" v-if="attraction.title !== 'undefined'">
                        <v-btn
                          :class="[
                            { 'red white--text': !inLibrary },
                            {
                              'grey grey--text lighten-3 text--darken-3': inLibrary,
                            },
                            'mt-6',
                          ]"
                          tile
                          large
                          depressed
                          :loading="LibraryLoading"
                          @click="Library"
                          >{{ !inLibrary ? 'Library' : 'inLibrary' }}</v-btn
                        >
                      </div>
                    </v-col>
                    <v-col class="pl-11" offset="1" cols="11" md="11">
                      <p>
                        {{ truncate ? truncateText(attraction.overview, 150) : attraction.overview }}
                      </p>
                      <v-btn text @click="show" class="remove-hover-bg">Show More</v-btn>
                    </v-col>
                  </v-row>
                </div>
              </v-skeleton-loader>

              <v-row>
                <!-- <v-col v-if="attraction && attraction.status === 'public'"> -->
                <v-col v-if="attraction">
                  <p class="mb-0">{{ attraction.comments }} Comments</p>

                  <AddComment v-if="attraction.contentId" @attractionCommentLength="attraction.comments++" :attractionId="attraction.contentId" />
                  <CommentList v-if="attraction.contentId" @attractionCommentLength="attraction.comments--" :attractionId="attraction.contentId" />
                </v-col>
              </v-row>
            </v-col>

            <v-col cols="12" sm="12" md="4" lg="4">
              <hr class="grey--text" />
              <h4 class="mb-3 mt-3">Up next</h4>
              <div v-for="(attraction, i) in loading ? 12 : attractions" :key="i" class="mb-5">
                <v-skeleton-loader class="mx-auto" type="list-item-avatar-three-line" :loading="loading" tile large>
                  <v-card class="card" tile flat v-if="!loading" :to="`/watch/${attraction.contentId}`">
                    <v-row no-gutters>
                      <v-col class="mx-auto" cols="12" sm="12" md="5" lg="5">
                        <!-- <v-responsive max-height="100%"> -->
                        <v-img v-if="attraction.firstImage" class="align-center" height="110" :src="`${attraction.firstImage}`"> </v-img>
                        <v-img v-else class="align-center" height="110" :src="noImgUrl"> </v-img>
                        <!-- </v-responsive> -->
                      </v-col>
                      <v-col>
                        <div class="ml-2">
                          <v-card-title class="pl-2 pt-2 subtitle-1 font-weight-bold " style="line-height: 1">
                            {{ attraction.title }}
                          </v-card-title>
                          <v-card-subtitle class="pl-2 pt-2 pb-0" style="line-height: 1">
                            {{ attraction.addr1 }}
                          </v-card-subtitle>
                          <v-card-subtitle class="pl-2 pt-2 pb-0" style="line-height: 1"> {{ attraction.readcount }} views </v-card-subtitle>
                        </div>
                      </v-col>
                    </v-row>
                  </v-card>
                </v-skeleton-loader>
              </div>
              <!-- <v-col cols="12" sm="12" md="12" lg="12"> -->
              <infinite-loading :identifier="infiniteId" @infinite="getAttractions">
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
import moment from 'moment';
import { mapGetters } from 'vuex';
import InfiniteLoading from 'vue-infinite-loading';

import AttractionService from '@/services/AttractionService';
import LibraryService from '@/services/LibraryService';
import ReactionService from '@/services/ReactionService';
// import HistoryService from "@/services/HistoryService";

import SigninModal from '@/components/SigninModal';
import AddComment from '@/components/comments/AddComment';
import CommentList from '@/components/comments/CommentList';
import ShowMap from '@/components/map/ShowMap';

export default {
  data: () => ({
    loading: false,
    loaded: false,
    errored: false,
    attractionLoading: true,
    inLibrary: false,
    LibraryLoading: false,
    showSubBtn: true,
    reaction: '',
    attraction: {},
    attractionId: '',
    attractions: [],
    page: 1,
    infiniteId: +new Date(),
    truncate: true,
    url: process.env.VUE_APP_URL,
    signinDialog: false,
    details: {},
    libraryId: null,
  }),
  computed: {
    ...mapGetters(['currentUser', 'getUrl', 'isAuthenticated', 'noImgUrl']),
  },
  methods: {
    async getAttraction(id) {
      this.errored = false;
      this.attractionLoading = true;
      this.attraction = {};
      try {
        const attraction = await AttractionService.getById(id);

        if (!attraction) return this.$router.push('/');
        this.attraction = attraction.data.data;
      } catch (err) {
        this.errored = true;
        console.log(err);
      } finally {
        this.attractionLoading = false;
        this.checkLibrary(this.attraction.contentId);
        this.checkReaction(this.attraction.contentId);
      }

      this.showSubBtn = true;

      if (!this.isAuthenticated) return;
    },
    async getAttractions($state) {
      this.errored = false;
      if (!this.loaded) {
        this.loading = true;
      }
      const attractions = await AttractionService.getAll('public', { page: this.page })
        .catch((err) => {
          console.log(err);
          this.errored = true;
        })
        .finally(() => (this.loading = false));

      if (typeof attractions === 'undefined') return;

      if (attractions.data.data.length) {
        this.page += 1;

        this.attractions.push(...attractions.data.data);
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
    async checkLibrary(contentId) {
      if (!this.isAuthenticated) return;

      this.loading = true;
      const sub = await LibraryService.checkLibraries(contentId)
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          this.loading = false;
        });

      if (!sub) return;

      this.inLibrary = sub.data.success;
      this.libraryId = null;
      if (sub.data.success) this.libraryId = sub.data.data.libraryId;
    },
    async checkReaction(id) {
      if (!this.isAuthenticated) return;

      this.loading = true;
      const reaction = await ReactionService.checkReaction(id)
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          this.loading = false;
        });

      if (!reaction) {
        return;
      }

      console.log(reaction);
      if (reaction.data.data.type === 'LIKE') this.reaction = 'like';
      else if (reaction.data.data.type === 'DISLIKE') this.reaction = 'dislike';
      else this.reaction = '';
    },
    async createReaction(type) {
      if (!this.isAuthenticated) {
        this.signinDialog = true;
        this.details = {
          title: type === 'like' ? 'Like this attraction?' : "Don't like this attraction?",
          text: 'Sign in to make your opinion count.',
        };
        return;
      }
      switch (true) {
        case type === 'like' && this.reaction === '':
          this.reaction = 'like';
          this.attraction.likes++;
          // console.log('new like')
          break;
        case type === 'like' && this.reaction === type:
          this.reaction = '';
          this.attraction.likes--;
          // console.log('remove like')
          break;
        case type === 'like' && this.reaction === 'dislike':
          this.reaction = 'like';
          this.attraction.dislikes--;
          this.attraction.likes++;
          // console.log('change to like')
          break;
        case type === 'dislike' && this.reaction === '':
          this.reaction = 'dislike';
          this.attraction.dislikes++;
          // console.log('new dislike')
          break;
        case type === 'dislike' && this.reaction === type:
          this.reaction = '';
          this.attraction.dislikes--;
          // console.log('remove dislike')
          break;
        case type === 'dislike' && this.reaction === 'like':
          this.reaction = 'dislike';
          this.attraction.likes--;
          this.attraction.dislikes++;
        // console.log('change to dislike')
      }

      if (this.reaction == '') {
        const reaction = await ReactionService.deleteReaction(this.attraction.contentId).catch((err) => {
          console.log(err);
        });

        if (!reaction) return;
      } else {
        const reaction = await ReactionService.createReaction({
          contentId: this.attraction.contentId,
          type: this.reaction.toUpperCase(),
        }).catch((err) => {
          console.log(err);
        });

        if (!reaction) return;
      }
    },
    async Library() {
      if (!this.isAuthenticated) {
        this.signinDialog = true;
        this.details = {
          title: 'Want to library to this attraction?',
          text: 'Sign in to library to this attraction.',
        };
        return;
      }
      this.LibraryLoading = true;
      let sub;
      if (!this.inLibrary) {
        sub = await LibraryService.createLibraries(this.attraction.contentId)
          .catch((err) => console.log(err))
          .finally(() => {
            this.LibraryLoading = false;
          });
      } else {
        sub = await LibraryService.deleteLibrary(this.libraryId)
          .catch((err) => console.log(err))
          .finally(() => {
            this.LibraryLoading = false;
          });
      }

      console.log(sub);
      if (!sub.data.success) return;

      if (sub.data.success) {
        this.inLibrary = !this.inLibrary;
        if (this.inLibrary) this.libraryId = sub.data.data.libraryId;
      }
    },

    play(e) {
      console.log(e);
    },
    actions() {
      this.getAttraction();
    },
    show(event) {
      if (event.target.innerText === 'SHOW MORE') {
        this.truncate = false;
        event.target.innerText = 'SHOW LESS';
      } else {
        this.truncate = true;
        event.target.innerText = 'SHOW MORE';
      }
    },
    truncateText(string = '', num) {
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
    this.getAttraction(this.$route.params.id);
    window.scrollTo(0, 0);
  },
  beforeRouteUpdate(to, from, next) {
    this.page = 1;
    (this.loading = false), (this.loaded = false), (this.attractions = []);
    this.infiniteId += 1;
    this.getAttraction(to.params.id);
    next();
  },
};
</script>

<style lang="scss">
attraction {
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
