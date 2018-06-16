package com.test.amaro.amarotest.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.amaro.amarotest.R;
import com.test.amaro.amarotest.domain.ProductListResponse;
import java.util.List;


/**
 *  Adapter responsible for carrying product data
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    /**
     *  The context in which Adapter is being displayed
     */
    private Context context;

    /**
     *  The list of products to be displayed
     */
    private List<ProductListResponse.Product> list;

    /**
     *  The View member of MainContract
     */
    private MainContract.View view;


    ProductAdapter(Context context,
                   List<ProductListResponse.Product> list,
                   MainContract.View view) {

        this.context = context;
        this.list = list;
        this.view = view;
    }

    /**
     *  Method responsible for setting current list of products being
     *  displayed.
     *  Adapter may present over three types of list: Complete List,
     *  On Sale List, Complete List sorted by price and On Sale List
     *  sorted by price.
     *  By calling this method with any list, RecyclerView is notified
     *  to display the current requested list.
     * @param list
     */
    public void setList(List<ProductListResponse.Product> list) {

        notifyItemRangeRemoved(0, this.list.size());

        this.list = list;

        notifyItemRangeInserted(0, this.list.size());
    }

    /**
     * Responsible for returning the current list being displayed
     * at the moment.
     * @return The current list being displayed.
     */
    public List<ProductListResponse.Product> getCurrentList() {
        return this.list;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cell_product,
                parent, false);

        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {

        RequestOptions opt = new RequestOptions()
                .placeholder(R.mipmap.icon_launcher)
                .error(R.mipmap.icon_launcher);

        Glide.with(context)
                .load(list.get(holder.getAdapterPosition()).getImageUrl())
                .apply(opt)
                .into(holder.iv);

        holder.tvName.setText(list.get(holder.getAdapterPosition()).getName());

        holder.containerRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.startDetailActivity(list.get(holder.getAdapterPosition()), holder.iv, holder.tvName, holder.tvPrice);
            }
        });

        if (list.get(holder.getAdapterPosition()).isOnSale()) {
            holder.ivSale.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(list.get(holder.getAdapterPosition()).getPricePromotional());
        } else {
            holder.tvPrice.setText(list.get(holder.getAdapterPosition()).getPriceRegular());
            holder.ivSale.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     *  ViewHolder for a Product.
     *  It displays data such as name, price, image and on sale status.
     */
    class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tvName;
        private TextView tvPrice;
        private ViewGroup containerRoot;
        private ImageView ivSale;


        ProductViewHolder(View itemView) {
            super(itemView);

            this.iv = itemView.findViewById(R.id.cell_product_iv);
            this.tvName = itemView.findViewById(R.id.cell_product_tv_name);
            this.tvPrice = itemView.findViewById(R.id.cell_product_tv_price);
            this.containerRoot = itemView.findViewById(R.id.cell_product_root);
            this.ivSale = itemView.findViewById(R.id.cell_product_iv_sale);
        }
    }
}
