package it.pianetatecno.gwt.utility.client.table;

import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Image;

public class TableAction
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
