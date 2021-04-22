<template>
    <v-app class="main-bg">

        <v-app-bar
                v-if="!($vuetify.breakpoint.xs && $route.path === '/chats')"
                app
                hide-on-scroll
                flat
                color="#F2F2F2">

            <v-app-bar-nav-icon color="black"
                                v-if="$vuetify.breakpoint.xs"
                                @click.stop="drawer = !drawer">
            </v-app-bar-nav-icon>

            <v-spacer></v-spacer>
            <NavigationBtns v-if="!$vuetify.breakpoint.xs"></NavigationBtns>

            <v-btn v-if="!profile && !$vuetify.breakpoint.xs"
                   text
                   color="#29F245"
                   @click="goToLogin">
                <span v-if="$route.path === '/login'" class="#29F245--text">Войти</span>
                <span v-else class="black--text">Войти</span>
            </v-btn>

            <v-btn text v-if="profile && !$vuetify.breakpoint.xs"
                   color="#29F245"
                   href="/logout">
                <span class="black--text">Выйти</span>
            </v-btn>


            <v-btn v-if="!profile && $vuetify.breakpoint.xs"
                   icon
                   @click="goToLogin">
                <v-icon color="black">
                    {{mdiLoginVariant}}
                </v-icon>
            </v-btn>


            <v-btn v-if="profile && $vuetify.breakpoint.xs"
                   icon
                   href="/logout">
                <v-icon color="black">
                    {{mdiLogoutVariant}}
                </v-icon>
            </v-btn>

        </v-app-bar>

        <v-navigation-drawer v-model="drawer"
                             absolute
                             temporary>
            <v-list dense
                    nav
                    class="text-uppercase">

                <NavigationBtns></NavigationBtns>

            </v-list>
        </v-navigation-drawer>


        <v-main>
            <router-view></router-view>
        </v-main>
    </v-app>
</template>

<script>
    import NavigationBtns from "components/navigation/NavigationBtns.vue"
    import {mapState, mapMutations} from "vuex";
    import {mdiLoginVariant, mdiLogoutVariant} from '@mdi/js'
    import {goToLogin} from "functions/routerPushMethods"
    import {addHandlerChatWs} from "../ws/chatWs"
    import {addHandlerTopicWs} from "../ws/topicWs"

    export default {
        name: "Main",
        computed: mapState(['profile', 'currentChat', 'userChats']),
        components: {
            NavigationBtns
        },
        methods: {
            goToLogin,
            ...mapMutations([
                'addMessageToChatMutation',
                'addUserTopicMutation', 'addTopicMutation',
                'removeTopicMutation', 'removeUserTopicMutation',
                'addUserChatMutation', 'updateUserChatsMutation', 'removeUserChatMutation',
                'setUserCurrentChatId']),
            isEmpty(obj) {
                for (var prop in obj) {
                    if (obj.hasOwnProperty(prop))
                        return false;
                }
                return true;
            },
        },
        data: () => ({
            drawer: false,
            group: null,
            mdiLoginVariant: mdiLoginVariant,
            mdiLogoutVariant: mdiLogoutVariant
        }),
        created() {
            addHandlerTopicWs(data => {
                if (data.wsObjectType === 'TOPIC') {
                    switch (data.wsEventType) {
                        case 'CREATE' :
                            if (data.body.creatorId === this.profile.id) {
                                this.addUserTopicMutation(data.body)
                            } else {
                                this.addTopicMutation(data.body)
                            }
                            break
                        case 'ADD_PARTICIPANT' :
                            this.removeTopicMutation(data.body)
                            this.removeUserTopicMutation(data.body)
                            if (data.body.creatorId === this.profile.id ||
                                data.body.participantId === this.profile.id)
                                this.addUserChatMutation(data.body)
                            break
                        case 'REMOVE' :
                            if (data.body.creatorId === this.profile.id) {
                                this.removeUserTopicMutation(data.body)
                            } else {
                                this.removeTopicMutation(data.body)
                            }
                            break
                        default:
                            console.error(`Event type is unknown "${data.wsEventType}"`)
                    }
                } else {
                    console.error(`Object type is unknown "${data.wsObjectType}"`)
                }
            })

            addHandlerChatWs(data => {
                if (data.wsObjectType === 'CHAT_MESSAGE') {
                    switch (data.wsEventType) {
                        case 'CREATE' :
                            this.addMessageToChatMutation(data.body)
                            break
                        default:
                            console.error(`Event type is unknown "${data.wsEventType}"`)
                    }
                } else if (data.wsObjectType === 'CHAT') {

                    if (data.wsEventType === 'SET_STATUS_NOT_ACTIVE') {
                        if (this.currentChat && this.currentChat.id === data.body.id) {
                            this.setUserCurrentChatId(data.body)
                        }
                        this.updateUserChatsMutation(data.body)
                    }
                    if (data.wsEventType === 'REMOVE') {
                        this.removeUserChatMutation(data.body)
                    }

                } else {
                    console.error(`Object type is unknown "${data.wsObjectType}"`)
                }
            })
        }
    }
</script>

<style scoped>
    .main-bg {
        background-color: #F2F2F2;
    }
</style>