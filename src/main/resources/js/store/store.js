import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
        state: {
            profile,
            usernamesList,
            userTopics,
            userChats,
            allTopics,
            currentChat: '',
        },
        mutations: {
            addUserTopicMutation(state, topic) {
                state.userTopics = [
                    ...state.userTopics,
                    topic
                ]
            },

            removeUserTopicMutation(state, topic) {
                const deletionIndex = state.userTopics.findIndex(item => item.id === topic.id)

                if (deletionIndex > -1) {
                    state.userTopics = [
                        ...state.userTopics.slice(0, deletionIndex),
                        ...state.userTopics.slice(deletionIndex + 1)
                    ]
                }
            },

            addTopicMutation(state, topic) {
                state.allTopics = [
                    ...state.allTopics,
                    topic
                ]
            },

            removeTopicMutation(state, topic) {
                const deletionIndex = state.allTopics.findIndex(item => item.id === topic.id)

                if (deletionIndex > -1) {
                    state.allTopics = [
                        ...state.allTopics.slice(0, deletionIndex),
                        ...state.allTopics.slice(deletionIndex + 1)
                    ]
                }
            },

            addUserChatMutation(state, topic) {
                state.userChats = [
                    ...state.userChats,
                    topic
                ]
            },

            removeUserChatMutation(state, chatId) {
                const deletionIndex = state.userChats.findIndex(item => item.id === chatId)

                if (deletionIndex > -1) {
                    state.userChats = [
                        ...state.userChats.slice(0, deletionIndex),
                        ...state.userChats.slice(deletionIndex + 1)
                    ]
                }
            },

            updateUserChatsMutation(state, chat) {

                const updateIndex = state.userChats.findIndex(item => item.id === chat.id)

                state.userChats = [
                    ...state.userChats.slice(0, updateIndex),
                    chat,
                    ...state.userChats.slice(updateIndex + 1)
                ]

            },

            addMessageToChatMutation(state, message) {
                let currentChat = state.userChats.find(chat => chat.id === message.chatId)

                currentChat.messages = [
                    ...currentChat.messages,
                    message
                ]

            },

            setReadMessageMutation(state, chatId, profileId) {
                let currentChat = state.userChats.find(chat => chat.id === chatId)

                for (let i = 0; i < currentChat.messages.length; i++) {
                    if (currentChat.messages[i].read === false && currentChat.messages[i].authorId !== profileId) {
                        currentChat.messages[i].read = true
                    }

                }
            },

            updateProfileMutation(state, profile) {
                state.profile = profile
            },

            setUserCurrentChatId(state, currentChatId) {
                state.currentChat = currentChatId
            },

        },


    }
)