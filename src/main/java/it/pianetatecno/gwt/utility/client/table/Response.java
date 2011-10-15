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
import java.util.Collection;

/**
 * The Respose is returned from the server to the client. The object contains the number of total results and the start
 * row index. It's abstract so the {@code SerializableResponse} can implements abract methods.
 * 
 * @param <RowType>
 */
public abstract class Response<RowType> implements Serializable
{

    private static final long serialVersionUID = -6175331798611533885L;

    protected int totalResults;

    protected int startRow;

    public Response()
    {
    }

    /**
     * @return the righe
     */
    public abstract Collection<RowType> getRows();

    /**
     * @return the totalResults
     */
    public int getTotalResults()
    {
        return totalResults;
    }

    /**
     * @param totalResults the totalResults to set
     */
    public void setTotalResults(int totalResults)
    {
        this.totalResults = totalResults;
    }

    /**
     * @return the startRow
     */
    public int getStartRow()
    {
        return startRow;
    }

    /**
     * @param startRow the startRow to set
     */
    public void setStartRow(int startRow)
    {
        this.startRow = startRow;
    }
}
