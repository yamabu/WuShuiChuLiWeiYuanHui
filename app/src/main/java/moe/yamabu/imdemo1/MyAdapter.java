package moe.yamabu.imdemo1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RvViewHolder> {
    private Context context;
    private ArrayList<MessageResponse> responseList = null;

    public MyAdapter(Context context) {
        this.context = context;
        responseList = new ArrayList<>();
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new RvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        holder.tvUserName.setText(responseList.get(position).userName);
        holder.tvContent.setText(responseList.get(position).content);
        holder.tvDate.setText(responseList.get(position).time);
    }


    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public void addData(MessageResponse response) {
        responseList.add(response);
        notifyItemChanged(responseList.size() - 1);
    }

    public class RvViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_username)
        TextView tvUserName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_date)
        TextView tvDate;

        public RvViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
