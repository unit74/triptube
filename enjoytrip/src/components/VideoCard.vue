<template>
  <v-card class="content-bg card mx-auto" :max-width="card.maxWidth" flat tile router :to="`/watch/${video.contentId}`">
    <v-img v-if="video.firstImage" :src="`${video.firstImage}`" max-width="400" max-height="200" style="border-radius: 5%"></v-img>
    <v-img v-else :src="require(`@/assets/logo.png`)" max-width="400" max-height="200" style="border-radius: 5%"></v-img>
    <v-row no-gutters>
      <v-col cols="2" v-if="card.type != 'noAvatar'">
        <v-list-item class="pl-0 pt-3" router :to="`/Watch/${video.contentId}`">
          <v-list-item-avatar>
            <!-- <v-img v-if="channel.photoUrl !== 'no-photo.jpg'" class="elevation-6" :src="`${url}/uploads/avatars/${channel.photoUrl}`"></v-img> -->
            <v-img v-if="video.firstImage" class="elevation-6" :src="`${video.firstImage2}`"></v-img>

            <v-avatar v-else color="red">
              <span class="white--text headline "> {{ video.title.split("")[0].toUpperCase() }}</span>
            </v-avatar>
          </v-list-item-avatar>
        </v-list-item>
      </v-col>
      <v-col>
        <v-card-title class="pl-2 pt-3 subtitle-1 font-weight-bold" style="line-height: 1.2rem">
          {{ video.title }}
        </v-card-title>

        <v-card-subtitle class="pl-2 pb-0">
          {{ video.addr1 }}
        </v-card-subtitle>
        <v-card-subtitle class="pl-2 pt-0"> {{ video.readcount }} views<v-icon>mdi-circle-small</v-icon>{{ dateFormatter(video.createdAt) }} </v-card-subtitle>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import moment from "moment";
export default {
  name: "VideoCard",
  props: {
    video: {
      type: Object,
      required: true,
    },
    // channel: {
    //   type: Object,
    //   required: true,
    // },
    card: Object,
  },
  data() {
    return {
      url: process.env.VUE_APP_URL,
    };
  },
  methods: {
    dateFormatter(date) {
      return moment(date).fromNow();
    },
  },
};
</script>

<style></style>
