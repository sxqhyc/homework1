<template>
  <div style="padding: 10px" class="page-wrapper">
<!--    <div style="margin: 20px 0">-->
<!--      <el-button size="medium" type="primary" @click="add">新增</el-button>-->
<!--    </div>-->
<!--    <div style="margin: 25px 0">-->
<!--      <el-input size="medium" v-model="search" placeholder="请输入用户名" style="width: 20%" clearable></el-input>-->
<!--      <el-button size="medium" type="primary" style="margin-left: 5px" @click="load">查询</el-button>-->
<!--    </div>-->
    <div style="margin: 20px 0" class="button-container">
      <el-button size="large" :style="{ backgroundColor: '#e1f3d8', color: 'black' }" round @click="add">新增</el-button>
      <div class="search-container">
        <el-input size="large" v-model="search" placeholder="请输入用户名" clearable></el-input>
        <el-button size="large":style="{ backgroundColor: '#d9ecff', color: 'black' }" @click="load">查询</el-button>
      </div>
    </div>
    <div class = "content">
    <el-table :data="tableData" border stripe style="width: 100%" size="medium">
    <el-table-column prop="id" label="ID" sortable/>
    <el-table-column prop="address" label="头像">
      <template #default="scope">
        <el-image
        style="width: 100px; height: 100px;"
        :src="scope.row.cover"
        :preview-src-list="[scope.row.cover]">
        </el-image>
      </template>
    </el-table-column>
    <el-table-column prop="username" label="用户名" />
    <el-table-column prop="phone" label="电话" />
    <el-table-column prop="age" label="年龄" />
    <el-table-column prop="sex" label="性别" />
    <el-table-column prop="address" label="地址" />
    <el-table-column fixed="right" label="操作" min-width="120">

      <template #default="scope">
        <el-button size="medium":style="{ backgroundColor: '#fdf6ec', color: 'black' }" round @click="handleEdit(scope.row)">
          编辑
        </el-button>
        <el-popconfirm title="确认删除吗？"  size="large" @confirm="handleDelete(scope.row.id)">
          <template #reference>
            <el-button size="medium":style="{ backgroundColor: '#fde2e2', color:'black'}" round >删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <div style="margin: 10px 0">
    <el-pagination
      v-model:current-page="currentPage4"
      v-model:page-size="pageSize4"
      :page-sizes="[5, 10, 20]"
      :size="10"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <el-dialog
    v-model="dialogVisible"
    title="编辑信息"
    width="500">
    <el-form label-width="120px" size="medium">
      <el-form-item :model="form" label="用户名">
        <el-input v-model="form.username" style="width: 80%"/>
      </el-form-item>
      <el-form-item :model="form" label="电话">
        <el-input v-model="form.phone" style="width: 80%"/>
      </el-form-item>
      <el-form-item :model="form" label="年龄">
        <el-input v-model="form.age" style="width: 80%"/>
      </el-form-item>
      <el-form-item :model="form" label="性别">
        <el-radio v-model="form.sex" label="男">男</el-radio>
        <el-radio v-model="form.sex" label="女">女</el-radio>
        <el-radio v-model="form.sex" label="保密">保密</el-radio>
      </el-form-item>
      <el-form-item :model="form" label="地址">
        <el-input type="textarea" v-model="form.address" style="width: 80%"/>
      </el-form-item>
      <el-form-item :model="form" label="上传头像">
        <el-upload
          ref="upload"
          action="http://localhost:8090/files/upload" :on-success="filesUploadSuccess"
        >
        <el-button type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="medium" @click="dialogVisible = false">取消</el-button>
          <el-button size="medium" type="primary" @click="save">
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
  </div>
  </div>
</template>

<script>

import request from '@/utils/request';


export default {
  name: 'HomeView',
  components: {

  },
  data() {
    return {
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      total: 10,
      tableData: []
    }
  },
  mounted(){
    this.load();
  },
  methods: {
    filesUploadSuccess(res){
      console.log(res)
      this.form.cover = res.data
    },
    load(){
      request.get("/api/user" ,{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    // add(){
    //   this.dialogVisible = true
    //   this.form = {}
    //   this.$refs['upload'].clearFiles()
    // },
    add(){
      this.dialogVisible = true
      this.form = {}
      this.$nextTick(() => {
        if (this.$refs.upload) {
          this.$refs.upload.clearFiles();
        }
      });
    },
    save(){
      if(this.form.id) {
        request.put("/api/user", this.form).then(res => {
        console.log(res)
        if(res.code === '0'){
          this.$message({
          type: "success",
          message: "更新成功"
        })
        }else{
          this.$message({
          type: "error",
          message: res.msg
        })
        }
        this.load()
        this.dialogVisible = false
      })
      }else {
        request.post("/api/user", this.form).then(res => {
        console.log(res);
        if(res.code === '0'){
          this.$message({
          type: "success",
          message: "新增成功"
        })
        }else{
          this.$message({
          type: "error",
          message: res.msg
        })
        }
        this.load()
        this.dialogVisible = false
      })
      }
    },
    // handleEdit(row) {
    //   this.form = JSON.parse(JSON.stringify(row))
    //   this.dialogVisible = true
    //   this.$nextTick(() => {
    //     this.$refs['upload'].clearFiles() //清除历史文件列表
    //   })
    // },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      this.$nextTick(() => {
        if (this.$refs.upload) {
          this.$refs.upload.clearFiles(); // 清除历史文件列表
        } else {
          console.error("Upload ref is undefined in handleEdit");
        }
      })
    },
    handleDelete(id) {
      console.log(id)
      request.delete("/api/user/" + id).then(res => {
        if(res.code === '0'){
          this.$message({
          type: "success",
          message: "删除成功"
        })
        }else{
          this.$message({
          type: "error",
          message: res.msg
        })
        }
        this.load()
      })
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>
<!--<style scoped>-->
<!--  .page-wrapper {-->
<!--    background-color: #fdf6ec; /* 这里设置你想要的背景颜色 */-->
<!--    padding: 20px;-->
<!--    min-height: 100vh; /* 使页面高度至少占满视口 */-->
<!--  }-->
<!--</style>-->
<!--<style scoped>-->
<!--  .page-wrapper {-->
<!--    background-image: url('a.png'); /* 这里替换为你的背景图片路径 */-->
<!--    background-size: cover; /* 确保背景图覆盖整个页面 */-->
<!--    background-position: center; /* 背景图居中 */-->
<!--    background-repeat: no-repeat; /* 背景图不重复 */-->

<!--    min-height: 100vh; /* 使页面高度至少占满视口 */-->
<!--    padding: 20px;-->
<!--    z-index: 1; /* 确保背景图在内容后面 */-->
<!--  }-->
<!--  .content{-->
<!--    z-index: 0;-->
<!--  }-->


<!--</style>-->
<style>
  body {
    background-image: url("a.png");
    background-size: cover;
  }
   .button-container {
     display: flex;
     justify-content: flex-start; /* 让新增按钮在最左侧 */
     align-items: center; /* 垂直居中对齐内容 */
     position: relative; /* 使搜索容器可以绝对定位 */
   }

  .search-container {
    position: absolute;
    left: 50%;
    transform: translateX(-50%); /* 使搜索容器水平居中 */
    display: flex;
    align-items: center;
    margin: 0; /* 移除容器的默认边距 */
  }


</style>
