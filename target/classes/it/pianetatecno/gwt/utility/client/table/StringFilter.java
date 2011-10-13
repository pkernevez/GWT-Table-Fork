/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pianetatecno.gwt.utility.client.table;

/**
 *
 * @author Daniele
 */
public class StringFilter extends Filter<String>{
    private static final long serialVersionUID = 1L;

    private String value;

    @Override
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }  

}
