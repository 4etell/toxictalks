<template>
        <div class="activeUserTopics-content">
            <user-topics-row v-for="userTopic in userTopics"
                             :key="userTopic.id"
                             :topic="userTopic"/>
        </div>
</template>

<script>
    import UserTopicsRow from "components/topics/UserTopicsRow.vue"
    import {mapState} from "vuex";

    export default {
        name: "TopicVue",
        components: {
            UserTopicsRow
        },
        computed: mapState(['userTopics']),
    }
</script>

<style scoped>
    .activeUserTopics-content {
        flex-direction: row;
        align-items: flex-start;
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
    }
</style>