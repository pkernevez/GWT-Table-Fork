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
 *
 * The Request object must be send to the server in order to require some data.
 * The Request contains the filter list (will be apply to data), the sorting
 * (ASC,DESC), the start row and the limit.
 */
public class Request implements Serializable{
    private static final long serialVersionUID = 1L;

    private int startRow = 1;
    private int pageSize;
    //The list of filters that will be applied to data
    private List<Filter> filters = new LinkedList<Filter>();
    //The name of the sorting column
    private String sortingColumn;
    //The type of sorting
    private int sortType;   
 
    /**
     * Clear all filters previously added.
     */
    public void clearFilters(){
        if(getFilters() != null)
            getFilters().clear();
    }     

    /**
     * @return the sortType
     */
    public int getSortType() {
        return sortType;
    }

    /**
     * @param sortType the sortType to set
     */
    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    /**
     * @return the startRow
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * @param startRow the startRow to set
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * @return the sortingColumn
     */
    public String getSortingColumn() {
        return sortingColumn;
    }

    /**
     * @param sortingColumn the sortingColumn to set
     */
    public void setSortingColumn(String sortingColumn) {
        this.sortingColumn = sortingColumn;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the filters
     */
    public List<Filter> getFilters() {
        return filters;
    }

    /**
     * @param filters the filters to set
     */
    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
