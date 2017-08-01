package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.History_contract;
import anhpha.clientfirst.crm.model.OrderDetailContract;
import anhpha.clientfirst.crm.utils.Utils;


public class adapter_History_contract extends RecyclerView.Adapter<adapter_History_contract.MyViewHolder> {

    private List<History_contract> list;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUpdate_date, tvPersonnel, tvStatus, tvPrepay1, tvExpected_date, tvAmount_payment, tvPrepay2, tvMissing_money;
        public RecyclerView lvProduct;
        public LinearLayout lay_display;
        private View vLine;

        public MyViewHolder(View v) {
            super(v);
            tvUpdate_date = (TextView) v.findViewById(R.id.tvUpdate_date);
            tvPersonnel = (TextView) v.findViewById(R.id.tvPersonnel);
            tvStatus = (TextView) v.findViewById(R.id.tvStatus);
            tvPrepay1 = (TextView) v.findViewById(R.id.tvPrepay1);
            tvExpected_date = (TextView) v.findViewById(R.id.tvExpected_date);
            tvAmount_payment = (TextView) v.findViewById(R.id.tvAmount_payment);
            tvPrepay2 = (TextView) v.findViewById(R.id.tvPrepay2);
            tvMissing_money = (TextView) v.findViewById(R.id.tvMissing_money);
            lvProduct = (RecyclerView) v.findViewById(R.id.lvProduct);
            vLine = (View) v.findViewById(R.id.vLine);

        }

    }

    public adapter_History_contract(Context context, List<History_contract> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_contract, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        History_contract p = list.get(position);
        holder.tvUpdate_date.setText(p.getCreateDate());
        holder.tvPrepay1.setText(Utils.formatCurrency(p.getPrepay()));
        holder.tvExpected_date.setText(p.getExpectedCompletionDate());
        holder.tvStatus.setText(p.getOrderStatusName());
        holder.tvPersonnel.setText(p.getUserName());
        holder.tvAmount_payment.setText(Utils.formatCurrency(p.getAmountPayment()));
        holder.tvPrepay2.setText(Utils.formatCurrency(p.getPrepay()));
        int money = p.getAmountPayment() - p.getPrepay();
        holder.tvMissing_money.setText(Utils.formatCurrency(money));
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL
                , false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.lvProduct.setHasFixedSize(true);
        holder.lvProduct.setLayoutManager(layoutManager);
        final List<OrderDetailContract> list = p.getOrderDetailContracts();
        adapter_OrderDetailContract adapter = new adapter_OrderDetailContract(context, list);
        holder.lvProduct.setAdapter(adapter);
        if (position == list.size() - 1) {
            holder.vLine.setVisibility(View.GONE);
        }
    }

//    public String funcConvert(int a) {
//        String product_price = null;
//        int h, e, b, d, i, f, c, m, q;
//        String t, tr, ch = null, n = null;
//        if (a > 1000000000) {
//            h = a / 1000000000;
//            e = h % 100000;
//            b = a % 1000000;
//            i = a % 1000000000;
//            q = i / 1000000;
//            m = q % 1000;
//            d = b / 1000;
//            f = d % 1000;
//            c = b % 1000;
//            if (m < 100 && m > 9) {
//                tr = "0" + m;
//
//            } else if (m < 10 && m > 0) {
//                tr = "00" + m;
//
//            } else if (m > 99) {
//                tr = m + "";
//
//            } else {
//                tr = m + "00";
//            }
//            //chuc
//            if (f < 100 && f > 9) {
//                ch = "0" + f;
//
//            } else if (f < 10 && f > 0) {
//                ch = "00" + f;
//
//            } else if (f > 99) {
//                tr = f + "";
//
//            } else {
//                ch = f + "00";
//            }
//            // ngan
//            if (c == 0) {
//                n = c + "00";
//                product_price = e + "." + tr + "." + ch + "." + n;
//            } else {
//                product_price = e + "." + tr + "." + ch + "." + c;
//            }
//
//        } else if (a >= 1000000) {
//            b = a % 1000000;
//            h = a / 1000000;
//            e = h % 1000;
//            d = b / 1000;
//            f = d % 1000;
//            c = b % 1000;
//
//            if (f < 100 && f > 9) {
//                ch = "0" + f;
//                if (c == 0) {
//                    n = c + "00";
//                    product_price = e + "." + ch + "." + n;
//                } else {
//                    product_price = e + "." + ch + "." + c;
//                }
//            } else if (f < 10 && f > 0) {
//                ch = "00" + f;
//
//                if (c == 0) {
//                    n = c + "00";
//                    product_price = e + "." + ch + "." + n;
//                } else {
//                    product_price = e + "." + ch + "." + c;
//                }
//            } else if (f > 99) {
//                ch = "" + f;
//
//                if (c == 0) {
//                    n = c + "00";
//                    product_price = e + "." + ch + "." + n;
//                } else {
//                    product_price = e + "." + ch + "." + c;
//                }
//            } else if (f == 0) {
//                ch = f + "00";
//
//                if (c == 0) {
//                    n = c + "00";
//                    product_price = e + "." + ch + "." + n;
//                } else {
//                    product_price = e + "." + ch + "." + c;
//                }
//            } else {
//                if (c == 0) {
//                    n = c + "00";
//                    product_price = e + "." + ch + "." + n;
//                } else {
//                    product_price = e + "." + ch + "." + c;
//                }
//            }
//        } else {
//            b = a % 1000;
//            h = a / 1000;
//            e = h % 1000;
//            if (b == 0) {
//                n = b + "00";
//                product_price = e + "." + n;
//            } else {
//                product_price = e + "." + b;
//            }
//        }
//        return product_price;
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
