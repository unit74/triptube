<template>
  <v-container fluid class="fill-height">
    <v-row>
      <v-col cols="12" xs="12" sm="6" md="5" lg="4" class="ma-auto">
        <v-card outlined :loading="loading"
          ><div class="px-8 pt-6 pb-12">
            <v-card-title class="text-center">환영합니다!</v-card-title>
            <v-card-subtitle class="mb-5"></v-card-subtitle>
            <v-card-text>
              <ValidationObserver ref="form" v-slot="{ handleSubmit, reset }">
                <form @submit.prevent="handleSubmit(signUp)" @reset.prevent="reset">
                  <ValidationProvider v-slot="{ errors }" name="Email" rules="required|email">
                    <v-text-field v-model="email" :error-messages="errors" label="Email" class="mb-3" outlined dense></v-text-field>
                  </ValidationProvider>
                  <ValidationProvider v-slot="{ errors }" name="Name" rules="required|min:3">
                    <v-text-field v-model="name" :error-messages="errors" label="Name" outlined dense></v-text-field>
                  </ValidationProvider>
                  <v-row>
                    <v-col cols="6">
                      <ValidationProvider v-slot="{ errors }" name="Password" rules="required|min:5|password:@confirm">
                        <v-text-field v-model="password" type="password" :error-messages="errors" label="Password" outlined dense></v-text-field>
                      </ValidationProvider>
                    </v-col>
                    <v-col cols="6">
                      <ValidationProvider v-slot="{ errors }" name="confirm" rules="required">
                        <v-text-field type="password" v-model="confirmPassword" :error-messages="errors" label="Confirm" outlined dense></v-text-field>
                      </ValidationProvider>
                    </v-col>
                  </v-row>
                  <div class="mt-6 d-flex justify-space-between">
                    <div>
                      <v-btn text small class="pl-0 text-capitalize" color="primary" router to="signin">이미 아이디가 있습니다.</v-btn>
                      <v-btn text small class="pl-0 text-capitalize" color="red" router to="/">돌아갈래요</v-btn>
                    </div>
                    <v-btn type="submit" class="primary" :loading="loading" depressed>회원가입</v-btn>
                  </div>
                </form>
              </ValidationObserver>
            </v-card-text>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "SignUp",
  data: () => ({
    email: "",
    name: "",
    password: "",
    confirmPassword: "",
    loading: false,
  }),
  methods: {
    async signUp() {
      this.loading = true;

      const data = await this.$store
        .dispatch("signUp", {
          email: this.email,
          name: this.name,
          password: this.password,
        })
        .catch((err) => {
          this.loading = false;
          const errors = err.response.data.error;

          this.$refs.form.setErrors({
            Email: errors.find((error) => {
              return error.field === "email";
            })
              ? ["This email is already taken"]
              : null,
            Name: errors.find((error) => {
              return error.field === "name";
            })
              ? ["This channel name is already taken"]
              : null,
          });
        });

      if (!data) return;

      // const user = await this.$store
      //   .dispatch("getCurrentUser", data.token)
      //   .catch((err) => console.log(err));

      const user = { name: data.data.name, profilePhotoUrl: data.data.profilePhotoUrl };
      if (!user) return;
      this.loading = false;
      this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style></style>
