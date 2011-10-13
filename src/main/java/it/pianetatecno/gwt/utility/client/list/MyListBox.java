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

package it.pianetatecno.gwt.utility.client.list;

import com.google.gwt.user.client.ui.ListBox;

/**
 * This class extends {@code ListBox} in order to add some usefull method. In
 * particolar are added the methods that permit you to get the index of a
 * value previously inserted in the list.
 * Furthermore there is a method that select a particular value in the list. * 
 */
public class MyListBox extends ListBox {

    /**
     * Return the index of a particular value present in the listbox.
     * @param value the value to search in the listbox
     * @return the index of the value element if present, -1 otherwise
     */
    public int getValueIndex(String value) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getValue(i).equalsIgnoreCase(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Select the item that corrispond to a particular value pass as argument.
     * @param value the item to search and select
     */
    public void setSelectedIndex(String value) {
        setSelectedIndex(getValueIndex(value));
    }
}
