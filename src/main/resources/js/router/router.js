import Vue from 'vue'
import VueRouter from 'vue-router'
import ChatPage from "pages/ChatPage.vue"
import ProfilePage from "pages/ProfilePage.vue"
import LoginPage from "pages/LoginPage.vue"
import LoginError from "pages/LoginError.vue"
import HomePage from "pages/HomePage.vue"
import NotFoundPage from "pages/NotFoundPage.vue"
import RegistrationPage from "pages/RegistrationPage.vue"
import TopicsPage from "pages/TopicsPage.vue"

Vue.use(VueRouter)

const routes = [
    {name: 'home', path: '/', component: HomePage},
    {name: 'login', path: '/login', component: LoginPage},
    {name: 'registration',path: '/registration', component: RegistrationPage},
    {name: 'login-error', path: '/login-error', component: LoginError},
    {name: 'topics', path: '/topics', component: TopicsPage},
    {name: 'chats', path: '/chats', component: ChatPage},
    {name: 'profile', path: '/profile', component: ProfilePage},
    {path: '*', component: NotFoundPage}
]

export default new VueRouter({
    mode: 'history',
    routes
})