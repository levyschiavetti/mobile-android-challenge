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
import com.test.amaro.amarotest.domain.ResponseList;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<ResponseList.Product> list;
    private MainContract.View view;


    ProductAdapter(Context context,
                   List<ResponseList.Product> list,
                   MainContract.View view) {

        this.context = context;
        this.list = list;
        this.view = view;
    }

    public void setList(List<ResponseList.Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<ResponseList.Product> getCurrentList() {
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
                .placeholder(R.drawable.vector_placeholder)
                .error(R.drawable.vector_placeholder);

        Glide.with(context)
                .load(list.get(holder.getAdapterPosition()).getImageUrl())
                .apply(opt)
                .into(holder.iv);

        holder.tvName.setText(list.get(holder.getAdapterPosition()).getName());
        holder.tvPrice.setText(list.get(holder.getAdapterPosition()).getPriceRegular());
        holder.containerRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.startDetailActivity(list.get(holder.getAdapterPosition()), holder.iv, holder.tvName, holder.tvPrice);
            }
        });

        if (list.get(holder.getAdapterPosition()).isOnSale()) {
            holder.ivSale.setVisibility(View.VISIBLE);
        } else {
            holder.ivSale.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


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
