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
import java.util.LinkedList;
import java.util.List;

/**
 * This class define the list of columns of a {@code PaginTable}
 */
public class ColumnDefinition<RowType extends Serializable>
{

    private List<Column<RowType>> columns;

    public ColumnDefinition()
    {
        columns = new LinkedList<Column<RowType>>();
    }

    /**
     * Add a column to the list of columns
     * 
     * @param c the column to add
     */
    public void addColumn(Column<RowType> c)
    {
        if (c != null)
        {
            getColumns().add(c);
        }
    }

    /**
     * @return the columns
     */
    public List<Column<RowType>> getColumns()
    {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(List<Column<RowType>> columns)
    {
        this.columns = columns;
    }
}
