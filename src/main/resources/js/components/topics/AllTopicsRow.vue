<template>
    <div>
        <div v-if="((this.userChats.length + this.userTopics.length) < 5)" class="topic" @click="enterChat"
             v-bind:style="topic.attitude===true ? 'background: #B7FAC1' : 'background: #FAB7B7'">
            {{topic.name}}
        </div>
        <div v-else class="topic" @click.stop="dialog = true"
             v-bind:style="topic.attitude===true ? 'background: #B7FAC1' : 'background: #FAB7B7'">
            {{topic.name}}
        </div>
        <v-dialog
                v-model="dialog"
                max-width="600px">
            <v-card>
                <v-card-title class="font-weight-regular">
                    <span style="font-size: 18px">
                        У вас не может быть больше <b>5</b> чатов.
                        <br>
                        Удалите существующий, чтобы добавить новый.
                    </span>
                </v-card-title>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>

    import {mapState} from 'vuex'

    export default {
        name: "AllTopicsRow",
        props: ['topic'],
        computed: mapState(['userChats', 'userTopics']),
        data() {
            return {
                dialog: false
            }
        },
        methods: {
            enterChat() {
                this.$http.post('topics/addParticipant', this.topic.id)
            }
        },
    }
</script>

<style scoped>
    .topic:hover {
        cursor: pointer;
    }
</style>