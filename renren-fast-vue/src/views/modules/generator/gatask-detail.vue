<template>

  <el-dialog :title="'任务详情' " :close-on-click-modal="false"  :visible.sync="visible" >



 <div class="mod-home">
    <h3>进本信息</h3>
    <ul>
      <li>名称：<a href="https://gitee.com/renrenio/renren-fast" target="_blank">{{dataForm.name}}</a></li>
      <li>状态：{{dataForm.state}}</li>
      <li>参数：{{dataForm.params}}</li>
    </ul>
    <h3>详情</h3>
    <ul>
      <li>{{dataForm.info}}</li>
    </ul>
  </div> 

    <DemoEcharts></DemoEcharts>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关 闭</el-button>
      <!-- <el-button type="primary" @click="dataFormSubmit()">确定</el-button> -->
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

        console.info("ga detail init ...");

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
<style>
  .mod-home {
    line-height: 1.5;
  }
</style>