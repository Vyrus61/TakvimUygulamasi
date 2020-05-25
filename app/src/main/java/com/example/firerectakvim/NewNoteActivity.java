package com.example.firerectakvim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class NewNoteActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    Button btnDatePick, btnTimePick;
    TextView txtVSaat,txtVTarih,txtVTarihE,mRepeatTypeText,mRepeatText;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    public static int myday,myMonth,myYear,myHour,myMinute;
    public static int hour,minute;

    public static int mydayE,myMonthE,myYearE,myHourE,myMinuteE;

    public static String title;
    public static String description;
    public static String mRepeatType;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Hatirlatma Ekle");
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        mRepeatText = (TextView) findViewById(R.id.set_repeat_type);

        //btnDatePick = (Button) findViewById(R.id.btnPickDate);
      //  btnTimePick = (Button) findViewById(R.id.btnPickTime);
        txtVSaat = (TextView) findViewById(R.id.txtViewSaat);
        txtVTarih = (TextView) findViewById(R.id.txtViewTarih);

        txtVTarihE=(TextView) findViewById(R.id.txtViewTarihE);

        calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.SECOND, 0);


        RelativeLayout relative1 = (RelativeLayout) findViewById(R.id.time);
        relative1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


        RelativeLayout relative2 = (RelativeLayout) findViewById(R.id.date);
        relative2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(NewNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myday=dayOfMonth;
                        myMonth=month+1;
                        myYear=year;
                        Toast.makeText(NewNoteActivity.this, String.valueOf(myYear), Toast.LENGTH_SHORT).show();
                        txtVTarih.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, day,month,year);
                datePickerDialog.show();
            }
        });

        RelativeLayout relative3 = (RelativeLayout) findViewById(R.id.dateE);
        relative3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                calendar = Calendar.getInstance();
                int dayE = calendar.get(Calendar.DAY_OF_MONTH);
                int monthE = calendar.get(Calendar.MONTH);
                int yearE = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(NewNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mydayE=dayOfMonth;
                        myMonthE=month+1;
                        myYearE=year;
                        Toast.makeText(NewNoteActivity.this, String.valueOf(myYear), Toast.LENGTH_SHORT).show();
                        txtVTarihE.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, dayE,monthE,yearE);
                datePickerDialog.show();
            }
        });


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                sendEMail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void saveNote() {
        title = editTextTitle.getText().toString();
        description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Başlık ve not ekleyin", Toast.LENGTH_SHORT).show();
            return;
        }
        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("Notebook");
        Toast.makeText(this, "yıl"+String.valueOf(myYear), Toast.LENGTH_SHORT).show();
        notebookRef.add(new Note(title, description, priority,myday,myMonth,myYear,myHour,myMinute,mRepeatType,mydayE,myMonthE,myYearE));

        Toast.makeText(this, "Hatırlatma eklendi", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
      //  mTextView.setText(timeText);
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);


        if (mRepeatType !=null && mRepeatType.equals("Tek")) {

            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1);
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        } else if (mRepeatType !=null &&mRepeatType.equals("Saat")) {
            Toast.makeText(this, mRepeatType, Toast.LENGTH_SHORT).show();

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                    AlarmManager.INTERVAL_HOUR, pendingIntent);

        } else if (mRepeatType !=null &&mRepeatType.equals("Gün")) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);

        } else if (mRepeatType !=null &&mRepeatType.equals("Hafta")) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY*7, pendingIntent);

        } else if (mRepeatType !=null &&mRepeatType.equals("Ay")) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY*30, pendingIntent);
        } else if (mRepeatType !=null &&mRepeatType.equals("Yıl")) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY*365, pendingIntent);
        }



    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
       // mTextView.setText("Alarm canceled");
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        myHour=hourOfDay;
        myMinute=minute;
        txtVSaat.setText(hourOfDay + " : "+ minute );
        updateTimeText(c);
        selectRepeatType(view);
        startAlarm(c);
    }

    public void selectRepeatType(View v){
        final String[] items = new String[6];

        items[0] = "Tek";
        items[1] = "Saat";
        items[2] = "Gün";
        items[3] = "Hafta";
        items[4] = "Ay";
        items[5] = "Yıl";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seç");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mRepeatType = items[item];

                mRepeatText.setText("Her " + " " + mRepeatType );
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void sendEMail() {

        String subject = NewNoteActivity.title;
        String message = NewNoteActivity.description+" Tarih: "+NewNoteActivity.myday+"/"+NewNoteActivity.myMonth+"/"+NewNoteActivity.myYear+" - "+NewNoteActivity.mydayE+"/"+NewNoteActivity.myMonthE+"/"+NewNoteActivity.myYearE+" saat: "+NewNoteActivity.myHour+":"+NewNoteActivity.myMinute;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}