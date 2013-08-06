-- likely old demo data from rice

TRUNCATE TABLE KREW_TYP_ATTR_T DROP STORAGE
/
INSERT INTO KREW_TYP_ATTR_T (ACTV, ATTR_DEFN_ID, SEQ_NO, TYP_ATTR_ID, TYP_ID, VER_NBR)
  VALUES ('Y', (SELECT ATTR_DEFN_ID FROM KREW_ATTR_DEFN_T WHERE NM = 'number' AND NMSPC_CD = 'KR-SAP'), 1, CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), (SELECT TYP_ID FROM KREW_TYP_T WHERE NM = 'Sample Type' AND NMSPC_CD = 'KR-SAP'), 1)
/
INSERT INTO KREW_TYP_ATTR_T (ACTV, ATTR_DEFN_ID, SEQ_NO, TYP_ATTR_ID, TYP_ID, VER_NBR)
  VALUES ('Y', (SELECT ATTR_DEFN_ID FROM KREW_ATTR_DEFN_T WHERE NM = 'id' AND NMSPC_CD = 'KR-SAP'), 2, CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), (SELECT TYP_ID FROM KREW_TYP_T WHERE NM = 'Sample Type' AND NMSPC_CD = 'KR-SAP'), 1)
/
