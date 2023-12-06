package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.data.BookListMainActivity;
import com.example.myapplication.data.DataBank;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        RecyclerView mainrecyclerView = rootView.findViewById(R.id.recyclerview_main);
        mainrecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));


        bookitems = new DataBank().LoadBookItems(requireActivity());
        if (0 == bookitems.size()) {
            bookitems.add(new BookListMainActivity("软件项目管理案例教程（第4版）", R.drawable.book_2));
            bookitems.add(new BookListMainActivity("创新工程实践", R.drawable.book_no_name));
            bookitems.add(new BookListMainActivity("信息安全数学基础（第2版）", R.drawable.book_1));
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
                        bookitems.add(new BookListMainActivity(name, R.drawable.book_1));
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

                        BookListMainActivity bookitem = bookitems.get(position);
                        bookitem.setName(name);
                        bookItemAdapter.notifyItemChanged(position);

                        new DataBank().SaveBookItems(requireActivity(), bookitems);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );
        return rootView;
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
            private final TextView textViewName;
            private final ImageView imageViewItem;

            public ViewHolder(View bookItemView) {
                super(bookItemView);
                // Define click listener for the ViewHolder's View
                textViewName = bookItemView.findViewById(R.id.text_view_book_title);
                imageViewItem = bookItemView.findViewById(R.id.image_view_book_cover);
                bookItemView.setOnCreateContextMenuListener(this);
            }

            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");

                menu.add(0, 0, this.getAdapterPosition(), "添加" + this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
                menu.add(0, 2, this.getAdapterPosition(), "修改" + this.getAdapterPosition());
            }

            public TextView getTextViewName() {
                return textViewName;
            }

            public ImageView getImageViewPrice() {
                return imageViewItem;
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
            viewHolder.getTextViewName().setText(bookitems.get(position).getName());
            viewHolder.getImageViewPrice().setImageResource(bookitems.get(position).getImageResourceId());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return bookitems.size();
        }
    }
}