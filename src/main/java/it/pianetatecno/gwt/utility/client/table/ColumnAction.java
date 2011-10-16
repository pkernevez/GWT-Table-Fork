package it.pianetatecno.gwt.utility.client.table;

import java.io.Serializable;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Image;

public class ColumnAction<RowType extends Serializable> extends Column<RowType>
{
    Image image;

    public ColumnAction()
    {
        super();
    }

    public ColumnAction(String pActionName, Image pImage, HorizontalAlignmentConstant pAlignement)
    {
        super("", null, false, pAlignement, pActionName);
        image = pImage;
    }

    @Override
    public HTML getValue(RowType pValue)
    {
        return new HTML(image.getElement().getString());
    }

    @Override
    public String getStyle()
    {
        return "table-td-icon";
    }
}
