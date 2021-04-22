<template>
    <div>
        <v-container>
            <LoginPage></LoginPage>
        </v-container>
    </div>
</template>

<script>
    import LoginPage from "pages/LoginPage.vue"
    import {mapState} from "vuex"

    export default {
        name: "LoginError",
        components: {
            LoginPage
        },
        computed: mapState(['profile'])
    }
</script>

<style scoped>

</style>