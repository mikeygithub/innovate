<template>
  <el-dialog
    width="86%"
    @close="closeDialog"
    v-loading="dataListLoading"
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row :gutter="20">
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="16rem" style="width: 94%; margin: 0 auto">
        <el-col :span="24">
          <el-form-item label="企业项目名称" prop="projectName">
            <el-input v-model="dataForm.projectName" placeholder="请输入企业项目名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业项目成立时间" prop="projectSetDate">
            <el-date-picker
              v-model="dataForm.projectSetDate"
              type="date"
              format="yyyy-MM-dd"
              value-format="timestamp"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业所属行业" prop="projectIndustry">
            <el-select v-model="dataForm.projectIndustry"  placeholder="请选择">
              <el-option v-for="item in industryStatusList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <!--<el-col :span="12">-->
          <!--<el-form-item label="企业项目入驻时间" prop="projectInDate">-->
            <!--<el-date-picker-->
              <!--v-model="dataForm.projectInDate"-->
              <!--type="date"-->
              <!--format="yyyy-MM-dd"-->
              <!--value-format="timestamp"-->
              <!--placeholder="选择日期">-->
            <!--</el-date-picker>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :span="12">
          <el-form-item label="企业类型" prop="projectType">
            <el-select v-model="dataForm.projectType"  placeholder="请选择">
              <el-option v-for="item in projectTypeList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="本企业是否拥有有效知识产权" prop="projectProperty">
          <el-tooltip class="item" effect="dark" content="知识产权一般包括三大项目即:商标、专利、版权" placement="bottom-end">
            <el-select v-model="dataForm.projectProperty"  placeholder="请选择">
              <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-tooltip>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="本企业是否就业见习基地" prop="projectBase">
            <el-select v-model="dataForm.projectBase"  placeholder="请选择">
              <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="创办企业初始投资资金（万元）" prop="projectInitMoney">
            <el-input v-model="dataForm.projectInitMoney" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="提供勤工助学岗位数" prop="projectStudyNum">
            <el-input v-model="dataForm.projectStudyNum" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业成员中的应届毕业生人数" prop="perNumFresh">
            <el-input v-model="dataForm.perNumFresh" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业成员中的历届毕业生人数" prop="perNumPast">
            <el-input v-model="dataForm.perNumPast" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="本年度吸纳就业人数" prop="perNumAbsorbEm">
            <el-input v-model="dataForm.perNumAbsorbEm" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业成立以来累计带动就业人数" prop="perNumDriveEmNum">
            <el-input v-model="dataForm.perNumDriveEmNum" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="本年度吸纳实习见习人数" prop="perNumAbsorbPt">
            <el-input v-model="dataForm.perNumAbsorbPt" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业成立以来累计带动实习见习人数" prop="perNumDrivePtNum">
            <el-input v-model="dataForm.perNumDrivePtNum" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="企业是否已进行工商注册" prop="projectRegStatus">
            <el-select v-model="dataForm.projectRegStatus"  placeholder="请选择">
              <el-option
                v-for="item in statusList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="dataForm.projectRegStatus === 1">
          <el-form-item label="营业执照">
            <el-upload
              class="upload-demo"
              :action="url"
              :data="{projectName: dataForm.projectName}"
              :on-success="successHandle1">
              <el-button size="small" type="primary">点击上传</el-button>
              <label>
                （以附件形式上传提交营业执照的扫描件）
              </label>
            </el-upload>
          </el-form-item>
        </el-col>
        <template v-if="dataForm.projectRegStatus === 1">
          <el-col :span="12">
            <el-form-item label="注册时间" prop="projectRegDate">
              <el-date-picker
                v-model="dataForm.projectRegDate"
                type="date"
                format="yyyy-MM-dd"
                value-format="timestamp"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业注册资金（万元）" prop="projectRegMoney">
              <el-input v-model="dataForm.projectRegMoney" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
        </template>
        <el-col :span="24">
          <el-form-item label="项目经营内容" prop="projectContent">
            <el-input
              type="textarea"
              :rows="5"
              placeholder="请输入项目经营内容"
              v-model="dataForm.projectContent">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="企业法人代表信息" prop="legalLists">
            <el-button size="mini"
                       v-if="addVisible(legalLists)" type="primary" plain @click="addLegal()">添加</el-button>
            <template v-for="(item,index) in legalLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <el-tag style="margin-right: 1rem"
                v-text="'姓名：' + item.legalName">
                </el-tag>
                <!--<el-button size="mini" type="primary" plain @click="addLegal()">添加</el-button>-->
                <el-button size="mini" type="primary" plain @click="addLegal(item, index)">修改</el-button>
                <el-button size="mini" type="danger" @click="delLegal(item, index)">删除</el-button>
              </el-col>
            </template>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="指导老师" prop="teacherLists">
            <el-button size="mini"
                       v-if="addVisible(teacherLists)"
                       type="primary" plain @click="addTeacher()">添加</el-button>
            <template v-for="(item,index) in teacherLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <el-tag style="margin-right: 1rem"
                        v-for="user in userTeacherInfoEntities"
                        :key="user.userId"
                        v-if="user.userId === item.userId"
                        v-text="'姓名：' + user.sysUserEntity.username">
                </el-tag>
                <el-button size="mini" type="primary" plain @click="addTeacher()">添加</el-button>
                <el-button size="mini" type="primary" plain @click="addTeacher(item, index)">修改</el-button>
                <el-button size="mini" type="danger" @click="delTeacher(item, index)">删除</el-button>
              </el-col>
            </template>
            <span v-if="isTeacherInfoNullVisible" style="color: crimson">*请联系指导老师完善个人信息</span>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="员工信息" prop="staffInfoLists">
            <el-button size="mini"
                       v-if="addVisible(staffInfoLists)" type="primary" plain @click="addStaff()">添加</el-button>
            <template v-for="(item,index) in staffInfoLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <el-tag style="margin-right: 1rem"
                        v-for="staff in staffTypeList"
                        :key="staff.value"
                        v-if="item.staffType == staff.value"
                        v-text="staff.label + '：' + item.staffName">
                </el-tag>
                <el-button size="mini" type="primary" plain @click="addStaff()">添加</el-button>
                <el-button size="mini" type="primary" plain @click="addStaff(item, index)">修改</el-button>
                <el-button size="mini" type="danger" @click="delStaff(item, index)">删除</el-button>
              </el-col>
            </template>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="资金收入" prop="moneyLists">
            <el-button size="mini"
                       v-if="addVisible(moneyLists)"
                       type="primary" plain @click="addMoney()">添加</el-button>
            <template v-for="(item,index) in moneyLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <el-tag style="margin-right: 1rem"
                        v-for="money in moneyTypeList"
                        :key="money.value"
                        v-if="item.subType === money.value"
                        v-text="money.label + '：' + item.subMoney + '（万元）'">
                </el-tag>
                <el-button size="mini" type="primary" plain @click="addMoney()">添加</el-button>
                <el-button size="mini" type="primary" plain @click="addMoney(item, index)">修改</el-button>
                <el-button size="mini" type="danger" @click="delMoney(item, index)">删除</el-button>
              </el-col>
            </template>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="企业所获成果/奖项" prop="awardLists">
            <el-button size="mini"
                       v-if="addVisible(awardLists)"
                       type="primary" plain @click="addAward()">添加</el-button>
            <template v-for="(item,index) in awardLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <template
                  v-for="runk in awardRankList"
                  v-if="item.awardRank === runk.value">
                  <el-tag style="margin-right: 1rem"
                          v-for="award in awardTypeList"
                          :key="award.value"
                          v-if="item.awardType === award.value"
                          v-text="item.awardName + '：' + award.label + '（' + runk.label + '）'">
                  </el-tag>
                </template>
                <el-button size="mini" type="primary" plain @click="addAward()">添加</el-button>
                <el-button size="mini" type="primary" plain @click="addAward(item, index)">修改</el-button>
                <el-button size="mini" type="danger" @click="delAward(item, index)">删除</el-button>
              </el-col>
            </template>
          </el-form-item>
        </el-col>
        <!--独立附件start-->
        <!--<el-col :span="24" v-if="dataForm.projectRegStatus === 1">-->
          <!--<el-form-item label="营业执照">-->
            <!--<el-upload-->
              <!--class="upload-demo"-->
              <!--:action="url"-->
              <!--:data="{projectName: dataForm.projectName}"-->
              <!--:on-success="successHandle1">-->
              <!--<el-button size="small" type="primary">点击上传</el-button>-->
              <!--<label>-->
                <!--（以附件形式上传提交营业执照的扫描件）-->
              <!--</label>-->
            <!--</el-upload>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :span="24">
          <el-form-item label="项目进驻申请表" prop="">
            <el-upload
              class="upload-demo"
              :action="url"
              :data="{projectName: dataForm.projectName}"
              :on-success="successHandle2">
              <el-button size="small" type="primary">点击上传</el-button>
              <label>
                （以附件形式上传提交项目进驻申请表的扫描件）
              </label>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="创业计划书">
            <el-upload
              class="upload-demo"
              :action="url"
              :data="{projectName: dataForm.projectName}"
              :on-success="successHandle3">
              <el-button size="small" type="primary">点击上传</el-button>
              <label>
                （以附件形式上传提交创业计划书的扫描件）
              </label>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="附件" prop="attachLists">
            <template v-for="(item,index) in attachLists" v-if="item.isDel !== 1">
              <el-col :span="24">
                <el-tag style="margin-right: 1rem"
                        v-text="item.attachName">
                </el-tag>
                <el-button size="mini" type="danger" @click="delAttach(item, index)">删除</el-button>
              </el-col>
            </template>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="文件上传">
            <el-upload
              multiple
              ref="upLoadFiles"
              list-type="card"
              :data="upLoadData"
              :action="upLoadUrl"
              :on-preview="upLoadPreview"
              :on-remove="upLoadRemove"
              :on-success="upLoadSuccess"
              :on-change="upLoadChange"
              :file-list="fileList"
              :auto-upload="false">
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              <el-button style="margin-left: 10px;" size="small" type="success" @click="upLoadSubmit">添加到附件列表</el-button>
              <div slot="tip" class="el-upload__tip">*注意文件需要添加到附件列表后确定保存才能添加成功</div>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
    <teacher-add-or-update v-if="teacherAddOrUpdateVisible" ref="teacherAddOrUpdate" @refreshDataList="teacherRef"></teacher-add-or-update>
    <person-add-or-update v-if="personAddOrUpdateVisible" ref="personAddOrUpdate" @refreshDataList="personRef"></person-add-or-update>
    <legal-add-or-update v-if="legalAddOrUpdateVisible" ref="legalAddOrUpdate" @refreshDataList="legalRef"></legal-add-or-update>
    <award-add-or-update v-if="awardAddOrUpdateVisible" ref="awardAddOrUpdate" @refreshDataList="awardRef"></award-add-or-update>
    <staff-add-or-update v-if="staffAddOrUpdateVisible" ref="staffAddOrUpdate" @refreshDataList="staffRef"></staff-add-or-update>
    <money-add-or-update v-if="moneyAddOrUpdateVisible" ref="moneyAddOrUpdate" @refreshDataList="moneyRef"></money-add-or-update>
  </el-dialog>
</template>

<script>
  import { isNumber, isFloatNumber } from '@/utils/validate'
  import MoneyAddOrUpdate from './money-add-or-update'
  import TeacherAddOrUpdate from './teacher-add-or-update'
  import AwardAddOrUpdate from './award-add-or-update'
  import PersonAddOrUpdate from './person-add-or-update'
  import StaffAddOrUpdate from './staff-add-or-update'
  import LegalAddOrUpdate from './legal-add-or-update'
  export default {
    components: {
      LegalAddOrUpdate,
      StaffAddOrUpdate,
      PersonAddOrUpdate,
      AwardAddOrUpdate,
      TeacherAddOrUpdate,
      MoneyAddOrUpdate
    },
    data () {
      var validateNumber = (rule, value, callback) => {
        if (!isNumber(value)) {
          callback(new Error('必须为整数数字'))
        } else {
          callback()
        }
      }
      var validateFloatNumber = (rule, value, callback) => {
        if (!isFloatNumber(value)) {
          callback(new Error('至少保留小数点后一位'))
        } else {
          callback()
        }
      }
      var validateTeacher = (rule, value, callback) => {
        if (this.teacherLists.length === 0) {
          callback(new Error('指导老师信息不能为空'))
        } else {
          let notNull = false
          for (let index = 0; index < this.teacherLists.length; index++) {
            if (this.teacherLists[index].isDel === 0) {
              notNull = true
            }
          }
          if (notNull) {
            callback()
          } else {
            callback(new Error('指导老师信息不能为空'))
          }
        }
      }
      var validateLegal = (rule, value, callback) => {
        if (this.legalLists.length === 0) {
          callback(new Error('企业法人代表信息不能为空'))
        } else {
          let notNull = false
          for (let index = 0; index < this.legalLists.length; index++) {
            if (this.legalLists[index].isDel === 0) {
              notNull = true
            }
          }
          if (notNull) {
            callback()
          } else {
            callback(new Error('企业法人代表信息不能为空'))
          }
        }
      }
      var projectRegStatus = (rule, value, callback) => {
        if (!value) {
          callback(new Error('请选择企业是否已进行工商注册'))
        } else {
          callback()
        }
      }
      var validateRegMoney = (rule, value, callback) => {
        if (this.dataForm.projectRegStatus === 1) {
          if (!value) {
            callback(new Error('企业注册资金不能为空'))
          } else {
            if (!isFloatNumber(value)) {
              callback(new Error('至少保留小数点后一位'))
            } else {
              callback()
            }
          }
        }
      }
      var projectRegDate = (rule, value, callback) => {
        if (this.dataForm.projectRegStatus === 1) {
          if (!value) {
            callback(new Error('注册时间不能为空'))
          } else {
            callback()
          }
        }
      }
      return {
        url: '',
        upLoadUrl: '',
        upLoadData: {},
        tables: [],
        fileList: [],
        teacherLists: [],
        moneyLists: [],
        personInfoList: [],
        attachLists: [],
        legalLists: [],
        awardLists: [],
        staffInfoLists: [],
        dataListLoading: false,
        teacherAddOrUpdateVisible: false,
        isTeacherInfoNullVisible: false,
        personAddOrUpdateVisible: false,
        legalAddOrUpdateVisible: false,
        awardAddOrUpdateVisible: false,
        staffAddOrUpdateVisible: false,
        moneyAddOrUpdateVisible: false,
        stationVisible: false,
        visible: false,
        staffInfoVisible: false,
        leadVisible: false,
        ptStaffInfoVisible: false,
        projectTeaInfoVisible: false,
        stationList: [],
        userTeacherInfoEntities: [],
        statusList: [{value: 1, label: '是'}, {value: 2, label: '否'}],
        industryStatusList: [
          {value: 1, label: '农、林、牧、渔业'}, {value: 2, label: '采矿业'},
          {value: 3, label: '制造业'}, {value: 4, label: '电力、热力、燃气及水的生产和供应业'},
          {value: 5, label: '环境和公共设施管理业'}, {value: 6, label: '建筑业'},
          {value: 7, label: '交通运输、仓储业和邮政业'}, {value: 8, label: '信息传输、计算机服务和软件业'},
          {value: 9, label: '批发和零售业'}, {value: 10, label: '住宿、餐饮业'},
          {value: 11, label: '金融、保险业'}, {value: 12, label: '房地产业'},
          {value: 13, label: '租赁和商务服务业'}, {value: 14, label: '科学研究、技术服务和地质勘查业'},
          {value: 15, label: '水利、环境和公共设施管理业'}, {value: 16, label: '居民服务和其他服务业'},
          {value: 17, label: '教育'}, {value: 18, label: '卫生、社会保障和社会服务业'},
          {value: 19, label: '文化、体育、娱乐业'}, {value: 20, label: '综合（含投资类、主业不明显）'},
          {value: 21, label: '其它'}
        ],
        projectTypeList: [
          {value: 1, label: '先进制造业'}, {value: 2, label: '现代服务业'},
          {value: 3, label: '现代农业'}, {value: 4, label: '批发零售业'},
          {value: 5, label: '其他'}
        ],
        moneyTypeList: [
          {value: 1, label: '投资'}, {value: 2, label: '管理服务'},
          {value: 3, label: '房租和宽带'}, {value: 4, label: '水电费'},
          {value: 5, label: '一次性创业'}, {value: 6, label: '吸纳困难群体'},
          {value: 7, label: '社会保险'}, {value: 8, label: '创业担保贷款'}
        ],
        awardTypeList: [
          {value: 1, label: '国际级'}, {value: 2, label: '国家级'},
          {value: 3, label: '省级'}, {value: 4, label: '市厅级'},
          {value: 5, label: '县局级'}, {value: 6, label: '校级'}
        ],
        awardRankList: [
          {value: 1, label: '特等奖'}, {value: 2, label: '一等奖'},
          {value: 3, label: '二等奖'}, {value: 4, label: '三等奖'},
          {value: 5, label: '优秀奖'}, {value: 6, label: '金奖'},
          {value: 7, label: '银奖'}, {value: 8, label: '铜奖'},
          {value: 9, label: '第一名'}, {value: 10, label: '第二名'},
          {value: 11, label: '第三名'}, {value: 12, label: '第四名'},
          {value: 13, label: '第五名'}, {value: 14, label: '其他'}
        ],
        staffTypeList: [{value: 1, label: '全职'}, {value: 2, label: '兼职'}],
        dataForm: {
          projectId: '',
          projectUserId: this.$store.state.user.id,
          perNumAbsorbEm: '',
          perNumAbsorbPt: '',
          perNumDriveEmNum: '',
          perNumDrivePtNum: '',
          perNumFresh: '',
          perNumPast: '',
          projectBase: '',
          projectContent: '',
          projectInDate: '',
          projectIndustry: '',
          projectInitMoney: '',
          projectName: '',
          projectProperty: '',
          projectRegDate: '',
          projectRegMoney: '',
          projectRegStatus: '',
          projectSetDate: '',
          projectStudyNum: '',
          projectType: '',
          projectStatus: 0,
          projectAuditApplyStatus: 0,
          projectBaseApplyStatus: 0,
          projectFinishApplyStatus: 0,
          projectMatchApplyStatus: 0,
          stationId: '',
          isUpdate: 0
        },
        dataRule: {
          projectName: [
            { required: true, message: '企业项目名称不能为空', trigger: 'blur' }
          ],
          projectSetDate: [
            { required: true, message: '请选择企业项目成立时间', trigger: 'blur' }
          ],
          projectInDate: [
            { required: true, message: '请选择企业项目入驻时间', trigger: 'blur' }
          ],
          projectRegStatus: [
            { validator: projectRegStatus, trigger: 'blur' }
          ],
          projectRegDate: [
            { validator: projectRegDate, trigger: 'blur' }
          ],
          projectRegMoney: [
            { validator: validateRegMoney, trigger: 'blur' }
          ],
          projectIndustry: [
            { required: true, message: '请选择企业所属行业', trigger: 'blur' }
          ],
          projectType: [
            { required: true, message: '请选择企业类型', trigger: 'blur' }
          ],
          projectInitMoney: [
            { required: true, message: '创办企业初始投资资金不能为空', trigger: 'blur' },
            { validator: validateFloatNumber, trigger: 'blur' }
          ],
          projectStudyNum: [
            { required: true, message: '提供勤工助学岗位数不能为空', trigger: 'blur' },
            { validator: validateNumber, trigger: 'blur' }
          ],
          perNumFresh: [
            { required: true, message: '企业成员中的应届毕业生人数不能为空', trigger: 'blur' },
            { validator: validateNumber, trigger: 'blur' }
          ],
          perNumPast: [
            { required: true, message: '企业成员中的历届毕业生人数不能为空', trigger: 'blur' },
            { validator: validateNumber, trigger: 'blur' }
          ],
          perNumDriveEmNum: [
            { required: true, message: '企业成立以来累计带动就业人数不能为空', trigger: 'blur' },
            { validator: validateNumber, trigger: 'blur' }
          ],
          perNumDrivePtNum: [
            { required: true, message: '企业成立以来累计带动实习见习人数不能为空', trigger: 'blur' },
            { validator: validateNumber, trigger: 'blur' }
          ],
          perNumAbsorbEm: [
            { required: true, message: '本年度吸纳就业人数不能为空', trigger: 'blur' },
            { validator: validateNumber, trigger: 'blur' }
          ],
          perNumAbsorbPt: [
            { required: true, message: '本年度吸纳实习见习人数不能为空', trigger: 'blur' },
            { validator: validateNumber, trigger: 'blur' }
          ],
          projectProperty: [
            { required: true, message: '请选择本企业是否拥有有效知识产权', trigger: 'blur' }
          ],
          projectBase: [
            { required: true, message: '请选择本企业是否就业见习基地', trigger: 'blur' }
          ],
          projectContent: [
            { required: true, message: '项目经营内容不能为空', trigger: 'blur' }
          ],
          teacherLists: [
            { validator: validateTeacher, trigger: 'blur' }
          ],
          legalLists: [
            { validator: validateLegal, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.url = this.$http.adornUrl(`/innovate/project/attach/upload?token=${this.$cookie.get('token')}`)
        this.visible = true
        this.dataListLoading = true
        this.dataForm.projectId = id || 0
        if (this.isAuth('innovate:station:list')) {
          this.$http({
            url: this.$http.adornUrl(`/innovate/base/station/list`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.stationList = data.page.list
            }
          })
        }
        this.$nextTick(() => {
          this.$http({
            url: this.$http.adornUrl(`/innovate/use/teacher/teacher`),
            method: 'get',
            params: this.$http.adornParams({
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.userTeacherInfoEntities = data.userTeacherInfoEntities
            }
          })
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.projectId) {
            this.$http({
              url: this.$http.adornUrl(`/innovate/project/info/info`),
              method: 'get',
              params: this.$http.adornParams({
                'projectId': this.dataForm.projectId,
                'apply': 'project_match_apply_status'
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.projectInfo.projectInfoEntity
                this.teacherLists = data.projectInfo.projectTeacherEntities
                this.personInfoList = []
                this.attachLists = data.projectInfo.projectAttachEntities
                this.legalLists = data.projectInfo.projectLegalInfoEntities
                this.awardLists = data.projectInfo.projectAwardEntities
                this.staffInfoLists = data.projectInfo.projectStaffInfoEntities
                this.moneyLists = data.projectInfo.projectSubMoneyEntities
                this.dataForm.projectSetDate = new Date(this.dataForm.projectSetDate)
                this.dataForm.projectInDate = new Date(this.dataForm.projectInDate)
                this.dataForm.projectRegDate = new Date(this.dataForm.projectRegDate)
                if (this.dataForm.projectRegStatus === 2) {
                  this.dataForm.projectRegMoney = ''
                }
                this.dataForm.baseNoPass = 0
                this.isTeacherInfoNull()
                this.dataListLoading = false
              }
            })
          } else {
            this.dataListLoading = false
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          this.dataForm.projectId = this.dataForm.projectId || undefined
          if (valid) {
            if (this.dataForm.projectId) {
              this.dataForm.projectSetDate = Number(this.dataForm.projectSetDate)
              this.dataForm.projectInDate = Number(this.dataForm.projectInDate)
              this.dataForm.projectRegDate = Number(this.dataForm.projectRegDate)
            }
            this.dataForm.isUpdate = 0
            this.$http({
              url: this.$http.adornUrl(`/innovate/project/info/${!this.dataForm.projectId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'projectInfoEntity': this.dataForm,
                'projectLegalInfoEntities': this.legalLists,
                'projectPersonInfoEntitys': this.personInfoList,
                'projectTeacherEntities': this.teacherLists,
                'projectAttachEntities': this.attachLists,
                'projectAwardEntities': this.awardLists,
                'projectStaffInfoEntities': this.staffInfoLists,
                'projectSubMoneyEntities': this.moneyLists
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
      },
      // 子组件的各类方法
      addVisible (list) {
        let isDelNum = 0
        let visible = false
        for (let index in list) {
          if (list.length > 0 && list[index].isDel === 1) {
            isDelNum++
          }
        }
        if (isDelNum === list.length) {
          visible = true
        }
        return visible
      },
      addTeacher (item, index) {
        this.teacherAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['teacherAddOrUpdate'].init(item, index + 1)
        })
        this.isTeacherInfoNull()
      },
      delTeacher: function (data, index) {
        data.isDel = 1
        this.teacherLists[index] = data
        this.isTeacherInfoNull()
      },
      teacherRef (data, index) {
        data.projectId = this.dataForm.projectId
        this.teacherAddOrUpdateVisible = false
        if (index) {
          this.teacherLists[index - 1] = data
        } else {
          this.teacherLists.push(data)
        }
        this.isTeacherInfoNull()
      },
      isTeacherInfoNull () {
        this.isTeacherInfoNullVisible = false
        var teacherNum = 0
        var teacherLength = 0
        for (let item in this.teacherLists) {
          for (let teacher in this.userTeacherInfoEntities) {
            if (this.teacherLists[item].userId !== null) {
              if (this.userTeacherInfoEntities[teacher].userId === this.teacherLists[item].userId && this.teacherLists[item].isDel !== 1) {
                teacherNum++
              }
            }
          }
          if (this.teacherLists[item].isDel !== 1) {
            teacherLength++
          }
        }
        if (teacherNum < teacherLength) {
          this.isTeacherInfoNullVisible = true
        }
      },
      personRef (data, index) {
        data.projectId = this.dataForm.projectId
        this.personAddOrUpdateVisible = false
        if (index) {
          this.personInfoList[index - 1] = data
        } else {
          this.personInfoList.push(data)
        }
      },
      legalRef (data, index) {
        data.projectId = this.dataForm.projectId
        this.legalAddOrUpdateVisible = false
        if (index) {
          this.legalLists[index - 1] = data
        } else {
          this.legalLists.push(data)
        }
      },
      addAward (item, index) {
        this.awardAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['awardAddOrUpdate'].init(item, index + 1)
        })
      },
      delAward: function (data, index) {
        data.isDel = 1
        this.awardLists[index] = data
      },
      awardRef (data, index) {
        data.projectId = this.dataForm.projectId
        this.awardAddOrUpdateVisible = false
        if (index) {
          this.awardLists[index - 1] = data
        } else {
          this.awardLists.push(data)
        }
      },
      addStaff (item, index) {
        this.staffAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['staffAddOrUpdate'].init(item, index + 1)
        })
      },
      addLegal (item, index) {
        this.legalAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['legalAddOrUpdate'].init(item, index + 1)
        })
      },
      delStaff: function (data, index) {
        data.isDel = 1
        this.staffInfoLists[index] = data
      },
      delLegal: function (data, index) {
        data.isDel = 1
        this.legalLists[index] = data
      },
      staffRef (data, index) {
        data.projectId = this.dataForm.projectId
        this.staffAddOrUpdateVisible = false
        if (index) {
          this.staffInfoLists[index - 1] = data
        } else {
          this.staffInfoLists.push(data)
        }
      },
      addMoney (item, index) {
        this.moneyAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs['moneyAddOrUpdate'].init(item, index + 1)
        })
      },
      delMoney: function (data, index) {
        data.isDel = 1
        this.moneyLists[index] = data
      },
      moneyRef (data, index) {
        data.projectId = this.dataForm.projectId
        this.moneyAddOrUpdateVisible = false
        if (index) {
          this.moneyLists[index - 1] = data
        } else {
          this.moneyLists.push(data)
        }
      },
      delAttach: function (data, index) {
        if (data.isDel !== 1) {
          data.isDel = 1
          this.attachLists[index] = data
        } else {
          this.attachLists.splice(index, 1)
        }
      },

      upLoadSubmit () {
        this.$refs.upLoadFiles.submit()
      },
      upLoadChange () {
        this.upLoadData = {
          'projectName': this.dataForm.projectName,
          'token': this.$cookie.get('token')
        }
        this.upLoadUrl = this.$http.adornUrl(`/innovate/project/attach/upload`)
      },
      upLoadRemove (file, fileList) {
        console.log(file, fileList)
      },
      upLoadPreview (file) {
        console.log(file)
      },
      upLoadSuccess (data) {
        if (data && data.code === 0) {
          this.attachLists.push(data.projectAttachEntity)
        } else {
          this.$message.error(data.msg)
        }
      },

      closeDialog () {
        this.visible = false
        this.$emit('refreshDataList')
      },
      // 上传成功
      successHandle1 (response, file, fileList) {
        if (response && response.code === 0) {
          this.attachLists.push(response.projectAttachEntity)
        } else {
          this.$message.error(response.msg)
        }
      },
      // 上传成功
      successHandle2 (response, file, fileList) {
        if (response && response.code === 0) {
          this.attachLists.push(response.projectAttachEntity)
        } else {
          this.$message.error(response.msg)
        }
      },
      // 上传成功
      successHandle3 (response, file, fileList) {
        if (response && response.code === 0) {
          this.attachLists.push(response.projectAttachEntity)
        } else {
          this.$message.error(response.msg)
        }
      }

    }
  }
</script>
<style>
  .item {
    margin: 4px;
  }
</style>
