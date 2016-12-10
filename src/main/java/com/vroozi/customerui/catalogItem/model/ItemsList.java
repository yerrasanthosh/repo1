package com.vroozi.customerui.catalogItem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemsList implements Serializable {
    private static final long serialVersionUID = 4597937394753979693L;
    private int catalogId;
    private List<Item> itemList;
    private Item selectedItem;
    private List<Item> selectedItems;

    public ItemsList() {
        itemList = new ArrayList<Item>();
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<Item> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Item> selectedItems) {
        this.selectedItems = selectedItems;
    }
}
