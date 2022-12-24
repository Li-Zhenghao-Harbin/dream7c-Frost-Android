package com.dream7c.frost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dream7c.frost.error.EmBusinessError;
import com.dream7c.frost.function.EmBusinessFunction;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ProjectActivity extends AppCompatActivity {
    public static String content = "";
    public static String splitter = " ";
    private boolean contentSaved = true;
    DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
    //Calc
    private String[] dialogListCalculation = new String[] {
            EmBusinessFunction.AUTO_SUM.getFuncName(),
            EmBusinessFunction.STATISTIC.getFuncName(),
            EmBusinessFunction.MATH.getFuncName()};
    private String[] dialogListCalculationSum = new String[] {
            EmBusinessFunction.MAX.getFuncName(),
            EmBusinessFunction.MIN.getFuncName(),
            EmBusinessFunction.SUM.getFuncName(),
            EmBusinessFunction.COUNT.getFuncName()};
    private String[] dialogListCalculationStatistic = new String[] {
            EmBusinessFunction.RANGE.getFuncName(),
            EmBusinessFunction.AVERAGE.getFuncName(),
            EmBusinessFunction.GEOMETRIC_MEAN.getFuncName(),
            EmBusinessFunction.HARMONIC_MEAN.getFuncName(),
            EmBusinessFunction.MEDIAN.getFuncName(),
            EmBusinessFunction.MODE.getFuncName(),
            EmBusinessFunction.PSDV.getFuncName(),
            EmBusinessFunction.SSDV.getFuncName(),
            EmBusinessFunction.PSD.getFuncName(),
            EmBusinessFunction.SSD.getFuncName(),
            EmBusinessFunction.COEFFICIENT_OF_VARIATION.getFuncName()};
    private String[] dialogListCalculationMath = new String[] {
            EmBusinessFunction.ABSOLUTE.getFuncName(),
            EmBusinessFunction.OPPOSITE.getFuncName(),
            EmBusinessFunction.CEIL.getFuncName(),
            EmBusinessFunction.FLOOR.getFuncName(),
            EmBusinessFunction.ROUND.getFuncName(),
            EmBusinessFunction.GCD.getFuncName(),
            EmBusinessFunction.LCM.getFuncName(),
            EmBusinessFunction.SIN.getFuncName(),
            EmBusinessFunction.COS.getFuncName(),
            EmBusinessFunction.TAN.getFuncName()};
    //Data
    private String[] dialogListProcess = new String[] {
            EmBusinessFunction.GENERATE.getFuncName(),
            EmBusinessFunction.RANDOM_SAMPLING.getFuncName(),
            EmBusinessFunction.SORT.getFuncName()};
    private String[] dialogListProcessGenerate = new String[] {
            EmBusinessFunction.GENERATE_NUMBER.getFuncName(),
            EmBusinessFunction.GENERATE_ARRAY1.getFuncName(),
            EmBusinessFunction.GENERATE_ARRAY2.getFuncName()};
    private String[] dialogListProcessSort = new String[] {
            EmBusinessFunction.ASC_SORT.getFuncName(),
            EmBusinessFunction.DESC_SORT.getFuncName(),
            EmBusinessFunction.CHAOS.getFuncName(),
            EmBusinessFunction.REVERSE.getFuncName(),
            EmBusinessFunction.REMOVE_REPEAT.getFuncName()};
    //Dialog
    private final String DIALOG_BUTTON_POSITIVE = "确定";

    private void showToast(String text) {
        Toast toast = Toast.makeText(ProjectActivity.this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void contentSaved(boolean status) {
        contentSaved = status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        //Initial
        setTitle(MainActivity.targetProjectName);
        String fileName = MainActivity.targetProjectName + MainActivity.fileSuffix;
        final TextView tx_input = findViewById(R.id.tx_input);
        if (MainActivity.newProject) {
            tx_input.setText("");
            saveProject();
        } else {
            try {
                initProblems(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final LinearLayout layout_output = findViewById(R.id.layout_output);
        layout_output.setVisibility(View.GONE);
        //Save
        contentSaved(true);
        tx_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contentSaved(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //OnClickListener
        final TextView tx_output = findViewById(R.id.tx_output);
        tx_output.setKeyListener(null);
        tx_output.setOnClickListener(view -> hideSoftKeyboard(ProjectActivity.this));
        Button btn_copy = findViewById(R.id.btn_copy);
        btn_copy.setOnClickListener(view -> {
            ClipboardManager Clipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            Clipboard.setPrimaryClip(ClipData.newPlainText("Frost", tx_output.getText().toString()));
        });
        Button btn_hide = findViewById(R.id.btn_hide);
        btn_hide.setOnClickListener(view -> layout_output.setVisibility(View.GONE));
        Button btn_fill = findViewById(R.id.btn_fill);
        btn_fill.setOnClickListener(view -> tx_input.setText(tx_output.getText().toString()));
    }

    @Override
    public void onBackPressed() {
        if (!contentSaved) {
            final AlertDialog.Builder saveDialog = new AlertDialog.Builder(ProjectActivity.this);
            saveDialog.setTitle("是否保存修改内容？");
            saveDialog.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    saveProject();
                    finish();
                }
            });
            saveDialog.setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            saveDialog.show();
        } else {
            finish();
        }
    }

    private void initProblems(String fileName) throws Exception {
        File file = new File(MainActivity.filePath + fileName);
        TextView tx_input = findViewById(R.id.tx_input);
        tx_input.setText(FileParser.parserTxt(file));
    }

    private void saveProject() {
        TextView tx_input = findViewById(R.id.tx_input);
        content = tx_input.getText().toString();
        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.initData();
        showToast("已保存");
        contentSaved(true);
    }

    private boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?\\d+(\\.\\d*)?|\\.\\d+$");
        return pattern.matcher(str).matches();
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private List<Integer> getIntegerList() {
        List<Integer> res = new ArrayList<>();
        TextView tx_input = findViewById(R.id.tx_input);
        String[] elements = tx_input.getText().toString().split(splitter);
        for (String element : elements) {
            if (!isInteger(element)) {
                return null;
            }
            res.add(Integer.valueOf((element)));
        }
        return res;
    }

    public List<Double> getDoubleList() {
        List<Double> res = new ArrayList<>();
        TextView tx_input = findViewById(R.id.tx_input);
        String[] elements = tx_input.getText().toString().split(splitter);
        for (String element : elements) {
            if (!isDouble(element)) {
                return null;
            }
            res.add(Double.valueOf(element));
        }
        return res;
    }

    public List<String> getStringList() {
        TextView tx_input = findViewById(R.id.tx_input);
        String[] elements = tx_input.getText().toString().split(splitter);
        return new ArrayList<>(Arrays.asList(elements));
    }

    private void reportError(String error) {
        hideSoftKeyboard(ProjectActivity.this);
        View layout_output = findViewById(R.id.layout_output);
        layout_output.setVisibility(View.GONE);
        displaySnackBar(error);
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void displaySnackBar(String text) {
        Snackbar.make(findViewById(R.id.ConstraintLayout), text, Snackbar.LENGTH_SHORT).show();
    }

    private void displayResult(String title) {
        //Display in output
        LinearLayout layout_output = findViewById(R.id.layout_output);
        layout_output.setVisibility(View.VISIBLE);
        //Snack bar
        displaySnackBar(title);
        //Hide keyboard
        hideSoftKeyboard(ProjectActivity.this);
    }

    private void setSingleResult(String title, double result) {
        TextView tx_output = findViewById(R.id.tx_output);
        tx_output.setText(decimalFormat.format(result));
        displayResult(title);
    }

    private void setMultipleIntegerResult(String title, List<Integer> list) {
        TextView tx_output = findViewById(R.id.tx_output);
        StringBuilder sb = new StringBuilder();
        for (Integer e : list) {
            sb.append(e);
            sb.append(splitter);
        }
        tx_output.setText(sb.toString());
        tx_output.setText(tx_output.getText().toString().trim());
        displayResult(title);
    }

    private void setMultipleCharResult(String title, List<Character> list) {
        TextView tx_output = findViewById(R.id.tx_output);
        StringBuilder sb = new StringBuilder();
        for (Character e : list) {
            sb.append(e);
            sb.append(splitter);
        }
        tx_output.setText(sb.toString());
        tx_output.setText(tx_output.getText().toString().trim());
        displayResult(title);
    }

    private void setMultipleStringResult(String title, List<String> list) {
        TextView tx_output = findViewById(R.id.tx_output);
        StringBuilder sb = new StringBuilder();
        for (String e : list) {
            sb.append(e);
            sb.append(splitter);
        }
        tx_output.setText(sb.toString());
        tx_output.setText(tx_output.getText().toString().trim());
        displayResult(title);
    }

    private void setMultipleResult(String title, List<Double> list) {
        TextView tx_output = findViewById(R.id.tx_output);
        StringBuilder sb = new StringBuilder();
        for (double e : list) {
            sb.append(decimalFormat.format(e));
            sb.append(splitter);
        }
        tx_output.setText(sb.toString());
        tx_output.setText(tx_output.getText().toString().trim());
        displayResult(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calc:
                if (getDoubleList() == null) {
                    reportError(EmBusinessError.DATA_VALIDATION_ERROR.getErrMsg());
                    return false;
                }
                final List<Double> calcList = new ArrayList<>(getDoubleList());
                final AlertDialog.Builder dialogCalculation = new AlertDialog.Builder(ProjectActivity.this);
                dialogCalculation.setTitle(EmBusinessFunction.CALCULATE.getFuncName());
                dialogCalculation.setItems(dialogListCalculation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final DataCalculation dataCalculation = new DataCalculation();
                        switch (i) {
                            case 0: //Sum
                                AlertDialog.Builder dialogCalculationSum = new AlertDialog.Builder(ProjectActivity.this);
                                dialogCalculationSum.setTitle(EmBusinessFunction.AUTO_SUM.getFuncName());
                                dialogCalculationSum.setItems(dialogListCalculationSum, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                setSingleResult(EmBusinessFunction.MAX.getFuncName(), dataCalculation.max(calcList));
                                                break;
                                            case 1:
                                                setSingleResult(EmBusinessFunction.MIN.getFuncName(), dataCalculation.min(calcList));
                                                break;
                                            case 2:
                                                setSingleResult(EmBusinessFunction.SUM.getFuncName(), dataCalculation.sum(calcList));
                                                break;
                                            case 3:
                                                setSingleResult(EmBusinessFunction.COUNT.getFuncName(), dataCalculation.count(getStringList()));
                                        }
                                    }
                                }).show();
                                break;
                            case 1: //Statistic
                                AlertDialog.Builder dialogCalculationStatistic = new AlertDialog.Builder(ProjectActivity.this);
                                dialogCalculationStatistic.setTitle(EmBusinessFunction.STATISTIC.getFuncName());
                                dialogCalculationStatistic.setItems(dialogListCalculationStatistic, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                setSingleResult(EmBusinessFunction.RANGE.getFuncName(), dataCalculation.range(calcList));
                                                break;
                                            case 1:
                                                setSingleResult(EmBusinessFunction.AVERAGE.getFuncName(), dataCalculation.average(calcList));
                                                break;
                                            case 2:
                                                setSingleResult(EmBusinessFunction.GEOMETRIC_MEAN.getFuncName(), dataCalculation.geometricMean(calcList));
                                                break;
                                            case 3:
                                                setSingleResult(EmBusinessFunction.HARMONIC_MEAN.getFuncName(), dataCalculation.harmonicMean(calcList));
                                                break;
                                            case 4:
                                                setSingleResult(EmBusinessFunction.MEDIAN.getFuncName(), dataCalculation.median(calcList));
                                                break;
                                            case 5:
                                                setMultipleResult(EmBusinessFunction.MODE.getFuncName(), dataCalculation.mode(calcList));
                                                break;
                                            case 6:
                                                setSingleResult(EmBusinessFunction.PSDV.getFuncName(), dataCalculation.psdv(calcList));
                                                break;
                                            case 7:
                                                setSingleResult(EmBusinessFunction.SSDV.getFuncName(), dataCalculation.ssdv(calcList));
                                                break;
                                            case 8:
                                                setSingleResult(EmBusinessFunction.SSD.getFuncName(), dataCalculation.ssd(calcList));
                                                break;
                                            case 9:
                                                setSingleResult(EmBusinessFunction.PSD.getFuncName(), dataCalculation.psd(calcList));
                                                break;
                                            case 10:
                                                setSingleResult(EmBusinessFunction.COEFFICIENT_OF_VARIATION.getFuncName(), dataCalculation.coefficientOfVariation(calcList));
                                                break;
                                        }
                                    }
                                }).show();
                                break;
                            case 2: //Math
                                AlertDialog.Builder dialogCalculationMath = new AlertDialog.Builder(ProjectActivity.this);
                                dialogCalculationMath.setTitle(EmBusinessFunction.MATH.getFuncName());
                                dialogCalculationMath.setItems(dialogListCalculationMath, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                setMultipleResult(EmBusinessFunction.ABSOLUTE.getFuncName(), dataCalculation.absolute(calcList));
                                                break;
                                            case 1:
                                                setMultipleResult(EmBusinessFunction.OPPOSITE.getFuncName(), dataCalculation.opposite(calcList));
                                                break;
                                            case 2:
                                                setMultipleResult(EmBusinessFunction.CEIL.getFuncName(), dataCalculation.ceil(calcList));
                                                break;
                                            case 3:
                                                setMultipleResult(EmBusinessFunction.FLOOR.getFuncName(), dataCalculation.floor(calcList));
                                                break;
                                            case 4:
                                                setMultipleResult(EmBusinessFunction.ROUND.getFuncName(), dataCalculation.round(calcList));
                                                break;
                                            case 5:
                                                try {
                                                    setSingleResult(EmBusinessFunction.GCD.getFuncName(), dataCalculation.nGCD(getIntegerList()));
                                                } catch (Exception e) {
                                                    reportError(EmBusinessError.DATA_VALIDATION_ERROR.getErrMsg());
                                                    return;
                                                }
                                                break;
                                            case 6:
                                                try {
                                                    setSingleResult(EmBusinessFunction.LCM.getFuncName(), dataCalculation.nLCM(getIntegerList()));
                                                } catch (Exception e) {
                                                    reportError(EmBusinessError.DATA_VALIDATION_ERROR.getErrMsg());
                                                    return;
                                                }
                                                break;
                                            case 7:
                                                setMultipleResult(EmBusinessFunction.SIN.getFuncName(), dataCalculation.sin(calcList));
                                                break;
                                            case 8:
                                                setMultipleResult(EmBusinessFunction.COS.getFuncName(), dataCalculation.cos(calcList));
                                                break;
                                            case 9:
                                                setMultipleResult(EmBusinessFunction.TAN.getFuncName(), dataCalculation.tan(calcList));
                                                break;
                                        }
                                    }
                                }).show();
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.data:
                //Detect data
                final List<String> dataList = new ArrayList<>(getStringList());
                if (dataList == null) {
                    reportError(EmBusinessError.DATA_VALIDATION_ERROR.getErrMsg());
                    return false;
                }
                final AlertDialog.Builder dialogData = new AlertDialog.Builder(ProjectActivity.this);
                dialogData.setTitle(EmBusinessFunction.PROCESS.getFuncName());
                dialogData.setItems(dialogListProcess, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final DataProcess dataProcess = new DataProcess();
                        switch (i) {
                            case 0: //Generate
                                AlertDialog.Builder dialogProcessGenerate = new AlertDialog.Builder(ProjectActivity.this);
                                dialogProcessGenerate.setTitle(EmBusinessFunction.GENERATE.getFuncName());
                                dialogProcessGenerate.setItems(dialogListProcessGenerate, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0: //generateValueList
                                                AlertDialog.Builder dialogProcessRandomGenerateNumber = new AlertDialog.Builder(ProjectActivity.this);
                                                View viewGenerateRandom = View.inflate(ProjectActivity.this, R.layout.dialog_generate_random, null);
                                                dialogProcessRandomGenerateNumber.setView(viewGenerateRandom);
                                                dialogProcessRandomGenerateNumber.setTitle(EmBusinessFunction.GENERATE_NUMBER.getFuncName());
                                                dialogProcessRandomGenerateNumber.setPositiveButton(DIALOG_BUTTON_POSITIVE, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        try {
                                                            TextView tx_min = viewGenerateRandom.findViewById(R.id.tx_min);
                                                            TextView tx_max = viewGenerateRandom.findViewById(R.id.tx_max);
                                                            TextView tx_count = viewGenerateRandom.findViewById(R.id.tx_count);
                                                            int minValue = Integer.parseInt(tx_min.getText().toString());
                                                            int maxValue = Integer.parseInt(tx_max.getText().toString());
                                                            if (minValue > maxValue) {
                                                                reportError(EmBusinessError.PARAMETER_VALIDATION_ERROR.getErrMsg());
                                                                return;
                                                            }
                                                            int count = Integer.parseInt(tx_count.getText().toString());
                                                            setMultipleIntegerResult(EmBusinessFunction.GENERATE_NUMBER.getFuncName(), dataProcess.generateValueList(minValue, maxValue, count));
                                                        } catch (Exception e) {
                                                            reportError(EmBusinessError.PARAMETER_VALIDATION_ERROR.getErrMsg());
                                                        }
                                                    }
                                                });
                                                dialogProcessRandomGenerateNumber.show();
                                                break;
                                            case 1: //generateArray1
                                                AlertDialog.Builder dialogProcessGenerateArray1 = new AlertDialog.Builder(ProjectActivity.this);
                                                View viewGenerateArray1 = View.inflate(ProjectActivity.this, R.layout.dialog_generate_array, null);
                                                dialogProcessGenerateArray1.setView(viewGenerateArray1);
                                                dialogProcessGenerateArray1.setTitle(EmBusinessFunction.GENERATE_ARRAY1.getFuncName());
                                                dialogProcessGenerateArray1.setPositiveButton(DIALOG_BUTTON_POSITIVE, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        try {
                                                            TextView tx_begin = viewGenerateArray1.findViewById(R.id.tx_begin);
                                                            TextView tx_step = viewGenerateArray1.findViewById(R.id.tx_step);
                                                            TextView tx_count = viewGenerateArray1.findViewById(R.id.tx_count);
                                                            int initValue = Integer.parseInt(tx_begin.getText().toString());
                                                            int step = Integer.parseInt(tx_step.getText().toString());
                                                            int count = Integer.parseInt(tx_count.getText().toString());
                                                            setMultipleIntegerResult(EmBusinessFunction.GENERATE_ARRAY1.getFuncName(), dataProcess.generateArray(initValue, step, count,0));
                                                        } catch (Exception e) {
                                                            reportError(EmBusinessError.PARAMETER_VALIDATION_ERROR.getErrMsg());
                                                        }
                                                    }
                                                });
                                                dialogProcessGenerateArray1.show();
                                                break;
                                            case 2: //generateArray2
                                                AlertDialog.Builder dialogProcessGenerateArray2 = new AlertDialog.Builder(ProjectActivity.this);
                                                View viewGenerateArray2 = View.inflate(ProjectActivity.this, R.layout.dialog_generate_array, null);
                                                dialogProcessGenerateArray2.setView(viewGenerateArray2);
                                                dialogProcessGenerateArray2.setTitle(EmBusinessFunction.GENERATE_ARRAY2.getFuncName());
                                                dialogProcessGenerateArray2.setPositiveButton(DIALOG_BUTTON_POSITIVE, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        try {
                                                            TextView tx_begin = viewGenerateArray2.findViewById(R.id.tx_begin);
                                                            TextView tx_step = viewGenerateArray2.findViewById(R.id.tx_step);
                                                            TextView tx_count = viewGenerateArray2.findViewById(R.id.tx_count);
                                                            int initValue = Integer.parseInt(tx_begin.getText().toString());
                                                            int step = Integer.parseInt(tx_step.getText().toString());
                                                            int count = Integer.parseInt(tx_count.getText().toString());
                                                            setMultipleIntegerResult(EmBusinessFunction.GENERATE_ARRAY2.getFuncName(), dataProcess.generateArray(initValue, step, count,1));
                                                        } catch (Exception e) {
                                                            reportError(EmBusinessError.PARAMETER_VALIDATION_ERROR.getErrMsg());
                                                        }
                                                    }
                                                });
                                                dialogProcessGenerateArray2.show();
                                                break;
                                        }
                                    }
                                }).show();
                                break;
                            case 1: //Random sampling
                                AlertDialog.Builder dialogProcessRandomSampling = new AlertDialog.Builder(ProjectActivity.this);
                                View viewRandomSampling = View.inflate(ProjectActivity.this, R.layout.dialog_random_sampling, null);
                                dialogProcessRandomSampling.setView(viewRandomSampling);
                                dialogProcessRandomSampling.setTitle(EmBusinessFunction.RANDOM_SAMPLING.getFuncName());
                                dialogProcessRandomSampling.setPositiveButton(DIALOG_BUTTON_POSITIVE, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            TextView tx_chosen = viewRandomSampling.findViewById(R.id.tx_chosen);
                                            CheckBox ck_repeat = viewRandomSampling.findViewById(R.id.ck_repeat);
                                            int chosen = Integer.parseInt(tx_chosen.getText().toString());
                                            boolean repeat = ck_repeat.isChecked();
                                            setMultipleStringResult(EmBusinessFunction.RANDOM_SAMPLING.getFuncName(), dataProcess.randomSampling(dataList, chosen, repeat));
                                        } catch (Exception e) {
                                            reportError(EmBusinessError.PARAMETER_VALIDATION_ERROR.getErrMsg());
                                        }
                                    }
                                });
                                dialogProcessRandomSampling.show();
                                break;
                            case 2: //Sort
                                AlertDialog.Builder dialogProcessSort = new AlertDialog.Builder(ProjectActivity.this);
                                dialogProcessSort.setTitle(EmBusinessFunction.SORT.getFuncName());
                                dialogProcessSort.setItems(dialogListProcessSort, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch (i) {
                                            case 0:
                                                try {
                                                    setMultipleResult(EmBusinessFunction.ASC_SORT.getFuncName(), dataProcess.ascSort(getDoubleList()));
                                                } catch (Exception e) {
                                                    reportError(EmBusinessError.SORT_ERROR.getErrMsg());
                                                }
                                                break;
                                            case 1:
                                                try {
                                                    setMultipleResult(EmBusinessFunction.DESC_SORT.getFuncName(), dataProcess.descSort(getDoubleList()));
                                                } catch (Exception e) {
                                                    reportError(EmBusinessError.SORT_ERROR.getErrMsg());
                                                }
                                                break;
                                            case 2:
                                                setMultipleStringResult(EmBusinessFunction.CHAOS.getFuncName(), dataProcess.chaos(dataList));
                                                break;
                                            case 3:
                                                setMultipleStringResult(EmBusinessFunction.REVERSE.getFuncName(), dataProcess.reverse(dataList));
                                                break;
                                            case 4:
                                                setMultipleStringResult(EmBusinessFunction.REMOVE_REPEAT.getFuncName(), dataProcess.removeRepeat(dataList));
                                                break;
                                        }
                                    }
                                }).show();
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.save:
                saveProject();
                break;
        }
        return false;
    }
}
