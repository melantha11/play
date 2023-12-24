package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.AwardItemMainActivity;
import com.example.myapplication.data.BookListMainActivity;
import com.example.myapplication.data.DataBank;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AwardFragment extends Fragment {
    ActivityResultLauncher<Intent> addItemlauncher;
    public AwardFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_award_list, container, false);
        RecyclerView mainrecyclerView = rootView.findViewById(R.id.recyclerview_main);
        mainrecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //awarditems = new DataBank().LoadBookItems(requireActivity());
        if (0 == awarditems.size()) {
            awarditems.add(new AwardItemMainActivity("原神一小时","1/∞",10));
            awarditems.add(new AwardItemMainActivity("星铁一小时","1/∞",10));
            awarditems.add(new AwardItemMainActivity("LOL一小时","2/∞",20));
        }
        awardItemAdapter = new AwardItemAdapter(awarditems);
        mainrecyclerView.setAdapter(awardItemAdapter);

        registerForContextMenu(mainrecyclerView);

        addItemlauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String name = data.getStringExtra("name");
                        String costText = data.getStringExtra("cost");
                        String times = data.getStringExtra("times");
                        int cost = Integer.parseInt(costText);
                        awarditems.add(new AwardItemMainActivity(name,times,cost));
                        awardItemAdapter.notifyItemInserted(awarditems.size());
                        //new DataBank().SaveBookItems(requireActivity(), awarditems);
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    }
                }
        );

        FloatingActionButton addButton = rootView.findViewById(R.id.AddButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), AwardItemDetailsActivity.class);
                addItemlauncher.launch(intent);
            }
        });

        return rootView;
    }

    private ArrayList<AwardItemMainActivity> awarditems = new ArrayList<>();
    private AwardFragment.AwardItemAdapter awardItemAdapter;

    public static class AwardItemAdapter extends RecyclerView.Adapter<AwardFragment.AwardItemAdapter.ViewHolder> {

        private ArrayList<AwardItemMainActivity> awarditems;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder  {
            private final TextView textViewCost;
            private final TextView textViewTimes;
            private final TextView textViewName;

            private final TextView textViewSum;
            public ViewHolder(View awardItemView) {
                super(awardItemView);
                // Define click listener for the ViewHolder's View
                textViewName = awardItemView.findViewById(R.id.award_item_name);
                textViewCost = awardItemView.findViewById(R.id.award_item_cost);
                textViewTimes = awardItemView.findViewById(R.id.times);
                textViewSum = awardItemView.findViewById(R.id.sum);
                //awardItemView.setOnCreateContextMenuListener(this);
            }



            public TextView getTextViewCost() {
                return textViewCost;
            }
            public TextView getTextViewTimes() {
                return textViewTimes;
            }
            public TextView getTextViewName() {
                return textViewName;
            }


        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param awarditems String[] containing the data to populate views to be used
         *                  by RecyclerView.
         */
        public AwardItemAdapter(ArrayList<AwardItemMainActivity> awarditems) {
            this.awarditems = awarditems;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public AwardFragment.AwardItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.award_details, viewGroup, false);

            return new AwardFragment.AwardItemAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.getTextViewName().setText(awarditems.get(position).getName());
            viewHolder.getTextViewTimes().setText(awarditems.get(position).getTimes());
            viewHolder.getTextViewCost().setText("-"+awarditems.get(position).getCost());
        }



        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return awarditems.size();
        }
    }
}
