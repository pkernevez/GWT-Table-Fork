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
package it.pianetatecno.gwt.utility.client.suggest;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This widget permit you to display a SuggestBox with a link in the bottom
 * with which the user can add gracefully a new suggest box and so on. The list
 * of suggest box has only one oracle {@code MultiWordSuggestOracle}.
 * Attually the oracle is populated only once when the user create the widget
 * so ALL the data are fetch immediately.
 *
 * @param <Type>
 */
public abstract class SuggestList<Type extends Serializable> extends Composite {

    private static final String CSS_PREFIX = "gwtutility-";
    private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
    private List<MySuggestBox> listaSuggest = new LinkedList<MySuggestBox>();
    private HashMap<String, Type> mappaOggetti = new HashMap<String, Type>();
    private HashMap<String, Type> mappaOggettiScelti = new HashMap<String, Type>();
    private FlexTable tableOggetti = new FlexTable();
    private String labelAdd = "Aggiungi...";
    private HTML linkAdd = new HTML("<span class=\"gwtutility-link\" role=\"link\">" + getLabelAdd() + "</span>");
    private RemoveBoxIspettoreHandler ispHandler = new RemoveBoxIspettoreHandler();
    private int maxListSize = 10;
    private SuggestCallback<Type> callback;
    private boolean valid = true;

    public SuggestList() {
        AbsolutePanel panel = new AbsolutePanel();
        //tableOggetti.setCellSpacing(5);

        panel.add(tableOggetti);

        //Aggiorno la lista
        refreshOggetti();

        linkAdd.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (listaSuggest.size() < maxListSize) {
                    MySuggestBox box = new MySuggestBox(oracle);
                    box.addClickHandler(ispHandler);
                    listaSuggest.add(box);
                    refreshOggetti();
                }
            }
        });
        panel.add(linkAdd);

        inizializzaWidget();

        initWidget(panel);
    }

    private void inizializzaWidget() {
        //AGGIUNGO IL PRIMO BOX CHE NON PUO'ESSERE ELIMINATO
        MySuggestBox bx1 = new MySuggestBox(getOracle());
        listaSuggest.add(bx1);
        tableOggetti.setWidget(0, 0, bx1);
    }

    /**
     * Metodo di utilità interna. Si occupa di ridisegnare la tabella che mostra
     * i suggest box
     */
    private void refreshOggetti() {
        tableOggetti.clear();
        int count = tableOggetti.getRowCount() - 1;
        while (count >= 0) {
            tableOggetti.removeRow(count);
            count--;
        }
        int i = 0;
        for (MySuggestBox riga : listaSuggest) {
            tableOggetti.setWidget(i, 0, riga);
            i++;
        }
    }

    /**
     * @return the oracle
     */
    public MultiWordSuggestOracle getOracle() {
        return oracle;
    }

    /**
     * @param oracle the oracle to set
     */
    public void setOracle(MultiWordSuggestOracle oracle) {
        this.oracle = oracle;
    }  

    /**
     * @param label
     */
    public void setLabelAdd(String label) {
        this.labelAdd = label;
        linkAdd.setHTML("<span class=\"gwtutility-link\" role=\"link\">" + getLabelAdd() + "</span>");
    }

    /**
     * @return the maxListSize
     */
    public int getMaxListSize() {
        return maxListSize;
    }

    /**
     * @param maxListSize the maxListSize to set
     */
    public void setMaxListSize(int maxListSize) {
        this.maxListSize = maxListSize;
    }

    /**
     * @return the callback
     */
    public SuggestCallback<Type> getCallback() {
        return callback;
    }

    /**
     * @param callback the callback to set
     */
    public void setCallback(SuggestCallback<Type> callback) {
        this.callback = callback;
        //POPOLO l'ORACLE
        oracle.addAll(getCollectionFromData());
    }

    /**
     * @return the valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @return the labelAdd
     */
    public String getLabelAdd() {
        return labelAdd;
    }

    private class RemoveBoxIspettoreHandler implements ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
            listaSuggest.remove(event.getSource());
            refreshOggetti();
        }
    }

    public HashMap<String, Type> getOggettiScelti() {
        valid = true;
        mappaOggettiScelti.clear();
        for (MySuggestBox box : listaSuggest) {
            if (mappaOggetti.get(box.getText()) != null) {
                mappaOggettiScelti.put(box.getText(), mappaOggetti.get(box.getText()));
            } else {
                //Visualizzo l'errore
                box.markInvalid();
                valid = false;
            }
        }
        return mappaOggettiScelti;
    }

    public List<Type> getListOggettiScelti() {
        List<Type> lista = new LinkedList<Type>();
        for (Type obj : getOggettiScelti().values()) {
            lista.add(obj);
        }
        return lista;
    }

    public void setListOggettiScelti(List<Type> list) {
        mappaOggettiScelti.clear();
        listaSuggest.clear();
        int i =0;
        //If the list is empty is displayed only one suggest box with empty text
        if(list.isEmpty()){
            MySuggestBox box = new MySuggestBox(oracle);
            listaSuggest.add(box);
            box.setText("");
        }
        for (Type obj : list) {
            MySuggestBox box = new MySuggestBox(oracle);
            if(i > 0)
                box.addClickHandler(ispHandler);
            listaSuggest.add(box);
            box.setText(getKey(obj));
            i++;
        }
        refreshOggetti();
    }

    public abstract String getKey(Type obj);

    private Collection<String> getCollectionFromData() {
        for(Type o:callback.getData()){
            mappaOggetti.put(getKey(o),o);
        }
        return mappaOggetti.keySet();
    }

    public void reset() {
        tableOggetti.clear();
        listaSuggest.clear();
        mappaOggettiScelti.clear();
        valid = true;
        inizializzaWidget();
    }
}
