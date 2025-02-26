create database if not exists EducationManagementSystem;
use EducationManagementSystem;
create table User(
    id int primary key auto_increment,
    username varchar(50) not null,
    password varchar(500) not null,
    usertype varchar(50) not null check (usertype in ('管理员', '教师', '学生')),
    affiliate_id varchar(50)
);
create table Faculty(
    faculty_id int primary key,
    faculty_name varchar(50) not null,
    faculty_address varchar(50)
);
create table Taro(
    taro_name varchar(50) not null,
    taro_id int primary key,
    affiliate_faculty_id int not null,
    foreign key (affiliate_faculty_id) references Faculty(faculty_id)
);
create table Major(
    major_id int primary key,
    major_name varchar(50) not null,
    affiliate_faculty_id int not null,
    foreign key (affiliate_faculty_id) references Faculty(faculty_id)
);
create table Teacher(
    teacher_id varchar(50) primary key,
    teacher_name varchar(50) not null,
    teacher_gender varchar(6) not null check (teacher_gender in ('男', '女', '')),
    teacher_age int not null,
    teacher_phone varchar(50),
    teacher_title varchar(50) not null,
    teacher_degree varchar(50),
    teacher_birthdate date,
    affiliate_taro_id int not null,
    foreign key (affiliate_taro_id) references Taro(taro_id)
);
create table Course(
    course_id int primary key,
    course_name varchar(50) not null,
    course_credit int not null,
    course_time int not null,
    course_room varchar(50) not null,
    affiliate_taro_id int not null,
    foreign key (affiliate_taro_id) references Taro(taro_id)
);
create table Class(
    class_id int primary key,
    class_name varchar(50) not null,
    affiliate_major_id int not null,
    foreign key (affiliate_major_id) references Major(major_id)
);
create table Student(
    student_id varchar(50) primary key,
    student_name varchar(50) not null,
    student_gender varchar(6) not null,
    student_age int not null,
    student_phone varchar(50),
    student_birthdate date,
    affiliate_class_id int not null,
    foreign key (affiliate_class_id) references Class(class_id)
);
create table Teaching(
    teacher_id varchar(50) not null,
    course_id int not null,
    primary key (teacher_id, course_id),
    teaching_type varchar(50) not null check (teaching_type in ('线上教学','线下教学','实验课') ),
    foreign key (teacher_id) references Teacher(teacher_id),
    foreign key (course_id) references Course(course_id)
);
create table Learning(
    student_id varchar(50) not null,
    course_id int not null,
    primary key (student_id, course_id),
    learning_type varchar(50) not null check (learning_type in ('选修课','必修课')),
    score int check (score >=-1 and score <= 100),
    foreign key (student_id) references Student(student_id),
    foreign key (course_id) references Course(course_id)
);
