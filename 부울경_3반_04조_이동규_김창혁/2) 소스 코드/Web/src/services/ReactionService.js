import Api from '@/services/Api';

export default {
  checkReaction(contentId) {
    return Api().get(`/api/v1/private/trips/reactions/${contentId}`);
  },
  createReaction(params) {
    console.log(params);
    return Api().post('/api/v1/private/trips/reactions', {
      contentId: params.contentId,
      type: params.type,
    });
  },
  deleteReaction(contentId) {
    return Api().delete(`/api/v1/private/trips/reactions/${contentId}`);
  },
  getLikedAttractions(page) {
    return Api().get('/api/v1/private/trips/attractions/likes', {
      params: {
        page: page,
      },
    });
  },
};
