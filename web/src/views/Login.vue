<template>
    <div style="width: 100%; height: 100vh; background-color: #B0C4DE; overflow: hidden">
        <div style="width:400px; margin:100px auto;">
            <div style="color: #FFF; font-size: 30px; text-align: center; padding: 40px 0;margin-left: 50px;">登录</div>
            <el-form ref="form" :model="form" size="normal" :rules="rules" label-width="80px" margin-right="40px">
                <el-form-item label="用户名" prop="username">
                    <el-input prefix-icon="el-icon-user-solid" v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 100%;" type="primary" @click="login">登 录</el-button>
                </el-form-item>
                <el-form-item> <!-- 新增的按钮 -->
                    <el-button type="text" style="width: 100%; color: #FFF;" @click="toRegister">没有账号？立即注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';

export default {
    name: "Login",
    data() {
        return {
            form: {},
            rules: {
                username: [
                    {
                        required: true, message: '请输入用户名', trigger: 'blur'
                    }
                ],
                password: [
                    {
                        required: true, message: '请输入密码', trigger: 'blur'
                    }
                ]
            }
        }
    },
    methods:{
        login(){
            this.$refs['form'].validate((valid) => {
                if(valid){
                    request.post("/api/person/login", this.form).then(res =>{
                if(res.code === '0') {
                    this.$message({
                        type: "success",
                        message: "登录成功"
                    })
                    this.$router.push("/home")
                } else {
                    this.$message({
                        type: "error",
                        message: res.msg
                    })
                }
            })
                }
            })
        },
        toRegister() { // 跳转到注册页面的方法
            this.$router.push("/register");
        }
    }
}
</script>

<style scoped>
.el-form-item .el-form-item__label {
    color: white; /* 设置label文字为白色 */
    font-weight: bold; /* 可选：增加label文字的粗细 */
}
.el-form-item {
    margin-bottom: 20px;
}

.el-input {
    width: 100%;
}
.label {
    color: #000;
}
</style>