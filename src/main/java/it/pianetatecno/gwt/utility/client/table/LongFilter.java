/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.pianetatecno.gwt.utility.client.table;

/**
 *
 * @author Daniele
 */
public class LongFilter extends Filter<Long>{
    private static final long serialVersionUID = 1L;

    private Long value;

    @Override
    public Long getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Long value) {
        this.value = value;
    }
}
