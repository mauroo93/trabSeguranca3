import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class GuiListaAberta extends JLabel implements ListCellRenderer<Object> {
    public Component getListCellRendererComponent(JList<? extends Object> arg0, Object arg1, int arg2, boolean arg3,
            boolean arg4) {
        setText(((IndexElem)arg1).toString());
        if(arg3){
            GuiLista.selecionado=(IndexElem)arg1;
            //aqui deve ser encaminhado para guilista
        }
        return this;
    }
}
