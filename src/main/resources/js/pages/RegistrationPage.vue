<template>
    <div>
        <v-container>
            <div v-if="profile">
                Вы уже зарегистрированы
            </div>
            <div v-else>
                <v-card
                        class="mx-auto mt-lg-10 white"
                        outlined
                        max-width="700">
                    <div :class="!$vuetify.breakpoint.xs ? 'reg-title' : 'reg-title-mobile'">Регистрация
                    </div>
                    <div :class="!$vuetify.breakpoint.xs ? 'reg-form' : 'reg-form-mobile'">
                        <v-text-field label="E-mail"
                                      color="#29F245"
                                      :error-messages="emailErrorMessage"
                                      :rules="formRules"
                                      type="email" v-model="email"/>

                        <v-text-field label="Логин"
                                      color="#29F245"
                                      :error-messages="usernameErrorMessage"
                                      :rules="formRules"
                                      type="text" v-model="username"/>

                        <v-text-field label="Пароль"
                                      color="#29F245"
                                      :error-messages="passwordErrorMessage"
                                      :rules="formRules"
                                      type="password" v-model="password"/>

                        <v-text-field label="Подтвердите пароль"
                                      color="#29F245"
                                      :error-messages="passwordErrorMessage"
                                      :rules="formRules"
                                      type="password" v-model="password2"/>
                        <div class="pt-2">
                            <v-btn block color="#B7FAC1" @click="save">Зарегистрироваться</v-btn>
                        </div>
                    </div>
                    <div class="reg-text">
                    <span>
                        Уже есть аккаунт?
                        <a style="color: #29F245; font-weight: bolder; text-decoration: underline"
                           @click="goToLogin">Войдите</a></span>
                    </div>
                </v-card>
            </div>
        </v-container>
    </div>

</template>

<script>
    import {mapState} from "vuex"
    import {goToLogin} from "functions/routerPushMethods";

    export default {
        name: "RegistrationPage",
        props: ['messageAttr'],
        data: () => ({
            username: '',
            email: '',
            password: '',
            password2: '',
            usernameError: '',
            passwordError: '',
            emailError: '',
            successRegistration: 'Вы успешно зарегистрированы, можете войти в аккаунт',
            formRules: [
                v => !!v || 'Поле не может быть пустым',
            ],
        }),
        watch: {
            messageAttr(newVal, oldVal) {
                this.username = newVal.username
                this.email = newVal.email
                this.password = newVal.password
                this.password2 = newVal.password2
            },
        },
        computed: {
            emailErrorMessage() {

                if (this.email && !this.validEmail()) {
                    return 'Некорректно введен email'
                }

                if (this.emailError) {
                    return this.emailError
                }

            },
            usernameErrorMessage() {

                if (this.username && !this.minUsernameLength()) {
                    return 'Минимальная длина имени пользователя 2 символа'
                }

                if (this.username && !this.maxUsernameLength()) {
                    return 'Максимальная длина имени пользователя 20 символов'
                }

                if (this.username && !this.validUsername()) {
                    return 'Имя пользователя может состоять только из латинских букв и цифр'
                }

                if (this.username && this.userExists()) {
                    return 'Пользователь с таким именем уже существует'
                }

                if (this.usernameError) {
                    return this.usernameError
                }
            },
            passwordErrorMessage() {

                if (this.password && !this.minPasswordLength()) {
                    return 'Минимальная длина пароля 6 символом'
                }

                if (this.password && !this.maxPasswordLength()) {
                    return 'Максимальная длина пароля 200 символов'
                }

                if (this.password && this.password2 && !this.matchPassword()) {
                    return 'Пароли не совпадают'
                }

                if (this.passwordError) {
                    return this.passwordError
                }
            },
            ...mapState(['profile', 'usernamesList'])
        },
        methods: {
            save() {

                this.validEmptyForm()

                if (this.validForm()) {
                    const form = {
                        "username": this.username,
                        "email": this.email,
                        "password": this.password,
                        "password2": this.password2
                    }
                    this.$http.post('registration/registerUser', form).then(response => {
                        if (!response.data) {
                            this.$router.push({name: 'login', params: {registrationMessage: this.successRegistration}})
                        } else {
                            this.passwordError = response.data.passwordError
                            this.usernameError = response.data.usernameError
                            this.emailError = response.data.emailError
                        }
                    });
                }
            },

            goToLogin,

            minPasswordLength() {
                return this.password.length >= 6
            },

            maxPasswordLength() {
                return this.password.length <= 200
            },

            minUsernameLength() {
                return this.username.length > 1
            },

            maxUsernameLength() {
                return this.username.length <= 20
            },

            validUsername() {
                const USERNAME_REGEX = /^[a-zA-Z0-9]+$/
                return USERNAME_REGEX.test(this.username)
            },

            validEmail() {
                const EMAIL_REGEX = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                return EMAIL_REGEX.test(this.email)
            },

            matchPassword() {
                return this.password === this.password2
            },

            userExists() {
                return this.usernamesList.includes(this.username)
            },

            validForm() {
                return this.password && this.username && this.email && this.password2 &&
                    this.minPasswordLength() && this.maxPasswordLength() &&
                    this.minUsernameLength() && this.maxUsernameLength() &&
                    this.validUsername() && this.validEmail() && this.matchPassword() &&
                    !this.userExists();
            },

            validEmptyForm() {
                if (this.password === '' || this.password2 === '') this.passwordError = 'Поле не может быть пустым'
                if (this.username === '') this.usernameError = 'Поле не может быть пустым'
                if (this.email === '') this.emailError = 'Поле не может быть пустым'
            },
        },
        created() {
            if (this.profile) {
                this.$router.push('/')
            }
        }
    }

</script>

<style scoped>
    .reg-title {
        padding-top: 110px;
        text-align: center;
        font-weight: bold;
        font-size: 24px;
        text-transform: uppercase;
    }

    .reg-title-mobile {
        padding-top: 60px;
        text-align: center;
        font-weight: bold;
        font-size: 24px;
        text-transform: uppercase;
    }

    .reg-form {
        padding: 2.86% 28.6%;
    }

    .reg-form-mobile {
        padding: 20px;
    }

    .reg-text {
        font-size: 14px;
        color: #808080;
        text-align: center;
        font-weight: 300;
        padding-bottom: 60px;
    }
</style>