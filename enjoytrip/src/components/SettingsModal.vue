<template>
  <div>
    <v-dialog v-model="dialog" persistent transition="fab-transition" max-width="800" id="settings">
      <v-card tile>
        <div class="d-flex justify-space-between mb-5" id="modal-header">
          <v-card-title class="py-3">Settings</v-card-title>

          <v-btn icon text @click="closeModal">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </div>

        <v-card-text>
          <v-row>
            <v-col order="last" order-sm="last" order-md="first" order-lg="first" order-xl="first" cols="12" sm="12" md="8" lg="8" class="pt-0">
              <h3 class="mb-6 mt-0 pt-0">Personal Information</h3>
              <ValidationObserver ref="personalInfoForm" v-slot="{ handleSubmit, reset }">
                <form @submit.prevent="handleSubmit(submit)" @reset.prevent="reset">
                  <ValidationProvider v-slot="{ errors }" name="Name" rules="required|min:3">
                    <v-text-field v-model="formData.name" :error-messages="errors" label="Name" filled dense></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider v-slot="{ errors }" name="email" rules="required|email">
                    <v-text-field v-model="formData.email" readonly :error-messages="errors" label="Email" filled dense></v-text-field>
                  </ValidationProvider>

                  <v-btn :loading="loading.personalInfo" type="submit" class="primary " depressed>Submit</v-btn>
                </form>
              </ValidationObserver>

              <h3 class="mb-6 mt-6">Change Password</h3>
              <ValidationObserver ref="passwordForm" v-slot="{ handleSubmit, reset }">
                <form @submit.prevent="handleSubmit(submitPassword)" @reset.prevent="reset">
                  <ValidationProvider v-slot="{ errors }" name="Current password" rules="required|min:5">
                    <v-text-field
                      v-model="formData.currentPassword"
                      :append-icon="showCurrentPassword ? 'mdi-eye' : 'mdi-eye-off'"
                      :error-messages="errors"
                      :type="showCurrentPassword ? 'text' : 'password'"
                      name="Current password"
                      label="Current Password"
                      filled
                      dense
                      hint="At least 5 characters"
                      class="input-group--focused"
                      @click:append="showCurrentPassword = !showCurrentPassword"
                    ></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider v-slot="{ errors }" name="New password" rules="required|min:5">
                    <v-text-field
                      v-model="formData.newPassword"
                      :append-icon="showNewPassword ? 'mdi-eye' : 'mdi-eye-off'"
                      :error-messages="errors"
                      :type="showNewPassword ? 'text' : 'password'"
                      name="New password"
                      label="New Password"
                      filled
                      dense
                      hint="At least 5 characters"
                      class="input-group--focused"
                      @click:append="showNewPassword = !showNewPassword"
                    ></v-text-field>
                  </ValidationProvider>

                  <v-btn :loading="loading.password" type="submit" class="primary" depressed>Submit</v-btn>
                </form>
              </ValidationObserver>
            </v-col>
            <v-col order-sm="1" cols="12" sm="12" md="4" lg="4" class="text-center">
              <v-responsive width="230" class="mx-auto" style="border-radius: 50%;">
                <div v-if="currentUser.profilePhotoUrl == 'no-photo.jpg'" class="px-12" id="settings-image-placeholder">
                  <v-icon>mdi-image-plus</v-icon>
                </div>
                <v-img v-else-if="this.imageUrl" height="220" :src="this.imageUrl"></v-img>
                <v-img v-else height="220" :src="defaultProfileUrl + currentUser.profilePhotoUrl"></v-img>
              </v-responsive>
              <v-file-input
                :rules="rules"
                accept="image/png, image/jpeg"
                placeholder="Pick an avatar"
                prepend-icon="mdi-camera"
                label="Avatar"
                @change="selectImg"
              ></v-file-input>
              <v-btn @click="uploadUserProfile" :loading="loading.profileImg">submit</v-btn>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-dialog>
    <v-snackbar v-model="snackbar">
      Comment {{ snackbarMessage }}
      <v-btn color="white" text @click="snackbar = false" icon>
        <v-icon>mdi-close-circle</v-icon>
      </v-btn>
    </v-snackbar>
  </div>
</template>

<script>
import AuthenticationService from '@/services/AuthenticationService';
//import myUpload from "vue-image-crop-upload";
import { mapGetters } from 'vuex';

export default {
  name: 'SettingsModal',
  props: ['openDialog'],
  data: function() {
    return {
      snackbar: false,
      snackbarMessage: '',
      image: null,
      imageUrl: null,
      rules: [(value) => !value || value.size < 1000000 || 'Attraction size should be less than 1 MB!'],
      showCurrentPassword: false,
      showNewPassword: false,
      valid: false,
      interval: {},
      show: false,
      categories: ['People', 'Technology', 'Fashion'],
      visibilty: ['Public', 'Private'],
      formData: {
        name: this.$store.getters.currentUser.name,
        email: this.$store.getters.currentUser.email,
        currentPassword: '',
        newPassword: '',
      },
      loading: {
        personalInfo: false,
        password: false,
        profileImg: false,
      },
    };
  },
  computed: {
    dialog() {
      return this.openDialog;
    },
    ...mapGetters(['currentUser', 'isAuthenticated', 'defaultProfileUrl']),
  },
  methods: {
    async submit() {
      if (this.loading.personalInfo) return;
      this.loading.personalInfo = true;

      const user = await AuthenticationService.updateUserDetails({
        name: this.formData.name,
      })
        .catch((err) => {
          console.log(err);
          this.snackbar = true;
          this.snackbarMessage = '이름 입력이 잘못됐습니다. 다시 입력해주세요';
        })
        .finally(() => (this.loading.personalInfo = false));

      if (!user.data.success) return;
      if (this.formData.name !== this.currentUser.name) {
        const getUser = JSON.parse(localStorage.getItem('user'));
        getUser.name = this.formData.name;
        this.$store.dispatch('updateUserInfo', getUser);
      }

      this.closeModal();
      this.$router.go();
    },
    async submitPassword() {
      if (this.loading.password) return;

      this.loading.password = true;

      const user = await AuthenticationService.updatePassword({
        currentPassword: this.formData.currentPassword,
        newPassword: this.formData.newPassword,
      })
        .catch((err) => {
          this.loading.password = false;
          const errors = err.response.data.error;

          this.$refs.passwordForm.setErrors({
            'Current password': errors.find((error) => {
              return error.field === 'currentPassword';
            })
              ? ['Current password is incorrect']
              : null,
            'New password': errors.find((error) => {
              return error.field === 'newPassword';
            })
              ? errors.find((error) => {
                  return error.field === 'newPassword';
                }).message
              : null,
          });
        })
        .finally(() => (this.loading.password = false));
      if (!user.data.success) {
        this.snackbar = true;
        this.snackbarMessage = '현재 비밀번호가 틀렸습니다.';
        return;
      }

      this.formData.currentPassword = '';
      this.formData.newPassword = '';

      this.snackbar = true;
      this.snackbarMessage = '다시 로그인이 필요합니다.';

      this.closeModal();
      this.$store.dispatch('signOut');
      setTimeout(() => {
        this.$router.push('/signin');
      }, 500);
    },
    closeModal() {
      this.$emit('closeDialog');
    },

    toggleShow() {
      this.show = !this.show;
    },

    selectImg(file) {
      this.image = file;
      this.imageUrl = URL.createObjectURL(file);
    },

    async uploadUserProfile() {
      if (this.loading.profileImg) return;
      if (this.image == null) {
        this.snackbar = true;
        this.snackbarMessage = '이미지를 등록해주세요';
        return;
      }
      this.loading.profileImg = true;

      const formData = new FormData();
      formData.append('image', this.image);
      const result = await AuthenticationService.uploadUserProfile(formData)
        .catch((err) => {
          console.log(err);
          this.snackbar = true;
          this.snackbarMessage = '이미지가 너무 크거나 손상되었습니다. 이미지를 다시 확인해주세요 ';
        })
        .finally(() => (this.loading.profileImg = false));

      if (!result.data.success) {
        console.log('???');
        this.snackbar = true;
        this.snackbarMessage = '이미지를 등록에 문제가 생겼습니다. 다시 시도해 주세요';
        return;
      }
      this.closeModal();
      this.$router.go();
    },
  },
  components: {
    // myUpload,
  },
};
</script>

<style lang="scss">
#modal-header {
  border-bottom: 1px solid rgb(238, 232, 232);
}

#settings-image-placeholder {
  padding-top: 7em;
  padding-bottom: 7em;
  border: 2px dashed rgb(209, 209, 209);
  border-radius: 50%;
}

@media (max-width: 700px) {
  .vue-image-crop-upload {
    .vicp-wrap {
      width: 95%;
      height: 550px;
      .vicp-step2 .vicp-crop {
        .vicp-crop-left {
          float: none;

          .vicp-range {
            margin: auto;
            margin-top: 30px;
          }

          & * {
            margin-left: auto;
            margin-right: auto;
          }
        }
        .vicp-crop-right {
          float: none;
          margin-top: 30px;
          .vicp-preview .vicp-preview-item {
            display: inline-block;
            float: none;
          }
          & * {
            // margin-left: auto;
            // margin-right: auto;
          }
        }
      }
    }
  }
}
</style>
