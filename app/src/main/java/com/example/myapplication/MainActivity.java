package com.example.myapplication;//包的名字

//引入的库

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.BookListMainActivity;

import java.util.ArrayList;


//声明类
public class MainActivity extends AppCompatActivity {
    @Override//重载（父类函数在子类重新实现）
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                 //调用父类的方法
        setContentView(R.layout.activity_main);             //设置布局；R->res

        RecyclerView mainrecyclerView = findViewById(R.id.recyclerview_main);
        mainrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<BookListMainActivity> bookListMainActivities = new ArrayList<>();
        bookListMainActivities.add(new BookListMainActivity("软件项目管理案例教程（第4版）",R.drawable.book_2));
        bookListMainActivities.add(new BookListMainActivity("创新工程实践",R.drawable.book_no_name));
        bookListMainActivities.add(new BookListMainActivity("信息安全数学基础（第2版）",R.drawable.book_1));
        BookItemAdapter bookItemAdapter = new BookItemAdapter(bookListMainActivities);
        mainrecyclerView.setAdapter(bookItemAdapter);

        registerForContextMenu(mainrecyclerView);
    }



    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        Toast.makeText(this,"点击了",Toast.LENGTH_SHORT).show();
            switch (item.getItemId()){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    return super.onContextItemSelected(item);
            }
        return true;
    }

    public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.ViewHolder> {

        private ArrayList<BookListMainActivity> bookListMainActivityArrayList;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textViewName;
            private final ImageView imageViewItem;

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View

                textViewName = view.findViewById(R.id.text_view_book_title);
                imageViewItem = view.findViewById(R.id.image_view_book_cover);
            }
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo){
                menu.setHeaderTitle("具体操作");
                menu.add(0, 0, Menu.NONE, "添加");
                menu.add(0, 0, Menu.NONE, "删除");
                menu.add(0, 0, Menu.NONE, "修改");
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
         * @param bookListMainActivities String[] containing the data to populate views to be used
         * by RecyclerView.
         */
        public BookItemAdapter(ArrayList<BookListMainActivity> bookListMainActivities) {
            bookListMainActivityArrayList = bookListMainActivities;
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
            viewHolder.getTextViewName().setText(bookListMainActivityArrayList.get(position).getName());
            viewHolder.getImageViewPrice().setImageResource(bookListMainActivityArrayList.get(position).getImageResourceId());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return bookListMainActivityArrayList.size();
        }
    }

}
