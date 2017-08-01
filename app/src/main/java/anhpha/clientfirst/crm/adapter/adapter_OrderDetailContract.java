package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.OrderDetailContract;
import anhpha.clientfirst.crm.utils.Utils;


public class adapter_OrderDetailContract extends RecyclerView.Adapter<adapter_OrderDetailContract.MyViewHolder> {

    private List<OrderDetailContract> list;
    private Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProduct,tvQuantity,tvPrice,tvSum;
        public RecyclerView lvProduct;
        private View vLine;


        public MyViewHolder(View v) {
            super(v);
            tvProduct =(TextView) v.findViewById(R.id.tvProduct);
            tvQuantity =(TextView) v.findViewById(R.id.tvQuantity);
            tvPrice =(TextView) v.findViewById(R.id.tvPrice);
            tvSum =(TextView) v.findViewById(R.id.tvSum);
            vLine =(View) v.findViewById(R.id.vLine);


        }

    }
    public adapter_OrderDetailContract(Context context, List<OrderDetailContract> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_orderdetailcontract, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        OrderDetailContract p = list.get(position);
        holder.tvProduct.setText(p.getContractName());
        holder.tvPrice.setText(Utils.formatCurrency(p.getPrice()));
        holder.tvQuantity.setText(p.getNumber()+"");
        int sum = p.getPrice()*p.getNumber();
        holder.tvSum.setText(Utils.formatCurrency(sum));
        if (position == list.size()-1){
            holder.vLine.setVisibility(View.GONE);
        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
