-- KSCM-1836 ref-data for CM Workflow
-- This script belongs to ks-core-sql (where Org service is). Unfortunately, has a dependency on ks-enroll-sql/upgrades/06-FR2-M1/2014-06-25-Update-ORGIDs-for-sensitive-digit-number-pattern.sql
-- which is altering the Org Ids, that this script relies on.
INSERT INTO KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) 
VALUES ('ORGID-2014072301', 'admin', TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ), 'admin', TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ), 1, '', '', TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), null, 'LIFE-Life Sciences Division', 'LIFE-Life Sciences', 'Active', 'kuali.org.Division')
/
INSERT INTO KSOR_ORG_ORG_RELTN (CREATEID,CREATETIME,EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,ST,TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'F8942B6E-C499-451E-88E5-3EE98AD4B8EF',null,'ORGID-1497225962','ORGID-2014072301','Active','kuali.org.Parent2CurriculumChild','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/  
INSERT INTO KSOR_ORG_ORG_RELTN (CREATEID,CREATETIME,EFF_DT,ID,OBJ_ID,ORG,RELATED_ORG,ST,TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'9E1AA86E-EB7C-401C-9EE7-E535E9724570',null,'ORGID-2014072301', 'ORGID-4284516367','Active','kuali.org.Parent2CurriculumChild','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
DELETE FROM KSOR_ORG_ORG_RELTN WHERE ORG='ORGID-1497225962' AND RELATED_ORG='ORGID-4284516367' AND TYPE='kuali.org.Parent2CurriculumChild'
/
--ARHU-ENGLISH:Chair:Carol
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'A3397680-9631-4396-9CA6-C5B90C9BA96C', null,'ORGID-2677933260','kuali.org.PersonRelation.Chair','carol','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--ARHU-ENGLISH:Member:Doug
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'F0CF8360-855D-4594-A20A-BA0C10D28E9C', null,'ORGID-2677933260','kuali.org.PersonRelation.Member','doug','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--CMNS-CHEM:Chair:Carl
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'DCCA0C23-6F0D-45A0-8F6F-57861476EBE6', null,'ORGID-4284516367','kuali.org.PersonRelation.Chair','carl','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--CMNS-CHEM:Member:Fred
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'9E5E3290-B1B0-47F4-8B93-19E5F0180290', null,'ORGID-4284516367','kuali.org.PersonRelation.Member','fred','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--LIFE-DIV:Chair:Edna
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'28EC8D81-5B34-489D-AC94-AFF407D9D7D2', null,'ORGID-2014072301','kuali.org.PersonRelation.Chair','edna','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--LIFE-DIV:Member:Eric
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'D8EFB70B-FC64-4D28-9BF2-DB1DB4BBD1E4', null,'ORGID-2014072301','kuali.org.PersonRelation.Member','eric','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--ARTS-COLLEGE:Chair:Earl
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'BE90691E-707D-4943-AD5C-C68D1B99ADAB', null,'ORGID-1089010951','kuali.org.PersonRelation.Chair','earl','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--ARTS-COLLEGE:Member:Erin
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'3800522C-046C-42C8-91AD-9D5E5F370FBC', null,'ORGID-1089010951','kuali.org.PersonRelation.Member','erin','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--CMNS-COLLEGE:Chair:Erin
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'F4EBA8A2-A4BB-4F01-9A9B-8DA34C754947', null,'ORGID-1497225962','kuali.org.PersonRelation.Chair','erin','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--CMNS-COLLEGE:Member:Earl
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'40D5F63C-9EB2-45A7-83AB-A99D1545E96E', null,'ORGID-1497225962','kuali.org.PersonRelation.Member','earl','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--CMNS-COLLEGE:Member:Doug
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'E58E3D0E-B44C-4A7E-B1AB-7C94770BA8AE', null,'ORGID-1497225962','kuali.org.PersonRelation.Member','doug','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--SENATE:Chair:Martha
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'3AE66454-FE99-4436-9136-601195082B4E', null,'ORGID-141','kuali.org.PersonRelation.Chair','martha','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--SENATE:MEMBER:Mark
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'1FE464F7-C13F-421B-BB0B-845769D97421', null,'ORGID-141','kuali.org.PersonRelation.Member','mark','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
--Pub:Chair:Alice
INSERT INTO KSOR_ORG_PERS_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OBJ_ID,ORG,ORG_PERS_RELTN_TYPE,PERS_ID,ST,UPDATEID,UPDATETIME,VER_NBR)
VALUES ('admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),null,'8CD2B691-4256-42C1-A4B7-0D67924F66F9', null,'ORGID-176','kuali.org.PersonRelation.Chair','alice','Active','admin',TO_DATE( '20140723000000', 'YYYYMMDDHH24MISS' ),0)
/
