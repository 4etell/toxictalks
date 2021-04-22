<template>
    <div class="topic">
        {{topic.name}}
    </div>
</template>

<script>
    export default {
        name: "ActiveUserTopicsRow",
        props: ['topic'],
    }
</script>

<style scoped>
</style>