<template>
    <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex;">
        <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue;"></div>
        <div style="flex: 1; color: red" ></div>
        <div style="width: 100px">
<!--            <el-dropdown>-->
<!--                <span class="el-dropdown-link">-->
<!--                    张三<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--                </span>-->
<!--                <template #dropdown>-->
<!--                    <el-dropdown-menu>-->
<!--                        <el-dropdown-item>个人信息</el-dropdown-item>-->
<!--                        <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>-->
<!--                    </el-dropdown-menu>-->
<!--                </template>-->
<!--            </el-dropdown>-->
        </div>
    </div>
</template>

<script>
export default {
    name: "Header"
}
</script>

<style scoped>

</style>
