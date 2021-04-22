<template>
    <div>
        <div class="message-form">
            <input v-model="text"
                   aria-rowcount="3"
                   maxlength="200"
                   placeholder="Введите сообщение..." class="message-input" v-on:keyup.enter="sendMessage"/>
            <v-icon class="send-btn" color="#bababa" @click="sendMessage">
                {{mdiSend}}
            </v-icon>
        </div>
    </div>
</template>

<script>
    import {mapState} from "vuex"
    import {mdiSend} from '@mdi/js'

    export default {

        name: "MessageForm",
        data() {
            return {
                text: '',
                mdiSend
            }
        },
        watch: {
            messageAttr(newVal) {
                this.text = newVal.text
            }
        },
        props: {
            chatId: Number,
            ...mapState(['profile'])
        },
        methods: {
            sendMessage() {

                if (this.text) {

                    const messageReq = {
                        chatId: this.chatId,
                        text: this.text,
                    }

                    this.$http.post('chat/createMessage', messageReq)

                    this.text = ''
                }
            }
        }
    }
</script>

<style scoped>
    .message-form {
        display: flex;
        flex-direction: row;
    }

    .message-input {
        padding: 10px;
        width: 95%;
        max-height: 40px;
        outline: none;
    }

    .send-btn {

    }

    .send-btn:hover {
        cursor: pointer;
    }
</style>