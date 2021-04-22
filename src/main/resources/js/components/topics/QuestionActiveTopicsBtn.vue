<template>
    <div>
        <v-tooltip :nudge-top="!$vuetify.breakpoint.xs ? '-50' : ''"
                   :right="!$vuetify.breakpoint.xs"
                   :bottom="$vuetify.breakpoint.xs">
            <template v-slot:activator="{ on }">
                <v-icon size="22px" class="pb-1" v-on="on" color="#CCCCCC">{{mdiHelpCircleOutline}}</v-icon>
            </template>
            <span style="color: #808080; font-size: 13px;">
                            Ваши активные чаты.
                            <br>
                            <br>
                            Чтобы начать общаться на данные темы,
                            <br>
                            нажмите на кнопку <b>чаты</b> в меню навигации.
                        </span>
        </v-tooltip>
    </div>
</template>

<script>
    import {mdiHelpCircleOutline} from '@mdi/js'

    export default {
        name: "QuestionActiveTopicsBtn",
        data() {
            return {
                mdiHelpCircleOutline
            }
        },
    }
</script>

<style>
</style>