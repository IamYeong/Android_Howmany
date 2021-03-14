package com.package1.householdledger3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public ArrayList<ArrayObject> arrayList;
    public Context mContext;

    public MyAdapter( ) {}//default constructor
    public MyAdapter(ArrayList<ArrayObject> mList, Context context) {
        this.arrayList = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
}

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        DecimalFormat formatter = new DecimalFormat("###,###");

        holder.tvStr.setText(arrayList.get(position).getExpensePlace());
        holder.tvInt.setText(formatter.format(arrayList.get(position).getExpenseMoney()));
        holder.checkBox.setChecked(arrayList.get(position).isSelected());
        holder.StrDate.setText(arrayList.get(position).getStrDate());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            //사용자가 체크박스를 누르면,
            @Override
            public void onClick(View v) {
                int pos = position;

                if (arrayList.get(pos).isSelected == false) {
                    arrayList.get(pos).setSelected(true);
                    Toast.makeText(mContext, "선택되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    arrayList.get(pos).setSelected(false);
                    Toast.makeText(mContext, "선택이 취소되었습니다", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(mContext, "" +pos+"번째 배열의 체크박스를 Check했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        //핵심 : 사용자의 체크박스 클릭을 감지한 후, 몇 번째 뷰인지 찾은 다음 체크액션을 수행한다.
        //삭제할 때 아이디어 : n번째 뷰의 체크박스가 체크되어있는지 검사한다. true면 remove.

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

}//MyAdapter

class MyViewHolder extends RecyclerView.ViewHolder {

    //추가당시 날짜 변수 넣어서 반영하기
    protected TextView tvStr;
    protected TextView tvInt;
    protected CheckBox checkBox;
    protected TextView StrDate;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        this.tvStr = (TextView) itemView.findViewById(R.id.tv1_card);
        this.tvInt = (TextView) itemView.findViewById(R.id.tv2_card);
        this.checkBox = (CheckBox) itemView.findViewById(R.id.checkBox_card);
        this.StrDate = (TextView) itemView.findViewById(R.id.date_card);
    }


}//MyViewHolder
