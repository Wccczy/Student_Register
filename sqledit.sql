
create database StudentRegister;
use StudentRegister;
drop table if exists Re_Student;
drop table if exists Re_Class;
drop table if exists Re_RMB;



create table Re_Class
(

/*班级ID*/
classID int(20) not null auto_increment ,
/*班级名字（专业名）*/
class_name varchar(50) not null,
/*班级人数,默认为0*/
class_people int(100) not null default 0,
/*班级已经注册人数*/
class_Repeople int(100) not null default 0,
primary key (classID)
); 

create table Re_RMB
(
 /*人民币ID*/
  rmbID int(20) not null auto_increment,
 /*人民币面值*/
 RMBvalue varchar(50) not null,
 /*人民币唯一标识码*/
 rmbcode varchar(50) not null,
 primary key (rmbID)
);


create table Re_Student
(
  /*主键*/
  id int(20) not null auto_increment,
  uuid char(36) collate utf8_bin not null,
  /*学生名字*/
  Sname varchar(50) not null,
  /*学生籍贯*/
  na_place varchar(100),
  /*学生家庭住址*/
  address varchar(100),
  /*学生手机*/
  tel  varchar(20),
  /*学生父亲名字*/
  p_name varchar(50) not null,
  /*学生母亲名字*/
  m_name varchar(50) not null,
  /*学生监护人联系电话*/
  f_tel varchar(20) not null,
  /*学生第一次到校注册时间*/
  reg_time timestamp not null,
  /*是否有注册*/
  isReg boolean default false,
  /*学生身高*/
  height varchar(20),
  /*学生班级ID*/
  classID int(20) not null,
  /*学生专业*/
  pro varchar(100) not null,
  /*学生交钱RMB标识*/
  rmbID int(20) not null,
  primary key(id),
  foreign key(classID) references Re_Class (classID),
  foreign key(rmbID) references Re_RMB (rmbID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;