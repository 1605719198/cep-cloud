<template>
  <div>
  <el-button v-hasPermi="['human:affairsBaseInfo:save']" type="primary" size="medium" plain @click="handleSave">保存</el-button>
  <el-button type="primary" size="medium" plain @click="handleEmpty">新增</el-button>
  <el-form ref="form" :model="form" :rules="rules" label-width="195px">
    <el-row>
      <el-col :span="6">
        <el-form-item label="工号" prop="empNo">
          <el-input v-model="form.empNo" placeholder="请输入工号"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="证件类型" prop="certificateTypeId">
          <el-select v-model="form.certificateTypeId" placeholder="证件类型" @change="changeCertificateTypeId">
            <el-option
              v-for="dict in baseInfoData.documentType"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="证件号码" prop="certificateNumber">
          <el-input v-model="form.certificateNumber" placeholder="请输入证件号码" :maxlength="18" @input="inputChange"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item prop="photoAddress">
          <photoUpload v-model="form.photoAddress" style="position: absolute"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="姓名" prop="fullName">
          <el-input v-model="form.fullName" placeholder="请输入姓名"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="英文名" prop="englishName">
          <el-input v-model="form.englishName" placeholder="请输入英文名"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="曾用名" prop="nameUsedBefore">
          <el-input v-model="form.nameUsedBefore" placeholder="请输入曾用名"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="参加工作时间" prop="joinWorkDate">
          <el-date-picker
            v-model="form.joinWorkDate"
            type="date"
            style="width: 207.33px"
            placeholder="选择参加工作时间">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="入企时间" prop="entryDate">
          <el-date-picker
            v-model="form.entryDate"
            type="date"
            style="width: 207.33px"
            placeholder="选择入企时间">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="工龄起始日" prop="workStartDate">
          <el-date-picker
            v-model="form.workStartDate"
            type="date"
            style="width: 207.33px"
            placeholder="选择工龄起始日">
          </el-date-picker>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="政治面貌" prop="politicalOutlookId">
          <el-select v-model="form.politicalOutlookId" placeholder="请选择政治面貌">
            <el-option
              v-for="dict in baseInfoData.politicalOutlook"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="民族">
          <el-select v-model="form.nationId" placeholder="请选择民族">
            <el-option
              v-for="dict in baseInfoData.nation"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="婚姻状态" prop="maritalStatusId">
          <el-select v-model="form.maritalStatusId" placeholder="请选择婚姻状态">
            <el-option
              v-for="dict in baseInfoData.maritalStatus"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="性别" prop="genderId">
          <el-radio-group v-model="form.genderId">
            <el-radio
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="出生日期" prop="birthDate">
          <el-date-picker
            v-model="form.birthDate"
            type="date"
            style="width: 207.33px"
            placeholder="选择出生日期">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="生日选择" prop="birthdayChoice">
          <el-select v-model="form.birthdayChoice" placeholder="请选择">
            <el-option
              v-for="dict in baseInfoData.birthdaySelection"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="生日" prop="birthTwoDate">
          <el-date-picker
            v-model="form.birthTwoDate"
            type="date"
            style="width: 207.33px"
            placeholder="选择生日">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="国籍" prop="nationalityId">
          <el-select v-model="form.nationalityId" placeholder="请选择国籍">
            <el-option
              v-for="dict in baseInfoData.nationality"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="出生地(省/市)" prop="provinceOfBirthId">
          <el-select v-model="form.provinceOfBirthId" placeholder="请选择出生地（省/市）" @change="handleChange">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="出生地(市/区)" prop="cityOfBirthId">
          <el-select v-model="form.cityOfBirthId" placeholder="请选择出生地（市/区）">
            <el-option
              v-for="item in city"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="籍贯" prop="nativePlaceId">
          <el-select v-model="form.nativePlaceId" placeholder="请选择籍贯">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="身体状况">
          <el-select v-model="form.healthId" placeholder="请选择身体状况">
            <el-option
              v-for="dict in baseInfoData.physicalCondition"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="是否有病史">
          <el-select v-model="form.ifMedicalHistory" placeholder="请选择">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="病史说明" prop="medicalHistoryDescription">
          <el-input v-model="form.medicalHistoryDescription" placeholder="请输入病史说明"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="职工来源">
          <el-select v-model="form.sourceOfEmployees" placeholder="请选择职工来源">
            <el-option
              v-for="dict in baseInfoData.employeeSource"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="户口性质" prop="natureOfHouseholdRegistrationId">
          <el-select v-model="form.natureOfHouseholdRegistrationId" placeholder="请选择户口性质">
            <el-option
              v-for="dict in baseInfoData.natureOfAccount"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="户口所在地(省)" prop="registeredPermanentResidenceProvinceId">
          <el-select v-model="form.registeredPermanentResidenceProvinceId" placeholder="请选择户口所在地（省）" @change="handleChange">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="户口所在地(市)" prop="registeredPermanentResidenceCityId">
          <el-select v-model="form.registeredPermanentResidenceCityId" placeholder="请选择户口所在地(市)">
            <el-option
              v-for="item in city"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="户口所在地邮编" prop="registeredPermanentResidencePostalCode">
          <el-input v-model="form.registeredPermanentResidencePostalCode" placeholder="请输入户口所在地邮编"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="血型">
          <el-select v-model="form.bloodTypeId" placeholder="请选择血型">
            <el-option
              v-for="dict in baseInfoData.bloodGroup"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="身高" prop="height">
          <el-input v-model="form.height" placeholder="请输入身高"><span slot="suffix">CM</span></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="体重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入体重"><span slot="suffix">KG</span></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="宗教信仰">
          <el-select v-model="form.religiousBeliefId" placeholder="请选择宗教信仰">
            <el-option
              v-for="dict in baseInfoData.religiousBelief"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="第一学历">
          <el-select v-model="form.firstRecordId" placeholder="请选择第一学历">
            <el-option
              v-for="dict in baseInfoData.qualification"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="毕业学校" prop="firstRecordGraduateSchool">
          <el-input v-model="form.firstRecordGraduateSchool" placeholder="请输入毕业学校"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="培养方式">
          <el-select v-model="form.firstRecordTrainingMethod" placeholder="请选择培养方式">
            <el-option
              v-for="dict in baseInfoData.trainingMethod"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="毕业时间" prop="firstRecordGraduationTime">
          <el-date-picker
            v-model="form.firstRecordGraduationTime"
            type="date"
            style="width: 207.33px"
            placeholder="选择毕业时间">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="第一学位">
          <el-select v-model="form.firstDegreeId" placeholder="请选择第一学位">
            <el-option
              v-for="dict in baseInfoData.degree"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="专业">
          <el-select v-model="form.firstDegreeMajorId" placeholder="请选择专业" @change="handleChangeA">
            <el-option
              v-for="dict in baseInfoData.profession"
              :key="dict.uuid"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="专业细分">
          <el-select v-model="form.firstDegreeMajorSpecializationId" placeholder="请选择专业细分">
            <el-option
              v-for="dict in degreeMajorSpecialization"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="是否居民">
          <el-select v-model="form.ifResidents" placeholder="请选择">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="最高学历" prop="lastRecordId">
          <el-select v-model="form.lastRecordId" placeholder="请选择">
            <el-option
              v-for="dict in baseInfoData.qualification"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="毕业学校" prop="lastRecordGraduateSchool">
          <el-input v-model="form.lastRecordGraduateSchool" placeholder="请输入毕业学校"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="培养方式">
          <el-select v-model="form.lastRecordTrainingMethod" placeholder="请选择培养方式">
            <el-option
              v-for="dict in baseInfoData.trainingMethod"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="毕业时间" prop="lastRecordGraduationTime">
          <el-date-picker
            v-model="form.lastRecordGraduationTime"
            type="date"
            style="width: 207.33px"
            placeholder="选择毕业时间">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="最高学位">
          <el-select v-model="form.lastDegreeId" placeholder="请选择最高学位">
            <el-option
              v-for="dict in baseInfoData.degree"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="专业">
          <el-select v-model="form.lastDegreeMajorId" placeholder="请选择专业" @change="handleChangeA">
            <el-option
              v-for="dict in baseInfoData.profession"
              :key="dict.uuid"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="专业细分">
          <el-select v-model="form.lastDegreeMajorSpecializationId" placeholder="请选择专业细分">
            <el-option
              v-for="dict in degreeMajorSpecialization"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="外语语种">
          <el-select v-model="form.firstForeignLanguageId" placeholder="请选择外语语种">
            <el-option
              v-for="dict in baseInfoData.foreignLanguage"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="掌握程度">
          <el-select v-model="form.firstForeignLanguageProficiencyId" placeholder="请选择掌握程度">
            <el-option
              v-for="dict in baseInfoData.mastery"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="等级证书" prop="firstForeignLanguageLevelCertificate">
          <el-input v-model="form.firstForeignLanguageLevelCertificate" placeholder="请输入等级证书"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="取得时间" prop="firstForeignLanguageAcquisitionTime">
          <el-date-picker
            v-model="form.firstForeignLanguageAcquisitionTime"
            type="date"
            style="width: 207.33px"
            placeholder="选择取得时间">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="第二外语语种">
          <el-select v-model="form.secondForeignLanguageId" placeholder="请选择第二外语语种">
            <el-option
              v-for="dict in baseInfoData.foreignLanguage"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="掌握程度">
          <el-select v-model="form.secondForeignLanguageProficiencyId" placeholder="请选择掌握程度">
            <el-option
              v-for="dict in baseInfoData.mastery"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="等级证书" prop="secondForeignLanguageLevelCertificate">
          <el-input v-model="form.secondForeignLanguageLevelCertificate" placeholder="请输入等级证书"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="取得时间" prop="secondForeignLanguageAcquisitionTime">
          <el-date-picker
            v-model="form.secondForeignLanguageAcquisitionTime"
            type="date"
            style="width: 207.33px"
            placeholder="选择取得时间">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="计算机类型" prop="computerType">
          <el-input v-model="form.computerType" placeholder="请输入计算机类型"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="掌握程度">
          <el-select v-model="form.computerTypeMastery" placeholder="请选择掌握程度">
            <el-option
              v-for="dict in baseInfoData.mastery"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="等级证书" prop="computerTypeLevelCertificate">
          <el-input v-model="form.computerTypeLevelCertificate" placeholder="请输入等级证书"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="取得时间" prop="computerTypeAcquisitionTime">
          <el-date-picker
            v-model="form.computerTypeAcquisitionTime"
            type="date"
            style="width: 207.33px"
            placeholder="选择取得时间">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="特长" prop="specialty">
          <el-input v-model="form.specialty" placeholder="请输入特长"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="办公地点" prop="officeAddress">
          <el-input v-model="form.officeAddress" placeholder="请输入办公地点"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="本人手机" prop="myMobilePhone">
          <el-input v-model="form.myMobilePhone" placeholder="请输入本人手机"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="办公电话" prop="officeTelephone">
          <el-input v-model="form.officeTelephone" placeholder="请输入办公电话"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="内线" prop="insideLine">
          <el-input v-model="form.insideLine" placeholder="请输入内线"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="办公Email" prop="officeEmail">
          <el-input v-model="form.officeEmail" placeholder="请输入"><span slot="suffix">@ejianlong.com</span></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="与原单位是否解除劳动关系" prop="ifTerminateContract">
          <el-select v-model="form.ifTerminateContract" placeholder="请选择">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="如没有，请说明原因" prop="reasonsForNotTerminatingTheContract">
          <el-input v-model="form.reasonsForNotTerminatingTheContract" placeholder="请输入未解除合同原因"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="档案所在地" prop="archivesLocation">
          <el-input v-model="form.archivesLocation" placeholder="请输入档案所在地"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="档案转入时间" prop="archivesTransferDate">
          <el-date-picker
            v-model="form.archivesTransferDate"
            type="date"
            style="width: 207.33px"
            placeholder="选择档案转入时间">
          </el-date-picker>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="档案编码" prop="archivesNumber">
          <el-input v-model="form.archivesNumber" placeholder="请输入档案编码"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="与建龙员工亲属姓名" prop="relativeFullName">
          <el-input v-model="form.relativeFullName" placeholder="请输入与建龙员工亲属姓名"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="亲属关系" prop="relationship">
          <el-input v-model="form.relationship" placeholder="请输入亲属关系"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="单位" prop="relationDepartment">
          <el-input v-model="form.relationDepartment" placeholder="请输入单位"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="联系电话" prop="relationTelephone">
          <el-input v-model="form.relationTelephone" placeholder="请输入联系电话"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="推荐人姓名" prop="recommenderName">
          <el-input v-model="form.recommenderName" placeholder="请输入推荐人姓名"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="与本人关系" prop="recommenderRelationship">
          <el-input v-model="form.recommenderRelationship" placeholder="请输入与本人关系"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="单位" prop="recommenderDepartment">
          <el-input v-model="form.recommenderDepartment" placeholder="请输入单位"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="联系电话" prop="recommenderTelephone">
          <el-input v-model="form.recommenderTelephone" placeholder="请输入联系电话"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="紧急联络人" prop="emergencyContactPerson">
          <el-input v-model="form.emergencyContactPerson" placeholder="请输入紧急联络人"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="与本人关系" prop="emergencyContactPersonRelationship">
          <el-input v-model="form.emergencyContactPersonRelationship" placeholder="请输入与本人关系"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="联系电话" prop="emergencyContactPersonTelephone">
          <el-input v-model="form.emergencyContactPersonTelephone" placeholder="请输入联系电话"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="Email" prop="emergencyContactPersonEmail">
          <el-input v-model="form.emergencyContactPersonEmail" placeholder="请输入Email"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="家庭住址" prop="homeAddress">
          <el-input v-model="form.homeAddress" placeholder="请输入家庭住址"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="邮政编码" prop="homePostCode">
          <el-input v-model="form.homePostCode" placeholder="请输入邮政编码"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="联系电话" prop="homeTelephone">
          <el-input v-model="form.homeTelephone" placeholder="请输入联系电话"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="现住址" prop="nowAddress">
          <el-input v-model="form.nowAddress" placeholder="请输入现住址"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="邮政编码" prop="nowAddressPostCode">
          <el-input v-model="form.nowAddressPostCode" placeholder="请输入邮政编码"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="联系电话" prop="nowAddressTelephone">
          <el-input v-model="form.nowAddressTelephone" placeholder="请输入联系电话"/>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="输入人" prop="creator">
          {{this.form.creator}}
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <el-form-item label="输入日期" prop="createDate">
          {{this.form.createDate}}
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
  </div>
</template>

<script>
import { getBaseInfo, getDegreeMajorSpecialization } from "@/api/human/hm/baseInfo"
import photoUpload from "@/views/human/hm/photoUpload";
import { regionData } from "element-china-area-data";
import {addPersonnelData, queryInfo} from "@/api/human/hm/personnelBasicInfo";
import {
  checkRealName,
  postalCode, validateAlphabets,
  validateEMail,
  validatePhoneTwo,
  validateSfz
} from "@/utils/jlkj";
export default {
  name: "HumanAffairsBaseInfo",
  dicts: ['sys_user_sex', 'sys_yes_no'],
  props: ['compId'],
  components: { photoUpload },
  data() {
    return {
      // 表单参数
      form: {},
      options: regionData,
      city: [],
      baseInfoData: [],
      degreeMajorSpecialization: [],
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'documentType',
          'politicalOutlook',
          'nation',
          'maritalStatus',
          'birthdaySelection',
          'nationality',
          'physicalCondition',
          'employeeSource',
          'natureOfAccount',
          'bloodGroup',
          'religiousBelief',
          'qualification',
          'trainingMethod',
          'degree',
          'profession',
          'foreignLanguage',
          'mastery']
      },
      // 表单校验
      rules: {
        empNo: [
          { pattern: /^[+]?(0|([1-9]\d*))?$/, message: "请输入数字", trigger: "blur"},
          { required: true, message: "工号不能为空", trigger: "blur" },
          { max: 6, message: '工号长度必须为6位数字', trigger: 'blur' }
        ],
        certificateTypeId: [
          { required: true, message: "证件类型不能为空", trigger: "blur" }
        ],
        certificateNumber: [
          { required: true, validator: validateSfz, trigger: "blur" }
        ],
        fullName: [
          { required: true, validator: checkRealName, trigger: "blur" }
        ],
        firstRecordGraduateSchool: [
          { pattern: /^[\u0391-\uFFE5A-Za-z]+$/, message: "请输入中文", trigger: "change" }
        ],
        lastRecordGraduateSchool: [
          { pattern: /^[\u0391-\uFFE5A-Za-z]+$/, message: "请输入中文", trigger: "change" }
        ],
        firstForeignLanguageLevelCertificate: [
          { pattern: /^[\u0391-\uFFE5A-Za-z]+$/, message: "请输入中文", trigger: "change" }
        ],
        secondForeignLanguageLevelCertificate: [
          { pattern: /^[\u0391-\uFFE5A-Za-z]+$/, message: "请输入中文", trigger: "change" }
        ],
        computerTypeLevelCertificate: [
          { pattern: /^[\u0391-\uFFE5A-Za-z]+$/, message: "请输入中文", trigger: "change" }
        ],
        computerType: [
          { pattern: /^[\u0391-\uFFE5A-Za-z]+$/, message: "请输入中文", trigger: "change" }
        ],
        specialty: [
          { pattern: /^[\u0391-\uFFE5A-Za-z]+$/, message: "请输入中文", trigger: "change" }
        ],
        officeAddress: [
          { pattern: /^[\u0391-\uFFE5A-Za-z]+$/, message: "请输入中文", trigger: "change" }
        ],
        registeredPermanentResidencePostalCode: [
          { required: false, validator: postalCode, trigger: "change" }
        ],
        homePostCode: [
          { required: false, validator: postalCode, trigger: "change" }
        ],
        nowAddressPostCode: [
          { required: false, validator: postalCode, trigger: "change" }
        ],
        englishName: [
          { required: false, validator: validateAlphabets, trigger: "blur" },
          { max: 50, message: '英文名长度不能超过50', trigger: 'blur' }
        ],
        height: [
          { pattern: /^\d+$|^\d+[.]?\d+$/, message: "请输入数字", trigger: "change"},
        ],
        weight: [
          { pattern: /^\d+$|^\d+[.]?\d+$/, message: "请输入数字", trigger: "change"},
        ],
        joinWorkDate: [
          { required: true, message: "参加工作时间不能为空", trigger: "blur" }
        ],
        entryDate: [
          { required: true, message: "入企时间不能为空", trigger: "blur" }
        ],
        workStartDate: [
          { required: true, message: "工龄起始日不能为空", trigger: "blur" }
        ],
        politicalOutlookId: [
          { required: true, message: "政治面貌不能为空", trigger: "blur" }
        ],
        maritalStatusId: [
          { required: true, message: "婚姻状态不能为空", trigger: "blur" }
        ],
        genderId: [
          { required: true, message: "性别不能为空", trigger: "blur" }
        ],
        birthdayChoice: [
          { required: true, message: "生日选择不能为空", trigger: "blur" }
        ],
        nationalityId: [
          { required: true, message: "国籍不能为空", trigger: "blur" }
        ],
        provinceOfBirthId: [
          { required: true, message: "出生地（省/市）不能为空", trigger: "blur" }
        ],
        cityOfBirthId: [
          { required: true, message: "出生地（市/区）不能为空", trigger: "blur" }
        ],
        natureOfHouseholdRegistrationId: [
          { required: true, message: "户口性质不能为空", trigger: "blur" }
        ],
        registeredPermanentResidenceProvinceId: [
          { required: true, message: "户口所在（省）不能为空", trigger: "blur" }
        ],
        registeredPermanentResidenceCityId: [
          { required: true, message: "户口所在（市）不能为空", trigger: "blur" }
        ],
        lastRecordId: [
          { required: true, message: "最高学历不能为空", trigger: "blur" }
        ],
        lastRecordGraduationTime: [
          { required: true, message: "毕业时间不能为空", trigger: "blur" }
        ],
        myMobilePhone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        officeTelephone: [
          { required: false, validator: validatePhoneTwo, trigger: "blur" }
        ],
        insideLine: [
          { required: false, validator: validatePhoneTwo, trigger: "blur" }
        ],
        emergencyContactPersonEmail: [
          { required: true, validator: validateEMail, trigger: "blur" }
        ],
        ifTerminateContract: [
          { required: true, message: "与原单位是否解除劳动关系不能为空", trigger: "blur" }
        ],
        archivesLocation: [
          { required: true, message: "档案所在地不能为空", trigger: "blur" }
        ],
        homeAddress: [
          { required: true, message: "家庭住址不能为空", trigger: "blur" }
        ],
        nowAddress: [
          { required: true, message: "现住址不能为空", trigger: "blur" }
        ],
      },
    }
  },
  watch: {
    compId(oldVal) {
      this.form.compId = oldVal
    }
  },
  created() {
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
  },
  methods: {
    queryInfo(query) {
      queryInfo(query).then(response => {
        this.form = response.data[0];
        this.$emit('empId', this.form.id)
        for (const item of this.options) {
          if (item.value == response.data[0].provinceOfBirthId){
            this.city = []
            for (const itemA of item.children) {
              this.city.push(itemA)
            }
          }
        }
      })
    },
    handleSave() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.compId = this.compId;
          addPersonnelData(this.form).then(response => {
            if (response.code == 200) {
              this.$message({
                type: 'success',
                message: response.msg
              })
            }
          })
        }
      });
    },
    handleChange(value) {
      for (const item of this.options) {
        if (item.value == value){
          this.city = []
          for (const itemA of item.children) {
            this.city.push(itemA)
          }
        }
      }
    },
    handleChangeA(value) {
      const selectedItem = this.baseInfoData.profession.find((item) => {
        return item.dicNo === value
      })
      this.baseInfo.uuid = selectedItem.uuid
      this.degreeMajorSpecialization = []
      getDegreeMajorSpecialization(this.baseInfo).then(response => {
        this.degreeMajorSpecialization = response.data
      });
    },
    handleEmpty() {
      this.form = {}
    },
    inputChange() {
      const reg =
        /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
      if (reg.test(this.form.certificateNumber)) {
        const org_birthday = this.form.certificateNumber.substring(6, 14);
        const org_gender = this.form.certificateNumber.substring(16, 17);
        const province = this.form.certificateNumber.substring(0, 2);
        const area = this.form.certificateNumber.substring(0, 6);
        const area1 = this.form.certificateNumber.substring(0, 4);
        const sex = org_gender % 2 == 1 ? "0" : "1";
        const birthday =
          org_birthday.substring(0, 4) +
          "-" +
          org_birthday.substring(4, 6) +
          "-" +
          org_birthday.substring(6, 8);
        const birthdays = new Date(birthday.replace(/-/g, "/"));
        this.form.genderId = sex;
        this.form.birthDate = birthdays;
        this.form.provinceOfBirthId = province + '0000';
        if (province == 11 || province == 12 || province == 31 || province == 50) {
          this.form.cityOfBirthId = area;
        } else {
          this.form.cityOfBirthId = area1 + '00';
        }
        this.handleChange(this.form.provinceOfBirthId);
      } else {
        this.form.genderId = "未填写";
        return false;
      }
    },
    changeCertificateTypeId(val) {
      if (val = '01') {
        this.form.nationalityId = '01'
      }
    }
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 10px;
}
</style>
