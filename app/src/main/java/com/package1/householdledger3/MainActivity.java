package com.package1.householdledger3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DecimalFormat;
//import java.sql.Date;

public class MainActivity extends AppCompatActivity {

    /*
    2020.07.12 13:32
    by 정광영
     */

    MySQLiteHelper dbHelper = null;
    MySQLiteHelper2 dbHelper2 = null;
    MySQLiteHelper3 dbHelper3 = null;
    MyAdapter mAdapter;
    ArrayList<ArrayObject> mArrayList;
    FloatingActionButton fab, fab1, fab2;
    EditText editText1, editText2, editText3, editText4;
    ImageView imgAdjust, imgTrend;
    TextView textViewMain1, textViewMain2, textViewMain3, textViewMain4, textViewToday, textViewAvg;
    TextView textViewNew0, textViewNew1, textViewNew2;
    RadioGroup radioGroup;
    Animation fab_open, fab_close, fab_rotate, fab_rotate_reverse;
    boolean openFlag = false;
    DecimalFormat formatter = new DecimalFormat("###,###");
    SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date todayDate = new Date();
    String strToday = todayFormat.format(todayDate);
    Button cardview_init, btn_review;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewMain1 = (TextView) findViewById(R.id.tv1_main);
        textViewMain2 = (TextView) findViewById(R.id.tv2_main);
        textViewMain3 = (TextView) findViewById(R.id.tv3_main);
        textViewMain4 = (TextView) findViewById(R.id.tv_main_date);
        textViewToday = (TextView) findViewById(R.id.tv_today_main);
        textViewAvg = (TextView) findViewById(R.id.tv_avg_main);
        imgTrend = (ImageView) findViewById(R.id.img_trending_main);
        textViewNew1 = (TextView) findViewById(R.id.tv_main_today_sum);
        textViewNew2 = (TextView) findViewById(R.id.tv_today_goal_main);
        textViewMain1.setText("0");
        textViewMain2.setText("0");
        textViewMain4.setText(strToday);
        imgTrend = (ImageView) findViewById(R.id.img_trending_main);
        imgTrend.setImageResource(R.drawable.ic_trending_flat_black_24dp);
        System.out.println(imgTrend.getId());


        init_value();
        init_value2();
        init_value3();

        //delete_table();

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate);
        fab_rotate_reverse = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_reverse);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_main);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //특정 탭을 클릭하면,아래 동작을 한다.
                int tapPos = tab.getPosition();
                changeTab(tapPos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Do nothing
            }
        });
        //탭 클릭하면 레이아웃 이동하는 코드

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_main);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //db 불러오는 코드
        //db ArrayList에 할당하는 코드

        mArrayList = new ArrayList<>();
        //ArrayObject mObject = new ArrayObject("수입/지출 출처란 ", 0);
        //mArrayList.add(mObject);
        mAdapter = new MyAdapter(mArrayList, this);
        mRecyclerView.setAdapter(mAdapter);

        //*************************Tab Layout and RecyclerView code. no problem.

        load_value();
        load_value3();
        load_value2();


        try {

            //adMob code
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            mAdView.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM));



        } catch (RuntimeException e) {
            System.out.println(e);
        }

        fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_main1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_main2);


        fab1.startAnimation(fab_close);
        fab2.startAnimation(fab_close);
        fab1.setClickable(false);
        fab2.setClickable(false);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (openFlag) {
                    fab.startAnimation(fab_rotate_reverse);

                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    openFlag = false;
                } else {

                    fab.startAnimation(fab_rotate);

                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);

                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    openFlag = true;
                }

            }
        });//Animation code

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fab눌리면 수행할 동작 입력

                try {
                    showAlertDialog();
                } catch (RuntimeException e) {
                    System.out.println(e);
                    Toast.makeText(MainActivity.this, "양식에 맞게 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }


                fabAnim();
            }
        });

        try {

            imgAdjust = (ImageView) findViewById(R.id.img_adjust);
            imgAdjust.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("imgAdjust click");
                    try {

                        showCardviewDialog();
                    } catch (RuntimeException e) {
                        System.out.println(e);
                        Toast.makeText(MainActivity.this, "양식에 맞게 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        } catch(RuntimeException e) {
            System.out.println(e);
        }

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클릭하면 체크된거 삭제
                for (int i=0; i<mArrayList.size(); i++) {
                    if (mArrayList.get(i).isSelected()) {
                        mArrayList.remove(i);
                        mAdapter.notifyDataSetChanged();
                        i--;
                        //여기까지 이상없음.
                        Toast.makeText(MainActivity.this, "선택한 목록이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }
                }
                update_value();
                fabAnim();
                load_value_text();

            }
        });//fab2 버튼 누르면 선택한 배열 삭제하는 코드
        //complete

        //Tab2 code ==================================================================





        //Tab3 code ==================================================================
        cardview_init = (Button) findViewById(R.id.init_main);
        cardview_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_value();
                delete_value2();
                delete_value3();
                load_value();
                load_value2();
                load_value3();
                load_value_text();
                textViewNew2.setText("0");
                textViewAvg.setText("0");
                Toast.makeText(MainActivity.this, "모든 목록이 삭제되었습니다", Toast.LENGTH_LONG).show();
            }
        });
        //initial code, complete

        btn_review = (Button) findViewById(R.id.btn_review_main);
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.package1.householdledger3")); startActivity(intent);
                startActivity(intent);

            }
        });


    }//onCreate()====================================================================

    public void fabAnim() {

        if (openFlag) {
            fab.startAnimation(fab_rotate_reverse);

            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            openFlag = false;
        } else {
            fab.startAnimation(fab_rotate);

            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);

            fab1.setClickable(true);
            fab2.setClickable(true);
            openFlag = true;
        }

    }

    private void changeTab(int tabPosition) {
        FrameLayout frame1 = (FrameLayout) findViewById(R.id.frame1_main);
        LinearLayout frame2 = (LinearLayout) findViewById(R.id.frame2_main);
        LinearLayout frame3 = (LinearLayout) findViewById(R.id.frame3_main);

        switch (tabPosition) {
            case 0 :
                frame1.setVisibility(View.VISIBLE);
                frame2.setVisibility(View.INVISIBLE);
                frame3.setVisibility(View.INVISIBLE);
                break;

            case 1 :
                frame1.setVisibility(View.INVISIBLE);
                frame2.setVisibility(View.VISIBLE);
                frame3.setVisibility(View.INVISIBLE);
                break;

            case 2 :
                frame1.setVisibility(View.INVISIBLE);
                frame2.setVisibility(View.INVISIBLE);
                frame3.setVisibility(View.VISIBLE);
                break;
        }
    }
    //탭 선택에 따라 visivle 속성 부여하는 함수 코드

    public void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View customLayout = getLayoutInflater().inflate(R.layout.fab_dialog_layout, null);
        builder.setView(customLayout);


        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                editText1 = (EditText) customLayout.findViewById(R.id.et1_card);
                editText2 = (EditText) customLayout.findViewById(R.id.et2_card);
                radioGroup = (RadioGroup) customLayout.findViewById(R.id.radio_group);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date nowDate = new Date();
                String strDate = simpleDateFormat.format(nowDate);
                //확인 버튼 클릭하면 String형으로 현재 날짜 변수 형성

                try {


                    //dialog에 있는 위젯 인스턴스
                    final String etStr1 = editText1.getText().toString();
                    final String etStr2 = editText2.getText().toString();
                    final int etInt = Integer.parseInt(etStr2);
                    //객체에 넘길 String 값과 int 값 변수 선언
                    int rb = radioGroup.getCheckedRadioButtonId();

                    switch (rb) {
                        case R.id.radioButton1:
                            ArrayObject addObject1 = new ArrayObject(etStr1, -(etInt), strDate);
                            mArrayList.add(addObject1);
                            only_save_value(etStr1, -(etInt), strDate);
                            break;

                        case R.id.radioButton2:
                            ArrayObject addObject2 = new ArrayObject(etStr1, etInt, strDate);
                            mArrayList.add(addObject2);
                            only_save_value(etStr1, etInt, strDate);
                            break;
                    }
                    Log.d("log : ", "라디오버튼 검사 후 배열에 추가");
                    Toast.makeText(MainActivity.this, "목록에 추가되었습니다", Toast.LENGTH_SHORT).show();
                    //save_value();
                    load_value_text();
                } catch(RuntimeException e) {
                    System.out.println(e);
                    Toast.makeText(MainActivity.this, "양식에 맞게 입력해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//여기까지 정상
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();

    }
    //FloatingActionButton클릭 시 나오는 Dialog
    //complete

    public void showCardviewDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View customLayout = getLayoutInflater().inflate(R.layout.card_dialog_layout, null);
        builder.setView(customLayout);



        builder.setPositiveButton("적용", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editText3 = (EditText) customLayout.findViewById(R.id.et_main);//금액
                editText4 = (EditText) customLayout.findViewById(R.id.et_main2);//날짜
                String etstr3 = editText3.getText().toString();
                String etstr4 = editText4.getText().toString();

                SimpleDateFormat beforFormat = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = beforFormat.parse(etstr4);
                } catch(ParseException e) {
                    e.printStackTrace();
                } catch(RuntimeException e1) {
                    e1.printStackTrace();
                    Toast.makeText(MainActivity.this, "양식에 맞게 입력해주세요", Toast.LENGTH_SHORT).show();
                }


                try {

                    String dateFormat = afterFormat.format(date);
                    //yyyy-MM-dd complete

                    SQLiteDatabase db = dbHelper2.getWritableDatabase();
                    db.execSQL(MySQLite.SQL_DELETE2);
                    //delete before data

                    int ietStr3 = Integer.parseInt(etstr3);
                    String sqlInsert = MySQLite.SQL_INSERT2 + "(" + ietStr3 + ", '" + dateFormat + "')";
                    db.execSQL(sqlInsert);
                    //save new data
                    textViewMain1.setText(formatter.format(ietStr3));


                //setting TextView complete
                //db에는 int와 yyyy-MM-dd로 저장, TextView 에는 ###,###형과 yyyy-MM-dd로 적용
                db.close();

                load_value_text();

                SQLiteDatabase db2 = dbHelper3.getWritableDatabase();
                db2.execSQL(MySQLite.SQL_DELETE3);
                String insert_value3 = MySQLite.SQL_INSERT3 + "(" + dateAvgCalc() + ", '" + strToday + "', " + R.drawable.ic_trending_flat_black_24dp + ")";
                db2.execSQL(insert_value3);
                db2.close();
                imgTrend.setImageResource(R.drawable.ic_trending_flat_black_24dp);
                int a = dateAvgCalc();
                textViewNew2.setText(formatter.format(a));
                textViewAvg.setText(formatter.format(a));

                } catch (RuntimeException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "양식에 맞게 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        try {


            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();//여기까지 정상
                }
            });

        } catch(RuntimeException e) {
            System.out.println(e);
        }

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    //Main CardView ImageView 클릭 시 나오는 Dialog
    //complete

    //1. 테이블 생성 및 호출 함수
    private void init_value() {
        dbHelper = new MySQLiteHelper(this);
    }
    //complete

    private void init_value2() {
        dbHelper2 = new MySQLiteHelper2(this);
    }
    //complete

    private void init_value3() {
        dbHelper3 = new MySQLiteHelper3(this);
    }

    //2. 레코드 추가 함수
    private void update_value() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(MySQLite.SQL_DELETE);

        for (int i = 0; i < mArrayList.size(); i++) {
            String sVar = mArrayList.get(i).getExpensePlace();
            int iVar = mArrayList.get(i).getExpenseMoney();
            String dVar = mArrayList.get(i).getStrDate();

            String sqlInsert = MySQLite.SQL_INSERT + "(" + "'" + sVar + "', " + iVar + ", '" + dVar + "'" + ")";
            db.execSQL(sqlInsert);

        }//추가한 배열로 업데이트 완료
        db.close();
    }
    //complete
    //ArrayList -> db save function

    private void only_save_value(String sOne, int iTwo, String sThree) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sqlInsert = MySQLite.SQL_INSERT + "(" + "'" + sOne + "', " + iTwo + ", '" + sThree + "'" + ")";
        db.execSQL(sqlInsert);

    }

    private void load_value2() {
        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        Cursor cursor = db.rawQuery(MySQLite.SQL_SELECT2, null);

        if (cursor.getCount() >= 1) {

            cursor.moveToFirst();
            int goalMoney = cursor.getInt(0);
            String mainDate = cursor.getString(1);
            String strGoal = formatter.format(goalMoney);

            textViewMain1.setText(strGoal);
            textViewMain4.setText(mainDate);

            db.close();

        } else {

            SQLiteDatabase db2 = dbHelper2.getWritableDatabase();
            String sqlInsert = MySQLite.SQL_INSERT2 + "(" + 0 + ", '" + strToday + "'" + ")";
            //(0, 'firstDate1')
            db2.execSQL(sqlInsert);
            db2.close();

            SQLiteDatabase db3 = dbHelper2.getReadableDatabase();
            Cursor cursor2 = db3.rawQuery(MySQLite.SQL_SELECT2, null);

            cursor2.moveToFirst();

            int intFirst = cursor2.getInt(0);
            String firstDate = cursor2.getString(1);
            String sIntFirst = Integer.toString(intFirst);
            textViewMain1.setText(sIntFirst);
            textViewMain4.setText(firstDate);

            db3.close();
        }

    }
    //complete

    //3. 레코드 값 불러오는 함수
    private void load_value() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(MySQLite.SQL_SELECT, null);

        mArrayList.removeAll(mArrayList);

        if (cursor.getCount() >= 1) {

            cursor.moveToFirst();

            for (int i = 0; i< cursor.getCount(); i++) {

                String place = cursor.getString(0);
                int money = cursor.getInt(1);
                String strDate = cursor.getString(2);

                ArrayObject arrayObject = new ArrayObject(place, money, strDate);
                mArrayList.add(arrayObject);

                cursor.moveToNext();
            }
            db.close();

       } else {

            ArrayObject arrayObject2 = new ArrayObject("지출/수입 출처", 0, strToday);
            mArrayList.add(arrayObject2);
            SQLiteDatabase db2 = dbHelper.getWritableDatabase();
            String insertDB = MySQLite.SQL_INSERT + "(" + "'" + mArrayList.get(0).getExpensePlace() + "', " + mArrayList.get(0).getExpenseMoney() + ", '" + mArrayList.get(0).getStrDate() + "'" + ")";
            db2.execSQL(insertDB);

        }

        load_value_text();

    }
    //complete

    //4. 레코드 값 삭제하는 함수
    private void delete_value() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(MySQLite.SQL_DELETE);
        mArrayList.removeAll(mArrayList);
        mAdapter.notifyDataSetChanged();
    }

    private void delete_value2() {
        SQLiteDatabase db = dbHelper2.getWritableDatabase();
        db.execSQL(MySQLite.SQL_DELETE2);

        load_value2();

    }

    private void delete_value3() {
        SQLiteDatabase db = dbHelper3.getWritableDatabase();
        db.execSQL(MySQLite.SQL_DELETE3);
        imgTrend.setImageResource(R.drawable.ic_trending_flat_black_24dp);
    }

    //String형으로 날짜를 넣고, int 형으로 몇 일 차이 나는지를 매개변수로 넣으면
    //int 값에 해당하는 금액을 모아서 합계를 구해주는 함수 만들기
    public int dateCalc(String date, long diffInt) {

        java.sql.Date sqlDate1 = java.sql.Date.valueOf(date);
        //java.sql.Date타입을 이용하여 valuOf() 메서드로 yyyy-MM-dd타입으로 입력받은 String매개변수를 Date형으로 변환.
        /*
        date형 정리
        getTime() : long 형으로 반환
        valueOf() : yyyy-MM-dd로 입력된 Stirng형을 Date형으로 변환
        parse() : SimpleDateFormat형에서 Date형으로 변환
         */
        int sumMoney = 0;

        for (int i=0; i< mArrayList.size(); i++) {

            String arrayDate = mArrayList.get(i).getStrDate();
            java.sql.Date sqlDate2 = java.sql.Date.valueOf(arrayDate);
            //i번째 배열의 Date를 가져와서 yyyy-MM-dd 로 Date형 저장.

            long longDate1 = sqlDate1.getTime() / (86400 * 1000);
            long longDate2 = sqlDate2.getTime() / (86400 * 1000);
            //입력된 날짜와 i번째 배열의 날짜를 하루단위로 연산.

            //입력한 날짜를 long형으로 반환하고 배열에서 가져온 날짜도 long형으로 변환하여
            //n일 이상 차이났을 시 sumMoney변수에 취합.

            if (longDate1 - longDate2 <= diffInt && mArrayList.get(i).getExpenseMoney() < 0) {
                //목표날짜 or 오늘날짜 랑 비교했을 때 배열에 입력돼있던 날짜와의 차이가 입력한 int인 diff만큼 난다면,
               sumMoney += mArrayList.get(i).getExpenseMoney();
               //조건에 맞는 i번째 배열에서 지출금액을 int형 변수에 합산.
            }
        }
        return sumMoney;
        //반환
    }
    //complete

    public int dateAvgCalc() {

        SQLiteDatabase db = dbHelper2.getReadableDatabase();
        Cursor cursor = db.rawQuery(MySQLite.SQL_SELECT2, null);
        cursor.moveToFirst();
        int dbGoalMoney = cursor.getInt(0);
        String goalDate = cursor.getString(1);
        //목표금액 가져오기

        java.sql.Date dateToday = java.sql.Date.valueOf(strToday);//오늘날짜
        java.sql.Date dateGoal = java.sql.Date.valueOf(goalDate);//목표날짜

        long longGoal2 = dateGoal.getTime() / (86400*1000);
        long longToday2 = dateToday.getTime() / (86400*1000);

        int intLong = ((int)longGoal2 - (int)longToday2) + 1;
        //날짜차이 계산 완료

        int sum = 0;

        for (int i=0; i < mArrayList.size(); i++) {
            sum += mArrayList.get(i).getExpenseMoney();
        }//현재 총 금액 계산

        int diffMoney = dbGoalMoney - sum;
        //금액차이 계산 완료

        int abcd;

        if (diffMoney == 0) {
            abcd = 0;
        } else if (intLong <= 0) {
            abcd = 0;
        } else {
            abcd = diffMoney / intLong;
        }

        return abcd;

    }

    //목표 date - today 만큼 목표 금액 - 현재 합계 / 남은날짜 해주는 함수.
    //결과가 양수라면 앞으로 하루에 모아야 할 돈을, 결과가 음수라면 하루에 사용해야 할 돈을 의미함.

    private void set_Image3(int a) {

        switch (a) {

            case 1 : imgTrend.setImageResource(R.drawable.ic_trending_flat_black_24dp);
            System.out.println(a);
            break;

            case 2 : imgTrend.setImageResource(R.drawable.ic_trending_up_black_24dp);
            System.out.println(a);
            break;

            case 3 : imgTrend.setImageResource(R.drawable.ic_trending_down_black_24dp);
            System.out.println(a);
            break;

        }

    }

    private void save_value3(int c) {
        int a = dateAvgCalc();
        SQLiteDatabase db = dbHelper3.getWritableDatabase();
        db.execSQL(MySQLite.SQL_DELETE3);
        //delete save before data

        String insert_value = MySQLite.SQL_INSERT3 + "(" + a + ", '" + strToday + "', " + c + ")";
        db.execSQL(insert_value);

        textViewNew2.setText(formatter.format(a));
        textViewAvg.setText(formatter.format(a));
        set_Image3(c);

        db.close();

        //insert to empty database
    }
    //complete

    private void load_value3() {
        //db에 저장된 날짜가 오늘과 다르면 이미지를 업데이트하고 새로저장
        //db에 저장된 날짜가 오늘과 같으면 아무것도 하지 않음.


        SQLiteDatabase db = dbHelper3.getReadableDatabase();
        Cursor cursor = db.rawQuery(MySQLite.SQL_SELECT3, null);

        System.out.println(cursor.getCount());
        if (cursor.getCount() >= 1) {
            //db가 존재한다면 아래 코드 수행
            cursor.moveToFirst();
            int dbAvg = cursor.getInt(0);
            String dbDate = cursor.getString(1);

            java.sql.Date dateDbDate = java.sql.Date.valueOf(dbDate);
            java.sql.Date dateTodayDate = java.sql.Date.valueOf(strToday);
            long longdbDate = dateDbDate.getTime();
            long longTodayDate = dateTodayDate.getTime();

            if (longdbDate != longTodayDate && dbAvg < dateAvgCalc()) {
                //up if code

                //change image code
                //image.setImage(R.drawable.up image id)
                cursor.close();
                imgTrend.setImageResource(R.drawable.ic_trending_down_black_24dp);
                save_value3(3);

            } else if(longdbDate != longTodayDate && dbAvg > dateAvgCalc()) {
                //down if code

                //image.setImage(R.drawable.down image id)
                cursor.close();
                imgTrend.setImageResource(R.drawable.ic_trending_up_black_24dp);
                save_value3(2);

            } else if (longdbDate != longTodayDate && dbAvg == dateAvgCalc()) {

                cursor.close();
                imgTrend.setImageResource(R.drawable.ic_trending_flat_black_24dp);
                save_value3(1);

            } else if(longdbDate == longTodayDate) {
                textViewNew2.setText(formatter.format(dbAvg));
                textViewAvg.setText(formatter.format(dbAvg));
                set_Image3(cursor.getInt(2));
                cursor.close();
            }
        } else {

            SQLiteDatabase db2 = dbHelper3.getWritableDatabase();
            String insert_value = MySQLite.SQL_INSERT3 + "(" + 0 + ", '" + strToday + "', " + 1 + ")";
            db2.execSQL(insert_value);
            db2.close();
            imgTrend.setImageResource(R.drawable.ic_trending_flat_black_24dp);
        }
    }

    private void load_value_text() {

        try {


            SQLiteDatabase db = dbHelper2.getReadableDatabase();
            Cursor cursor = db.rawQuery(MySQLite.SQL_SELECT2, null);
            cursor.moveToFirst();
            int sum = 0;

            int todaySum = -1 * (dateCalc(strToday, 0));

            textViewNew1.setText(formatter.format(todaySum));
            textViewToday.setText(formatter.format(todaySum));
            //오늘 사용액


            Date nowDate = new Date();
            String strToday = todayFormat.format(nowDate);
            String strGoal = cursor.getString(1);
            java.sql.Date dateToday = java.sql.Date.valueOf(strToday);
            java.sql.Date dateGoalstr = java.sql.Date.valueOf(strGoal);
            long longDateToday = (dateToday.getTime()) / (86400 * 1000);
            long longDateGoalStr = (dateGoalstr.getTime()) / (86400 * 1000);
            int intDateDay = (int) longDateGoalStr - (int) longDateToday;
            String strIntDateDay = Integer.toString(intDateDay);
            textViewNew0 = (TextView) findViewById(R.id.tv_Dday_main);
            textViewNew0.setText(strIntDateDay);
            //d-day

            textViewMain4.setText(strGoal);
            //목표 기간

            for (int i = 0; i < mArrayList.size(); i++) {
                sum += mArrayList.get(i).getExpenseMoney();
            }

            int getInt = cursor.getInt(0);
            int minusInt = getInt - sum;

            textViewMain2.setText(formatter.format(sum));
            //총 사용액
            textViewMain3.setText(formatter.format(minusInt));
            //목표까지 남은 돈

            db.close();

        } catch(RuntimeException e) {
            System.out.println(e);
            Toast.makeText(this, "양식에 맞게 입력해주세요", Toast.LENGTH_SHORT).show();
        }

    }

}