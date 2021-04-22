<template>
    <div>
        <v-tooltip
                   color="rgba(255,255,255,1)"
                   :nudge-top="!$vuetify.breakpoint.xs ? '-50' : ''"
                   :right="!$vuetify.breakpoint.xs"
                   :top="$vuetify.breakpoint.xs">
            <template v-slot:activator="{ on }">
                <v-icon size="22px" class="pb-1" v-on="on" color="#CCCCCC">{{mdiHelpCircleOutline}}</v-icon>
            </template>
            <span style="color: #808080; font-size: 13px">
                            Вы можете присоединиться к существующим чатам.
                            <br>
                            <br>
                            Если вы поддерживаете данную тему, нажмите на <span style="color: #F01313">Красную кнопку</span>,
                            <br>
                            вы будете общаться с  человеком, который не поддерживает данную тему.
                            <br>
                            Если вы не поддерживаете тему, нажмите на <span style="color: #29F245">Зеленую кнопку.</span>
                        </span>
        </v-tooltip>
    </div>
</template>

<script>
    import {mdiHelpCircleOutline} from '@mdi/js'

    export default {
        name: "QuestionAllTopicBtn",
        data() {
            return {
                mdiHelpCircleOutline
            }
        },
    }
</script>

<style scoped>

</style>