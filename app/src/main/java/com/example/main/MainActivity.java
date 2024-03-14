package com.example.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.graphics.Color;

import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    public Button colorPicker, btnProcesar, btnCopear;
    public int defaultColor, colorNew;
    public ConstraintLayout constraintLayout;
    public TextView txtRes, lblFormato, lblColor, lblFor;
    public RadioGroup rgFormato;
    public RadioButton rbHex, rbRGB, rbARGB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.consTraint), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rgFormato = findViewById(R.id.rgFormato);
        rbHex = findViewById(R.id.rbHex);
        rbARGB = findViewById(R.id.rbARGB);
        rbRGB = findViewById(R.id.rbRGB);
        lblFormato = findViewById(R.id.lblFormato);
        lblColor = findViewById(R.id.lblColor);
        btnCopear = findViewById(R.id.btnCopear);
        btnProcesar = findViewById(R.id.btnProcesar);
        txtRes = findViewById(R.id.txtRes);
        colorPicker = findViewById(R.id.btnOpenColorPicker);
        constraintLayout = findViewById(R.id.consTraint);
        lblFor = findViewById(R.id.lblFor);


        defaultColor = ContextCompat.getColor(this,R.color.white);

        lblFormato.setVisibility(View.INVISIBLE);
        rgFormato.setVisibility(View.INVISIBLE);
        btnProcesar.setVisibility(View.INVISIBLE);
        txtRes.setVisibility(View.INVISIBLE);
        btnCopear.setVisibility(View.INVISIBLE);
        lblColor.setVisibility(View.INVISIBLE);
        lblFor.setVisibility(View.INVISIBLE);



        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgFormato.clearCheck();
                btnProcesar.setVisibility(View.INVISIBLE);
                btnCopear.setVisibility(View.INVISIBLE);
                AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(MainActivity.this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        defaultColor = color;
                        colorNew = color;
                        lblColor.setBackgroundColor(colorNew);
                        lblColor.setVisibility(View.VISIBLE);
                        lblFormato.setVisibility(View.VISIBLE);
                        rgFormato.setVisibility(View.VISIBLE);

                    }


                });

                ambilWarnaDialog.show();
            }
        });

        rgFormato.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FormatosColor formatos = convertirFormato(colorNew);
                txtRes.setVisibility(View.INVISIBLE);
                lblFor.setVisibility(View.INVISIBLE);
                btnCopear.setVisibility(View.INVISIBLE);
                if (checkedId == R.id.rbHex) {
                    btnProcesar.setVisibility(View.VISIBLE);
                    btnProcesar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String hexColor  = formatos.getHexColor();
                            txtRes.setText("Formato creado - Hexadecimal");
                            lblFor.setText(hexColor);
                            txtRes.setVisibility(View.VISIBLE);
                            lblFor.setVisibility(View.VISIBLE);
                            btnCopear.setVisibility(View.VISIBLE);
                        }
                    });
                } else if (checkedId == R.id.rbARGB) {
                    btnProcesar.setVisibility(View.VISIBLE);
                    btnProcesar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String argbColor = formatos.getArgbColor();
                            txtRes.setText("Formato creado - ARGB");
                            lblFor.setText(argbColor);
                            txtRes.setVisibility(View.VISIBLE);
                            lblFor.setVisibility(View.VISIBLE);
                            btnCopear.setVisibility(View.VISIBLE);
                        }
                    });
                } else if (checkedId == R.id.rbRGB) {
                    btnProcesar.setVisibility(View.VISIBLE);
                    btnProcesar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String rgbColor = formatos.getRgbColor();
                            txtRes.setText("Formato creado - RGB");
                            lblFor.setText(rgbColor);
                            txtRes.setVisibility(View.VISIBLE);
                            lblFor.setVisibility(View.VISIBLE);
                            btnCopear.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });

        btnCopear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = lblFor.getText().toString();

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Texto copiado", texto);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private FormatosColor convertirFormato(int color) {
        defaultColor = color;
        String hexColor = String.format("#%06X", (0xFFFFFF & defaultColor));
        String rgbColor = String.format(Locale.getDefault(), "RGB(%d, %d, %d)",
                Color.red(defaultColor), Color.green(defaultColor), Color.blue(defaultColor));
        String argbColor = String.format(Locale.getDefault(), "ARGB(%d, %d, %d, %d)",
                Color.alpha(defaultColor), Color.red(defaultColor), Color.green(defaultColor), Color.blue(defaultColor));
        return new FormatosColor(hexColor, rgbColor, argbColor);
    }

}