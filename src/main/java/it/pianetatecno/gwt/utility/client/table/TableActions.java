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

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Image;

/**
 * This class is used from Widget {@code PagingTable}. This class permit you to define some actions that will be
 * available to all rows of the table. Every action run the onActionPerformed method so that the user can be catch the
 * event. The classical events may be: edit, delete and so on.
 **/
public class TableActions
{

    private List<TableAction> listActions = new LinkedList<TableAction>();

    public void addAction(String actionName, Image image)
    {
        getListActions().add(new TableAction(actionName, image));
    }

    public void addAction(String actionName, Image image, HorizontalAlignmentConstant alignment)
    {
        getListActions().add(new TableAction(actionName, image, alignment));
    }

    /**
     * @return the listActions
     */
    public List<TableAction> getListActions()
    {
        return listActions;
    }

    protected class TableAction
    {

        private String actionName;

        private Image image;

        private HorizontalAlignmentConstant align;

        public TableAction(String actionName, Image pImage)
        {
            this.actionName = actionName;
            this.image = pImage;
        }

        public TableAction(String actionName, Image pImage, HorizontalAlignmentConstant alignment)
        {
            this(actionName, pImage);
            this.align = alignment;
        }

        /**
         * @return the actionName
         */
        public String getActionName()
        {
            return actionName;
        }

        /**
         * @param actionName the actionName to set
         */
        public void setActionName(String actionName)
        {
            this.actionName = actionName;
        }

        /**
         * @return the imageHtml
         */
        public Image getImage()
        {
            return image;
        }

        /**
         * @param imageHtml the imageHtml to set
         */
        public void setImage(Image pImage)
        {
            this.image = pImage;
        }

        /**
         * @return the align
         */
        public HorizontalAlignmentConstant getAlign()
        {
            return align;
        }

        /**
         * @param align the align to set
         */
        public void setAlign(HorizontalAlignmentConstant align)
        {
            this.align = align;
        }
    }
}
