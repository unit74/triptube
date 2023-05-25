import Api from '@/services/Api';

export default {
  checkLibraries(contentId) {
    return Api().get(`/api/v1/private/trips/libraries/${contentId}`);
  },
  createLibraries(contentId) {
    return Api().post('/api/v1/private/trips/libraries', { contentId });
  },
  deleteLibrary(libraryId) {
    return Api().delete(`/api/v1/private/trips/libraries/${libraryId}`);
  },
  getSaveAttractions(page) {
    return Api().get('/api/v1/private/trips/attractions/libraries', {
      params: {
        page,
      },
    });
  },
};
