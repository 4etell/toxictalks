<template>
    <div>
        <v-btn text
               color="#29F245"
               :block="$vuetify.breakpoint.xs"
               @click="goToHome">
            <span v-if="$route.path === '/'">Главная</span>
            <span v-else class="black--text">Главная</span>
        </v-btn>
        <v-btn v-if="profile"
               text
               color="#29F245"
               :block="$vuetify.breakpoint.xs"
               @click="goToChats">
            <span v-if="$route.path === '/chats'">Чаты</span>
            <span v-else class="black--text">Чаты</span>
        </v-btn>
        <v-btn v-if="profile"
               text
               color="#29F245"
               :block="$vuetify.breakpoint.xs"
               @click="goToTopics">
            <span v-if="$route.path === '/topics'">Темы</span>
            <span v-else class="black--text">Темы</span>
        </v-btn>
        <v-btn v-if="profile"
               text
               color="#29F245"
               :block="$vuetify.breakpoint.xs"
               @click="goToProfile">
            <span v-if="$route.path === '/profile'">Профиль</span>
            <span v-else class="black--text">Профиль</span>
        </v-btn>
        <v-btn v-if="!profile"
               text
               color="#29F245"
               :block="$vuetify.breakpoint.xs"
               @click="goToRegistration">
            <span v-if="$route.path === '/registration'">Регистрация</span>
            <span v-else class="black--text">Регистрация</span>
        </v-btn>
</div>
</template>

<script>
    import {mapState} from "vuex"

    import {goToRegistration, goToChats, goToTopics, goToHome, goToProfile} from "functions/routerPushMethods"

    export default {
        name: "NavigationBtns",
        computed: mapState(['profile']),
        methods: {
            goToHome,
            goToRegistration,
            goToChats,
            goToProfile,
            goToTopics,
        }
    }
</script>

<style scoped>

</style>