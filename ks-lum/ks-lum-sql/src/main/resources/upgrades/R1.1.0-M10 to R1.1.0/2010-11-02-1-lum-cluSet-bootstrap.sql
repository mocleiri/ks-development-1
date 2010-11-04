ALTER TABLE
    KSLU_CLU_SET_JN_CLU ADD (ID VARCHAR2(255) )/
ALTER TABLE
    KSLU_CLU_SET_JN_CLU ADD (VER_NBR NUMBER(19) )/
ALTER TABLE
    KSLU_CLU_SET_JN_CLU ADD (OBJ_ID VARCHAR2(36) )/
ALTER TABLE
    KSLU_CLU_SET_JN_CLU RENAME COLUMN CLU_ID TO CLU_VER_IND_ID/
ALTER TABLE
    KSLU_CLU_SET_JN_CLU DROP CONSTRAINT KSLU_CLU_SET_JN_CLU_FK2
/

DROP INDEX "KSLU_CLU_SET_JN_CLU_I1"
/
DROP INDEX "KSLU_CLU_SET_JN_CLU_I2"
/

UPDATE
    KSLU_CLU
SET
    VER_IND_ID = ID,
    SEQ_NUM = 1,
    CURR_VER_START = TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' )
WHERE
    VER_IND_ID IS NULL AND LUTYPE_ID = 'kuali.lu.type.CreditCourse'
/

UPDATE
    KSLU_CLU_SET_JN_CLU j
SET
    j.ID = SYS_GUID(),
    j.OBJ_ID = SYS_GUID(),
    j.VER_NBR = 0,
    j.CLU_VER_IND_ID =
    (
        SELECT
            c.VER_IND_ID
        FROM
            KSLU_CLU c
        WHERE
            j.CLU_VER_IND_ID=c.ID
        AND c.VER_IND_ID IS NOT NULL
    )
WHERE
    EXISTS
    (
        SELECT
            c.VER_IND_ID
        FROM
            KSLU_CLU c
        WHERE
            j.CLU_VER_IND_ID=c.ID
        AND c.VER_IND_ID IS NOT NULL
    )
/    
    
    
ALTER TABLE
    KSLU_CLU_SET_JN_CLU ADD PRIMARY KEY (ID)
/

ALTER TABLE
    KSLU_CLU_SET_JN_CLU ADD CONSTRAINT KSLU_CLU_SET_JN_CLU_I1 UNIQUE (CLU_SET_ID,
    CLU_VER_IND_ID)
/