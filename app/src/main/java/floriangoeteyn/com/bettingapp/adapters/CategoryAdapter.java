package floriangoeteyn.com.bettingapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import floriangoeteyn.com.bettingapp.R;
import floriangoeteyn.com.bettingapp.model.Bet;
import floriangoeteyn.com.bettingapp.model.BetCategory;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{


    private List<BetCategory> categories;



    //doorgeven van de fragmentInteractionListener en items
    public CategoryAdapter(List<BetCategory> categories){
        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_category, viewGroup, false);
        return new CategoryViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final CategoryViewHolder categoryViewHolder, final int i) {

        //pas text en omschrijving in de itemviewholder aan voor elk item
        final BetCategory category = categories.get(i);

        categoryViewHolder.categoryTitle.setText(category.toString());

        //onclicklistener voor de items in de recyclerview
        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return categories !=null? categories.size():0;
    }


    //itemviewholder: custom layout voor items
    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView categoryTitle;

        CategoryViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.categoryCardView);
            categoryTitle = (TextView) itemView.findViewById(R.id.categoryTitle);
        }

    }

}
