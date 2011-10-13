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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * This widget permits to display a paging table with remote filtering and
 * remote sorting.
 * In order to use this widget you have to:
 * 1- Create a TableModelImpl that extends {@code TableModel}
 * 2- Create a new PagingTable object
 * 3- Add the PaginTable to your panel
 */
public class PagingTable<RowType extends Serializable> extends Composite {

    private static final String CSS_PREFIX = "gwtutility-";
    private ConstantsApp constants = GWT.create(ConstantsApp.class);
    private int pageSize;
    private Widget emptyTableWidget;
    private TableModel model;
    private ColumnDefinition columnDefinition;
    private FlexTable table = new FlexTable();
    private Request request = new Request();
    private Response risposta;
    private Label labelFooter = new Label("");
    private List<Filter> filters = new LinkedList<Filter>();
    private ColumnHandler columnHandler = new ColumnHandler();
    private ActionHandler<RowType> actionHandler;
    private TableActions tableActions;
    private HorizontalPanel footer = new HorizontalPanel();
    private Image loading = new Image(GWT.getModuleBaseURL()+"/img/tableLoader.gif");

    public PagingTable(TableModel model, ColumnDefinition columnDefinition, int pageSize) {
        this(model, columnDefinition, null, pageSize);
    }

    public PagingTable(TableModel model, ColumnDefinition columnDefinition, TableActions tableActions, int pageSize) {
        this.columnDefinition = columnDefinition;
        request.setPageSize(pageSize);
        this.model = model;
        this.tableActions = tableActions;

        //VISUALIZZO LA TABELLA
        displayTable();
    }

    public PagingTable(TableModel model, ColumnDefinition columnDefinition, TableActions tableActions, int pageSize, String sortingColumn, int sortType) {
        this.columnDefinition = columnDefinition;
        request.setPageSize(pageSize);
        this.model = model;
        this.tableActions = tableActions;
        request.setSortingColumn(sortingColumn);
        request.setSortType(sortType);

        //VISUALIZZO LA TABELLA
        displayTable();
    }

    private void displayTable() {
        AbsolutePanel panel = new AbsolutePanel();

        //AGGIUNGO LA TABELLA
        panel.add(table);

        //CREO L'HEADER DELLA TABELLA
        displayHeader();

        //CREO IL FOOTER DELLA TABELLA
        panel.add(displayFooter());

        //Aggiorno i dati senza nessun filtro inizialmente
        refreshData();

        initWidget(panel);
    }

    private void refreshData() {
        if (filters != null) {
            request.setFilters(filters);
        } else {
            request.clearFilters();
        }
 
        footer.add(loading);
        
        model.requestRows(request, new Callback<RowType>() {

            @Override
            public void onFailure(Throwable caught) {
                footer.remove(loading);
                //VISUALIZZA ERRORE ALL'INTERNO DELLA TABELLA
                Window.alert(constants.error() + caught.getMessage());
            }

            @Override
            public void onRowsReady(Request request, Response<RowType> response) {
                risposta = response;
                footer.remove(loading);
                //FACCIO IL REFRESH DELLE ETICHETTE
                int risTot = risposta.getTotalResults();
                int start = risposta.getStartRow();
                int results = risposta.getRows().size();
                int pageSize = request.getPageSize();
                int resto = risTot % pageSize;
                int totPagina = risTot / pageSize;
                if (resto > 0) {
                    totPagina++;
                }

                int paginaCorrente = start / pageSize + 1;

                labelFooter.setText(" "+constants.page()+" " + paginaCorrente + " "+constants.of()+" " + totPagina);
                
                //VISUALIZZO I DATI
                int i = 1;
                int j;
                for (final RowType row : response.getRows()) {
                    j = 0;
                    if (i % 2 == 0) {
                        table.getRowFormatter().setStyleName(i, CSS_PREFIX + "table-tr-even");
                    } else {
                        table.getRowFormatter().setStyleName(i, CSS_PREFIX + "table-tr-odd");
                    }

                    for (Column c : columnDefinition.getColumns()) {
                        HTML content = new HTML(c.getValue(row));
                        table.setWidget(i, j, content);
                        table.getFlexCellFormatter().setStyleName(i, j, CSS_PREFIX + "table-td");
                        table.getFlexCellFormatter().setColSpan(i, j, 1);
                        j++;
                    }
                    //DISEGNO LE ACTIONS
                    if (tableActions != null) {
                        for (final TableActions.TableAction action : tableActions.getListActions()) {
                            HTML azioneIcon = new HTML(action.getImageHtml());
                            if(action.getAlign() != null)
                                azioneIcon.setHorizontalAlignment(action.getAlign());
                            azioneIcon.setStyleName("");
                            azioneIcon.setTitle(action.getActionName());
                            table.setWidget(i, j, azioneIcon);
                            table.getFlexCellFormatter().setStyleName(i, j, CSS_PREFIX + "table-td-icon");
                            
                            if(action.getAlign() != null)
                                table.getFlexCellFormatter().setHorizontalAlignment(i,j,action.getAlign());
                            azioneIcon.addClickHandler(new ClickHandler() {

                                @Override
                                public void onClick(ClickEvent event) {
                                    actionHandler.onActionPerformed(action.getActionName(), row);
                                }
                            });
                            j++;
                        }
                    }
                    i++;
                }
                if (table.getRowCount() > i) {
                    for (; i < table.getRowCount();) {
                        table.removeRow(i);
                    }
                }
            }
        });
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the emptyTableWidget
     */
    public Widget getEmptyTableWidget() {
        return emptyTableWidget;
    }

    /**
     * @param emptyTableWidget the emptyTableWidget to set
     */
    public void setEmptyTableWidget(Widget emptyTableWidget) {
        this.emptyTableWidget = emptyTableWidget;
    }

    /**
     * @return the model
     */
    public TableModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(TableModel model) {
        this.model = model;
    }

    /**
     * @return the columnDefinition
     */
    public ColumnDefinition getColumnDefinition() {
        return columnDefinition;
    }

    /**
     * @param columnDefinition the columnDefinition to set
     */
    public void setColumnDefinition(ColumnDefinition columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    /**
     * Metodo che si occupa di aggiungere dei filtri al model legato alla tabella.
     * The method call the refresh of data in the table.
     */
    public void filterData(List<Filter> listaFiltri) {
        //APPENA AGGIUNGO I FILTRI DEVO AGGIORNARE I DATI
        //DELLA TABELLA
        this.setFilters(listaFiltri);
        //Visualizzo la prima pagina poiché cambia la logica
        request.setStartRow(1);
        refreshData();
    }

    private void displayHeader() {
        table.setCellPadding(0);
        table.setCellSpacing(0);
        //VISUALIZZO l'HEADER
        int j = 0;
        table.getRowFormatter().setStyleName(0, CSS_PREFIX + "table-tr-head");

        for (final Column c : columnDefinition.getColumns()) {
            final HTML header = new HTML(c.getTitle() + "<span class=\"table-sorting\">&nbsp;</span>");
            if (c.isSortable()) {
                header.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        columnHandler.onClick(c, header);
                    }
                });
            }
            table.setWidget(0, j, header);
            table.getFlexCellFormatter().setStyleName(0, j, CSS_PREFIX + "table-td-head");
            j++;
        }
        //DISEGNO LE COLONNE VUOTE PER LE ACTIONS
        if (tableActions != null) {
            for (TableActions.TableAction action : tableActions.getListActions()) {
                table.setWidget(0, j, new HTML(""));
                table.getFlexCellFormatter().setStyleName(0, j, CSS_PREFIX + "table-td-head");
                j++;
            }
        }

    }

    private Widget displayFooter() {
        //AGGIUNGO IL FOOTER
        loading.setStyleName(CSS_PREFIX + "footer");
        labelFooter.setStyleName(CSS_PREFIX + "footer");
        Button btnNext = new Button("►");
        btnNext.addStyleName("valign-middle");
        btnNext.setWidth("34px");
        btnNext.setHeight("21px");
        Button btnPrev = new Button("◄");
        btnPrev.addStyleName("valign-middle");
        btnPrev.setWidth("34px");
        btnPrev.setHeight("21px");

        btnNext.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (risposta != null) {
                    int start = risposta.getStartRow();
                    int results = risposta.getRows().size();
                    int pageSize = request.getPageSize();

                    if (results > 0) {
                        //Imposto la prima riga della prossima pagina
                        request.setStartRow(start + pageSize);
                        refreshData();
                    }
                }
            }
        });

        btnPrev.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (risposta != null) {
                    int start = risposta.getStartRow();
                    int results = risposta.getRows().size();
                    int pageSize = request.getPageSize();

                    if (results > 0 && start > 1) {
                        //Imposto la prima riga della pagina precedente
                        request.setStartRow(start - pageSize);
                        refreshData();
                    }
                }
            }
        });
        footer.add(btnPrev);
        footer.add(btnNext);
        footer.add(labelFooter);

        return footer;
    }

    /**
     * Il metodo rimuove tutti i filtri dal model legato alla tabella. in questo modo vengono visualizzati
     * tutti i dati senza alcun filtro. Il metodo si occupa di fare il refresh della tabella
     */
    public void resetFilterData() {
        this.filters.clear();
    }

    /**
     * @return the actionHandler
     */
    public ActionHandler<RowType> getActionHandler() {
        return actionHandler;
    }

    /**
     * @param actionHandler the actionHandler to set
     */
    public void addActionHandler(ActionHandler<RowType> actionHandler) {
        this.actionHandler = actionHandler;
    }

    /**
     * @param filters the filters to set
     */
    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    private class ColumnHandler {

        public void onClick(Column colonnaOrdinamento, HTML header) {
            //ELIMINAZIONE FRECCIA DA COLONNE
            int j = 0;
            for (Column c : columnDefinition.getColumns()) {
                NodeList<Element> colonne = table.getWidget(0, j).getElement().getElementsByTagName("span");
                if (colonne != null && colonne.getLength() > 0) {
                    colonne.getItem(0).setInnerHTML("&nbsp;");
                }
                j++;
            }

            if (!colonnaOrdinamento.getPropertyName().equalsIgnoreCase(request.getSortingColumn())) {
                request.setSortingColumn(colonnaOrdinamento.getPropertyName());
                request.setSortType(Column.SORTING_ASC);
                header.getElement().getElementsByTagName("span").getItem(0).setInnerText("▲");
            } else {
                if (request.getSortType() == Column.SORTING_ASC) {
                    request.setSortType(Column.SORTING_DESC);
                    header.getElement().getElementsByTagName("span").getItem(0).setInnerText("▼");
                } else {
                    request.setSortType(Column.SORTING_ASC);
                    header.getElement().getElementsByTagName("span").getItem(0).setInnerText("▲");
                }
            }
            //Visualizzo la prima pagina poiché cambia la logica
            request.setStartRow(1);
            refreshData();
        }
    }

    /**
     * Effettua un refresh dei dati della tabella
     */
    public void refresh() {
        refreshData();
    }
}