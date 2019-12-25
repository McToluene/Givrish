package com.example.givrish.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity(tableName = "item_sub_category")
public class ItemSubCategoryData {
	@PrimaryKey
	@NonNull
	private String item_sub_category_id;
	private String item_category_id;
	private String item_sub_category_name;
	private String item_sub_category_descp;
	private String item_sub_category_status;
	private String ref_code;
	private String item_sub_category_date;

	public ItemSubCategoryData(@NonNull String item_sub_category_id, String item_category_id, String item_sub_category_name, String item_sub_category_descp, String item_sub_category_status, String ref_code, String item_sub_category_date) {
		this.item_sub_category_id = item_sub_category_id;
		this.item_category_id = item_category_id;
		this.item_sub_category_name = item_sub_category_name;
		this.item_sub_category_descp = item_sub_category_descp;
		this.item_sub_category_status = item_sub_category_status;
		this.ref_code = ref_code;
		this.item_sub_category_date = item_sub_category_date;
	}

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
public static Comparator<ItemSubCategoryData> itemSubCategoryDataComparator =  new Comparator<ItemSubCategoryData>() {
	@Override
	public int compare(ItemSubCategoryData itemSubCategoryData, ItemSubCategoryData t1) {
		String a  = itemSubCategoryData.getItem_sub_category_name().toUpperCase();
		String b = t1.getItem_sub_category_name().toUpperCase();
		return a.compareTo(b);
	}
};

}
