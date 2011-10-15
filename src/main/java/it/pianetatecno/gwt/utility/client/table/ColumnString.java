package it.pianetatecno.gwt.utility.client.table;

import java.io.Serializable;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/** Implementation for column presenting their data as String. */
public abstract class ColumnString<RowType extends Serializable> extends Column<RowType>
{
    public abstract String getStringValue(RowType pValue);

    public ColumnString()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public ColumnString(String pTitle, String pPropertyName, boolean pSortable)
    {
        super(pTitle, pPropertyName, pSortable);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Widget getValue(RowType pValue)
    {
        return new HTML(getStringValue(pValue));
    }
}
