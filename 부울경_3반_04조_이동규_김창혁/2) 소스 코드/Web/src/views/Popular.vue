<template>
  <div class="pl-6">
    <v-container fluid>
      <h3 class="mt-4 headline font-weight-medium">Popular Attractions</h3>
      <v-row>
        <v-alert prominent class="mx-auto" type="error" v-if="errored">
          <v-row align="center">
            <v-col class="grow">
              <div class="title">Error!</div>
              <div>
                Something went wrong, but don’t fret — let’s give it another shot.
              </div>
            </v-col>
            <v-col class="shrink">
              <v-btn @click="get">Take action</v-btn>
            </v-col>
          </v-row>
        </v-alert>

        <v-col v-else cols="12" sm="12" md="10" lg="11">
          <template v-if="results.length === 0">
            <p class="text-center">Ops! No results</p>
          </template>

          <div v-else v-for="(result, i) in loading ? 5 : results" :key="i" class="mb-5">
            <v-skeleton-loader class="mx-auto" type="list-item-avatar-three-line" :loading="loading" tile large>
              <v-card :to="`/popularDetail/${result.contentType}`" class="card mb-10" v-if="typeof result.contentTypeName !== 'undefined'" tile flat>
                <v-row no-gutters justify="center">
                  <v-col cols="10" sm="10" md="3" lg="3" class="d-flex">
                    <!-- <v-responsive max-height="100%"> -->

                    <v-avatar size="150" max-width="150" class="mx-auto blue">
                      <img
                        v-if="result.contentTypeName !== 'no-photo.jpg'"
                        :src="`http://localhost:8080/api/v1/public/images/contents/${result.contentTypeImageUrl}`"
                        :alt="`${result.name} avatar`"
                      />
                      <template v-else color="red">
                        <span class="white--text display-1"> {{ result.contentTypeName.split('')[0].toUpperCase() }}</span>
                      </template>
                    </v-avatar>
                    <!-- </v-responsive> -->
                  </v-col>
                  <v-col cols="10" sm="10" md="6" lg="6" class="justify-center align-center">
                    <v-row
                      ><v-card-title class="pl-2 pt-12 subtitle-1 font-weight-bold align-center">
                        {{ result.contentTypeName }}
                      </v-card-title></v-row
                    >
                  </v-col>
                </v-row>
              </v-card>
            </v-skeleton-loader>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import SearchService from '@/services/SearchService';

export default {
  data: () => ({
    errored: false,
    loading: true,
    loaded: false,
    results: [],
  }),
  computed: {
    ...mapGetters(['getUrl', 'defaultUrl']),
  },
  methods: {
    async getSearchResults($state) {
      this.errored = false;
      if (!this.loaded) {
        this.loading = true;
      }

      const results = await SearchService.getContents()
        .catch((err) => {
          console.log(err);
          this.errored = true;
        })
        .finally(() => {
          this.loading = false;
        });

      if (!results) return;

      if (results.data.data.length) {
        this.page += 1;

        this.results.push(...results.data.data);
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
  },
  created() {
    this.getSearchResults();
  },
};
</script>

<style></style>
