package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.BookListMainActivity;
import com.example.myapplication.data.DataBank;
import com.example.myapplication.data.SumMainActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListFragment extends Fragment {
    public ShoppingListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShoppingListFragment.
     */
    public static ShoppingListFragment newInstance() {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    private String []tabHeaderStrings = {"每日任务","每周任务","普通任务","剧本任务"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        RecyclerView mainrecyclerView = rootView.findViewById(R.id.recyclerview_main);
        mainrecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // ViewPager2 and TabLayout setup
        ViewPager2 viewPager = rootView.findViewById(R.id.view_pager1);
        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout1);

        // Create and set the adapter for ViewPager2
        ShoppingListPagerAdapter pagerAdapter = new ShoppingListPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Link TabLayout and ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab,position) -> tab.setText(tabHeaderStrings[position])
        ).attach();



        bookitems = new DataBank().LoadBookItems(requireActivity());
        if (0 == bookitems.size()) {
            bookitems.add(new BookListMainActivity("跑步",10,"0/99"));
            bookitems.add(new BookListMainActivity("阅读",20,"0/2"));
            bookitems.add(new BookListMainActivity("练琴",30,"1/4"));
        }
        bookItemAdapter = new BookItemAdapter(bookitems);
        mainrecyclerView.setAdapter(bookItemAdapter);

        registerForContextMenu(mainrecyclerView);

        addItemlauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String name = data.getStringExtra("name");
                        String scoreText = data.getStringExtra("score");
                        String times = data.getStringExtra("times");

                        int score = Integer.parseInt(scoreText);
                        bookitems.add(new BookListMainActivity(name,score,times));
                        bookItemAdapter.notifyItemInserted(bookitems.size());
                        new DataBank().SaveBookItems(requireActivity(), bookitems);
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );

        updateItemlauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int position = data.getIntExtra("position", -1);
                        String name = data.getStringExtra("name");
                        int score = data.getIntExtra("score",10);
                        String times = data.getStringExtra("times");

                        BookListMainActivity bookitem = bookitems.get(position);
                        bookitem.setName(name);
                        bookitem.setScore(score);
                        bookitem.setTimes(times);
                        bookItemAdapter.notifyItemChanged(position);

                        new DataBank().SaveBookItems(requireActivity(), bookitems);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        return rootView;
    }

    private static class ShoppingListPagerAdapter extends FragmentStateAdapter {

        public ShoppingListPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // Create and return your individual fragments
            switch(position){
                case 0:
                    return new TencentMapsFragment();
                case 1:
                    return new TencentMapsFragment();
                case 2:
                    return new TencentMapsFragment();
                case 3:
                    return new TencentMapsFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            // Return the total number of tabs
            return 4; // Change this based on your requirements
        }
    }

    private ArrayList<BookListMainActivity> bookitems = new ArrayList<>();
    private BookItemAdapter bookItemAdapter;

    //private ArrayList<BookListMainActivity> bookitems;
    // private BookItemAdapter bookItemAdapter;

    ActivityResultLauncher<Intent> addItemlauncher;
    ActivityResultLauncher<Intent> updateItemlauncher;

    public boolean onContextItemSelected(MenuItem item) {
        //int position = menuInfo.position;
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(requireActivity(), BookItemDetailsActivity.class);
                addItemlauncher.launch(intent);
                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bookitems.remove(item.getOrder());
                        bookItemAdapter.notifyItemRemoved(item.getOrder());
                        new DataBank().SaveBookItems(requireActivity(), bookitems);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create().show();
                // deleteItem(position);
                break;
            case 2:
                Intent intentUpdate = new Intent(requireActivity(), BookItemDetailsActivity.class);
                BookListMainActivity bookitem = bookitems.get(item.getOrder());
                intentUpdate.putExtra("name", bookitem.getName());
                intentUpdate.putExtra("position", item.getOrder());
                updateItemlauncher.launch(intentUpdate);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }


    public static class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.ViewHolder> {

        private ArrayList<BookListMainActivity> bookitems;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewScore;
            private final TextView textViewTimes;
            private final CheckBox checkBox;
            private final TextView textViewSum;
            public ViewHolder(View bookItemView) {
                super(bookItemView);
                checkBox = bookItemView.findViewById(R.id.checkBox);
                // Define click listener for the ViewHolder's View
                textViewScore = bookItemView.findViewById(R.id.score);
                textViewTimes = bookItemView.findViewById(R.id.times);
                textViewSum = bookItemView.findViewById(R.id.sum);
                bookItemView.setOnCreateContextMenuListener(this);
            }

            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");

                menu.add(0, 0, this.getAdapterPosition(), "添加" + this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
                menu.add(0, 2, this.getAdapterPosition(), "修改" + this.getAdapterPosition());
            }


            public CheckBox getCheckBox() {
                return checkBox;
            }
            public TextView getTextViewScore() {
                return textViewScore;
            }
            public TextView getTextViewTimes() {
                return textViewTimes;
            }
            public TextView getTextViewSum() {
                return textViewSum;
            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param bookitems String[] containing the data to populate views to be used
         *                  by RecyclerView.
         */
        public BookItemAdapter(ArrayList<BookListMainActivity> bookitems) {
            this.bookitems = bookitems;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_item_row, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override

        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.getCheckBox().setText(bookitems.get(position).getName());
            viewHolder.getTextViewScore().setText("+"+bookitems.get(position).getScore());
            viewHolder.getTextViewTimes().setText(bookitems.get(position).getTimes());

            viewHolder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    // 处理CheckBox的点击事件
                    if (isChecked) {
                        Toast.makeText(compoundButton.getContext(), "finished", Toast.LENGTH_SHORT).show();
                        int clickedScore = bookitems.get(viewHolder.getAdapterPosition()).getScore();
                        // 更新显示sum的TextView
                        SumMainActivity.sum += clickedScore;
                        // 更新显示sum的TextView
                        MainActivity.sumTextView.setText("" + SumMainActivity.sum);
                    }
                }
            });
        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return bookitems.size();
        }
    }
}