package com.test.amaro.amarotest.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;


public class ResponseList {

    @SerializedName("products")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    public static class Product implements Parcelable {

        @SerializedName("name")
        private String name;

        @SerializedName("image")
        private String imageUrl;

        @SerializedName("actual_price")
        private String priceActual;

        @SerializedName("regular_price")
        private String priceRegular;

        @SerializedName("on_sale")
        private boolean isOnSale;

        @SerializedName("sizes")
        private List<Size> sizeList;

        private Double price;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isOnSale() {
            return isOnSale;
        }

        public void setOnSale(boolean onSale) {
            isOnSale = onSale;
        }

        public String getPriceRegular() {
            return priceRegular;
        }

        public void setPriceRegular(String priceRegular) {
            this.priceRegular = priceRegular;
        }

        public String getPriceActual() {
            return priceActual;
        }

        public void setPriceActual(String priceActual) {
            this.priceActual = priceActual;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public List<Size> getSizeList() {
            return sizeList;
        }

        public void setSizeList(List<Size> sizeList) {
            this.sizeList = sizeList;
        }

        Product(Parcel in) {
            name = in.readString();
            imageUrl = in.readString();
            priceActual = in.readString();
            priceRegular = in.readString();
            isOnSale = in.readByte() != 0x00;
            if (in.readByte() == 0x01) {
                sizeList = new ArrayList<Size>();
                in.readList(sizeList, Size.class.getClassLoader());
            } else {
                sizeList = null;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(imageUrl);
            dest.writeString(priceActual);
            dest.writeString(priceRegular);
            dest.writeByte((byte) (isOnSale ? 0x01 : 0x00));
            if (sizeList == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeList(sizeList);
            }
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
            @Override
            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }

            @Override
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };

    }

    public static class Size implements Parcelable {

        @SerializedName("available")
        private boolean isAvailable;

        @SerializedName("size")
        public String size;

        @SerializedName("sku")
        private String sku;

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }




        Size(Parcel in) {
            isAvailable = in.readByte() != 0x00;
            size = in.readString();
            sku = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (isAvailable ? 0x01 : 0x00));
            dest.writeString(size);
            dest.writeString(sku);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<Size> CREATOR = new Parcelable.Creator<Size>() {
            @Override
            public Size createFromParcel(Parcel in) {
                return new Size(in);
            }

            @Override
            public Size[] newArray(int size) {
                return new Size[size];
            }
        };
    }
}

