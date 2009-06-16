package org.kuali.student.lum.ui.requirements.client.view;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTabPanel;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;
import org.kuali.student.lum.ui.requirements.client.RulesUtilities;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class ComplexView extends ViewComposite {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    
    //view's widgets
    private Panel mainPanel = new SimplePanel();
    VerticalPanel complexView = new VerticalPanel();  
    private KSButton btnAddRule = new KSButton("+ Add new rule");
    private KSButton btnAddAND = new KSButton("AND");
    private KSButton btnAddOR = new KSButton("OR");
    private KSButton btnAddToGroup = new KSButton("Add to group");
    private KSButton btnUndo = new KSButton("Undo");
    private KSButton btnRedo = new KSButton("Redo");
    private KSButton btnDelete = new KSButton("Delete");
    private KSButton btnMoveRuleDown = new KSButton();
    private KSButton btnMoveRuleUp = new KSButton();
    private KSButton btnSaveRule = new KSButton("Save");     
    private KSButton btnCancelChange = new KSButton("Cancel"); 
    private KSTabPanel ruleViewsPanel = new KSTabPanel();
    private KSLabel logicalExpression = new KSLabel();
    private KSLabel simpleRuleNL = new KSLabel();
    private KSLabel complexRuleNL = new KSLabel();
    private KSLabel editManually = new KSLabel("Edit manually");    
    private String naturalLanguage;
    private RuleTable ruleTable = new RuleTable();

    private ClickHandler ruleTableSelectionHandler = null;
    private ClickHandler ruleTableToggleClickHandler = null;
    private ClickHandler ruleTableEditClauseHandler = null;
    private HandlerRegistration textClickHandler = null;
    
    //view's data
    private Model<RuleInfo> model;
    private boolean isInitialized = false;

    public ComplexView(Controller controller) {
        super(controller, "Complex View");
        super.initWidget(mainPanel);                
    }
    
    public void beforeShow() {

        if (isInitialized == false) {
            setupHandlers();            
        }            
            
        getController().requestModel(RuleInfo.class, new ModelRequestCallback<RuleInfo>() {
            public void onModelReady(Model<RuleInfo> theModel) {
                model = theModel;    
                
                if (isInitialized == false) {                    
                    model.addModelChangeHandler(new ModelChangeHandler<RuleInfo>() {
                        public void onModelChange(ModelChangeEvent<RuleInfo> event) {
                            //redraw();
                        }
                    });                          
                }
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        }); 
       
        redraw();
        
        isInitialized = true;
    }

    private void setupHandlers() {            
               
        ruleTableToggleClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = ruleTable.getCellForEvent(event);
                if (cell == null) {
                    System.out.println("Cell is NULL");
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());                 
                Object userObject = widget.getNode().getUserObject();
                
                if (userObject instanceof StatementVO) {
                    StatementVO statementVO = (StatementVO) userObject;
                    if (statementVO != null) {
                        StatementOperatorTypeKey currentOp =
                            (statementVO.getLuStatementInfo().getOperator());
                        StatementOperatorTypeKey newOp = null;
                        if (currentOp == StatementOperatorTypeKey.AND) {
                            newOp = StatementOperatorTypeKey.OR;
                        } else {
                            newOp = StatementOperatorTypeKey.AND;
                        }
                        statementVO.getLuStatementInfo().setOperator(newOp);
                        RuleInfo prereqInfo = 
                            RulesUtilities.getPrereqInfoModelObject(model);
                        prereqInfo.getStatementVO().simplify();
                        prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                    }
                    redraw();
                }
            }
        };
        
        ruleTableSelectionHandler = new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                              
                Cell cell = ruleTable.getCellForEvent(event);
                //System.out.println("Cell is NULL 0 ???");
                if (cell == null) {
                    //System.out.println("Cell is NULL 0" + event.getSource());
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());
                Object userObject = widget.getNode().getUserObject();
                if (userObject instanceof StatementVO) { 
                    StatementVO statementVO = (StatementVO) userObject;
                    statementVO.setCheckBoxOn(!statementVO.isCheckBoxOn());
                } else if (userObject instanceof ReqComponentVO) {                    
                    ReqComponentVO reqComponentVO = (ReqComponentVO) userObject;
                    reqComponentVO.setCheckBoxOn(!reqComponentVO.isCheckBoxOn());
                }
                updateTable();
            }
        };
        textClickHandler = ruleTable.addTextClickHandler(ruleTableSelectionHandler);
        
        ruleTableEditClauseHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = ruleTable.getCellForEvent(event); 
            
                if (cell == null) {
                    return;
                }
                
                RuleNodeWidget widget = (RuleNodeWidget) ruleTable.getWidget(cell.getRowIndex(), cell.getCellIndex());                 
                Object userObject = widget.getNode().getUserObject();   
                if (userObject instanceof ReqComponentVO) {
                    final ReqComponentVO rule = (ReqComponentVO) userObject;
                
                    getController().requestModel(ReqComponentVO.class, new ModelRequestCallback<ReqComponentVO>() {
                        public void onModelReady(Model<ReqComponentVO> theModel) {
                            RulesUtilities.clearModel(theModel);
                            theModel.add(rule);                            
                        }
        
                        public void onRequestFail(Throwable cause) {
                            throw new RuntimeException("Unable to connect to model", cause);
                        }
                    });                
                                    
                    getController().showView(PrereqViews.CLAUSE_EDITOR);
                }
            }
        };
        
        btnAddRule.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {      
                
                getController().requestModel(ReqComponentVO.class, new ModelRequestCallback<ReqComponentVO>() {
                    public void onModelReady(Model<ReqComponentVO> theModel) {
                        if (theModel != null) {
                            RulesUtilities.clearModel(theModel);
                        }                    
                    }

                    public void onRequestFail(Throwable cause) {
                        throw new RuntimeException("Unable to connect to model", cause);
                    }
                });                  
                
                getController().showView(PrereqViews.CLAUSE_EDITOR);
            }
        });                
        
        btnMoveRuleDown.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                StatementVO statementVO = prereqInfo.getStatementVO();
                if (statementVO != null) {
                    List<ReqComponentVO> selectedRCs =
                        statementVO.getSelectedReqComponentVOs();
                    List<StatementVO> selectedSs =
                        statementVO.getSelectedStatementVOs();
                    int numSelectedRCs = (selectedRCs == null)? 0 :
                        selectedRCs.size();
                    int numSelectedSs = (selectedSs == null)? 0 :
                        selectedSs.size();
                    ReqComponentVO selectedReqCompVO = null;
                    if (numSelectedRCs == 1 && numSelectedSs == 0) {
                        selectedReqCompVO = selectedRCs.get(0);
                        StatementVO enclosingStatementVO = 
                            prereqInfo.getStatementVO().getEnclosingStatementVO(
                                    prereqInfo.getStatementVO(), selectedReqCompVO);
                        enclosingStatementVO.shiftReqComponent("RIGHT", selectedReqCompVO);
                        prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                        redraw();
                    }
                }
            }
        });

        btnMoveRuleUp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                StatementVO statementVO = prereqInfo.getStatementVO();
                if (statementVO != null) {
                    List<ReqComponentVO> selectedRCs =
                        statementVO.getSelectedReqComponentVOs();
                    List<StatementVO> selectedSs =
                        statementVO.getSelectedStatementVOs();
                    int numSelectedRCs = (selectedRCs == null)? 0 :
                        selectedRCs.size();
                    int numSelectedSs = (selectedSs == null)? 0 :
                        selectedSs.size();
                    ReqComponentVO selectedReqCompVO = null;
                    if (numSelectedRCs == 1 && numSelectedSs == 0) {
                        selectedReqCompVO = selectedRCs.get(0);
                        StatementVO enclosingStatementVO = 
                            prereqInfo.getStatementVO().getEnclosingStatementVO(
                                    prereqInfo.getStatementVO(), selectedReqCompVO);
                        enclosingStatementVO.shiftReqComponent("LEFT", selectedReqCompVO);
                        prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                        redraw();
                    }
                }
            }
        });
        
        btnAddOR.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                prereqInfo.insertOR();
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                redraw();
            }
        });
        
        btnAddAND.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                prereqInfo.insertAND();
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                redraw();
            }
        });
        
        btnDelete.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                prereqInfo.deleteItem();
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                redraw();
            }
        });
        
        btnAddToGroup.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                prereqInfo.addToGroup();
                prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                redraw();
            }
        });
        
        editManually.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                prereqInfo.populateExpression();
                prereqInfo.setPreviewedExpression(prereqInfo.getExpression());
                getController().showView(PrereqViews.RULE_EXPRESSION_EDITOR);
            }
        });
        
        btnUndo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                StatementVO previousState = prereqInfo.getEditHistory().undo();
                if (previousState != null) {
                    prereqInfo.setStatementVO(previousState);
                }
                redraw();
            }
        });
        
        btnRedo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                StatementVO nextState = prereqInfo.getEditHistory().redo();
                if (nextState != null) {
                    prereqInfo.setStatementVO(nextState);
                }
                redraw();
            }
        });
    }
       
    private void redraw() {
        complexView.clear();
        complexView.setStyleName("Content-Margin");
        
        SimplePanel tempPanel = new SimplePanel();
        tempPanel.setStyleName("KS-Rules-FullWidth");
        KSLabel preReqHeading = new KSLabel("Manage Prerequisite Rules");
        preReqHeading.setStyleName("KS-ReqMgr-Heading");
        tempPanel.add(preReqHeading);
        complexView.add(tempPanel);
        
        //setup top rules table buttons
        HorizontalPanel topButtonsPanel = new HorizontalPanel();
        btnAddRule.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnAddRule);
        SimplePanel spacer1 = new SimplePanel();
        spacer1.setWidth("30px");
        topButtonsPanel.add(spacer1);       
        btnAddAND.addStyleName("KS-Rules-Tight-Grey-Button");
        topButtonsPanel.add(btnAddAND);   
        btnAddOR.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnAddOR);        
        btnAddToGroup.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnAddToGroup);
        SimplePanel spacer2 = new SimplePanel();
        spacer2.setWidth("30px");
        topButtonsPanel.add(spacer2);
        btnUndo.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnUndo);    
        btnRedo.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnRedo);
        SimplePanel spacer3 = new SimplePanel();
        spacer3.setWidth("30px");
        topButtonsPanel.add(spacer3);         
        btnDelete.addStyleName("KS-Rules-Tight-Grey-Button");        
        topButtonsPanel.add(btnDelete);        
        complexView.add(topButtonsPanel);        
        
        SimplePanel verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        complexView.add(verticalSpacer);        
        
        //setup Rules Table
        HorizontalPanel tempPanel2 = new HorizontalPanel();
        HorizontalPanel tableButtonsPanel = new HorizontalPanel();
        VerticalPanel arrowButtonsPanel = new VerticalPanel();
        tempPanel2.add(ruleTable);
        arrowButtonsPanel.add(btnMoveRuleUp);
        btnMoveRuleUp.setStyleName("KS-RuleTable-UpArrow");
        arrowButtonsPanel.add(btnMoveRuleDown);
        btnMoveRuleDown.setStyleName("KS-RuleTable-DownArrow");
        tableButtonsPanel.add(arrowButtonsPanel);
        tempPanel2.add(tableButtonsPanel);
        complexView.add(tempPanel2);
        SimplePanel verticalSpacer2 = new SimplePanel();
        verticalSpacer2.setHeight("30px");
        complexView.add(verticalSpacer2);                 
               
        updateRulesTable();    
        
        SimplePanel verticalSpacer3 = new SimplePanel();
        verticalSpacer3.setHeight("30px");
        complexView.add(verticalSpacer3);         
        
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        buttonsPanel.setSpacing(0);
        btnSaveRule.addStyleName("KS-Rules-Tight-Button");  
        buttonsPanel.add(btnSaveRule);
        SimplePanel horizSpacer = new SimplePanel();
        horizSpacer.setWidth("30px");        
        buttonsPanel.add(horizSpacer);        
        btnCancelChange.addStyleName("KS-Rules-Tight-Grey-Button"); 
        buttonsPanel.add(btnCancelChange);
        complexView.add(buttonsPanel);                 
        
        mainPanel.clear();
        mainPanel.add(complexView); 
    }
    
    private void updateRulesTable() {
        
        RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
        simpleRuleNL.setText("");                
        complexRuleNL.setText("");
        updateTable(); 
        
        if (prereqInfo != null) {
            
            boolean simpleRule = (prereqInfo.getStatementVO() == null)? true : prereqInfo.getStatementVO().isSimple();
            boolean emptyRule = (prereqInfo.getStatementVO() == null)? true : prereqInfo.getStatementVO().isEmpty();
            
            btnAddAND.setVisible(!simpleRule);
            btnAddOR.setVisible(!simpleRule);
            btnAddToGroup.setVisible(!simpleRule);
            btnMoveRuleUp.setVisible(!simpleRule);
            btnMoveRuleDown.setVisible(!simpleRule);           
            
            //update rule views tabs
            if (emptyRule) {    //don't show NL or tabs
                ruleViewsPanel.setVisible(false);
                KSLabel noRulesText = new KSLabel("No prerequisite rules.");
                noRulesText.setStyleName("KS-ReqMgr-NoRuleText");
                noRulesText.addStyleName("KS-Rules-BoldText");
                complexView.add(noRulesText);
            } else if (simpleRule) {                
                KSLabel nlHeading = new KSLabel("Natural Language");
                nlHeading.setStyleName("KS-ReqMgr-SubHeading"); 
                complexView.add(nlHeading);
                complexView.add(simpleRuleNL);
                updateNaturalLanguage();
                ruleViewsPanel.setVisible(false);
            } else {  //rule with 1 or more logical operators
                if (isInitialized == false) {
                    HorizontalPanel logicExpressionTab = new HorizontalPanel();
                    logicExpressionTab.add(logicalExpression);
                    editManually.addStyleName("KS-Rules-URL-Link");
                    logicExpressionTab.add(editManually);
                    ruleViewsPanel.addTab(logicExpressionTab, "Logic Expression");
                    SimplePanel NLPanel = new SimplePanel();
                    NLPanel.add(complexRuleNL);
                    //NLPanel.setStyleName("KS-Rules-Rule-Views");
                    ruleViewsPanel.addTab(NLPanel, "Natural Language");
                    ruleViewsPanel.setStyleName("KS-Rules-Rule-Views");
                    ruleViewsPanel.selectTab(0);                    
                }        
                
                KSLabel viewsHeading = new KSLabel("Alternate Views");
                viewsHeading.setStyleName("KS-ReqMgr-BlockHeading");
                complexView.add(viewsHeading);                
                complexView.add(ruleViewsPanel);
                logicalExpression.setText((prereqInfo.getStatementVO() == null ? "" : prereqInfo.getStatementVO().convertToExpression()));                
                updateNaturalLanguage();                
                ruleViewsPanel.setVisible(true);
            }                                                     
        }        
    }        
    
    private void updateTable() {        
        RuleInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
        btnAddAND.setEnabled(prereqInfo.statementVOIsGroupAble());
        btnAddOR.setEnabled(prereqInfo.statementVOIsGroupAble());  
        btnAddToGroup.setEnabled(prereqInfo.isAddToGroupOK());  
        btnUndo.setEnabled(prereqInfo.getEditHistory().isUndoable());  
        btnRedo.setEnabled(prereqInfo.getEditHistory().isRedoable());                 
        btnAddRule.setEnabled(true);
        btnDelete.setEnabled(prereqInfo.statementVOIsDegroupAble());
        btnSaveRule.setEnabled(false);  
        
        ruleTable.clear();
        Node tree = prereqInfo.getStatementTree();            
        if (tree != null) {
            ruleTable.buildTable(tree);
            textClickHandler.removeHandler();
            ruleTable.addTextClickHandler(ruleTableSelectionHandler);
            ruleTable.addToggleHandler(ruleTableToggleClickHandler);
            ruleTable.addEditClauseHandler(ruleTableEditClauseHandler);                
        }        
    }
    
    private void updateNaturalLanguage() {                 
        
        requirementsRpcServiceAsync.getNaturalLanguageForStatementVO(RulesUtilities.getPrereqInfoModelObject(model).getCluId(),
                                RulesUtilities.getPrereqInfoModelObject(model).getStatementVO(), "KUALI.CATALOG", new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                caught.printStackTrace();
           }
            
            public void onSuccess(final String statementNaturalLanguage) {               
                naturalLanguage = statementNaturalLanguage;
                simpleRuleNL.setText(naturalLanguage);                
                complexRuleNL.setText(naturalLanguage);
            } 
        }); 
    }           
}
