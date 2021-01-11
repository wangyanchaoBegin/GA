<template>
  <div class="mod-config">

    <!-- 表头查询和操作部分 -->
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>

       <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名2" clearable></el-input>
      </el-form-item>


      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名3" clearable></el-input>
      </el-form-item>

      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名 D" clearable></el-input>
      </el-form-item>


      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('generator:gatask:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('generator:gatask:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>


<!-- 上传文件 -->

      


      </el-form-item>
    </el-form>

    <!-- 具体的列表 -->
    <el-table :data="dataList" border  v-loading="dataListLoading"  @selection-change="selectionChangeHandle" style="width: 100%;"> 
      <el-table-column  type="selection"  header-align="center"  align="center"  width="50"></el-table-column>
      <el-table-column prop="id"  header-align="center"  align="center"  label="id"> </el-table-column>
      <el-table-column  prop="name"  header-align="center"  align="center"  label="GA任务名称"></el-table-column>
      <el-table-column  prop="params"  header-align="center"  align="center"  label="参数"> </el-table-column>
      <el-table-column  prop="state"  header-align="center"  align="center"  label="状态"> </el-table-column>
      <el-table-column  prop="info"  header-align="center"  align="center"  label="详情"></el-table-column>
      <el-table-column  prop="createTime"  header-align="center"  align="center"   label="创建时间"></el-table-column>
      <el-table-column  prop="updateTime"  header-align="center"  align="center"  label="完成时间"></el-table-column>
      <!-- 分页列表中的 操作栏 -->
      <el-table-column fixed="right"  header-align="center"  align="center"  width="150"  label="操作">

        <!-- slot-scope="scope"      @click="deleteHandle(scope.row.id)"      scope.row.id   这都是什么啊？？？  -->
        <template slot-scope="scope">

          <el-button type="text" size="small" @click="detail(scope.row.id)">详情</el-button>

          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页下面的页脚 -->
    <el-pagination @size-change="sizeChangeHandle"  @current-change="currentChangeHandle"  :current-page="pageIndex"  
                :page-sizes="[10, 20, 50, 100]"  :page-size="pageSize"  :total="totalPage"  layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>



    <!-- 弹窗, 新增 / 修改    为什么在在各个地方呢？？？             ref="addOrUpdate"  这个是什么，父子组件么？？？       
     @refreshDataList="getDataList"     这个又是什么，子组件向父组件传递么？？？       
     


      ref="addOrUpdate"   页面中，用 addOrUpdate 代替子组件对象本身  调用时候，  this.$refs.addOrUpdate.init(id)
     
     -->


<!--  ref="detail" 代表子组件自己   本（父组件）调用    this.$refs.detail.init(id)   这样。   -->
    <detail v-if="detailVisible"   ref="detail"    @refreshDataList="getDataList"></detail>

    <add-or-update2 v-if="addOrUpdateVisible"   ref="addOrUpdate"    @refreshDataList="getDataList"></add-or-update2>
  </div>
</template>

<script>
  import AddOrUpdate2 from './gatask-add-or-update'// 引入咋这里，之后在 components 加入。
  import Detail from './gatask-detail'// 引入咋这里，之后在 components 加入。
  export default {
    data () {
      return {
        // fileList: [],
        // 查询条件
        dataForm: {
          key: ''
        },

        // 分页列表
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,

        dataListLoading: false,// 加载状态
        dataListSelections: [],// 选中的

        addOrUpdateVisible: false ,   //  弹窗, 新增 / 修改 是否可以是根据这个
        detailVisible: false    //  弹窗, 新增 / 修改 是否可以是根据这个
      }
    },
    components: {
      // 引入的组件 ！！！
      AddOrUpdate2,
      Detail
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/generator/gatask/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true


        // 这 一句是 啥意思？？？？    1. $nextTick    2.$refs   3..addOrUpdate.init(id)        addOrUpdate 是这个组件
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },


      // 新增 / 修改
      detail (id) {
        this.detailVisible = true


        // 这 一句是 啥意思？？？？    1. $nextTick    2.$refs   3..addOrUpdate.init(id)        addOrUpdate 是这个组件
        this.$nextTick(() => {
          this.$refs.detail.init(id)
        })
      },

      // 删除            是关于那个弹窗的。。。    
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })

        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/generator/gatask/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {

            // data && data.code === 0   data 就戴白这个不是null了吧？？？
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      },

      // // 上传相关
      // submitUpload() {
      //   this.$refs.upload.submit();
      // },
      // handleRemove(file, fileList) {
      //   console.log(file, fileList);
      // },
      // handlePreview(file) {
      //   console.log(file);
      // }
    }
  }
</script>
