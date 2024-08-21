<template>
    <div style="width: 100%; height: 100vh; background-color: #B0C4DE; overflow: hidden">
        <div style="width:400px; margin:100px auto">
            <div style="color: #FFF; font-size: 30px; text-align: center; padding: 40px 0; margin-left: 50px;">注册</div>
            <el-form ref="form" :model="form" size="normal" :rules="rules" label-width="80px">
                <el-form-item label="用户名" prop="username">
                    <el-input prefix-icon="el-icon-user-solid" v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="confirm">
                    <el-input prefix-icon="el-icon-lock" v-model="form.confirm" show-password></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 100%;" type="primary" @click="register">注 册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';

export default {
    name: "Register",
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
                ],
                confirm: [
                    {
                        required: true, message: '请确认密码', trigger: 'blur'
                    }
                ]
            }
        }
    },
    methods:{
        register(){
            if(this.form.password !== this.form.confirm){
                this.$message({
                    type: "error",
                    message: '两次密码输入不一致'
                })
                return
            }

            this.$refs['form'].validate((valid) => {
                if(valid){
                    request.post("/api/person/register", this.form).then(res =>{
                if(res.code === '0') {
                    this.$message({
                        type: "success",
                        message: "注册成功"
                    })
                    this.$router.push("/login")
                } else {
                    this.$message({
                        type: "error",
                        message: res.msg
                    })
                }
            })
                }
            })
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
</style>