import Api from '@/services/Api';

export default {
  getAll(data, params) {
    console.log(data, params);
    return Api().get(`/api/v1/public/trips/attractions`);
  },
  getHotplace(page) {
    return Api().get(`/api/v1/public/trips/attractions/trends`, { params: page });
  },
  async getById(id) {
    const token = localStorage.getItem('token');
    return Api().get(`/api/v1/public/trips/attractions/${id}`, {
      headers: { Authorization: token },
    });
  },
  getNearbyAttractions(contentId, page) {
    return Api().get(`/api/v1/public/trips/attractions/${contentId}/nearby`, { params: page });
  },
  getTop10LikedAttractions(contentType) {
    return Api().get(`/api/v1/public/trips/attractions/hots`, { params: contentType });
  },
};
