package gwt.material.design.client.ui;

/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.base.HasType;
import gwt.material.design.client.base.HasWaves;
import gwt.material.design.client.base.helper.DOMHelper;
import gwt.material.design.client.base.mixin.CssTypeMixin;
import gwt.material.design.client.constants.Edge;
import gwt.material.design.client.constants.SideNavType;
import gwt.material.design.client.ui.html.ListItem;
import gwt.material.design.client.ui.html.UnorderedList;


//@formatter:off

/**
 * SideNav is a material component that gives you a lists of menus and other navigation components.
 *
 * <h3>UiBinder Usage:</h3>
 * <pre>
 * {@code
 * <m:MaterialSideNav ui:field="sideNav" width="280" m:id="mysidebar"  type="OPEN" closeOnClick="false">
 *     <m:MaterialLink href="#about" iconPosition="LEFT" icon="OUTLINE" text="About" textColor="blue"  />
 *     <m:MaterialLink href="#gettingStarted" iconPosition="LEFT" icon="DOWNLOAD" text="Getting Started" textColor="blue"  >
 * </m:MaterialSideNav>
 * }
 * </pre>
 *
 * @author kevzlou7979
 * @author Ben Dol
 * @see <a href="http://gwt-material-demo.herokuapp.com/#sidenav">Material SideNav</a>
 */
//@formatter:on
public class MaterialSideNav extends UnorderedList implements HasType<SideNavType> {

    private int width = 240;
    private Edge edge = Edge.LEFT;
    private boolean closeOnClick = false;

    private final CssTypeMixin<SideNavType, MaterialSideNav> typeMixin = new CssTypeMixin<>(this);

    /**
     * Container for App Toolbar and App Sidebar , contains Material Links,
     * Icons or any other material components.
     */
    public MaterialSideNav() {
        super();
        setStyleName("side-nav");
    }

    /**
     *  Creates a list and adds the given widgets.
     */
    public MaterialSideNav(final Widget... widgets){
        this();
        for (final Widget w : widgets) {
            add(w);
        }
    }

    @UiConstructor
    public MaterialSideNav(SideNavType type){
        this();
        setId("nav-mobile");
        setType(type);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        // Initialize the side nav
        initialize();
    }

    @Override
    public void add(Widget child) {
        if(child instanceof MaterialImage) {
            child.getElement().getStyle().setProperty("border", "1px solid #e9e9e9");
            child.getElement().getStyle().setProperty("textAlign", "center");
        }

        if(!(child instanceof ListItem)) {
            ListItem listItem = new ListItem();
            if(child instanceof MaterialCollapsible) {
                listItem.getElement().getStyle().setBackgroundColor("transparent");
            }
            if(child instanceof HasWaves) {
                listItem.setWaves(((HasWaves) child).getWaves());
                ((HasWaves) child).setWaves(null);
            }
            listItem.add(child);
            child = listItem;
        }
        child.getElement().getStyle().setDisplay(Style.Display.BLOCK);
        super.add(child);
    }

    @Override
    public void setWidth(String width) {
        setWidth(Integer.parseInt(width));
    }

    public void setWidth(int width){
        this.width = width;
        getElement().getStyle().setWidth(width, Unit.PX);
    }

    public int getWidth() {
        return width;
    }

    public boolean isCloseOnClick() {
        return closeOnClick;
    }

    public void setCloseOnClick(boolean closeOnClick){
        this.closeOnClick = closeOnClick;
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public void setType(SideNavType type) {
        typeMixin.setType(type);
    }

    @Override
    public SideNavType getType() {
        return typeMixin.getType();
    }

    public void initialize() {
        Element activator = DOMHelper.getElementByAttribute("data-activates", getId());
        if(activator != null && activator.getClassName().contains("button-collapse")) {
            initialize(activator, width, closeOnClick, edge.getCssName());
        } else {
            throw new RuntimeException("Cannot find an activator for the MaterialSideNav, " +
                "please ensure you have a MaterialNavBar with an activator setup to match " +
                "this widgets id.");
        }
    }

    private static native void initialize(Element e, int width, boolean closeOnClick, String edge)/*-{
        $wnd.jQuery(document).ready(function() {
            $wnd.jQuery(e).sideNav({
                menuWidth: width,
                edge: edge,
                closeOnClick: closeOnClick
            });
        })
    }-*/;

    /**
     * Hide the overlay menu
     */
    public native void hideOverlay()/*-{
        $wnd.jQuery(document).ready(function(){
            $wnd.jQuery('#sidenav-overlay').css('z-index', '994');
        })
    }-*/;

    /**
     * Shoe the sidenav.
     */
    public native void show(Element e)/*-{
        $wnd.jQuery(e).sideNav('show');
    }-*/;

    /**
     * Hide the sidenav.
     */
    public native void hide(Element e)/*-{
        $wnd.jQuery(e).sideNav('hide');
    }-*/;
}
