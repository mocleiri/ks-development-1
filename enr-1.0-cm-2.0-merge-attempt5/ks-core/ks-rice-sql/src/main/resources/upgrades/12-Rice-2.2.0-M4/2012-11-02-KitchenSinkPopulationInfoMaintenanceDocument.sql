-- sequences are not set correctly, so use hopefully-unique values instead

INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
    VALUES (77300,(SELECT KREW_DOC_TYP_T.DOC_TYP_ID FROM KREW_DOC_TYP_T WHERE KREW_DOC_TYP_T.DOC_TYP_NM='RiceDocument'),'KitchenSinkPopulationInfoMaintenanceDocument',0,1,1,'Create KSKS PopulationInfo Maintenance Document','KSKS PopulationInfo Maintenance Document',NULL,'','${application.url}/kr-krad/maintenance?methodToCall=docHandler&dataObjectClassName=org.kuali.student.r2.core.population.dto.PopulationInfo','','','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor','1','1','','','2','','','','',SYS_GUID(),1)
    --VALUES (KREW_DOC_HDR_S.NEXTVAL, ...
/

INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR)
    VALUES (77400,(SELECT KREW_DOC_TYP_T.DOC_TYP_ID FROM KREW_DOC_TYP_T WHERE KREW_DOC_TYP_T.DOC_TYP_NM='KitchenSinkPopulationInfoMaintenanceDocument'),'Initiated','org.kuali.rice.kew.engine.node.InitialNode','',0,0,'1','','P','','','1')
    --VALUES (KREW_RTE_NODE_S.NEXTVAL, ...
/

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL)
    VALUES (77320,77400,'contentFragment','<start name="Initiated"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>')
    --VALUES (KREW_RTE_NODE_CFG_PARM_S.NEXTVAL,KREW_RTE_NODE_S.CURRVAL, ...
/

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL)
    VALUES (77321,77400,'activationType','P')
    --VALUES (KREW_RTE_NODE_CFG_PARM_S.NEXTVAL,KREW_RTE_NODE_S.CURRVAL,'activationType','P')
/

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL)
    VALUES (77322,77400,'mandatoryRoute',0)
    --VALUES (KREW_RTE_NODE_CFG_PARM_S.NEXTVAL,KREW_RTE_NODE_S.CURRVAL,'mandatoryRoute',0)
/

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL)
    VALUES (77323,77400,'finalApproval',0)
    --VALUES (KREW_RTE_NODE_CFG_PARM_S.NEXTVAL,KREW_RTE_NODE_S.CURRVAL,'finalApproval',0)
/

INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL)
    VALUES (77324,77400,'ruleSelector','Template')
    --VALUES (KREW_RTE_NODE_CFG_PARM_S.NEXTVAL,KREW_RTE_NODE_S.CURRVAL,'ruleSelector','Template')
/

INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR)
    VALUES (77301,(SELECT KREW_DOC_TYP_T.DOC_TYP_ID FROM KREW_DOC_TYP_T WHERE KREW_DOC_TYP_T.DOC_TYP_NM='KitchenSinkPopulationInfoMaintenanceDocument'),77400,'PRIMARY',1,'1')
    --VALUES (KREW_DOC_HDR_S.NEXTVAL,(SELECT KREW_DOC_TYP_T.DOC_TYP_ID FROM KREW_DOC_TYP_T WHERE KREW_DOC_TYP_T.DOC_TYP_NM='KitchenSinkPopulationInfoMaintenanceDocument'),KREW_RTE_NODE_S.CURRVAL,'PRIMARY',1,'1')
/
