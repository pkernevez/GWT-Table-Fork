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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This widget permits to display a paging table with remote filtering and remote sorting. In order to use this widget
 * you have to: 1- Create a TableModelImpl that extends {@code TableModel} 2- Create a new PagingTable object 3- Add the
 * PaginTable to your panel
 */
public class PagingTable<RowType extends Serializable> extends Composite
{

    private static final String CSS_PREFIX = "gwtutility-";

    private ConstantsApp constants = GWT.create(ConstantsApp.class);

    private int pageSize;

    private Widget emptyTableWidget;

    private TableModel<RowType> model;

    private ColumnDefinition<RowType> columnDefinition;

    private FlexTable table = new FlexTable();

    private Request request = new Request();

    private Response<?> risposta;

    private Label labelFooter = new Label("");

    private List<Filter<?>> filters = new LinkedList<Filter<?>>();

    private ColumnHandler columnHandler = new ColumnHandler();

    private ActionHandler<RowType> actionHandler;

    private HorizontalPanel footer = new HorizontalPanel();

    private Image loading = new Image(GWT.getModuleBaseURL() + "/img/tableLoader.gif");

    public PagingTable(TableModel<RowType> model, ColumnDefinition<RowType> columnDefinition, int pageSize)
    {
        this(model, columnDefinition, pageSize, null, -1);
    }

    public PagingTable(TableModel<RowType> model, ColumnDefinition<RowType> columnDefinition, int pageSize,
        String sortingColumn, int sortType)
    {
        this.columnDefinition = columnDefinition;
        request.setPageSize(pageSize);
        this.model = model;
        if (sortingColumn != null)
        {
            request.setSortingColumn(sortingColumn);
            request.setSortType(sortType);
        }
        // VISUALIZZO LA TABELLA
        displayTable();
    }

    private void displayTable()
    {
        AbsolutePanel panel = new AbsolutePanel();

        // AGGIUNGO LA TABELLA
        panel.add(table);

        // CREO L'HEADER DELLA TABELLA
        displayHeader();

        // CREO IL FOOTER DELLA TABELLA
        panel.add(displayFooter());

        // Aggiorno i dati senza nessun filtro inizialmente
        refreshData();

        initWidget(panel);
    }

    private void refreshData()
    {
        if (filters != null)
        {
            request.setFilters(filters);
        } else
        {
            request.clearFilters();
        }

        footer.add(loading);

        model.requestRows(request, new Callback<RowType>()
        {

            @Override
            public void onFailure(Throwable caught)
            {
                footer.remove(loading);
                // VISUALIZZA ERRORE ALL'INTERNO DELLA TABELLA
                Window.alert(constants.error() + caught.getMessage());
            }

            @Override
            public void onRowsReady(Request request, Response<RowType> response)
            {
                risposta = response;
                footer.remove(loading);
                // FACCIO IL REFRESH DELLE ETICHETTE
                int risTot = risposta.getTotalResults();
                int start = risposta.getStartRow();
                int pageSize = request.getPageSize();
                int resto = risTot % pageSize;
                int totPagina = risTot / pageSize;
                if (resto > 0)
                {
                    totPagina++;
                }

                int paginaCorrente = start / pageSize + 1;

                labelFooter.setText(" " + constants.page() + " " + paginaCorrente + " " + constants.of() + " "
                    + totPagina);

                // VISUALIZZO I DATI
                int i = 1;
                int j;
                for (final RowType row : response.getRows())
                {
                    j = 0;
                    if (i % 2 == 0)
                    {
                        table.getRowFormatter().setStyleName(i, CSS_PREFIX + "table-tr-even");
                    } else
                    {
                        table.getRowFormatter().setStyleName(i, CSS_PREFIX + "table-tr-odd");
                    }

                    for (final Column<RowType> c : columnDefinition.getColumns())
                    {
                        HTML tCell = c.getValue(row);
                        table.setWidget(i, j, tCell);
                        table.getFlexCellFormatter().setStyleName(i, j, CSS_PREFIX + c.getStyle());
                        table.getFlexCellFormatter().setColSpan(i, j, 1);
                        if (c.getAlign() != null)
                        {
                            table.getFlexCellFormatter().setHorizontalAlignment(i, j, c.getAlign());
                        }
                        if (c.getActionName() != null)
                        {
                            tCell.addClickHandler(new ClickHandler()
                            {

                                @Override
                                public void onClick(ClickEvent event)
                                {
                                    actionHandler.onActionPerformed(c.getActionName(), row);
                                }
                            });
                            tCell.setTitle(c.getActionName());
                        }
                        j++;
                    }

                    i++;
                }
                if (table.getRowCount() > i)
                {
                    for (; i < table.getRowCount();)
                    {
                        table.removeRow(i);
                    }
                }
            }
        });
    }

    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * @return the emptyTableWidget
     */
    public Widget getEmptyTableWidget()
    {
        return emptyTableWidget;
    }

    /**
     * @param emptyTableWidget the emptyTableWidget to set
     */
    public void setEmptyTableWidget(Widget emptyTableWidget)
    {
        this.emptyTableWidget = emptyTableWidget;
    }

    /**
     * @return the model
     */
    public TableModel<RowType> getModel()
    {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(TableModel<RowType> model)
    {
        this.model = model;
    }

    /**
     * @return the columnDefinition
     */
    public ColumnDefinition<RowType> getColumnDefinition()
    {
        return columnDefinition;
    }

    /**
     * @param columnDefinition the columnDefinition to set
     */
    public void setColumnDefinition(ColumnDefinition<RowType> columnDefinition)
    {
        this.columnDefinition = columnDefinition;
    }

    /**
     * Metodo che si occupa di aggiungere dei filtri al model legato alla tabella. The method call the refresh of data
     * in the table.
     */
    public void filterData(List<Filter<?>> listaFiltri)
    {
        // APPENA AGGIUNGO I FILTRI DEVO AGGIORNARE I DATI
        // DELLA TABELLA
        this.setFilters(listaFiltri);
        // Visualizzo la prima pagina poiché cambia la logica
        request.setStartRow(1);
        refreshData();
    }

    @SuppressWarnings("unused")
    private void displayHeader()
    {
        table.setCellPadding(0);
        table.setCellSpacing(0);
        // VISUALIZZO l'HEADER
        int j = 0;
        table.getRowFormatter().setStyleName(0, CSS_PREFIX + "table-tr-head");

        for (final Column<RowType> c : columnDefinition.getColumns())
        {
            final HTML header = new HTML(c.getTitle() + "<span class=\"table-sorting\">&nbsp;</span>");
            if (c.isSortable())
            {
                header.addClickHandler(new ClickHandler()
                {

                    @Override
                    public void onClick(ClickEvent event)
                    {
                        columnHandler.onClick(c, header);
                    }
                });
            }
            table.setWidget(0, j, header);
            table.getFlexCellFormatter().setStyleName(0, j, CSS_PREFIX + "table-td-head");
            j++;
        }
    }

    private Widget displayFooter()
    {
        // AGGIUNGO IL FOOTER
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

        btnNext.addClickHandler(new ClickHandler()
        {

            @Override
            public void onClick(ClickEvent event)
            {
                if (risposta != null)
                {
                    int start = risposta.getStartRow();
                    int results = risposta.getRows().size();
                    int pageSize = request.getPageSize();

                    if (results > 0)
                    {
                        // Imposto la prima riga della prossima pagina
                        request.setStartRow(start + pageSize);
                        refreshData();
                    }
                }
            }
        });

        btnPrev.addClickHandler(new ClickHandler()
        {

            @Override
            public void onClick(ClickEvent event)
            {
                if (risposta != null)
                {
                    int start = risposta.getStartRow();
                    int results = risposta.getRows().size();
                    int pageSize = request.getPageSize();

                    if (results > 0 && start > 1)
                    {
                        // Imposto la prima riga della pagina precedente
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
     * Il metodo rimuove tutti i filtri dal model legato alla tabella. in questo modo vengono visualizzati tutti i dati
     * senza alcun filtro. Il metodo si occupa di fare il refresh della tabella
     */
    public void resetFilterData()
    {
        this.filters.clear();
    }

    /**
     * @return the actionHandler
     */
    public ActionHandler<RowType> getActionHandler()
    {
        return actionHandler;
    }

    /**
     * @param actionHandler the actionHandler to set
     */
    public void addActionHandler(ActionHandler<RowType> actionHandler)
    {
        this.actionHandler = actionHandler;
    }

    /**
     * @param filters the filters to set
     */
    public void setFilters(List<Filter<?>> filters)
    {
        this.filters = filters;
    }

    private class ColumnHandler
    {

        @SuppressWarnings("unused")
        public void onClick(Column<RowType> colonnaOrdinamento, HTML header)
        {
            // ELIMINAZIONE FRECCIA DA COLONNE
            int j = 0;
            for (Column<RowType> c : columnDefinition.getColumns())
            {
                NodeList<Element> colonne = table.getWidget(0, j).getElement().getElementsByTagName("span");
                if (colonne != null && colonne.getLength() > 0)
                {
                    colonne.getItem(0).setInnerHTML("&nbsp;");
                }
                j++;
            }

            if (colonnaOrdinamento.getPropertyName() != null)
            {
                if (!colonnaOrdinamento.getPropertyName().equalsIgnoreCase(request.getSortingColumn()))
                {
                    request.setSortingColumn(colonnaOrdinamento.getPropertyName());
                    request.setSortType(Column.SORTING_ASC);
                    header.getElement().getElementsByTagName("span").getItem(0).setInnerText("▲");
                } else
                {
                    if (request.getSortType() == Column.SORTING_ASC)
                    {
                        request.setSortType(Column.SORTING_DESC);
                        header.getElement().getElementsByTagName("span").getItem(0).setInnerText("▼");
                    } else
                    {
                        request.setSortType(Column.SORTING_ASC);
                        header.getElement().getElementsByTagName("span").getItem(0).setInnerText("▲");
                    }
                }
            }
            // Visualizzo la prima pagina poiché cambia la logica
            request.setStartRow(1);
            refreshData();
        }
    }

    /**
     * Effettua un refresh dei dati della tabella
     */
    public void refresh()
    {
        refreshData();
    }
}
