<template>
    <div>
        <v-icon
                size="22px"
                class="pb-1"
                color="#CCCCCC"
                @click.stop="dialog = true">
            {{mdiPlusCircleOutline}}
        </v-icon>
        <v-dialog v-if="((this.userChats.length + this.userTopics.length) < 5)"
                v-model="dialog"
                max-width="600px">
            <v-card>
                <v-card-title class="font-weight-bold">
                    <span style="font-size: 18px">Добавить тему</span>
                    <v-tooltip color="#FAFAFA"
                               :nudge-top="!$vuetify.breakpoint.xs ? '-35' : ''"
                               :right="!$vuetify.breakpoint.xs"
                               :bottom="$vuetify.breakpoint.xs">
                        <template v-slot:activator="{ on }">
                            <v-icon class="ml-2" v-on="on" color="#CCCCCC">{{mdiHelpCircleOutline}}</v-icon>
                        </template>
                        <span style="color: #808080; font-size: 13px;">
                            Напишите тему, на которую вы хотите подискутировать
                            <br>
                            <br>
                            Если к данной теме вы относитель положительно
                            <br>
                            выберите — <span style="color: #29F245"> Нравится</span>
                            <br>
                            Отрицательно — <span style="color: #F01313"> Не нравится</span>
                        </span>
                    </v-tooltip>
                </v-card-title>
                <v-divider></v-divider>
                <v-card-text class="pb-0 pt-5">
                    <v-text-field
                            :error-messages="formErrorMessage"
                            label="Тема"
                            v-model="name"
                            outlined
                            color="#29F245"
                    ></v-text-field>
                    <v-radio-group
                            v-model="radios"
                            mandatory
                            class="mt-0">
                        <v-radio
                                color="#29F245"
                                value="like">
                            <template v-slot:label>
                                <div>Нравится</div>
                            </template>
                        </v-radio>
                        <v-radio
                                color="#F01313"
                                value="dislike">
                            <template v-slot:label>
                                <div>Не нравится</div>
                            </template>
                        </v-radio>
                    </v-radio-group>
                </v-card-text>
                <v-card-actions class="pt-0">
                    <v-spacer></v-spacer>
                    <v-btn
                            text
                            color="#29F245"
                            @click="dialog = false">
                        Закрыть
                    </v-btn>
                    <v-btn
                            text
                            color="#29F245"
                            @click="save">
                        Добавить
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-dialog v-else
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
    import {mdiHelpCircleOutline, mdiPlusCircleOutline} from '@mdi/js'
    import {mapState} from 'vuex'

    export default {
        name: "AddTopicBtn",
        data() {
            return {
                name: '',
                dialog: false,
                radios: null,
                mdiHelpCircleOutline,
                mdiPlusCircleOutline,
                formError: ''
            }
        },
        watch: {
            messageAttr(newVal) {
                this.name = newVal.name
            }
        },
        computed: {
            ...mapState(['userChats', 'userTopics']),
            formErrorMessage() {
                if (this.name && !this.maxFormLength()) {
                    return 'Максимальная длина темы 30 символов'
                }
            }

        },
        methods: {
            maxFormLength() {
                return this.name.length <= 30
            },
            save() {
                this.dialog = false;

                if (this.name.trim() !== '' && this.maxFormLength()) {

                    let attitude = false;

                    if (this.radios === 'like') {
                        attitude = true;
                    }

                    const topic = {
                        name: this.name,
                        attitude: attitude
                    }

                    this.$http.post('topics/createTopic', topic)

                    this.name = ''
                }

            },

        },
        created() {
        }
    }
</script>

<style scoped>

</style>