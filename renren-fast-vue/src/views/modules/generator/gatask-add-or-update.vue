<template>

  <el-dialog :title="!dataForm.id ? '新增GA任务' : '修改GA任务'" :close-on-click-modal="false"  :visible.sync="visible" >

    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      
      <el-form-item label="名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="GA任务名称"></el-input>
      </el-form-item>

      <el-form-item label="参数" prop="params">
        <el-input v-model="dataForm.params" placeholder="使用‘ ，’间隔"></el-input>
      </el-form-item>

      <!-- <el-form-item label="任务状态" prop="state">
        <el-input v-model="dataForm.state" placeholder="任务状态"></el-input>
      </el-form-item> -->

      <el-form-item label="详情" prop="info">
        <el-input v-model="dataForm.info" placeholder="附加任务描述"></el-input>
      </el-form-item>

      <el-form-item label="详情3" prop="info">
        <el-input type="textarea"  placeholder="请输入内容"  v-model="dataForm.info"  maxlength="30"  show-word-limit></el-input>
      </el-form-item>


    </el-form>

    <!-- <DemoEcharts></DemoEcharts> -->


    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
 import DemoEcharts from '../../demo/echarts'// 引入咋这里，之后在 components 加入。
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          name: '',
          params: '',
          state: '',
          info: '',
          createTime: '',
          endTime: ''
        },
        dataRule: {
          name: [
            { required: true, message: '任务名称不能为空', trigger: 'blur' }
          ],
          // params: [
          //   { required: true, message: '任务参数，使用‘ ，’间隔不能为空', trigger: 'blur' }
          // ],
          // state: [
          //   { required: true, message: '任务状态不能为空', trigger: 'blur' }
          // ],
          // info: [
          //   { required: true, message: '不能为空', trigger: 'blur' }
          // ],
          // createTime: [
          //   { required: true, message: '不能为空', trigger: 'blur' }
          // ],
          // endTime: [
          //   { required: true, message: '不能为空', trigger: 'blur' }
          // ]
        }
      }
    },
     components: {
      // 引入的组件 ！！！
      DemoEcharts
    },
    methods: {

      // 父组件调用这个方法 。。  刚刚上面的是方式啊。
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/generator/gatask/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.gaTask.name
                this.dataForm.params = data.gaTask.params
                this.dataForm.state = data.gaTask.state
                this.dataForm.info = data.gaTask.info
                this.dataForm.createTime = data.gaTask.createTime
                this.dataForm.endTime = data.gaTask.endTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/generator/gatask/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'params': this.dataForm.params,
                // 'state': this.dataForm.state,
                'info': this.dataForm.info,
                // 'createTime': this.dataForm.createTime,
                // 'endTime': this.dataForm.endTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false


                    // 发送  refreshDataList  事件！！！
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
