import Vue from "vue"
import vuetify from "plugins/vuetify";
import "@babel/polyfill"
import "api/vueResource"
import router from "router/router"
import store from "store/store"

import Main from "pages/Main.vue"

import {connectTopicWs} from "ws/topicWs"
import {connectChatWs} from "ws/chatWs"

if (profile) {
    connectTopicWs()
    connectChatWs(profile.id)
}

new Vue({
    el: '#app',
    store,
    router,
    vuetify,
    render: a => a(Main)
})