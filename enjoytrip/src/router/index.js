import Vue from "vue";
import VueRouter from "vue-router";
import NavBar from "@/components/NavBar.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    components: {
      NavBar,
      default: () => import(/* webpackChunkName: "about" */ "../views/Home.vue"),
    },
  },
  {
    path: "/library",
    name: "Library",
    components: {
      NavBar,
      default: () => import(/* webpackChunkName: "about" */ "../views/Library.vue"),
    },
    meta: { requiresAuth: true },
  },
  {
    path: "/liked",
    name: "LikedVideos",
    components: {
      NavBar,
      default: () => import(/* webpackChunkName: "about" */ "../views/LikedVideo.vue"),
    },
    meta: { requiresAuth: true },
  },
  {
    path: "/signin",
    name: "SignIn",
    component: () => import(/* webpackChunkName: "signin" */ "../views/Auth/SignIn.vue"),
    meta: { requiresVisitor: true },
  },
  {
    path: "/signup",
    name: "SignUp",
    component: () => import(/* webpackChunkName: "signup" */ "../views/Auth/SignUp.vue"),
    meta: { requiresVisitor: true },
  },
  {
    path: "/trending",
    name: "Trending",
    components: {
      NavBar,
      default: () => import(/* webpackChunkName: "trending" */ "../views/Trending.vue"),
    },
  },

  {
    path: "/watch/:id",
    name: "Watch",
    components: {
      NavBar,
      default: () => import(/* webpackChunkName: "video" */ "../views/Watch.vue"),
    },
  },
  {
    path: "/popular",
    name: "Popular",
    components: {
      NavBar,
      default: () => import(/* webpackChunkName: "video" */ "../views/Popular.vue"),
    },
  },
  {
    path: "/popularDetail/:id",
    name: "PopularDetail",
    components: {
      NavBar,
      default: () => import(/* webpackChunkName: "video" */ "../views/PopularDetail.vue"),
    },
  },
  {
    path: "/history",
    name: "History",
    components: {
      NavBar,
      default: () => import(/* webpackChunkName: "video" */ "../views/History.vue"),
    },
    meta: { requiresAuth: true },
  },
  {
    path: "/search",
    name: "Search",
    components: {
      NavBar,
      default: () => import(/* webpackChunkName: "video" */ "../views/Search.vue"),
    },
  },
  {
    path: "/nearby/:id",
    name: "Nearby",
    components: {
      NavBar,
      default: () => import(/* webpackChunkName: "video" */ "../views/Nearby.vue"),
    },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  const loggedIn = localStorage.getItem("user");

  if (to.matched.some((record) => record.meta.requiresAuth) && !loggedIn) {
    alert("로그인이 필요한 서비스 입니다.");
    next("/");
  } else if (to.matched.some((record) => record.meta.requiresVisitor) && loggedIn) {
    next("/");
  } else {
    next();
  }
});

export default router;
