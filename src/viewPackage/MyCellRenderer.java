/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewPackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Joachim
 */
public  class MyCellRenderer extends JLabel implements ListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String str = value.toString();
        setText(str);
        
        Map attributes = getFont().getAttributes();
        
        //AttributedString str_attribut = new AttributedString(str);
        
        if(str.isEmpty() == false && str.contains("(Abandonné)") == true){            
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        }
        setFont(new Font(attributes));
        
        if(str.isEmpty() == false && str.contains("(-") == true){
            setForeground(Color.RED);
        }
        else {
            setForeground(Color.BLACK);
        }
        if (isSelected) {
            setBackground(list.getSelectionBackground());
        }
        else {
            setBackground(list.getBackground());
        }

        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }    
}


