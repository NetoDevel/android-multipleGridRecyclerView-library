package com.gigigo.multiplegridrecyclerview_demo;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.gigigo.multiplegridrecyclerview.MultipleGridRecyclerView;
import com.gigigo.multiplegridrecyclerview_demo.recyclerview.ImageViewHolder;
import com.gigigo.multiplegridrecyclerview_demo.recyclerview.ImageWidget;
import com.gigigo.multiplegridrecyclerview_demo.recyclerview.TextViewHolder;
import com.gigigo.multiplegridrecyclerview_demo.recyclerview.TextWidget;

public class MainActivity extends AppCompatActivity {

  FloatingActionButton floatingActionButtonLoad;
  FloatingActionButton floatingActionButtonAddImage;
  FloatingActionButton floatingActionButtonAddText;
  FloatingActionButton floatingActionButtonClear;
  FloatingActionButton floatingActionButtonMoreColumns;
  FloatingActionButton floatingActionButtonLessColumns;
  FloatingActionButton floatingActionButtonIncreaseRatio;
  FloatingActionButton floatingActionButtonDecreaseRatio;

  private MultipleGridRecyclerView multipleGridRecyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews();
  }

  private void initViews() {
    initFloatingActions();
    initMultipleGridRecyclerView();
  }

  private void initFloatingActions() {
    initFloatingDataActions();
    initFloatingAspectActions();
  }

  private void initFloatingDataActions() {
    final FloatingActionsMenu floatingDataActionsMenu =
        (FloatingActionsMenu) findViewById(R.id.floating_action_data_menu);

    floatingActionButtonLoad =
        (FloatingActionButton) findViewById(R.id.floating_action_button_load);
    floatingActionButtonLoad.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        floatingDataActionsMenu.collapse();
        loadData();
      }
    });

    floatingActionButtonAddImage =
        (FloatingActionButton) findViewById(R.id.floating_action_button_add_image);
    floatingActionButtonAddImage.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        floatingDataActionsMenu.collapse();
        addImageData();
      }
    });

    floatingActionButtonAddText =
        (FloatingActionButton) findViewById(R.id.floating_action_button_add_text);
    floatingActionButtonAddText.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        floatingDataActionsMenu.collapse();
        addTextData();
      }
    });

    floatingActionButtonClear =
        (FloatingActionButton) findViewById(R.id.floating_action_button_clear);
    floatingActionButtonClear.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        floatingDataActionsMenu.collapse();
        clearData();
      }
    });
  }

  private void initFloatingAspectActions() {
    final FloatingActionsMenu floatingAspectActionsMenu =
        (FloatingActionsMenu) findViewById(R.id.floating_action_aspect_menu);

    floatingActionButtonMoreColumns =
        (FloatingActionButton) findViewById(R.id.floating_action_button_more_columns);
    floatingActionButtonMoreColumns.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        floatingAspectActionsMenu.collapse();
        moreColumns();
      }
    });

    floatingActionButtonLessColumns =
        (FloatingActionButton) findViewById(R.id.floating_action_button_less_columns);
    floatingActionButtonLessColumns.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        floatingAspectActionsMenu.collapse();
        lessColumns();
      }
    });

    floatingActionButtonIncreaseRatio =
        (FloatingActionButton) findViewById(R.id.floating_action_button_increase_ratio);
    floatingActionButtonIncreaseRatio.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        floatingAspectActionsMenu.collapse();
        increaseRatio();
      }
    });

    floatingActionButtonDecreaseRatio =
        (FloatingActionButton) findViewById(R.id.floating_action_button_decrease_ratio);
    floatingActionButtonDecreaseRatio.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        floatingAspectActionsMenu.collapse();
        decreaseRatio();
      }
    });
  }

  private void initMultipleGridRecyclerView() {
    multipleGridRecyclerView =
        (MultipleGridRecyclerView) findViewById(R.id.multiple_grid_recycler_view);

    setAdapterDataViewHolders();

    multipleGridRecyclerView.setOnRefreshListener(new MultipleGridRecyclerView.OnRefreshListener() {
      @Override public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
          @Override public void run() {
            multipleGridRecyclerView.setRefreshing(false);
          }
        }, 3000);
      }
    });
  }

  private void setAdapterDataViewHolders() {
    multipleGridRecyclerView.setAdapterDataViewHolder(ImageWidget.class, ImageViewHolder.class);
    multipleGridRecyclerView.setAdapterDataViewHolder(TextWidget.class, TextViewHolder.class);
  }

  private void loadData() {
    Display display = getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    int width = size.x;
    int grid_columns = 3;
    multipleGridRecyclerView.loadData(
        DataGenerator.generateRandomDataList(30, width / grid_columns));
  }

  private void addImageData() {
    multipleGridRecyclerView.add(DataGenerator.generateRandomTextData(99));
  }

  private void addTextData() {
    multipleGridRecyclerView.add(DataGenerator.generateRandomImageData(200));
  }

  private void moreColumns() {
    int columns = 1;
    columns += 1;
    if(columns>1){
      floatingActionButtonLessColumns.setEnabled(true);
    }
    multipleGridRecyclerView.setGridColumns(columns);
    Toast.makeText(this,"Columns "+columns, Toast.LENGTH_SHORT).show();
  }

  private void lessColumns() {
    int columns = 1;
    if(columns<=1){
      floatingActionButtonLessColumns.setEnabled(false);
    }
    else {
      columns-=1;
      multipleGridRecyclerView.setGridColumns(columns);
      Toast.makeText(this,"Columns "+columns, Toast.LENGTH_SHORT).show();
    }
  }

  private void increaseRatio() {
    float ratio = 1f;
    ratio += 0.1f;
    if(ratio>0.1f){
      floatingActionButtonDecreaseRatio.setEnabled(true);
    }
    multipleGridRecyclerView.setCellAspectRatio(ratio);
    Toast.makeText(this,"Ratio "+ratio, Toast.LENGTH_SHORT).show();
  }

  private void decreaseRatio() {
    float ratio = 1f;
    if(ratio>0.1f){
      floatingActionButtonDecreaseRatio.setEnabled(false);
    }
    else {
      ratio += 0.1f;
      multipleGridRecyclerView.setCellAspectRatio(ratio);
      Toast.makeText(this, "Ratio " + ratio, Toast.LENGTH_SHORT).show();
    }
  }

  private void clearData() {
    multipleGridRecyclerView.clearData();
  }
}
