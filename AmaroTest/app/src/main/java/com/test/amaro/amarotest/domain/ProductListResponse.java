package com.test.amaro.amarotest.domain;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;


/**
 *  Model used to parse response from server.
 *  It represents a list of Product object (@see Product object)
 */
public class ProductListResponse {

    @SerializedName("products")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    /**
     *  An object that represents a single Amaro product.
     *
     */
    public static class Product implements Parcelable {

        /**
         * The product name
         */
        @SerializedName("name")
        private String name;

        /**
         * The image which represent the product
         */
        @SerializedName("image")
        private String imageUrl;

        /**
         *  The promotional price of a product.
         *  If a product is on sale, actual price (or promotional price)
         *  is lower than regular price. In case product is not on sale,
         *  promotional price is the same as regular price.
         */
        @SerializedName("actual_price")
        private String pricePromotional;

        /**
         * Price of a product in normal conditions
         */
        @SerializedName("regular_price")
        private String priceRegular;

        /**
         *  Boolean which represents on sale status of a product.
         */
        @SerializedName("on_sale")
        private boolean isOnSale;

        /**
         *  A list of all available sizes of the product
         */
        @SerializedName("sizes")
        private List<Size> sizeList;

        /**
         *  The lower price (between promotional and regular) of the product,
         *  in Double.
         */
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

        public String getPricePromotional() {
            return pricePromotional;
        }

        public void setPricePromotional(String pricePromotional) {
            this.pricePromotional = pricePromotional;
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
            pricePromotional = in.readString();
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
            dest.writeString(pricePromotional);
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

    /**
     *  Class that provides information regarding the
     *  size of the product.
     */
    public static class Size implements Parcelable {

        /**
         *  Flag that tells if size is available or not
         */
        @SerializedName("available")
        private boolean isAvailable;

        /**
         *  Size's name representation (e.g. PP, P, M, G, GG)
         */
        @SerializedName("size")
        public String size;

        /**
         *  Serial number of the product and its size
         */
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

