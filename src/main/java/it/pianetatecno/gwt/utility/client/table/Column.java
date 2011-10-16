/*
 * Copyright (c) 2008-2009 PianetaTecno sas http://www.pianetatecno.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package it.pianetatecno.gwt.utility.client.table;

import java.io.Serializable;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

/**
 * This class define a single Column. Every column has two types: ColType and RowType. The first is the data type that
 * is displayed in the header of the {@code PagingTable}. The second is the type of object that will be dispayed in the
 * rows of the table. The propertyName must be the name of the property of the bean that is stored in the DB.
 */
public abstract class Column<RowType extends Serializable>
{
    public static final int SORTING_ASC = 0;

    public static final int SORTING_DESC = 1;

    private String title;

    // The name of the property in the bean to store in the db
    private String propertyName;

    private boolean sortable = true;

    private HorizontalAlignmentConstant horizontalAlignement;

    private String actionName;

    public Column(String title, String propertyName, boolean sortable, HorizontalAlignmentConstant pAlignement,
        String pActionName)
    {
        this.title = title;
        this.propertyName = propertyName;
        this.sortable = sortable;
        this.horizontalAlignement = pAlignement;
        actionName = pActionName;
    }

    public Column()
    {
    }

    /**
     * @return the value
     */
    public abstract HTML getValue(RowType value);

    /**
     * @return the sortable
     */
    public boolean isSortable()
    {
        return sortable;
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    public String getStyle()
    {
        return "table-td";
    }

    public HorizontalAlignmentConstant getAlign()
    {
        return horizontalAlignement;
    }

    public String getActionName()
    {
        return actionName;
    }

}
