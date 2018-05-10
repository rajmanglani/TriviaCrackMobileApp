# TriviaCrackMobileApp
create table player(
Email varchar(255),
FName varchar(255),
LName varchar(255),
Password varchar(16),
Picture blob,
level1_correct int,
level2_correct int,
level3_correct int,
level4_correct int,
level5_correct int,
primary key(Email)
);
create table categories(
categoryName varchar(255),
primary key(categoryName)
);
create table questions(
Qid varchar(255),
text varchar(1000),
question_level int,
category varchar(255),
correct_answer varchar(255),
question_type varchar(255),
primary key (Qid),
foreign key(category) references categories(categoryname)
);
create table answeroptions(
optionID varchar(255),
option_text varchar(255),
Qid varchar(255),
primary key (optionID),
foreign key(Qid) references questions(Qid)
);
