<template>
  <div style="padding: 10px">
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%">
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
    <el-table-column prop="nickName" label="昵称" />
    <el-table-column prop="age" label="年龄" />
    <el-table-column prop="sex" label="性别" />
    <el-table-column prop="address" label="地址" />
    <el-table-column fixed="right" label="操作" min-width="120">

      <template #default="scope">
        <el-button size="mini" @click="handleEdit(scope.row)">
          编辑
        </el-button>
        <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
          <template #reference>
            <el-button size="mini" type="danger">删除</el-button>
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
    title="Tips"
    width="500">
    <el-form label-width="120px">
      <el-form-item :model="form" label="用户名">
        <el-input v-model="form.username" style="width: 80%"/>
      </el-form-item>
      <el-form-item :model="form" label="昵称">
        <el-input v-model="form.nickName" style="width: 80%"/>
      </el-form-item>
      <el-form-item :model="form" label="年龄">
        <el-input v-model="form.age" style="width: 80%"/>
      </el-form-item>
      <el-form-item :model="form" label="性别">
        <el-radio v-model="form.sex" label="男">男</el-radio>
        <el-radio v-model="form.sex" label="女">女</el-radio>
        <el-radio v-model="form.sex" label="未知">未知</el-radio>
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
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>
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
