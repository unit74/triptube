<template>
  <div id="home" class="pa-4">
    <v-container fluid>
      <v-alert prominent type="error" v-if="errored">
        <v-row align="center">
          <v-col class="grow">
            <div class="title">Error!</div>
            <div>
              Something went wrong, but don’t fret — let’s give it another shot.
            </div>
          </v-col>
          <v-col class="shrink">
            <v-btn @click="getAttractions">Take action</v-btn>
          </v-col>
        </v-row>
      </v-alert>

      <main v-else>
        <h3 class="headline font-weight-medium">Library</h3>
        <v-row>
          <v-col cols="12" sm="6" md="4" lg="3" v-for="(attraction, i) in loading ? 12 : attractions" :key="i" class="mx-xs-auto">
            <v-skeleton-loader type="card-avatar" :loading="loading">
              <attraction-card :card="{ maxWidth: 350 }" :attraction="attraction.attractionInfo"></attraction-card>
            </v-skeleton-loader>
          </v-col>
          <v-col class="text-center" v-if="attractions.length === 0 && !loading">
            <p>You haven't library to any attraction yet</p>
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
      </main>
    </v-container>
  </div>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading';
import moment from 'moment';

import AttractionCard from '@/components/AttractionCard';
import LibraryService from '@/services/LibraryService';

export default {
  name: 'Library',
  data: () => ({
    loading: false,
    loaded: false,
    errored: false,
    attractions: [],
    page: 1,
  }),
  methods: {
    async getAttractions($state) {
      if (!this.loaded) {
        this.loading = true;
      }

      const attractions = await LibraryService.getSaveAttractions(this.page)
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
        for (var i = 0; i < this.attractions.length; i++) {
          this.attractions[i].attractionInfo.updatedAt = this.attractions[i].updatedAt;
        }
        $state.loaded();
        this.loaded = true;
      } else {
        $state.complete();
      }
    },
    dateFormatter(date) {
      return moment(date).fromNow();
    },
  },
  components: {
    AttractionCard,
    InfiniteLoading,
  },
  mounted() {
    // this.getAttractions()
  },
};
</script>

<style lang="scss">
.card {
  background: #f9f9f9 !important;
}
</style>
