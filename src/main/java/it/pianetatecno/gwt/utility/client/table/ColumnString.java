package it.pianetatecno.gwt.utility.client.table;

import java.io.Serializable;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

/** Implementation for column presenting their data as String. */
public abstract class ColumnString<RowType extends Serializable> extends Column<RowType>
{
    public abstract String getStringValue(RowType pValue);

    public ColumnString()
    {
        super();
    }

    public ColumnString(String pTitle, String pPropertyName, boolean pSortable)
    {
        this(pTitle, pPropertyName, pSortable, null);
    }

    public ColumnString(String pTitle, String pPropertyName, boolean pSortable, HorizontalAlignmentConstant pAlignement)
    {
        super(pTitle, pPropertyName, pSortable, pAlignement, null);
    }

    @Override
    public HTML getValue(RowType pValue)
    {
        return new HTML(getStringValue(pValue));
    }
}
