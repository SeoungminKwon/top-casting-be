INSERT INTO MEMBER (ID, USERNAME, PASSWORD, NICKNAME,NAME, EMAIL, BIRTH_DATE, ADDRESS1,ADDRESS2,ZIPCODE, PHONE_NUMBER,ROLES) VALUES (default, 'user1', '$2a$10$RipmJ2.1x5wRiO5Dx4a2SeAZRfWDiKCQEezI9TzAiP5yAfiH5jIhK', '유저1','유저1 이름','nana@naver.com', NOW(), '서울시 낙원구 행복동','46번지','17171771','01012345678','ROLE_USER');
INSERT INTO MEMBER (ID, USERNAME, PASSWORD, NICKNAME,NAME, EMAIL, BIRTH_DATE, ADDRESS1,ADDRESS2,ZIPCODE, PHONE_NUMBER,ROLES) VALUES (default, 'user2', '$2a$10$RipmJ2.1x5wRiO5Dx4a2SeAZRfWDiKCQEezI9TzAiP5yAfiH5jIhK', '유저2','유저2 이름','nana2@naver.com', NOW(), '서울특별시 서초구 서초대로65길 13-10','서초래미안아파트 101호','06602','01022223333','ROLE_USER');
INSERT INTO MEMBER (ID, USERNAME, PASSWORD, NICKNAME,NAME, EMAIL, BIRTH_DATE, ADDRESS1,ADDRESS2,ZIPCODE, PHONE_NUMBER,ROLES) VALUES (default, 'admin', '$2a$10$RipmJ2.1x5wRiO5Dx4a2SeAZRfWDiKCQEezI9TzAiP5yAfiH5jIhK', '관리자','관리자 이름','admin@naver.com', NOW(), '관리동','관리사무소','06602','01011111111','ROLE_ADMIN, ROLE_USER');

INSERT INTO MAIN_CATEGORY (ID, CATEGORY_NAME) VALUES (default, '하드베이트');
INSERT INTO MAIN_CATEGORY (ID, CATEGORY_NAME) VALUES (default, '소프트베이트');
INSERT INTO MAIN_CATEGORY (ID, CATEGORY_NAME) VALUES (default, '메탈지그&스푼');
INSERT INTO MAIN_CATEGORY (ID, CATEGORY_NAME) VALUES (default, '스커트베이트');
INSERT INTO MAIN_CATEGORY (ID, CATEGORY_NAME) VALUES (default, '각종장비');

-- 하드베이트 하위
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 1, '플로팅미노우'); --1
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 1, '메탈지그');
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 1, '타이라바');
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 1, '에기');
-- 소프트베이트 하위
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 2, '새드'); --5
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 2, '테일');
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 2, '호그');

-- 메탈지그&스푼 하위
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 3, '메탈지그'); --8
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 3, '스푼');

-- 스커트베이트 하위
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 4, '스피너베이트'); --10
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 4, '스피너');

-- 각종장비 하위
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 5, '낚시대'); --10
INSERT INTO SUB_CATEGORY (ID, MAIN_CATEGORY_ID,SUBCATEGORY_NAME) VALUES (default, 5, '악세사리');

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/9_gs_img_dae.jpg','사파이어14A','사파이어14A',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','d_사파이어14A','사파이어14A',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/10_gs_img_jung.jpg','사파이어142','사파이어142',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어142','사파이어142',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/15_gs_img_dae.jpg','사파이어91','사파이어91',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어91','사파이어91',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/19_gs_img_dae.jpg','사파이어40C','사파이어40C',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어40C','사파이어40C',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/2/135_gs_img_jung.jpg','사파이어103','사파이어103',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/9_bed3_tcs14abbf3bcbcc6e4c0ccc1f6.jpg','사파이어103','사파이어103',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/42_gs_img_dae.jpg','헬라R85새드','헬라R85새드',now()); --6
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/42_img_add11.jpg','헬라R85새드','헬라R85새드',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/46_gs_img_dae.jpg','사라리플새드4인치','사라리플새드4인치',now()); --7
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/46_img_add11.jpg','사라리플새드4인치','사라리플새드4인치',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/47_gs_img_dae.jpg','T어쌔신5인치','T어쌔신5인치',now()); --8
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/47_img_add11.jpg','T어쌔신5인치','T어쌔신5인치',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_tiny/1/37_gs_img_so.jpg','5인치 T-LINE(LT)','5인치 T-LINE(LT)',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/37_img_add11.jpg','5인치 T-LINE(LT)','5인치 T-LINE(LT)',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_tiny/1/38_gs_img_so.jpg','5인치 T-LINE(TD)','5인치 T-LINE(LT)',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/38_img_add11.jpg','5인치 T-LINE(TD)','5인치 T-LINE(LT)',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_tiny/1/40_gs_img_so.jpg','울트라R38C테일','울트라R38C테일',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/40_img_add11.jpg','울트라R38C테일','울트라R38C테일',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/2/124_gs_img_jung.jpg','TOP LT 메탈','TOP LT 메탈',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/2/124_c74e_top_lt_b8dec5bb__bbe7c0ccc6aebcf6c1a4.jpg','TOP LT 메탈','TOP LT 메탈',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/77_gs_img_dae.jpg','대구전용 메탈','대구전용 메탈',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/77_img_add11.jpg','대구전용 메탈','대구전용 메탈',now());

INSERT INTO IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_big/1/1_gs_img_dae.jpg','TPIAA 털스푼','TPIAA 털스푼',now());
INSERT INTO DETAILED_IMAGE (ID,PATH,IMAGE_NAME,FULL_NAME,CREATED_DATE) VALUES (DEFAULT,'https://www.topcasting.co.kr/shop/data/tntshop1/img_body/1/1_646b_c5d0bdbac7acb1ddbbf6bbf3bcbcc6e4c0ccc1f6.jpg','TPIAA 털스푼','TPIAA 털스푼',now());

INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어14A',9000,1,1,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어142',10000,2,2,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어91',8000,3,3,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어40C',8000,4,4,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사파이어103',12000,5,5,1,1,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'헬라R85새드',6000,6,6,2,5,NOW(),NOW()); --6
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'사라리플새드4인치',6000,7,7,2,5,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'T어쌔신5인치',4000,8,8,2,5,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'5인치 T-LINE(LT)',6000,9,9,2,6,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'5인치 T-LINE(TD)',6000,10,10,2,6,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'울트라R38C테일',6000,11,11,2,6,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'TOP LT 메탈',8000,12,12,3,8,NOW(),NOW()); --12
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'대구전용 메탈',12000,13,13,3,8,NOW(),NOW());
INSERT INTO ITEM (ID, ITEM_NAME, ITEM_PRICE, IMAGE_ID, DETAILED_IMAGE_ID, MAIN_CATEGORY_ID, SUB_CATEGORY_ID, CREATE_DATE, MODIFY_DATE) VALUES (default,'TPIAA 털스푼',5000,14,14,3,9,NOW(),NOW());

--itemId 1번의 옵션 5개
insert into option (id,item_id,color_name,stock) values (default,1,'A390',100);
insert into option (id,item_id,color_name,stock) values (default,1,'A027',200);
insert into option (id,item_id,color_name,stock) values (default,1,'A026',300);
insert into option (id,item_id,color_name,stock) values (default,1,'A028',400);
insert into option (id,item_id,color_name,stock) values (default,1,'F1104',500);
--itemId 2번의 옵션
insert into option (id,item_id,color_name,stock) values (default,2,'B390',100); --6
insert into option (id,item_id,color_name,stock) values (default,2,'B655',200);
--itemId 3번의 옵션
insert into option (id,item_id,color_name,stock) values (default,3,'91-001',100); --8
insert into option (id,item_id,color_name,stock) values (default,3,'91-002',200);
--itemId 4번의 옵션
insert into option (id,item_id,color_name,stock) values (default,4,'F1631',100); --10
insert into option (id,item_id,color_name,stock) values (default,4,'F1632',200);
--itemId 5번의 옵션
insert into option (id,item_id,color_name,stock) values (default,5,'103-001',100); --12
insert into option (id,item_id,color_name,stock) values (default,5,'103-002',200);
--itemId 6번의 옵션
insert into option (id,item_id,color_name,stock) values (default,6,'NO-001',100); --14
insert into option (id,item_id,color_name,stock) values (default,6,'NO-002',100);
insert into option (id,item_id,color_name,stock) values (default,6,'NO-003',100);
--item 7번의 옵션
insert into option (id,item_id,color_name,stock) values (default,7,'NO-001',100); --17
insert into option (id,item_id,color_name,stock) values (default,7,'NO-002',100);
insert into option (id,item_id,color_name,stock) values (default,7,'NO-005',100);
--item 8번의 옵션
insert into option (id,item_id,color_name,stock) values (default,8,'NO-001',100); --20
insert into option (id,item_id,color_name,stock) values (default,8,'NO-004',100);
insert into option (id,item_id,color_name,stock) values (default,8,'NO-038',100);

--itemId 9번의 옵션
insert into option (id,item_id,color_name,stock) values (default,9,'WATERMELON SEED',100); --23
insert into option (id,item_id,color_name,stock) values (default,9,'WATERMELON GOLD',200);

--itemId 10번의 옵션
insert into option (id,item_id,color_name,stock) values (default,10,'WATERMELON SEED',100); --25
insert into option (id,item_id,color_name,stock) values (default,10,'WATERMELON CHART',200);

--itemId 11번의 옵션
insert into option (id,item_id,color_name,stock) values (default,11,'001',100); --27
insert into option (id,item_id,color_name,stock) values (default,11,'002',200);

--itemId 12번의 옵션
insert into option (id,item_id,color_name,stock) values (default,12,'NO-001',100); --29
insert into option (id,item_id,color_name,stock) values (default,12,'NO-002',200);

--itemId 13번의 옵션
insert into option (id,item_id,color_name,stock) values (default,13,'NO-001',100); --31
insert into option (id,item_id,color_name,stock) values (default,13,'NO-002',200);

--itemId 14번의 옵션
insert into option (id,item_id,color_name,stock) values (default,14,'금-5g',100); --33
insert into option (id,item_id,color_name,stock) values (default,14,'금-7g',200);
insert into option (id,item_id,color_name,stock) values (default,14,'금-9g',200);
