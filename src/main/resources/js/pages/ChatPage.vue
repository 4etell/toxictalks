<template>
    <div>
        <div v-if="!$vuetify.breakpoint.xs">
            <v-container>
                <div v-if="chatsExist">
                    <div class="chatHeader">
                        <div class="chatList-title">Мои чаты</div>
                        <div class="messageList-title">
                            <div class="messageList-title-text">
                                Вы общаетесь на тему: <span>{{currentChat.name}}</span>.
                                Ваш собеседник
                                <span style="color: #29F245"
                                      v-if="(currentChat.attitude === true && currentChat.creatorId !== profile.id)
                                    || (currentChat.attitude === false && currentChat.creatorId === profile.id) ">
                            поддерживает
                            </span>
                                <span v-else
                                      style="color: #F01313">
                            не поддерживает
                            </span>
                                данную тему.
                            </div>
                            <v-menu
                                    offset-y
                                    left
                                    bottom>
                                <template v-slot:activator="{on}">
                                    <v-icon v-on="on" class="messageList-title-dots" size="40px" color="#CCCCCC">
                                        {{mdiDotsHorizontal}}
                                    </v-icon>
                                </template>
                                <v-list>
                                    <v-list-item>
                                        <v-btn text @click="leaveChat">Покинуть чат</v-btn>
                                    </v-list-item>
                                </v-list>
                            </v-menu>
                        </div>
                    </div>
                    <div class="chatContent">
                        <div class="chatList-content">
                            <div class="chatList-chat"
                                 v-for="chat in userChats"
                                 :key="chat.id"
                                 :style="currentChat === chat ? 'background-color: rgba(41, 242, 69, 0.2)' : ''"
                                 :class="currentChat !== chat && findCountUnreadMessages(chat) > 0 ? 'unreadMessage-color' : ''"
                                 @click="setCurrentChat(chat.id)">
                                <v-icon class="ml-2 mt-1"
                                        size="60px"
                                        color="#CCCCCC">
                                    {{mdiIncognitoCircle}}
                                </v-icon>
                                <div class="chatList-chat-title">
                                    <div class="chatList-chat-title-chatName">
                                        {{chat.name}}
                                    </div>
                                    <div v-if="messagesNotEmpty(chat)"
                                         class="chatList-chat-title-lastMessage">

                                        <b v-if="chat.messages[chat.messages.length-1].authorId === profile.id"
                                           class="mr-1">
                                            Вы:
                                        </b>
                                        <div style="overflow: hidden; text-overflow: ellipsis;">
                                            {{ chat.messages[chat.messages.length-1].text}}
                                        </div>
                                    </div>
                                    <b v-else
                                       class="chatList-chat-title-lastMessage">
                                        Нет сообщений
                                    </b>
                                </div>
                                <div v-if="messagesNotEmpty(chat) &&
                            chat !== currentChat &&
                            findCountUnreadMessages(chat) > 0"
                                     class="countOfUnreadMessages">
                                    {{findCountUnreadMessages(chat)}}
                                </div>
                            </div>
                        </div>
                        <div class="messageList-content">
                            <div class="messages-wrap">
                                <div class="messages-list" ref="messagesList">
                                    <chat-message v-for="message in getMessages"
                                                  :key="message.id"
                                                  :message="message"/>
                                </div>
                                <div class="message-form">
                                    <message-form v-if="isActiveChat" :chat-id="currentChat.id"/>
                                    <v-alert v-else dense color="#FAB7B7" style="text-align: center">
                                        Ваш собеседник вышел из чата.
                                        <span class="deleteChat" @click="leaveChat">
                                    Удалить чат
                                    </span>
                                    </v-alert>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div v-else class="mt-10">
                    <div class="not-chats-text">
                        <div :style="$vuetify.breakpoint.xs ? 'font-size: 16px' : ''">
                            <div class="mb-2">У Вас нет чатов.</div>
                            <div>Создайте свой или выберите из предложенных.</div>
                        </div>
                    </div>
                    <div class="not-chats-button" @click="goToTopics">
                        Создать
                    </div>
                </div>
            </v-container>
        </div>


        <div v-else>
            <v-app-bar
                    v-if="!mobileMessages"
                    flat
                    color="#F2F2F2">

                <v-app-bar-nav-icon color="black"
                                    @click.stop="drawer = !drawer">
                </v-app-bar-nav-icon>

                <v-spacer></v-spacer>

                <v-btn v-if="!profile"
                       icon
                       @click="goToLogin">
                    <v-icon color="black">
                        {{mdiLoginVariant}}
                    </v-icon>
                </v-btn>


                <v-btn v-if="profile"
                       icon
                       href="/logout">
                    <v-icon color="black">
                        {{mdiLogoutVariant}}
                    </v-icon>
                </v-btn>

            </v-app-bar>

            <v-navigation-drawer v-model="drawer"
                                 absolute
                                 left
                                 temporary>
                <v-list dense
                        style="z-index: 100"
                        nav
                        class="text-uppercase">

                    <NavigationBtns></NavigationBtns>

                </v-list>
            </v-navigation-drawer>


            <div v-if="chatsExist">

                <div class="mt-5" v-if="!mobileMessages">
                    <v-list>
                        <v-subheader>Мои чаты</v-subheader>
                        <div v-for="chat in userChats"
                             :key="chat.id"
                             @click="setCurrentChatMobile(chat.id)">
                            <v-list-item>
                                <v-list-item-avatar>
                                    <v-icon color="#CCCCCC">{{mdiIncognitoCircle}}</v-icon>
                                </v-list-item-avatar>
                                <v-list-item-content>
                                    <v-list-item-title>{{chat.name}}</v-list-item-title>
                                    <v-list-item-subtitle v-if="messagesNotEmpty(chat)">
                                        <b v-if="chat.messages[chat.messages.length-1].authorId === profile.id"
                                           class="mr-1">
                                            Вы:
                                        </b>{{ chat.messages[chat.messages.length-1].text}}
                                    </v-list-item-subtitle>
                                    <v-list-item-subtitle v-else>
                                        <b>Нет сообщений</b>
                                    </v-list-item-subtitle>
                                </v-list-item-content>
                                <div v-if="messagesNotEmpty(chat) &&
                                        findCountUnreadMessages(chat) > 0" class="countOfUnreadMessages">
                                    {{findCountUnreadMessages(chat)}}
                                </div>
                            </v-list-item>
                            <v-divider v-if="chat.id !== userChats[userChats.length - 1].id"></v-divider>
                        </div>
                    </v-list>
                </div>

                <div v-else>
                    <v-app-bar
                            flat
                            color="white">

                        <v-icon @click="mobileGoToChats"
                                color="black"
                                class="mr-4">
                            {{mdiArrowLeft}}
                        </v-icon>

                        <v-app-bar-title>
                            {{currentChat.name}}
                        </v-app-bar-title>

                        <v-spacer></v-spacer>

                        <v-menu
                                offset-y
                                left>
                            <template v-slot:activator="{on}">
                                <v-icon v-on="on" class="messageList-title-dots" color="black">
                                    {{mdiDotsVertical}}
                                </v-icon>
                            </template>
                            <v-btn text @click="leaveChatMobile" style="font-size: 14px; background: white">Покинуть чат
                            </v-btn>
                        </v-menu>

                    </v-app-bar>
                    <div class="messages-wrap-mobile">
                        <div class="messages-content-mobile">
                            <div class="messages-list-mobile" ref="messagesListMobile">
                                <chat-message v-for="message in getMessages"
                                              :key="message.id"
                                              :message="message"/>
                            </div>
                            <div class="message-form">
                                <message-form v-if="isActiveChat" :chat-id="currentChat.id"/>
                                <v-alert v-else dense color="#FAB7B7" style="text-align: center">
                                    Ваш собеседник вышел из чата.
                                    <span class="deleteChat" @click="leaveChatMobile">
                                    Удалить чат
                                    </span>
                                </v-alert>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div v-else class="mt-10">
                <v-container>
                    <div class="not-chats-text">
                        <div :style="$vuetify.breakpoint.xs ? 'font-size: 16px' : ''">
                            <div class="mb-2">У Вас нет чатов.</div>
                            <div>Создайте свой или выберите из предложенных.</div>
                        </div>
                    </div>
                    <div class="not-chats-button" @click="goToTopics">
                        Создать
                    </div>
                </v-container>
            </div>
        </div>
    </div>
</template>

<script>
    import ChatMessage from "components/messages/ChatMessage.vue";
    import MessageForm from "components/messages/MessageForm.vue";
    import NavigationBtns from "components/navigation/NavigationBtns.vue";
    import {mapState, mapMutations} from "vuex"
    import {
        mdiIncognitoCircle, mdiDotsHorizontal, mdiLoginVariant,
        mdiLogoutVariant, mdiArrowLeft, mdiDotsVertical
    } from '@mdi/js';
    import {goToTopics} from "functions/routerPushMethods"

    export default {
        name: "ChatPage",
        components: {NavigationBtns, MessageForm, ChatMessage},
        computed: {
            ...mapState(['userChats', 'profile', 'currentChat']),
            getMessages() {
                if (this.currentChat) {
                    return this.currentChat.messages
                }
            },
            isActiveChat() {
                return this.currentChat.status === 'ACTIVE'
            },
            getCurrentChat() {
                return this.currentChat
            }
        },
        data() {
            return {
                drawer: false,
                mdiIncognitoCircle,
                mdiDotsHorizontal,
                mdiLoginVariant,
                mdiLogoutVariant,
                mdiDotsVertical,
                mdiArrowLeft,
                chatsExist: false,
                dotsMenu: false,
                mobileMessages: false,
            }
        },
        methods: {
            ...mapMutations(['setReadMessageMutation', 'setUserCurrentChatId']),
            setCurrentChat(chatId) {
                this.setReadMessageMutation(this.currentChat.id, this.profile.id)
                this.setUserCurrentChatId(this.userChats.find(chat => chat.id === chatId))
                this.setReadMessageMutation(chatId, this.profile.id)
                this.$http.post('chat/setReadMessages', chatId)
            },
            setCurrentChatMobile(chatId) {
                this.mobileMessages = true
                this.setReadMessageMutation(this.currentChat.id, this.profile.id)
                this.setUserCurrentChatId(this.userChats.find(chat => chat.id === chatId))
                this.setReadMessageMutation(chatId, this.profile.id)
                this.$http.post('chat/setReadMessages', chatId)
            },
            isEmpty(obj) {
                for (var prop in obj) {
                    if (obj.hasOwnProperty(prop))
                        return false;
                }
                return true;
            },
            messagesNotEmpty(chat) {
                return !this.isEmpty(chat.messages);
            },
            findCountUnreadMessages(chat) {
                let count = 0

                for (let i = 0; i < chat.messages.length; i++) {
                    if (chat.messages[i].read === false && chat.messages[i].authorId !== this.profile.id) {
                        count++
                    }
                }

                return count;
            },

            leaveChat() {
                if (this.userChats.length === 1) {
                    this.chatsExist = false;
                }
                this.$http.post('chat/leaveChat', this.currentChat.id).then(response => {
                    if (response.ok) {
                        this.setUserCurrentChatId(this.userChats[0])
                    }
                })
            },
            leaveChatMobile() {
                if (this.userChats.length === 1) {
                    this.chatsExist = false;
                }
                this.$http.post('chat/leaveChat', this.currentChat.id).then(response => {
                    if (response.ok) {
                        this.setUserCurrentChatId(this.userChats[0])
                        this.mobileMessages = false;
                    }
                })
            },

            mobileGoToChats() {
                this.mobileMessages = false;
            },

            goToTopics

        },
        watch: {
            getMessages: function () {
                if (this.chatsExist && window.innerWidth > 600) {
                    setTimeout(() => {
                        this.$refs.messagesList.scrollTop = this.$refs.messagesList.scrollHeight
                    })
                }
                if (this.chatsExist && window.innerWidth <= 600 && this.mobileMessages) {
                    setTimeout(() => {
                        this.$refs.messagesListMobile.scrollTop = this.$refs.messagesListMobile.scrollHeight
                    })
                }
            },
            mobileMessages: function () {
                if (this.chatsExist && window.innerWidth <= 600 && this.mobileMessages) {
                    setTimeout(() => {
                        this.$refs.messagesListMobile.scrollTop = this.$refs.messagesListMobile.scrollHeight
                    })
                }
            }
        },
        created() {

            if (this.userChats[0]) this.chatsExist = true

            if (!this.currentChat) {
                this.setUserCurrentChatId(this.userChats[0])
            }

            if (this.currentChat && window.innerWidth > 600) {
                this.$http.post('chat/setReadMessages', this.currentChat.id)
            }
        }


    }
</script>

<style scoped>
    .chatHeader {
        margin-top: 20px;
        display: flex;
        flex-direction: row;
    }

    .chatList-title {
        overflow: hidden;
        width: 24.36%;
        background-color: white;
        margin-right: 0.86%;
        padding-left: 20px;
        padding-top: 14px;
        padding-bottom: 14px;
        font-size: 18px;
    }

    .messageList-title {
        position: relative;
        display: flex;
        flex-direction: row;
        overflow: hidden;
        padding-left: 20px;
        padding-top: 15px;
        padding-bottom: 14px;
        font-size: 15px;
        vertical-align: center;
        width: 74.78%;
        background-color: white;
    }

    .chatContent {
        height: 530px;
        display: flex;
        flex-direction: row;
        margin-top: 0.43%;
    }

    .chatList-content {
        width: 24.36%;
        background-color: white;
        margin-right: 0.86%;
    }

    .messageList-content {
        width: 74.78%;
        background-color: white;
    }

    .chatList-chat {
        position: relative;
        height: 66px;
        display: flex;
        flex-direction: row;
    }

    .chatList-chat:hover {
        cursor: pointer;
    }

    .chatList-chat-title {
        overflow: hidden;
        margin-top: 8px;
        margin-left: 10px;
        padding-right: 8px;
        font-weight: normal;
        font-size: 14px;
        line-height: 19px;
    }

    .chatList-chat-title-chatName {
        margin-right: 30px;
        height: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .chatList-chat-title-lastMessage {
        margin-right: 30px;
        display: flex;
        flex-direction: row;
        margin-top: 5px;
        height: 20px;
        color: #808080;
        overflow: hidden;
        text-overflow: ellipsis;
    }


    .messages-wrap {
        height: 100%;
        position: relative;
        overflow: hidden;
    }

    .messages-list {
        margin: 10px 0px 10px 15px;
        position: absolute;
        top: 0;
        right: 0;
        left: 0;
        bottom: 50px;
        overflow-y: auto;
    }

    .messages-list-mobile {
        background: white;
        padding: 10px 0px 10px 15px;
        position: absolute;
        top: 56px;
        right: 0;
        left: 0;
        bottom: 50px;
        overflow-y: auto;
    }

    .message-header-mobile {
        top: 0;
        height: 40px;
        background: white;
        position: absolute;
        width: 100%;

    }


    .message-form {
        background-color: #F2F2F2;
        margin: 10px 15px;
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        height: 40px;
        border-radius: 3px;
    }

    ::-webkit-scrollbar {
        width: 12px;
        height: 12px;
    }

    ::-webkit-scrollbar-thumb:vertical {
        height: 10px;
        background: #E6E6E6
    }

    ::-webkit-scrollbar-thumb:vertical:hover {
        background: #d9d9d9
    }

    .not-chats-button {
        font-size: 15px;
        width: 122px;
        margin-top: 20px;
        background: #cccccc;
        border-radius: 3px;
        text-align: center;
        padding: 7px 32px;
    }

    .not-chats-button:hover {
        cursor: pointer;
        background: #adacac;
    }

    .not-chats-text {
        font-size: 18px;
        line-height: 25px;
    }

    .countOfUnreadMessages {
        overflow: hidden;
        text-align: center;
        font-size: 14px;
        color: white;
        padding-top: 2px;
        margin-left: 2px;
        width: 25px;
        height: 25px;
        position: absolute;
        right: 10px;
        top: 21px;
        background-color: #78F689;
        border-radius: 50%;
    }

    .unreadMessage-color {
        background: #FAFAFA
    }

    .messageList-title-text {
        text-align: center;
        margin-right: 10%;

    }

    .messageList-title-dots {
        top: 50%;
        transform: translate(0, -50%);
        right: 10px;
        position: absolute;

    }

    .messageList-title-dots:hover {
        cursor: pointer;
    }

    .deleteChat {
        font-weight: bold;
    }

    .deleteChat:hover {
        cursor: pointer;
    }

</style>