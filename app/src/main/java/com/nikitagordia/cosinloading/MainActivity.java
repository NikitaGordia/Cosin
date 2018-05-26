package com.nikitagordia.cosinloading;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import com.nikitagordia.cosin.Cosin;

import com.nikitagordia.cosin.colorAdapters.ColorAdapterBG;
import com.nikitagordia.cosin.colorAdapters.ColorAdapterBR;
import com.nikitagordia.cosin.colorAdapters.ColorAdapterGR;
import com.nikitagordia.cosin.colorAdapters.ColorAdapterRB;
import com.nikitagordia.cosin.colorAdapters.ColorAdapterRG;
import com.nikitagordia.cosin.colorAdapters.DefaultColorAdapterGB;
import com.nikitagordia.cosin.textAdapters.DefaultBinaryTextAdapter;
import com.nikitagordia.cosin.textAdapters.WordTextAdapter;
import com.nikitagordia.cosinloading.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);

        loadView();
    }

    private void loadView() {

        bind.isLoading.setChecked(bind.cosin.isLoadingData());
        bind.isLoading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bind.cosin.setLoadingData(isChecked);
            }
        });

        bind.tvRectWidth.setText(partRev(bind.cosin.getRectWidth(), bind.cosin.limRectWidth) + "");
        bind.rectWidth.setProgress(partRev(bind.cosin.getRectWidth(), bind.cosin.limRectWidth));
        bind.rectWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bind.cosin.setRectWidth(part(progress, bind.cosin.limRectWidth));
                bind.tvRectWidth.setText(part(progress, bind.cosin.limRectWidth) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bind.tvViewWidth.setText(partRev(bind.cosin.getViewWidth(), bind.cosin.limLayoutWidth) + "");
        bind.viewWidth.setProgress(partRev(bind.cosin.getViewWidth(), bind.cosin.limLayoutWidth));
        bind.viewWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bind.cosin.setLayoutWidth(part(progress, bind.cosin.limLayoutWidth));
                bind.tvViewWidth.setText(part(progress, bind.cosin.limLayoutWidth) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bind.tvViewHeight.setText(partRev(bind.cosin.getViewHeight(), bind.cosin.limLayoutHeight) + "");
        bind.viewHeight.setProgress(partRev(bind.cosin.getViewHeight(), bind.cosin.limLayoutHeight));
        bind.viewHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bind.cosin.setLayoutHeight(part(progress, bind.cosin.limLayoutHeight));
                bind.tvViewHeight.setText(part(progress, bind.cosin.limLayoutHeight) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bind.period.check(R.id.p1);
        bind.period.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.p0 :
                        bind.cosin.setPeriod(Math.PI / 2d);
                        break;
                    case R.id.p1 :
                        bind.cosin.setPeriod(Math.PI);
                        break;
                    case R.id.p2 :
                        bind.cosin.setPeriod(Math.PI * 2d);
                        break;
                    case R.id.p3 :
                        bind.cosin.setPeriod(Math.PI * 5d);
                        break;
                    case R.id.p4 :
                        bind.cosin.setPeriod(Math.PI * 10d);
                        break;
                }
            }
        });

        bind.tvSpeed.setText(Math.round(partRev(bind.cosin.getSpeed(), bind.cosin.limSpeed)) + "");
        bind.speed.setProgress((int)partRev(bind.cosin.getSpeed(), bind.cosin.limSpeed));
        bind.speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bind.cosin.setSpeed(part(progress, bind.cosin.limSpeed));
                bind.tvSpeed.setText(Math.round(partRev(bind.cosin.getSpeed(), bind.cosin.limSpeed)) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bind.colorAdapter.check(R.id.gb);
        bind.colorAdapter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb :
                        bind.cosin.setColorAdapter(new ColorAdapterRB());
                        break;
                    case R.id.br :
                        bind.cosin.setColorAdapter(new ColorAdapterBR());
                        break;
                    case R.id.rg :
                        bind.cosin.setColorAdapter(new ColorAdapterRG());
                        break;
                    case R.id.gr :
                        bind.cosin.setColorAdapter(new ColorAdapterGR());
                        break;
                    case R.id.bg :
                        bind.cosin.setColorAdapter(new ColorAdapterBG());
                        break;
                    case R.id.gb :
                        bind.cosin.setColorAdapter(new DefaultColorAdapterGB());
                        break;
                }
            }
        });

        bind.tvOffset.setText(partRev(bind.cosin.getOffset(), bind.cosin.limOffset) + "");
        bind.offset.setProgress((int)partRev(bind.cosin.getOffset(), bind.cosin.limOffset));
        bind.offset.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bind.cosin.setOffset(part(progress, bind.cosin.limOffset));
                bind.tvOffset.setText(part(progress, bind.cosin.limOffset) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bind.rectText.check(R.id.binary);
        bind.rectText.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.binary :
                        bind.cosin.setTextAdapter(new DefaultBinaryTextAdapter());
                        break;
                    case R.id.rb_text :
                        bind.cosin.setTextAdapter(new WordTextAdapter(bind.etText.getText().toString()));
                }
            }
        });
        bind.etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bind.cosin.setTextAdapter(new WordTextAdapter(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bind.dir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bind.cosin.setDirectionRight(isChecked);
            }
        });

        bind.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.cosin.setEnd();
            }
        });
    }

    private int part(int x, Cosin.Limit<Integer> lim) {
        int len = lim.max - lim.min;
        return (x * len) / 100 + lim.min;
    }

    private int partRev(int x, Cosin.Limit<Integer> lim) {
        int len = lim.max - lim.min;
        return ((x - lim.min) * 100) / len;
    }

    private double part(double x, Cosin.Limit<Double> lim) {
        double len = lim.max - lim.min;
        return ((x * len) / 100d + lim.min);
    }

    private double partRev(double x, Cosin.Limit<Double> lim) {
        double len = lim.max - lim.min;
        return ((x - lim.min) * 100d) / len;
    }
}
