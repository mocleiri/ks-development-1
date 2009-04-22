package org.kuali.student.ui.kitchensink.client.kscommons.modaldialogpanel;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ModalDialogPanelExampleDescriptor extends KitchenSinkExample {
    public ModalDialogPanelExampleDescriptor() {
        super();
        super.addResource("java", "ModalDialogPanelExample.java", "kscommons/modaldialogpanel/ModalDialogPanelExample.java", "Example usage of KSModalDialogPanel.");
        super.addResource("css", "KSModalDialogPanel.css", "KSModalDialogPanel.css", "Default styling of KSModalDialogPanel.");
        super.addResource("css", "KSResizablePanel.css", "KSResizablePanel.css", "Default styling of KSResizablePanel.");
        super.addResource("css", "KSDialogPanel.css", "KSDialogPanel.css", "Default styling of KSDialogPanel.");
        super.addCssResource(KSCommonResources.INSTANCE.modalDialogPanelCss());
        super.addCssResource(KSCommonResources.INSTANCE.resizablePanelCss());
        super.addCssResource(KSCommonResources.INSTANCE.dialogPanelCss());
    }
    public String getDescription() {       
        return "ModalDialogPanel is a moveable, modal panel that can pop up over other widgets. It overlays the browser's client area (and any previously-created popups) "; 
    }

    public Widget getExampleWidget() {
        return new ModalDialogPanelExample();
    }
 
    public String getTitle() {
        return "Modal Dialog Panel";
    }

}
