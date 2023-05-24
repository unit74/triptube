<template>
  <nav id="navbar">
    <v-app-bar class="white" flat app clipped-left>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title class="font-weight-bold">
        <router-link to="/" class="black--text" style="text-decoration: none">
          <v-row align="center">
            <v-col cols="auto">
              <v-img :src="`${logoUrl}`" max-width="50"></v-img>
            </v-col>
            <v-col cols="auto">TripTube</v-col>
          </v-row>
        </router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>
      <v-text-field flat hide-details append-icon="mdi-magnify" placeholder="Search" outlined dense v-model="searchText" @click:append="search"></v-text-field>

      <v-spacer></v-spacer>

      <!-- <v-tooltip bottom>
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on"> <v-icon size="25">mdi-apps</v-icon></v-btn>
        </template>
        <span>VueTube apps</span>
      </v-tooltip> -->

      <!-- <v-tooltip bottom>
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on" class="mr-7">
            <v-icon size="25">mdi-bell</v-icon></v-btn
          >
        </template>
        <span>Notifications</span>
      </v-tooltip> -->
      <v-btn tile outlined color="blue" class="font-weight-bold" v-if="!$store.getters.isAuthenticated" router to="/signin">
        <v-icon left size="26">mdi-account-circle</v-icon> 로그인
      </v-btn>

      <v-menu offset-y left v-else>
        <template v-slot:activator="{ on }">
          <v-btn small color="red" depressed fab v-on="on" class="white--text">
            <v-avatar v-if="currentUser.profilePhotoUrl !== 'no-photo.jpg'">
              <img :src="`${defaultProfileUrl + currentUser.profilePhotoUrl}`" :alt="`${currentUser.name} avatar`" />
            </v-avatar>
            <template v-else>
              <span class="headline">
                {{ currentUser.name.split("")[0].toUpperCase() }}
              </span>
            </template>
          </v-btn>
        </template>

        <v-card>
          <v-list>
            <v-list-item>
              <v-list-item-avatar>
                <v-avatar v-if="currentUser.profilePhotoUrl !== 'no-photo.jpg'">
                  <img :src="`${defaultProfileUrl + currentUser.profilePhotoUrl}`" :alt="`${currentUser.name} avatar`" />
                </v-avatar>
                <template v-else>
                  <v-avatar color="red">
                    <span class="white--text headline "> {{ currentUser.name.split("")[0].toUpperCase() }}</span>
                  </v-avatar>
                </template>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title class="text-capitalize">{{ currentUser.name }}</v-list-item-title>
                <v-list-item-subtitle>{{ currentUser.email }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>

          <v-divider></v-divider>

          <v-list>
            <v-list-item @click="updateProfileImage()">
              <v-list-item-icon>
                <v-icon>mdi-account-box</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Your channel</v-list-item-title>
            </v-list-item>
            <v-list-item @click="settingsMoal()">
              <v-list-item-icon>
                <v-icon>mdi-youtube-studio</v-icon>
              </v-list-item-icon>
              <v-list-item-title>VueTube Studio</v-list-item-title>
            </v-list-item>
            <v-list-item @click="signOut">
              <v-list-item-icon>
                <v-icon>mdi-login-variant</v-icon>
              </v-list-item-icon>
              <v-list-item-title>Sign out</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-card>
      </v-menu>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" app :clipped="$route.name !== 'Watch'" :temporary="$route.name === 'Watch'" id="nav">
      <div tag="div" class="v-navigation-drawer__content" v-bar>
        <v-list dense nav class="py-0" tag="div">
          <v-list-item
            :class="{
              'hidden-lg-and-up': $route.name === 'Watch' ? false : true,
            }"
          >
            <v-app-bar-nav-icon @click="drawer = !drawer" class="mr-5"></v-app-bar-nav-icon>
            <v-toolbar-title class="font-weight-bold">VueTube</v-toolbar-title>
          </v-list-item>
          <v-divider class="hidden-lg-and-up"></v-divider>
          <div v-for="parentItem in items" :key="parentItem.header">
            <v-subheader v-if="parentItem.header" class="pl-3 py-4 subtitle-1 font-weight-bold text-uppercase">{{ parentItem.header }}</v-subheader>
            <v-list-item
              v-for="(item, i) in parentItem.header === 'Subscriptions' ? items[2].pages.slice(0, channelLength) : parentItem.pages"
              :key="item.title"
              class="mb-0"
              :to="parentItem.header === 'Subscriptions' ? '/channels/' + item.channelId._id : item.link"
              exact
              active-class="active-item"
            >
              <v-list-item-icon v-if="parentItem.header !== 'Subscriptions'">
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-item-icon>
              <v-list-item-avatar v-else class="mr-5">
                {{ i }}
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title class=" font-weight-medium subtitle-2">{{
                  parentItem.header === "Subscriptions" ? item.channelId.channelName : item.title
                }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <!-- <v-btn
              id="showBtn"
              @click="moreChannels"
              v-if="parentItem.header === 'Subscriptions' && isAuthenticated && items[2].length > 0"
              block
              text
              class="text-none"
            >
              <v-icon>{{ channelLength === 3 ? "mdi-chevron-down" : "mdi-chevron-up" }}</v-icon>
              {{ channelLength === 3 ? `Show ${items[2].pages.length - 3} more ` : "Show less" }}</v-btn
            > -->

            <v-divider v-if="parentItem.header !== false" class="mt-2 mb-2"></v-divider>
          </div>
        </v-list>
      </div>
    </v-navigation-drawer>
    <settings-modal :open-dialog="settingsDialog" v-on:closeDialog="settingsDialog = false" />
  </nav>
</template>

<script>
import { mapGetters } from "vuex";
import SettingsModal from "@/components/SettingsModal";

export default {
  data: () => ({
    drawer: true,
    items: [
      {
        header: null,
        pages: [
          { title: "Home", link: "/", icon: "mdi-home" },
          { title: "Top Viewed Attractions", link: "/trending", icon: "mdi-fire" },
          {
            title: "Popular Attractions",
            link: "#sd",
            icon: "mdi-podium",
          },
        ],
      },
      {
        header: "Storage",
        pages: [
          {
            title: "Library",
            link: "/subscriptions",
            icon: "mdi-package-variant",
          },
          {
            title: "History",
            link: "/history",
            icon: "mdi-history",
          },

          // {
          //   title: 'Watch later',
          //   link: '#wl',
          //   icon: 'mdi-clock'
          // },

          {
            title: "Liked Attractions",
            link: "/liked",
            icon: "mdi-thumb-up",
          },
        ],
      },
    ],
    channelLength: 0,
    searchText: "",
    settingsDialog: false,
    // user: null
  }),
  computed: {
    ...mapGetters(["currentUser", "getUrl", "isAuthenticated", "logoUrl", "defaultProfileUrl"]),
  },
  components: { SettingsModal },

  methods: {
    async search() {
      if (!this.searchText) return;
      // console.log(this.searchText == this.$route.query['search-query'])
      if (this.searchText == this.$route.query["search-query"]) return;
      // this.searchText = this.$route.query['search-query']
      // const data = {
      //   type: "search",
      //   searchText: this.searchText,
      // };

      // if (this.isAuthenticated) await HistoryService.createHistory(data).catch((err) => console.log(err));

      this.$router.push({
        name: "Search",
        query: { "search-query": this.searchText },
      });
    },
    // async getSubscribedChannels() {
    //   const channels = await SubscriptionService.getSubscribedChannels(this.currentUser._id).catch((err) => console.log(err));
    //   this.items[2].pages = channels.data.data;
    //   this.channelLength = 3;
    // },
    moreChannels() {
      if (this.channelLength === 3) this.channelLength = this.items[2].pages.length;
      else this.channelLength = 3;
    },
    signOut() {
      this.$store.dispatch("signOut");
      // this.$router.push('/')
    },
    settingsMoal() {
      this.settingsDialog = true;
    },
  },
  // beforeRouteLeave(to, from, next) {
  //   this.searchText = ''
  //   next()
  // },
  // beforeRouteUpdate(to, from, next) {
  //   if (!to.query['search-query'] === '') return
  //   this.searchText = to.query['search-query']
  //   next()
  // },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      if (!to.query["search-query"] === "") return;
      vm.searchText = to.query["search-query"];
      // vm.getSearchResults(to.query['search-query'])
    });
  },
  mounted() {
    // if (this.$route.query['search-query'])
    //   this.searchText = this.$route.query['search-query']

    //if (this.currentUser) this.getSubscribedChannels();
    // this.user = this.$store.getters.currentUser
    // console.log(this.user)
    this.drawer = this.$vuetify.breakpoint.mdAndDown ? false : true;
    // console.log(this.$route.name)
    this.drawer = this.$route.name === "Watch" ? false : this.drawer;
  },
  created() {
    this.drawer = this.$route.name === "Watch" ? false : this.drawer;
    if (!this.isAuthenticated) {
      this.items[2].header = false;
      this.items[0].pages.pop();
    }
  },
};
</script>

<style lang="scss">
.v-list-item__avatar {
  justify-content: center !important;
}
#showBtn {
  .v-btn__content {
    justify-content: flex-start;

    i {
      margin-right: 30px;
    }
  }
}
#navbar {
  .active-item {
    .v-list-item__icon {
      color: red !important;
    }
  }
  .v-navigation-drawer__border {
    width: 0 !important;
  }

  .vuebar-element {
    height: 250px;
    width: 100%;
    max-width: 500px;
    background: #dfe9fe;
  }

  .vb > .vb-dragger {
    z-index: 5;
    width: 10px;
    right: 0;
  }

  .vb > .vb-dragger > .vb-dragger-styler {
    -webkit-backface-visibility: hidden;
    backface-visibility: hidden;
    -webkit-transform: rotate3d(0, 0, 0, 0);
    transform: rotate3d(0, 0, 0, 0);
    -webkit-transition: background-color 100ms ease-out, margin 100ms ease-out, height 100ms ease-out;
    transition: background-color 100ms ease-out, margin 100ms ease-out, height 100ms ease-out;

    margin: 5px 5px 5px 0;
    border-radius: 20px;
    height: calc(100% - 10px);
    display: block;
  }

  .v-navigation-drawer__content:hover .vb > .vb-dragger > .vb-dragger-styler {
    width: 10px;
    background-color: #e0e0e0;
  }

  .vb.vb-scrolling-phantom > .vb-dragger > .vb-dragger-styler {
    background-color: rgba(48, 121, 244, 0.3);
    background-color: rgba(255, 255, 255, 0.3);
  }

  .vb > .vb-dragger:hover > .vb-dragger-styler {
    margin: 0px;
    width: 10px;
  }

  .vb.vb-dragging > .vb-dragger > .vb-dragger-styler {
    background-color: rgba(48, 121, 244, 0.5);
    margin: 0px;
    height: 100%;
  }

  .vb.vb-dragging-phantom > .vb-dragger > .vb-dragger-styler {
    background-color: rgba(48, 121, 244, 0.5);
  }
}
</style>
