-- likely old demo data from rice

TRUNCATE TABLE KREW_ATTR_DEFN_T DROP STORAGE
/
INSERT INTO KREW_ATTR_DEFN_T (ACTV,ATTR_DEFN_ID,CMPNT_NM,LBL,NM,NMSPC_CD,VER_NBR)
  VALUES ('Y',CONCAT('KS-', KS_RICE_ID_S.NEXTVAL),'edu.sampleu.travel.bo.TravelAccount','Travel Number','number','KR-SAP',1)
/
INSERT INTO KREW_ATTR_DEFN_T (ACTV,ATTR_DEFN_ID,CMPNT_NM,NM,NMSPC_CD,VER_NBR)
  VALUES ('Y',CONCAT('KS-', KS_RICE_ID_S.NEXTVAL),'edu.sampleu.travel.bo.FiscalOfficer','id','KR-SAP',1)
/
