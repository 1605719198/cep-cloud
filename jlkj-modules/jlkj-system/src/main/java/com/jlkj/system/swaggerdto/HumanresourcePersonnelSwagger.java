package com.jlkj.system.swaggerdto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
* @TableName 人员数据
* @TableName human_hm_personnel
* @author zyf
*/
@Data
public class HumanresourcePersonnelSwagger implements Serializable {

@Schema(title = "主键")
@TableId
private String id;

@Schema(title = "工号")
private String job_number;

@Schema(title = "密码")
private String password;

@Schema(title = "证件类型ID")
private String certificate_type_id;

@Schema(title = "证件号码")
private String certificate_number;

@Schema(title = "姓名")
private String full_name;

@Schema(title = "姓名拼音")
private String full_name_pinyin;

@Schema(title = "曾用名")
private String name_used_before;

@Schema(title = "英文名")
private String english_name;

@Schema(title = "照片地址")
private String photo_address;

@Schema(title = "性别ID")
private String gender_id;

@Schema(title = "出生日期")
private String birth_date;

@Schema(title = "生日选择")
private String birthday_choice;

@Schema(title = "政治面貌ID")
private String political_outlook_id;

@Schema(title = "民族ID")
private String nation_id;

@Schema(title = "婚姻状况ID")
private String marital_status_id;

@Schema(title = "血型ID")
private String blood_type_id;

@Schema(title = "身高")
private String height;

@Schema(title = "体重")
private String weight;

@Schema(title = "国籍ID")
private String nationality_id;

@Schema(title = "出生地（省/市）ID")
private String province_of_birth_id;

@Schema(title = "出生地（市/区）ID")
private String city_of_birth_id;

@Schema(title = "身体状况ID")
private String health_id;

@Schema(title = "是否有病史")
private String if_medical_history;

@Schema(title = "病史说明")
private String medical_history_description;

@Schema(title = "宗教信仰ID")
private String religious_belief_id;

@Schema(title = "户口性质ID")
private String nature_of_household_registration_id;

@Schema(title = "户口所在地（省/市）ID")
private String registered_permanent_residence_province_id;

@Schema(title = "户口所在地（市/区）ID")
private String registered_permanent_residence_city_id;

@Schema(title = "户口所在地邮编")
private String registered_permanent_residence_postal_code;

@Schema(title = "籍贯ID")
private String native_place_id;

@Schema(title = "参加工作时间")
private String join_work_date;

@Schema(title = "入企日期")
private String entry_date;

@Schema(title = "工龄起始日")
private String work_start_date;

@Schema(title = "职工来源ID")
private String source_of_employees;

@Schema(title = "第一学历ID")
private String first_record_id;

@Schema(title = "第一学历毕业学校")
private String first_record_graduate_school;

@Schema(title = "第一学历培养方式ID")
private String first_record_training_method;

@Schema(title = "第一学历毕业时间")
private String first_record_graduation_time;

@Schema(title = "第一学位ID")
private String first_degree_id;

@Schema(title = "第一学位专业ID")
private String first_degree_major_id;

@Schema(title = "第一学位专业细分ID")
private String first_degree_major_specialization_id;

@Schema(title = "最高学历ID")
private String last_record_id;

@Schema(title = "最高学历毕业学校")
private String last_record_graduate_school;

@Schema(title = "最高学历培养方式ID")
private String last_record_training_method;

@Schema(title = "最高学历毕业时间")
private String last_record_graduation_time;

@Schema(title = "最高学位ID")
private String last_degree_id;

@Schema(title = "最高学位专业ID")
private String last_degree_major_id;

@Schema(title = "最高学位专业细分ID")
private String last_degree_major_specialization_id;

@Schema(title = "第一外语语种ID")
private String first_foreign_language_id;

@Schema(title = "第一外语掌握程度ID")
private String first_foreign_language_proficiency_id;

@Schema(title = "第一外语等级证书")
private String first_foreign_language_level_certificate;

@Schema(title = "第一外语取得时间")
private String first_foreign_language_acquisition_time;

@Schema(title = "第二外语语种ID")
private String second_foreign_language_id;

@Schema(title = "第二外语掌握程度ID")
private String second_foreign_language_proficiency_id;

@Schema(title = "第二外语等级证书")
private String second_foreign_language_level_certificate;

@Schema(title = "第二外语取得时间")
private String second_foreign_language_acquisition_time;

@Schema(title = "计算机类型")
private String computer_type;

@Schema(title = "计算机类型掌握程度")
private String computer_type_mastery;

@Schema(title = "计算机类型等级证书")
private String computer_type_level_certificate;

@Schema(title = "计算机类型取得时间")
private String computer_type_acquisition_time;

@Schema(title = "特长")
private String specialty;

@Schema(title = "本人手机")
private String my_mobile_phone;

@Schema(title = "办公电话")
private String office_telephone;

@Schema(title = "内线")
private String inside_line;

@Schema(title = "办公Email")
private String office_email;

@Schema(title = "未解除合同原因")
private String reasons_for_not_terminating_the_contract;

@Schema(title = "个人Email")
private String personnel_email;

@Schema(title = "档案所在地")
private String archives_location;

@Schema(title = "档案转入时间")
private String archives_transfer_date;

@Schema(title = "档案编码")
private String archives_number;

@Schema(title = "与建龙员工亲属姓名")
private String relative_full_name;

@Schema(title = "亲属关系")
private String relationship;

@Schema(title = "亲属单位")
private String relation_department;

@Schema(title = "亲属联系电话")
private String relation_telephone;

@Schema(title = "推荐人姓名")
private String recommender_name;

@Schema(title = "推荐人与本人关系")
private String recommender_relationship;

@Schema(title = "推荐人单位")
private String recommender_department;

@Schema(title = "推荐人联系电话")
private String recommender_telephone;

@Schema(title = "紧急联络人")
private String emergency_contact_person;

@Schema(title = "紧急联络人与本人关系")
private String emergency_contact_person_relationship;

@Schema(title = "紧急联络人联系电话")
private String emergency_contact_person_telephone;

@Schema(title = "紧急联络人Email")
private String emergency_contact_person_email;

@Schema(title = "家庭住址")
private String home_address;

@Schema(title = "家庭邮政编码")
private String home_post_code;

@Schema(title = "家庭联系电话")
private String home_telephone;

@Schema(title = "现住址")
private String now_address;

@Schema(title = "现住址邮政编码")
private String now_address_post_code;

@Schema(title = "现住址联系电话")
private String now_address_telephone;

@Schema(title = "输入人")
private String creator;

@Schema(title = "输入人ID")
private String creator_id;

@Schema(title = "输入日期")
private String create_date;

@Schema(title = "公司ID")
private String company_id;

@Schema(title = "是否在职")
private String status;

@Schema(title = "备用字段1")
private String alternate_field1;

@Schema(title = "备用字段2")
private String alternate_field2;

@Schema(title = "备用字段3")
private String alternate_field3;

@Schema(title = "备用字段4")
private String alternate_field4;

@Schema(title = "备用字段5")
private String alternate_field5;

@Schema(title = "备用字段6")
private String alternate_field6;

@Schema(title = "备用字段7")
private String alternate_field7;

@Schema(title = "备用字段8")
private String alternate_field8;

@Schema(title = "备用字段9")
private String alternate_field9;

@Schema(title = "备用字段10")
private String alternate_field10;

@Schema(title = "备用字段11")
private String alternate_field11;

@Schema(title = "备用字段12")
private String alternate_field12;

@Schema(title = "备用字段13")
private String alternate_field13;

@Schema(title = "备用字段14")
private String alternate_field14;

@Schema(title = "备用字段15")
private String alternate_field15;

@Schema(title = "备用字段16")
private String alternate_field16;

@Schema(title = "部门名称")
private String department_name;

@Schema(title = "岗位名称")
private String post_name;

@Schema(title = "创建时间")
private String create_time;

@Schema(title = "更新时间")
private String update_time;

@Schema(title = "创建人")
private String create_user;

@Schema(title = "修改人")
private String update_user;

@Schema(title = "出生日期2")
private String birth_two_date;

@Schema(title = "是否解除合同")
private String if_terminate_contract;

}
