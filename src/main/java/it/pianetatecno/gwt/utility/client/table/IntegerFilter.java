/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pianetatecno.gwt.utility.client.table;

/**
 *
 * @author Daniele
 */
public class IntegerFilter extends Filter<Integer>{
    private static final long serialVersionUID = 1L;

    private Integer value;

    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Integer value) {
        this.value = value;
    }
}
