-- 测试数据
INSERT INTO sys_user (user_key,create_time,update_time,email,creator,age,phone,telphone,user_name,nick_name,pwd,sex,birthday,
head_img_url,STATUS,user_type,job_number,remark ) VALUES (REPLACE ( uuid( ), '-', '' ),now( ),now( ),'598475619@qq.com',REPLACE ( uuid( ), '-', '' ),30,
'18615406262','0531-8888888','lichao','栗子','123456',1,'1990-06-22','/imgage/headImg.png',1,0,'000001','测试账号' );
INSERT INTO sys_user (user_key,create_time,update_time,email,creator,age,phone,telphone,user_name,nick_name,pwd,sex,birthday,
head_img_url,STATUS,user_type,job_number,remark ) VALUES (REPLACE ( uuid( ), '-', '' ),now( ),now( ),'598475619@qq.com',
REPLACE ( uuid( ), '-', '' ),30,'18615406263','0531-8888888','admin','管理员','123456',1,'1990-06-22','/imgage/headImg.png',1,0,'000001','管理员账号');
