/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pianetatecno.gwt.utility.client.table;

import java.util.Date;

/**
 *
 * @author Daniele
 */
public class DateFilter extends Filter<Date>{
    private static final long serialVersionUID = 1L;

    private Date value;

    @Override
    public Date getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Date value) {
        this.value = value;
    }  

}
