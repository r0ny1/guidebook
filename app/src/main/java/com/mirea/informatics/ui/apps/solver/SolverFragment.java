package com.mirea.informatics.ui.apps.solver;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mirea.informatics.R;

public class SolverFragment extends Fragment {

    int number_of_in_var;
    String[] VarNames;
    String[] OutNames;

    boolean MDNF = true;

    EditText edtTxtInput;
    TextView txtViewOutput;

    GridView gvMain;
    ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_solver, container, false);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button btnExec = (Button) getActivity().findViewById(R.id.btnExec);
        View.OnClickListener oclBtnExec = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMinimize();
            }
        };
        btnExec.setOnClickListener(oclBtnExec);
    }

    private static int number_of_in_Var(String vector) {
        if(vector.isEmpty())
            return 0;
        int number_of_in_var = 0;
        int vector_length = vector.length();
        while(vector_length != 1){
            if((vector_length % 2) != 0)
                return 0;
            vector_length /= 2;
            number_of_in_var++;
        }
        return number_of_in_var;
    }

    private static boolean checkVector(String vector, int number_of_in_var) {
        if (vector.isEmpty() || (number_of_in_var == 0))
            return false;
        for (int i = 0; i < vector.length(); i++)
            if ((vector.charAt(i) != '0') && (vector.charAt(i) != '1'))
                return false;
        return true;
    }

    private static String[] convertToArray(char[][] data2d, char[][] vectorFunctions) {

        int rows = data2d.length;
        int cols = data2d[0].length;
        String[] arr = new String[rows*(cols+1)];
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                arr[k++] = Character.toString(data2d[i][j]);
            arr[k++] = Character.toString(vectorFunctions[i][0]);
        }
        return arr;
    }

    private void onMinimize() {

        edtTxtInput = (EditText) getActivity().findViewById(R.id.edtTxtInput);
        String str_input = edtTxtInput.getText().toString();
        number_of_in_var = number_of_in_Var(str_input);
        Log.d("NUMOFVAR >", ((Integer)number_of_in_var).toString());
        if(!checkVector(str_input, number_of_in_var)) {
            Toast.makeText(getContext(), "Введен не вектор", Toast.LENGTH_SHORT).show();
            return;
        }

        LogicFunctionGenerator gen = new LogicFunctionGenerator(number_of_in_var, 1);

        char[][] vectorFunctions = new char[str_input.length()][1];

        String str_input_gen = new String();

        for (int i = 0; i < gen.VectorFunctions.length; i++)
        str_input_gen += gen.VectorFunctions[i][0];

        for (int i = 0; i < str_input.length(); i++)
            vectorFunctions[i][0] = str_input.charAt(i);

        String[] data = convertToArray(gen.VarValues, vectorFunctions);

        adapter = new ArrayAdapter<String>(getContext(), R.layout.item, R.id.tvText, data);
        gvMain = (GridView) getActivity().findViewById(R.id.gridTab);
        gvMain.setNumColumns(number_of_in_var + 1);
        gvMain.setColumnWidth(80);
        gvMain.setAdapter(adapter);
        gvMain.setStretchMode(GridView.NO_STRETCH);

        VarNames = new String[number_of_in_var];
        OutNames = new String[1];

        for(int i = 0; i < number_of_in_var; i++){
            String varName = "x"+(i);
            VarNames[i] = varName;
        }

        for(int i = 0; i < 1; i++)
        {
            String outName = "z"+i;
            OutNames[i] = outName;}

        Solver sol = new Solver(
                vectorFunctions,
                number_of_in_var,
                VarNames,
                1,
                OutNames,
                MDNF,
                false,
                true
        );
        sol.run();

        BasisConverter converter = new BasisConverter();
        txtViewOutput = (TextView) getActivity().findViewById(R.id.txtViewOutput);
        converter.ToZhegalkinPolynomial(vectorFunctions,0, VarNames);
        txtViewOutput.setText(sol.getSolution() + "\n" + converter.getBooleanFunction());
    }
}