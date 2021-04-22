<template>
    <div>
        <v-container>
            <div v-if="profile">
                <div>
                    Вы уже авторизовались.
                </div>
            </div>
            <div v-else>
                <v-card
                        class="mx-auto mt-lg-10 white"
                        outlined
                        max-width="700"
                >
                    <div :class="!$vuetify.breakpoint.xs ? 'log-title' : 'log-title-mobile'">ВХОД
                    </div>
                    <div :class="!$vuetify.breakpoint.xs ? 'log-form' : 'log-form-mobile'">
                        <form action="/login" method="post">
                            <v-text-field
                                    :rules="rules"
                                    label="Логин"
                                    color="#29F245"
                                    type="text"
                                    name="username"
                                    hide-details="auto"
                            ></v-text-field>
                            <v-text-field label="Пароль"
                                          :rules="rules"
                                          color="#29F245"
                                          type="password"
                                          name="password"></v-text-field>
                            <div class="pt-2">
                                <v-btn block color="#B7FAC1" type="submit">Войти</v-btn>
                            </div>
                        </form>
                    </div>
                    <div class="log-text">
                    <span v-if="$route.path==='/login-error'" class="error--text text-center">
                        Данного аккаунта не существует <br>
                        Введите другой логин или <a class="error--text text-center"
                                                    style="text-decoration: underline; font-weight: bold"
                                                    @click="goToRegistration">Зарегистрируйтесь</a>
                    </span>
                        <span v-else-if="!registrationMessage">
                        Нет аккаунта?
                        <a style="color: #29F245; font-weight: bolder; text-decoration: underline"
                           @click="goToRegistration">Зарегистрируйтесь</a></span>
                        <span v-else style="font-weight:bolder; color: #29F245">{{registrationMessage}}</span>
                    </div>
                </v-card>
            </div>
        </v-container>
    </div>
</template>

<script>
    import {mapState} from "vuex"
    import {goToRegistration} from "functions/routerPushMethods"

    export default {
        name: "LoginPage",
        computed: mapState(['profile']),
        methods: {
            goToRegistration
        },
        data() {
            return {
                registrationMessage: this.$route.params.registrationMessage,
                rules: [
                    value => !!value || 'Поле не может быть пустым'
                ],
            }
        },
        created() {
            if (this.profile) {
                this.$router.push('/')
            }
        }
    }
</script>

<style scoped>
    .log-form {
        padding: 2.86% 28.6%;
    }

    .log-form-mobile {
        padding: 20px;
    }

    .log-text {
        font-size: 14px;
        color: #808080;
        text-align: center;
        font-weight: 300;
        padding-bottom: 60px;
    }

    .log-title {
        padding-top: 120px;
        text-align: center;
        font-weight: bold;
        font-size: 24px;
        text-transform: uppercase;
    }

    .log-title-mobile {
        padding-top: 60px;
        text-align: center;
        font-weight: bold;
        font-size: 24px;
        text-transform: uppercase;
    }
</style>