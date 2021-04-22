<template>
    <div>
        <v-container>
            <div>
                <v-card
                        class="mx-auto mt-lg-10 white"
                        outlined
                        max-width="700">
                    <div :class="!$vuetify.breakpoint.xs ? 'profile-title' : 'profile-title-mobile'">
                        {{profile.username}}
                    </div>
                    <div :class="!$vuetify.breakpoint.xs ? 'profile-form' : 'profile-form-mobile'">
                        <v-text-field
                                v-model="email"
                                :rules="formRules"
                                :error-messages="emailErrorMessage"
                                label="Почта"
                                color="#29F245"
                                type="email"/>
                        <v-text-field
                                v-model="password"
                                label="Пароль"
                                color="#29F245"
                                :error-messages="passwordErrorMessage"
                                type="password"/>
                        <div class="pt-2">
                            <v-btn block color="#B7FAC1" @click="save" type="submit">Изменить</v-btn>
                        </div>
                    </div>
                    <div :class="!$vuetify.breakpoint.xs ? 'successUpdate-text' : 'successUpdate-text-mobile'">
                        {{successUpdateMessage}}
                    </div>
                </v-card>
            </div>
        </v-container>
    </div>
</template>

<script>
    import {mapState, mapMutations} from "vuex";

    export default {
        name: "ProfilePage",
        data() {
            return {
                email: '',
                password: '',
                passwordError: '',
                emailError: '',
                successUpdateMessage: '',
                formRules: [
                    v => !!v || 'Поле не может быть пустым',
                ],
            }
        },
        created() {
            this.email = this.profile.email
        },
        watch: {
            messageAttr(newVal, oldVal) {
                this.email = newVal.email
                this.password = newVal.password
            },
        },
        computed: {
            ...mapState(['profile']),
            emailErrorMessage() {

                if (this.email && !this.validEmail()) {
                    return 'Некорректно введен email'
                }

                if (this.emailError && !this.emailError) {
                    return this.emailError
                }

            },
            passwordErrorMessage() {

                if (this.password && !this.minPasswordLength()) {
                    return 'Минимальная длина пароля 6 символом'
                }

                if (this.password && !this.maxPasswordLength()) {
                    return 'Максимальная длина пароля 200 символов'
                }

                if (this.passwordError && !this.password) {
                    return this.passwordError
                }
            },
        },
        methods: {
            ...mapMutations(['updateProfileMutation']),
            validEmail() {
                const EMAIL_REGEX = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                return EMAIL_REGEX.test(this.email)
            },
            minPasswordLength() {
                return this.password.length >= 6
            },

            maxPasswordLength() {
                return this.password.length <= 200
            },
            validForm() {
                return this.password && this.email &&
                    this.minPasswordLength() && this.maxPasswordLength() &&
                    this.validEmail()
            },
            validEmptyForm() {
                if (this.password === '') this.passwordError = 'Поле не может быть пустым'
                if (this.email === '') this.emailError = 'Поле не может быть пустым'
            },
            save() {

                this.validEmptyForm()

                if (this.validForm()) {

                    const userToChange = {
                        email: this.email,
                        password: this.password
                    }

                    this.$http.post('profile/changeProfile', userToChange).then(response => {
                       if(response.ok) {
                           this.updateProfileMutation(response.body)
                           this.successUpdateMessage = 'Ваши данные успешно изменены!'
                       }
                        }
                    )

                    this.password = ''
                }
            }
        }
    }
</script>

<style scoped>
    .profile-form {
        padding: 2.86% 28.6%;
    }

    .profile-form-mobile {
        padding: 20px;
    }

    .profile-title {
        padding-top: 120px;
        text-align: center;
        font-weight: bold;
        font-size: 24px;
        text-transform: uppercase;
    }

    .profile-title-mobile {
        padding-top: 60px;
        text-align: center;
        font-weight: bold;
        font-size: 24px;
        text-transform: uppercase;
    }
    .successUpdate-text {
        font-size: 14px;
        color: #29F245;
        text-align: center;
        margin-bottom: 80px;
    }
    .successUpdate-text-mobile {
        margin-bottom: 40px;
    }

</style>