<template>
    <div class="allTopics-content">
        <all-topics-row v-for="topic in allTopics"
                        :key="topic.id"
                        :topic="topic"/>
    </div>
</template>

<script>
    import AllTopicsRow from "components/topics/AllTopicsRow.vue"
    import {mapState} from "vuex"

    export default {
        name: "AllTopicsList",
        components: {AllTopicsRow},
        computed: mapState(['allTopics']),
    }
</script>

<style scoped>
    .allTopics-content {
        flex-direction: row;
        align-items: flex-start;
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
    }
</style>