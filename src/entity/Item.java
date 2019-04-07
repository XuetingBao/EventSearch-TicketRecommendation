package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class Item { // 将来会反馈在前端的数据
	private String itemId;
	private String name;
	private double rating;
	private String address;
	private Set<String> categories;
	private String imageUrl;
	private String url;
	private double distance;
	
	private Item(ItemBuilder builder) { // 用ItemBuilder限制只能用ItemBuilder来创建item对象
		this.itemId = builder.itemId;
		this.name = builder.name;
		this.rating = builder.rating;
		this.address = builder.address;
		this.categories = builder.categories;
		this.imageUrl = builder.imageUrl;
		this.url = builder.url;
		this.distance = builder.distance;
	}
	
	public String getItemId() {
		return itemId;
	}
	public String getName() {
		return name;
	}
	public double getRating() {
		return rating;
	}
	public String getAddress() {
		return address;
	}
	public Set<String> getCategories() {
		return categories;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getUrl() {
		return url;
	}
	public double getDistance() {
		return distance;
	}

	public JSONObject toJSONObject() { // 将数据以key.value的形式
		JSONObject object = new JSONObject();
		try {
			object.put("item_id", itemId);
			object.put("name", name);
			object.put("rating", rating);
			object.put("address", address);
			object.put("categories", new JSONArray(categories));
			object.put("image_url", imageUrl);
			object.put("url", url);
			object.put("distance", distance);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return object;
		
	}
	
	public static class ItemBuilder { // 静态内部类,用于创建item对象 （目的：防止这些item被修改，因为直接在item class里用set赋值，会被修改）
		private String itemId;
		private String name;
		private double rating;
		private String address;
		private Set<String> categories;
		private String imageUrl;
		private String url;
		private double distance;
		
		public ItemBuilder setItemId(String itemId) {
			this.itemId = itemId;
			return this;
		}
		public ItemBuilder setName(String name) {
			this.name = name;
			return this;
		}
		public ItemBuilder setRating(double rating) {
			this.rating = rating;
			return this;
		}
		public ItemBuilder setAddress(String address) {
			this.address = address;
			return this;
		}
		public ItemBuilder setCategories(Set<String> categories) {
			this.categories = categories;
			return this;
		}
		public ItemBuilder setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}
		public ItemBuilder setUrl(String url) {
			this.url = url;
			return this;
		}
		public ItemBuilder setDistance(double distance) {
			this.distance = distance;
			return this;
		}
		
		public Item build() {
			return new Item(this);
		}
		
	}
	
}
