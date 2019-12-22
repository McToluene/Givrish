package com.example.givrish;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;


public class ItemSubCategoryData {
private String	item_sub_category_id;

private String item_category_id;

private String item_sub_category_name;
private String item_sub_category_descp;
private String item_sub_category_status;
private String ref_code;
private String item_sub_category_date;

public String getItem_sub_category_id() {
	return item_sub_category_id;
}

public String getItem_category_id() {
	return item_category_id;
}

public String getItem_sub_category_name() {
	return item_sub_category_name;
}

public String getItem_sub_category_descp() {
	return item_sub_category_descp;
}

public String getItem_sub_category_status() {
	return item_sub_category_status;
}

public String getRef_code() {
	return ref_code;
}

public String getItem_sub_category_date() {
	return item_sub_category_date;
}

@Override
public String toString() {
	return item_sub_category_name;
}

public static Comparator<ItemSubCategoryData> SubCategoryComparator = new Comparator<ItemSubCategoryData>() {
	@Override
	public int compare(ItemSubCategoryData itemSubCategoryData, ItemSubCategoryData t1) {
		String subDataOne = itemSubCategoryData.getItem_sub_category_name().toUpperCase();
		String subDatatwo = t1.getItem_sub_category_name().toUpperCase();
		return subDataOne.compareTo(subDatatwo);
	}
};



}

