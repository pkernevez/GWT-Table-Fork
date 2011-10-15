/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pianetatecno.gwt.utility.client.table;

import java.io.Serializable;

/**
 * 
 * @author Daniele
 */
public abstract class Filter<Type> implements Serializable
{
    private static final long serialVersionUID = 403128909136638609L;

    private String propertyName;

    public Filter()
    {
    }

    public abstract Type getValue();

    public abstract void setValue(Type value);

    /**
     * @return the propertyName
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    /**
     * @param propertyName the propertyName to set
     */
    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

}
