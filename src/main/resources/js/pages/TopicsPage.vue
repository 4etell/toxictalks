<template>
    <div>
        <v-container>
            <div class="topics-title">
                <div>Активные чаты</div>
                <question-active-topics-btn class="ml-2"> class="ml-2"></question-active-topics-btn>
            </div>
            <active-user-topics-list></active-user-topics-list>
            <div class="topics-title">
                <div>Мои темы</div>
                <add-topic-btn class="ml-2"></add-topic-btn>
            </div>
            <user-topics-list></user-topics-list>
            <div class="topics-title">
                <div>Темы пользователей</div>
                <question-all-topic-btn class="ml-2"></question-all-topic-btn>
            </div>
            <all-topics-list></all-topics-list>
        </v-container>
    </div>
</template>

<script>
    import AllTopicsList from "components/topics/AllTopicsList.vue"
    import UserTopicsList from "components/topics/UserTopicsList.vue"
    import AddTopicBtn from "components/topics/AddTopicBtn.vue"
    import QuestionAllTopicBtn from "components/topics/QuestionAllTopicsBtn.vue"
    import ActiveUserTopicsList from "components/topics/ActiveUserTopicsList.vue"
    import QuestionActiveTopicsBtn from "components/topics/QuestionActiveTopicsBtn.vue"

    import {mapState} from 'vuex'


    export default {
        name: "TopicsPage",
        components: {
            QuestionActiveTopicsBtn,
            ActiveUserTopicsList,
            QuestionAllTopicBtn,
            UserTopicsList,
            AllTopicsList,
            AddTopicBtn,

        },
        computed: mapState(['profile'])

    }
</script>

<style>
    .v-tooltip__content{
        background: rgba(255, 255, 255, 1);
        z-index: 100;
        opacity: 1!important;
        box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.15);
        border-radius: 3px;
    }

    .topic {
        position: relative;
        display: inline-block;
        color: #000000;
        background: #e5e5e5;
        font-weight: normal;
        border-radius: 3px;
        font-size: 14px;
        line-height: 19px;
        min-width: 100px;
        width: auto;
        height: 25px;
        padding: 2px 10px;
        margin-right: 15px;
        margin-bottom: 13px;
        margin-top: 13px;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
        z-index: 1;
    }

    .topics-title {
        display: flex;
        margin-top: 40px;
        margin-bottom: 10px;
        font-weight: 600;
        font-size: 18px;
        line-height: 25px;
    }
</style>