package com.example.c196schedulingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.c196schedulingapp.Entity.Term;
import com.example.c196schedulingapp.R;
import com.example.c196schedulingapp.UI.TermDetails;
import com.example.c196schedulingapp.Helper.ParseDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TermViewAdapter extends RecyclerView.Adapter<TermViewAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView listItemView;
        private final TextView listItemView1;
        private final TextView listItemView2;

        private TermViewHolder(View itemView) {
            super(itemView);
            listItemView = itemView.findViewById(R.id.textView);
            listItemView1 = itemView.findViewById(R.id.textView1);
            listItemView2 = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("termStart", ParseDate.dateParseString(current.getStartDate()));
                    intent.putExtra("termEnd", ParseDate.dateParseString(current.getEndDate()));
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("termID", current.getTermID());
                    context.startActivity(intent);

                }
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflator;


    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflator.inflate(R.layout.list_items, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        if(mTerms!=null){

            Term current=mTerms.get(position);
            int id=current.getTermID();
            holder.listItemView.setText((current.getTermName()));
            holder.listItemView1.setText(ParseDate.dateParseString(current.getStartDate()));
            holder.listItemView2.setText(ParseDate.dateParseString(current.getEndDate()));
        }
        else{
            holder.listItemView.setText("No Thing Name");
            holder.listItemView1.setText("No Thing ID");
            holder.listItemView2.setText("No Thing ID");
        }
    }

   public TermViewAdapter(Context context){
        mInflator=LayoutInflater.from(context);
        this.context=context;
    }


    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }

    public String dateFormat(String date) throws ParseException {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
       return sdf.format(date);
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }
}
