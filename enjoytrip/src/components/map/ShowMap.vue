<template>
  <div id="map" style="width:100%;height:350px;"></div>
</template>

<script>
export default {
  name: 'ShowMap',
  props: ['attraction'],
  data() {
    return {
      map: null,
      mapLoading: false,
    };
  },
  mounted() {
    if (window.kakao) {
      this.loadMap();
    } else {
      this.loadScript();
    }
  },
  methods: {
    loadScript() {
      const script = document.createElement('script');
      script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=5cbe260f12f7558c41e541afc9525bf6&autoload=false';
      script.onload = () => window.kakao.maps.load(this.loadMap);
      this.mapLoading = true;
      document.head.appendChild(script);
    },
    loadMap() {
      const mapContainer = document.getElementById('map'); // 지도를 표시할 div
      const mapOption = {
        center: new window.kakao.maps.LatLng(this.attraction.latitude, this.attraction.longitude), // 지도의 중심좌표
        level: 3, // 지도의 확대 레벨
      };

      this.map = new window.kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
      this.loadMaker();
    },
    loadMaker() {
      const markerPosition = new window.kakao.maps.LatLng(this.attraction.latitude, this.attraction.longitude);

      // 마커를 생성합니다
      const marker = new window.kakao.maps.Marker({
        position: markerPosition,
      });

      // 마커가 지도 위에 표시되도록 설정합니다
      marker.setMap(this.map);

      const iwContent = `
      <div style="padding:5px;font-weight:bold;text-align:center;height:100%;width:240px">${this.attraction.title} <br>
        <a href="https://map.kakao.com/link/map/${this.attraction.title},${this.attraction.latitude}, ${this.attraction.longitude}" style="color:gray;text-decoration: none; font-size:12px;" target="_blank">큰지도보기</a>
        <a href="https://map.kakao.com/link/to/${this.attraction.title},${this.attraction.latitude}, ${this.attraction.longitude}" style="color:gray;text-decoration: none; font-size:12px;" target="_blank">길찾기</a>
      </div>`; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다

      const iwPosition = new window.kakao.maps.LatLng(this.attraction.latitude, this.attraction.longitude); //인포윈도우 표시 위치입니다

      // 인포윈도우를 생성합니다
      const infowindow = new window.kakao.maps.InfoWindow({
        position: iwPosition,
        content: iwContent,
      });

      // 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
      infowindow.open(this.map, marker);
    },

    setCenter() {
      // 이동할 위도 경도 위치를 생성합니다
      var moveLatLon = new window.kakao.maps.LatLng(this.attraction.latitude, this.attraction.longitude);

      // 지도 중심을 이동 시킵니다
      this.map.setCenter(moveLatLon);
      this.loadMaker();
    },
  },
  watch: {
    attraction: function() {
      this.setCenter();
    },
  },
};
</script>

<style scoped></style>
