package com.example.jguzikowski.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jguzikowski.model.Product;
import com.example.jguzikowski.myapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by j.guzikowski on 9/19/17.
 */

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.ViewHolder>  {


    List<Product> products;




Context context;




    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }



    public RecViewAdapter(Context context, List<Product> products) {
        this.products=products;

        this.context = context;
    }



    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_items, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {


        holder.tvName.setText(products.get(position).getName());
        holder.tvDelivery.setText(products.get(position).getDelivery());
        holder.tvPrice.setText(String.valueOf(products.get(position).getPrice()));
        holder.tvCountry.setText(products.get(position).getCountry());
        holder.itemView.setId((int )products.get(position).getId());



    }




    @Override public int getItemCount() {
       // return items.size();
        return products.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rv_tv_name) TextView tvName;
        @BindView(R.id.rv_tv_price) TextView tvPrice;
        @BindView(R.id.rv_tv_country) TextView tvCountry;
        @BindView(R.id.rv_tv_delivery) TextView tvDelivery;
        @BindView(R.id.button) Button button;
        @BindView(R.id.button1) Button button1;
        @BindView(R.id.button2) Button button2;

       // @BindView(R.id.job_title) TextView jobTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
                }
            });



            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "test1", Toast.LENGTH_SHORT).show();
                }
            });
            //itemView.setOnClickListener(this);

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, getAdapterPosition() + "test2", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

}
