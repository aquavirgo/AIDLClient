package com.example.jguzikowski.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jguzikowski.aidlproductservice.IMyAidlInterface;
import com.example.jguzikowski.model.Product;
import com.example.jguzikowski.myapplication.adapter.RecViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    Context context;
    List<Product> products;
    Parcelable mListState;
    private final String KEY_RECYCLER_STATE = "recycler_state";

    public  static RecyclerView.Adapter recyclerView_Adapter;
    IMyAidlInterface service;
    AddServiceConnection connection;
    DialogViewHolder dialogViewHolder;

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv_numbers) RecyclerView recyclerView;










    class AddServiceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IMyAidlInterface.Stub.asInterface((IBinder) boundService);
            Log.i("TAG", "onServiceConnected(): Connected");
            Toast.makeText(MainActivity.this, "AIDLExample Service connected", Toast.LENGTH_LONG).show();

            readProductService();
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Log.i("TAG", "onServiceDisconnected(): Disconnected");
            Toast.makeText(MainActivity.this, "AIDLExample Service Connected", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TEST");


        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable dr = getResources().getDrawable(R.drawable.tv_ab_back_mat);

        getSupportActionBar().setHomeAsUpIndicator(dr);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        initService();




        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE);
            Log.v("onCreate ",mListState.toString());

        }


      //  recyclerView = (RecyclerView) findViewById(R.id.rv_numbers);
        products = new ArrayList<Product>();
context = getApplicationContext();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView_Adapter = new RecViewAdapter(context,products);
        recyclerView.setAdapter(recyclerView_Adapter);
        recyclerView_Adapter.notifyDataSetChanged();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete

                // COMPLETED (1) Construct the URI for the item to delete
                //[Hint] Use getTag (from the adapter code) to get the id of the swiped item
                // Retrieve the id of the task to delete
               long id = (long) viewHolder.itemView.getId();

                // Build appropriate uri with String row id appended

                // COMPLETED (2) Delete a single row of data using a ContentResolver
                try {
                    service.deleteProduct(id);
                 //   recyclerView_Adapter.notifyDataSetChanged();
            readProductService();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                // COMPLETED (3) Restart the loader to re-query for all tasks after a deletion
               // getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, MainActivity.this);

            }
        }).attachToRecyclerView(recyclerView);



    }


    @Override
    public void onResume() {

        super.onResume();




    }

    private void initService() {
        Log.i("TAG", "initService()" );
        connection = new AddServiceConnection();
        Intent i = new Intent();

        i.setClassName("com.example.jguzikowski.aidlproductservice", "com.example.jguzikowski.aidlproductservice.ProductService");
        boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
        Log.i("TAG", "initService() bound value: " + ret);

    }

    /** This is our function to un-binds this activity from our service. */
    private void releaseService() {
        unbindService(connection);
        connection = null;
        Log.d("TAG", "releaseService(): unbound.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);







     final EditText dialogName = (EditText) promptView.findViewById(R.id.dialog_name);
        final EditText dialogPrice = (EditText) promptView.findViewById(R.id.dialog_price);
        final EditText dialogCountry = (EditText) promptView.findViewById(R.id.dialog_country);
        final EditText dialogDelivery = (EditText) promptView.findViewById(R.id.dialog_delivery);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {




                        Product product = new Product();
                        product.setName(dialogName.getText().toString());
                        product.setPrice(Double.parseDouble(dialogPrice.getText().toString()));
                        product.setCountry(dialogCountry.getText().toString());

                        product.setDelivery(dialogDelivery.getText().toString());

                        Log.d("value", product.getCountry());



                        try {
                         service.addProduct(product);

                           readProductService();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        // Save list state
        mListState = new Bundle();
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();
        savedInstanceState.putParcelable(KEY_RECYCLER_STATE, mListState);
        Log.v("onSave ",mListState.toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    void readProductService(){
        try {
            products = service.getAllProduct();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        recyclerView_Adapter = new RecViewAdapter(context, products );
        recyclerView.setAdapter(recyclerView_Adapter);

        recyclerView_Adapter.notifyDataSetChanged();
    }
}
