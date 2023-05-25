<template>
  <v-card class="content-bg card mx-auto" :max-width="card.maxWidth" flat tile router :to="`/watch/${attraction.contentId}`">
    <v-img v-if="attraction.firstImage" :src="`${attraction.firstImage}`" width="400" height="200" style="border-radius: 5%;"></v-img>
    <v-img v-else :src="`${noImgUrl}`" max-width="400" contain max-height="200" style="border-radius: 5%;"></v-img>
    <v-row no-gutters>
      <v-col cols="2" v-if="card.type != 'noAvatar'">
        <v-list-item class="pl-0 pt-3" router :to="`/Watch/${attraction.contentId}`">
          <v-list-item-avatar>
            <v-img v-if="attraction.firstImage" class="elevation-6" :src="`${attraction.firstImage2}`"></v-img>

            <v-avatar v-else color="red">
              <span class="white--text headline "> {{ attraction.title.split("")[0].toUpperCase() }}</span>
            </v-avatar>
          </v-list-item-avatar>
        </v-list-item>
      </v-col>
      <v-col>
        <v-card-title class="pl-2 pt-3 subtitle-1 font-weight-bold" style="line-height: 1.2rem">
          {{ attraction.title }}
        </v-card-title>

        <v-card-subtitle class="pl-2 pb-0">
          {{ attraction.addr1 }}
        </v-card-subtitle>
        <v-card-subtitle class="pl-2 pt-0">
          {{ attraction.readcount }} views
          <span v-if="attraction.updatedAt"><v-icon>mdi-circle-small</v-icon> {{ dateFormatter(attraction.updatedAt) }}</span>
          <span v-else-if="attraction.createAt"><v-icon>mdi-circle-small</v-icon> {{ dateFormatter(attraction.createAt) }}</span>
        </v-card-subtitle>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import moment from "moment";
import { mapGetters } from "vuex";
export default {
  name: "AttractionCard",
  props: {
    attraction: {
      type: Object,
      required: true,
    },

    card: Object,
  },

  data() {
    return {
      url: process.env.VUE_APP_URL,
    };
  },
  computed: {
    ...mapGetters(["noImgUrl"]),
  },
  methods: {
    dateFormatter(date) {
      return moment(date).fromNow();
    },
  },
};
</script>

<style></style>
