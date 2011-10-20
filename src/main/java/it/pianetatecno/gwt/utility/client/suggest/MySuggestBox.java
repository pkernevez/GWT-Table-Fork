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

import it.pianetatecno.gwt.utility.client.images.ImagesApp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;

/**
 * This is a simple suggest box with a close button on its right.
 * 
 */
public class MySuggestBox extends Composite implements HasClickHandlers
{

    private static final String CSS_ERROR = "textBox-error";

    private static final String CSS_PREFIX = "gwtutility-";

    private ImagesApp images = GWT.create(ImagesApp.class);

    private SuggestBox suggestBox;

    private Image immagine;

    public MySuggestBox(SuggestOracle oracle)
    {
        HorizontalPanel panel = new HorizontalPanel();
        immagine = new Image(images.iconCloseSmall());
        immagine.setStyleName(CSS_PREFIX + "pointer");

        suggestBox = new SuggestBox(oracle);
        panel.add(suggestBox);

        panel.add(immagine);
        panel.setCellVerticalAlignment(immagine, HasVerticalAlignment.ALIGN_MIDDLE);

        addHandler();

        initWidget(panel);
    }

    /**
     * @return the suggestBox
     */
    public SuggestBox getSuggestBox()
    {
        return suggestBox;
    }

    public String getText()
    {
        return suggestBox.getText();
    }

    public void markInvalid()
    {
        suggestBox.getTextBox().addStyleName(CSS_PREFIX + CSS_ERROR);
    }

    private void addHandler()
    {
        suggestBox.getTextBox().addBlurHandler(new BlurHandler()
        {

            public void onBlur(BlurEvent event)
            {
                suggestBox.getTextBox().removeStyleName(CSS_PREFIX + CSS_ERROR);
            }
        });

        immagine.addClickHandler(new ClickHandler()
        {

            public void onClick(ClickEvent event)
            {
                fireEvent(event);
            }
        });
    }

    public HandlerRegistration addClickHandler(ClickHandler handler)
    {
        return addHandler(handler, ClickEvent.getType());
    }

    public void setText(String text)
    {
        suggestBox.setText(text);
    }
}
