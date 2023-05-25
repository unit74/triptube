<template>
  <div id="trending" class="pt-7 px-sm-10">
    <v-container fluid>
      <v-row>
        <v-col cols="8" sm="7" md="10" lg="10" v-for="(attraction, i) in loading ? 12 : attractions" :key="i" class="mx-lg-0 mx-md-0 mx-sm-auto mx-auto">
          <v-skeleton-loader class="mx-auto" type="list-item-avatar-three-line" :loading="loading" tile large>
            <v-card class="card" tile flat :to="`/watch/${attraction.contentId}`">
              <v-row no-gutters>
                <v-col class="mx-auto" cols="12" sm="8" md="5" lg="4">
                  <!-- <v-responsive max-height="100%"> -->
                  <v-img v-if="attraction.firstImage" max-height="200" class="align-center" :src="`${attraction.firstImage}`"> </v-img>
                  <v-img v-else :src="require(`@/assets/logo.png`)" max-height="200" class="align-center"> </v-img>
                  <!-- </v-responsive> -->
                </v-col>
                <v-col class="hidden-sm-and-down">
                  <div class="ml-4 ">
                    <v-card-title class="pl-2 pt-0 subtitle-1 font-weight-bold">
                      {{ attraction.title }}
                    </v-card-title>

                    <v-card-subtitle class="pl-2 pb-0" v-if="attraction">
                      {{ attraction.addr1 }}
                      <v-icon>mdi-circle-small</v-icon>{{ attraction.readcount }} views<v-icon>mdi-circle-small</v-icon
                      >{{ dateFormatter(attraction.createdAt) }}
                    </v-card-subtitle>
                    <br />
                    <v-card-subtitle class="pl-2 pt-0">
                      {{ truncateText(attraction.overview, 100) }}
                    </v-card-subtitle>
                  </div>
                </v-col>
              </v-row>
            </v-card>
          </v-skeleton-loader>
        </v-col>
        <v-col class="text-center" v-if="attractions.length === 0 && !loading">
          <p>No trending attractions yet</p>
        </v-col>
        <v-col cols="12" sm="12" md="12" lg="12">
          <infinite-loading @infinite="getAttractions">
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
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading';
import moment from 'moment';

import { mapGetters } from 'vuex';

import AttractionService from '@/services/AttractionService';

export default {
  name: 'Trending',
  data: () => ({
    loading: false,
    loaded: false,
    errored: false,
    attractions: [],
    page: 1,
  }),
  computed: {
    ...mapGetters(['currentUser', 'getUrl', 'isAuthenticated']),
  },
  methods: {
    async getAttractions($state) {
      if (!this.loaded) {
        this.loading = true;
      }

      const attractions = await AttractionService.getHotplace({
        page: this.page,
      })
        .catch((err) => {
          console.log(err);
          this.errored = true;
        })
        .finally(() => {
          this.loading = false;
        });

      if (typeof attractions === 'undefined') return;

      if (attractions.data.data.length) {
        this.page += 1;
        this.attractions.push(...attractions.data.data);
        $state.loaded();
        this.loaded = true;
      } else {
        $state.complete();
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
    InfiniteLoading,
  },
};
</script>

<style lang="scss">
.v-skeleton-loader__list-item-avatar-three-line {
  height: 250px;
  .v-skeleton-loader__paragraph.v-skeleton-loader__bone {
    margin-top: -125px;
    @media (max-width: 700px) {
      display: none;
      margin-top: inherit;
    }
  }
  .v-skeleton-loader__avatar {
    height: 200px !important;
    width: 300px !important;
  }
  .v-skeleton-loader__text {
    height: 20px;
  }
}
</style>
