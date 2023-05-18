<script lang="ts">
import {defineComponent} from 'vue';

export default defineComponent({
    name: "MoyuForm",
    props: {
        userId: Number,
        widgetId: Number,
        buttons: Array,
        flush: Number,
    },
})
</script>

<template>
    <div>
        <el-card shadow="hover"> Hover </el-card>
    </div>
</template>

<style scoped>

</style>