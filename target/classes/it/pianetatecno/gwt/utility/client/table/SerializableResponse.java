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
 * La risposta serializzabile ritornata dal server al client. La response ritorna una collection di dati.
 * The class extends {@code Response} and it implement getRows method. This
 * method will retur the data to the client. Using the Generics made possibile
 * to pass from server to client the user'type data.
 */
public class SerializableResponse<RowType extends Serializable> extends Response<RowType> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Collection<RowType> rows;
    private Request request;

    public SerializableResponse() {
    }

    @Override
    public Collection<RowType> getRows() {
        return rows;
    }   

    /**
     * @return the request
     */
    public Request getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
        if (request != null) {
            if (totalResults < request.getStartRow()) {
                int numPages = totalResults / request.getPageSize();
                int resto = totalResults % request.getPageSize();
                if (resto > 0 || numPages == 0) {
                    numPages++;
                }
                int startRow = 1 + (request.getPageSize() * (numPages - 1));
                this.startRow = startRow;
            } else {
                this.startRow = request.getStartRow();
            }
        }
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(Collection<RowType> rows) {
        this.rows = rows;
    }
}
