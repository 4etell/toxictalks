<template>
    <div>
        <div class="topic pr-5"
             :style="topic.attitude===true ? 'background: #B7FAC1' : 'background: #FAB7B7'"
             @click="deleteChat">
            <span>{{topic.name}}</span>
            <v-icon class="deleteIcon">{{mdiClose}}</v-icon>
        </div>
    </div>
</template>

<script>
    import {mdiClose} from '@mdi/js';

    export default {
        name: "UserTopicsRow",
        props: ['topic'],

        data() {
            return {
                mdiClose
            }
        },
        methods: {
            deleteChat() {
                this.$http.post('topics/deleteTopic', this.topic.id)
            }
        }
    }
</script>

<style scoped>
    .deleteIcon {
        position: absolute;
        right: 2px;
        width: 14px;
        height: 14px;
    }

    .topic:hover {
        cursor: pointer;
    }
</style>