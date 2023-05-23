<template>
  <div id="search" class="pl-6">
    <v-container fluid>
      <v-row>
        <v-container>
          <v-row>
            <v-col> <v-select v-model="sido" :items="sidos" item-text="sidoName" item-value="sido" label="시/도"></v-select></v-col>

            <!-- <v-col> <v-select :sidos="sidos" label="Standard"></v-select></v-col> -->
            <v-col> <v-select v-model="gugun" :items="guguns" item-text="gugunName" item-value="gugun" label="구/군"></v-select></v-col>
            <v-col> <v-select v-model="contentType" :items="contents" item-text="contentTypeName" item-value="contentType" label="컨텐츠"></v-select></v-col>
          </v-row>
        </v-container>
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
            <p class="text-center">Ops! No search results</p>
          </template>
          <div v-else v-for="(result, i) in loading ? 5 : results" :key="i" class="mb-5">
            <v-skeleton-loader class="mx-auto" type="list-item-avatar-three-line" :loading="loading" tile large>
              <v-card :to="`/watch/${result.contentId}`" class="card mb-10" tile flat>
                <v-row no-gutters>
                  <v-col cols="5" sm="3" md="3" lg="3">
                    <v-img v-if="result.firstImage" class="align-center" :src="`${result.firstImage}`" :alt="`${result.title} avatar`" max-height="200">
                    </v-img>
                    <v-img v-else class="align-center" :src="require(`@/assets/logo.png`)" :alt="`${result.title} avatar`" max-height="200"> </v-img>
                  </v-col>
                  <v-col cols="7" sm="7" md="8" lg="8">
                    <div class="ml-2">
                      <v-card-title class="pl-2 pt-0 subtitle-1 font-weight-bold">
                        {{ result.title }}
                      </v-card-title>

                      <v-card-subtitle class="pl-2 pt-2 pb-0" style="line-height: 1.2">
                        {{ result.title }}<br />
                        {{ result.readcount }}
                        views<v-icon>mdi-circle-small</v-icon>6 hours ago
                      </v-card-subtitle>
                      <v-card-subtitle class="pl-2 pt-2 pb-0">
                        {{ result.addr1 }}
                      </v-card-subtitle>
                    </div>
                  </v-col>
                </v-row>
              </v-card>
            </v-skeleton-loader>
          </div>
          <infinite-loading :identifier="infiniteId" @infinite="getSearchResults">
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
import InfiniteLoading from "vue-infinite-loading";
import { mapGetters } from "vuex";
import SearchService from "@/services/SearchService";

export default {
  data: () => ({
    errored: false,
    loading: true,
    loaded: false,
    page: 1,
    results: [],
    searchText: "",
    infiniteId: +new Date(),
    sidos: [],
    guguns: [],
    contents: [],
    sido: "",
    gugun: "",
    contentType: "",
  }),
  computed: {
    ...mapGetters(["getUrl"]),
  },
  methods: {
    async getSearchResults($state) {
      this.errored = false;
      if (!this.loaded) {
        this.loading = true;
      }
      const params = {
        page: this.page,
        searchText: this.searchText,
        sido: this.sido,
        gugun: this.gugun,
        contentType: this.contentType,
      };
      const results = await SearchService.search(params)
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
    async getSidos() {
      const sidos = await SearchService.getSidos().catch((err) => {
        console.log(err);
      });

      if (!sidos) return;

      // this.sidos = [{ sidoName: "선택 X", sido: "" }];
      this.sidos = [];
      this.sidos = sidos.data.data;
      this.sidos.unshift({ sidoName: "시/도", sido: "" });
    },

    async getGuguns(sido) {
      const guguns = await SearchService.getGuguns(sido).catch((err) => {
        console.log(err);
      });
      if (!guguns) return;
      this.guguns = guguns.data.data;

      this.guguns.unshift({ gugunName: "구/군", gugun: "" });
    },
    async getContents() {
      const contents = await SearchService.getContents().catch((err) => {
        console.log(err);
      });

      if (!contents) return;

      this.contents = contents.data.data;
      this.contents.unshift({ contentTypeName: "컨텐츠", contentType: "" });
    },
    clearResult() {
      this.page = 1;
      this.results = [];
      this.getSearchResults();
    },
  },
  components: {
    InfiniteLoading,
  },
  beforeRouteUpdate(to, from, next) {
    // console.log(to.query['search-query'])
    if (to.query["search-query"] === "") return;
    this.searchText = to.query["search-query"];
    this.searchParams = to.query.searchParams;
    this.page = 1;
    this.results = [];
    this.infiniteId += 1;
    next();
  },
  mounted() {
    this.searchText = this.$route.query["search-query"];
    this.searchParams = this.$route.query.searchParams;

    this.getSidos();
    this.getContents();
  },
  watch: {
    sido() {
      this.getGuguns(this.sido);
      this.gugun = "";
      this.clearResult();
    },
    content() {
      this.clearResult();
    },
    gugun() {
      if (this.gugun != "") {
        this.clearResult();
      }
    },
  },
};
</script>

<style></style>
