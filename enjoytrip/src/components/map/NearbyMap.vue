v-container<template>
  <div id="map" style="width:100%;height:800px;"></div>
</template>

<script>
export default {
  name: 'NearbyMap',
  props: ['attraction', 'attractions'],
  data() {
    return {
      map: null,
      mapLoading: false,
      clickWait: true,
      selectComent: 0,
    };
  },
  created() {
    this.loadScript();
  },
  // mounted() {
  //   if (window.kakao) {
  //     this.loadMapInit();
  //   } else {
  //     this.loadScript();
  //     this.loadMapInit();
  //   }
  // },
  methods: {
    loadScript() {
      const script = document.createElement('script');
      script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=5cbe260f12f7558c41e541afc9525bf6&autoload=false';
      script.onload = () => window.kakao.maps.load(this.loadMapInit);
      this.mapLoading = true;
      document.head.appendChild(script);
    },
    loadMapInit() {
      const mapContainer = document.getElementById('map'); // 지도를 표시할 div
      const mapOption = {
        center: new window.kakao.maps.LatLng(this.attraction.latitude, this.attraction.longitude), // 지도의 중심좌표
        level: 5, // 지도의 확대 레벨
      };

      this.map = new window.kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
      this.loadMakers();
    },

    loadMakers() {
      let positions = [];

      this.attractions.forEach((element) => {
        positions.push({
          content: `<div style="padding:5px;font-weight:bold;text-align:center;height:100%;width:240px">
                      ${element.title}

                    </div>`,
          latlng: new window.kakao.maps.LatLng(element.latitude, element.longitude),
          contentId: element.contentId,
        });
      });

      for (var i = 0; i < positions.length; i++) {
        // 마커를 생성합니다
        var marker = new window.kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: positions[i].latlng, // 마커의 위치
          title: positions[i].contentId,
        });

        // 마커에 표시할 인포윈도우를 생성합니다
        var infowindow = new window.kakao.maps.InfoWindow({
          content: positions[i].content, // 인포윈도우에 표시할 내용
        });

        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        // 이벤트 리스너로는 클로저를 만들어 등록합니다
        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다

        // const contentId = positions[i].contentId;

        window.kakao.maps.event.addListener(marker, 'mouseover', this.makeOverListener(this.map, marker, infowindow));
        window.kakao.maps.event.addListener(marker, 'mouseout', this.makeOutListener(infowindow));
        // window.kakao.maps.event.addListener(marker, "click", function() {
        //   console.log(this.Gb);
        //   console.log(this.test);
        //   //this.movePage(marker.Gb);
        // });
      }
    },
    // movePage(contentId) {
    //   this.$router.push(`watch/${contentId}`);
    // },
    setCenter() {
      // 이동할 위도 경도 위치를 생성합니다
      var moveLatLon = new window.kakao.maps.LatLng(this.attraction.latitude, this.attraction.longitude);

      // 지도 중심을 이동 시킵니다
      this.map.setCenter(moveLatLon);
      this.loadMaker();
    },
    makeOverListener(map, marker, infowindow) {
      return function() {
        infowindow.open(map, marker);
      };
    },
    makeOutListener(infowindow) {
      return function() {
        infowindow.close();
      };
    },
  },

  watch: {
    attractions: function() {
      this.loadMakers();
    },
  },
};
</script>

<style scoped></style>
