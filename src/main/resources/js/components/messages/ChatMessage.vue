<template>
    <div>
        <div class="wrap">
            <v-card class="message"
                    flat
                    :class="message.authorId===profile.id ? 'owner' : ''">
                <div class="message-text">
                    {{message.text}}
                </div>
                <div class="message-date">
                    {{message.updateDate}}
                </div>
            </v-card>
        </div>
    </div>
</template>

<script>

    import {mapState} from 'vuex'

    export default {
        name: "ChatMessage",
        computed: mapState(['profile']),
        props: {
            message: Object
        }
    }
</script>

<style scoped>
    .wrap {
        display: flex;
        flex-direction: column;
    }

    .message {
        padding: 0.6rem 3rem 0.6rem 0.6rem;
        height:auto;
        position:relative;
        margin-right: 15px;
        min-width: 10%;
        max-width: 60%;
        align-self: flex-start;
        border-radius: 4px;
        background-color: #E6E6E6;
        margin-bottom: 5px;
    }

    .message-text {
        font-weight: 300;
        font-size: 14px;
        line-height: 19px;
        color: black;
    }

    .message-date {
        color: rgba(0, 0, 0, 0.5);
        font-size: 10px;
        padding: 0.4rem 0.6rem 0.4rem 0.4rem;
        position: absolute;
        bottom: 0;
        right: 0;
    }

    .owner {
        background-color: #B7FAC1;
        height: auto;
        align-self: flex-end;
    }
</style>