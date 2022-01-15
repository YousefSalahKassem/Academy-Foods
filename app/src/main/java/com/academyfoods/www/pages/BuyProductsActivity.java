package com.academyfoods.www.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.academyfoods.www.R;
import com.academyfoods.www.adapter.CheckOutAddressAdapter;
import com.academyfoods.www.adapter.CheckoutCartAdapter;
import com.academyfoods.www.model.OrderModel;
import com.academyfoods.www.model.PopularModel;
import com.academyfoods.www.model.UserInformationModel;
import com.academyfoods.www.model.VoucherModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BuyProductsActivity extends AppCompatActivity {
    RecyclerView cart, addresses;
    EditText coupon;
    Button apply, pick;
    TextView phone, total, delivery, subtotal, discount, time, text, textType;
    ArrayList<String> list = new ArrayList<>();
    LinearLayout layout,linearLayout;
    ArrayList<PopularModel> list2 = new ArrayList<>();
    ArrayList<VoucherModel> list3 = new ArrayList<>();
    UserInformationModel userInformationModel;
    VoucherModel voucherModel;
    DatabaseReference databaseReference, reference, reference2, reference3, reference4;
    CheckoutCartAdapter cartAdapter;
    CheckOutAddressAdapter addressAdapter;
    PopularModel popularModel;
    CardView cardView, buy;
    double sum;
    String type;
    int i = 0, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_products);

        getSupportActionBar().hide();

        calling();

        type = getIntent().getStringExtra("type");

        if(type.equals("PickNGo")){
            linearLayout.setVisibility(View.GONE);
            delivery.setText("0");
        }

        textType.setText(type);

        coupon.setBackgroundResource(R.drawable.borders);

        discount.setText("0");

        layout.setVisibility(View.GONE);

        cardView.setBackgroundResource(R.drawable.cardviewcorner);

        buy.setBackgroundResource(R.drawable.cardviewcornerr);

        getCart();

        getAddresses();

        getPhoneNumber();

        checkCoupon();

        pickTime();

        orderNow();

    }

    private void calling() {
        cart = findViewById(R.id.cart);
        addresses = findViewById(R.id.addresses);
        phone = findViewById(R.id.phone);
        total = findViewById(R.id.total);
        subtotal = findViewById(R.id.subtotal);
        delivery = findViewById(R.id.delivery);
        apply = findViewById(R.id.apply);
        coupon = findViewById(R.id.coupon);
        cardView = findViewById(R.id.card);
        buy = findViewById(R.id.checkout);
        discount = findViewById(R.id.discount);
        layout = findViewById(R.id.hide);
        pick = findViewById(R.id.pick);
        time = findViewById(R.id.timer);
        text = findViewById(R.id.addresstext);
        textType = findViewById(R.id.type);
        linearLayout=findViewById(R.id.linear1);
    }

    private void getCart() {
        cart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        reference = FirebaseDatabase.getInstance().getReference("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    popularModel = new PopularModel();
                    popularModel.setName(dataSnapshot.child("name").getValue().toString());
                    popularModel.setId(dataSnapshot.child("id").getValue().toString());
                    popularModel.setImage(dataSnapshot.child("image").getValue().toString());
                    popularModel.setPrice(dataSnapshot.child("price").getValue().toString());
                    popularModel.setCategory(dataSnapshot.child("category").getValue().toString());
                    popularModel.setQuantity(dataSnapshot.child("quantity").getValue().toString());
                    popularModel.setRestaurantName(dataSnapshot.child("restaurantName").getValue().toString());
                    popularModel.setRestaurantImage(dataSnapshot.child("restaurantImage").getValue().toString());
                    list2.add(popularModel);

                    sum += Double.parseDouble(popularModel.getPrice());
                    subtotal.setText(String.valueOf(sum) + "EGP");
                    if(type.equals("Delivery")){
                    delivery.setText("10 EGP");
                    total.setText(String.valueOf((10 + sum) - ((10 + sum) * Double.valueOf(discount.getText().toString()) / 100)) + " EGP");}
                    else {
                        total.setText(String.valueOf((sum) - ((sum) * Double.valueOf(discount.getText().toString()) / 100)) + " EGP");
                    }
                }
                cartAdapter = new CheckoutCartAdapter(list2, BuyProductsActivity.this);
                cart.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getAddresses() {
        if (type.equals("Delivery")) {
            addresses.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

            databaseReference = FirebaseDatabase.getInstance().getReference("addresses").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        list.add(dataSnapshot.getValue().toString());
                    }
                    addressAdapter = new CheckOutAddressAdapter(list, BuyProductsActivity.this);
                    addresses.setAdapter(addressAdapter);
                    addressAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            addresses.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
        }

    }

    private void getPhoneNumber() {
        reference2 = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInformationModel = snapshot.getValue(UserInformationModel.class);
                phone.setText(userInformationModel.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void checkCoupon() {
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference3 = FirebaseDatabase.getInstance().getReference("vouchers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("items");

                reference3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list3.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            voucherModel = new VoucherModel();
                            voucherModel.setName(dataSnapshot.child("name").getValue().toString());
                            voucherModel.setImage(dataSnapshot.child("image").getValue().toString());
                            voucherModel.setCode(dataSnapshot.child("code").getValue().toString());
                            voucherModel.setType(dataSnapshot.child("type").getValue().toString());
                            voucherModel.setValid(dataSnapshot.child("valid").getValue().toString());
                            voucherModel.setValue(dataSnapshot.child("value").getValue().toString());
                            list3.add(voucherModel);
                            for (i = 0; i < list3.size(); i++) {
                                if (coupon.getText().toString().equals(list3.get(i).getCode())) {
                                    layout.setVisibility(View.VISIBLE);
                                    discount.setText(list3.get(i).getValue());
                                    if(type.equals("Delivery")){
                                    total.setText((10 + sum) * ((Double.valueOf(discount.getText().toString()) / 100)) + " EGP");}
                                    else {
                                        total.setText((sum) * ((Double.valueOf(discount.getText().toString()) / 100)) + " EGP");
                                }
                                    System.out.println(Double.valueOf(discount.getText().toString()) / 100);
                                    Toast.makeText(BuyProductsActivity.this, "Enjoy Your Offer", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(BuyProductsActivity.this, "Applied Wrong Coupon", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }

    private void pickTime() {
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        time.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                    }
                };

                int style = AlertDialog.THEME_HOLO_DARK;
                TimePickerDialog timePickerDialog = new TimePickerDialog(BuyProductsActivity.this, style, onTimeSetListener, hour, minute, true);
                timePickerDialog.setTitle("Selected Time");
                timePickerDialog.show();

            }
        });
    }

    private void orderNow() {

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (type.equals("Delivery")) {
                    Date today = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateToStr = format.format(today);
                    reference4 = FirebaseDatabase.getInstance().getReference("myOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(FirebaseAuth.getInstance().getCurrentUser().getUid() + list.get(0) + time.getText().toString());
                    OrderModel orderModel = new OrderModel(list2, list.get(0), phone.getText().toString(), time.getText().toString(), coupon.getText().toString(), subtotal.getText().toString(), delivery.getText().toString(), discount.getText().toString(), total.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), "Preparing", dateToStr, list2.get(0).getRestaurantName(), list2.get(0).getRestaurantImage(),type);
                    reference4.setValue(orderModel);
                    FirebaseDatabase.getInstance().getReference("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                } else {
                    Date today = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String dateToStr = format.format(today);
                    reference4 = FirebaseDatabase.getInstance().getReference("myOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "1" + time.getText().toString());
                    OrderModel orderModel2 = new OrderModel(list2, "", phone.getText().toString(), time.getText().toString(), coupon.getText().toString(), subtotal.getText().toString(), delivery.getText().toString(), discount.getText().toString(), total.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), "Preparing", dateToStr, list2.get(0).getRestaurantName(), list2.get(0).getRestaurantImage(),type);
                    reference4.setValue(orderModel2);
                    FirebaseDatabase.getInstance().getReference("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                }


                sendNotification();
                startActivity(new Intent(BuyProductsActivity.this, HomeActivity.class));
            }
        });
    }

    private void sendNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel=new NotificationChannel("my_notification","n_channel",NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle("Preparing your order")
                .setContentText("Your order is preparing we will send another notification when the order is completed !")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setChannelId("my_notification")
                .setColor(Color.BLUE);

        //.setProgress(100,50,false);
        assert notificationManager != null;
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());
    }
}