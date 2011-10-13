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

/**
 * This class represent the model that must be used with {@code PagingTable}.
 * This class define an abstract method requestRows that will be implement
 * from the user to retrieve his data from the server. *
 */
public abstract class TableModel<RowType extends Serializable> {

    public abstract void requestRows(final Request request, final Callback<RowType> callback);

}
