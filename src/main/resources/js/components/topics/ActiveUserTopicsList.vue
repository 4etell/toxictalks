<template>
    <div class="activeUserTopics-content">
        <active-user-topics-row v-for="userTopic in userChats"
                         :key="userTopic.id"
                         :topic="userTopic"/>
    </div>
</template>

<script>
    import ActiveUserTopicsRow from "components/topics/ActiveUserTopicsRow.vue"

    import {mapState} from "vuex"

    export default {

        name: "ActiveUserTopicsList",
        components: {
            ActiveUserTopicsRow,
        },
        computed: mapState(['userChats']),
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