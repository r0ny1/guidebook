package com.mirea.informatics.ui.apps.solver;

public class LogicFunctionGenerator {

    public char[][] VectorFunctions;
    public String[] VarNames,OutNames;
    public char[][] VarValues;

    private int seed = 1;
    private float maxTruePercent = 0.5f;
    private float minTruePercent = 0.4f;
    char[][] buffVector;

    LogicFunctionGenerator(int varCount, int funcCount){
        GenerateVectorFunction( varCount, funcCount);
        GenerateVarValues(varCount);
    }

    //генератор логической функции в виде таблицы истинности
    private void GenerateVectorFunction(int varCount, int funcCount) {

        int totalVarCount = (int) Math.pow(2,varCount);

        //VectorFunctions = new char[totalVarCount][varCount];
        VectorFunctions = new char[totalVarCount][funcCount];
        //массивы имён входных переменных и выходных функций. нужны для Квайн-Мак-Класки
        VarNames = new String[varCount];
        OutNames = new String[funcCount];

        for(int i = 0; i < varCount; i++){
            String varName = "x"+(i);
            VarNames[i] = varName;
            //System.out.println(varName);
        }

        for(int i = 0; i < funcCount; i++)
        {
            String outName = "z"+i;
            OutNames[i] = outName;

            //на нулевой комбинации значение функции всегда 0, иначе тренировка теряет смысол
            VectorFunctions[0][i] = '0';
            Generator(totalVarCount);

            //заполняем выходной масссив
            for(int j = 1; j < totalVarCount; j++)
            {
                VectorFunctions[j][i] = buffVector[j][0];
                //System.out.println(VectorFunctions[j][i]);
            }

        }

    }

    //Реализация Генератора
    public void Generator (int totalVarCount){

        buffVector = new char[totalVarCount][1];

        int unitCounter = 0;

        for(int j = 1; j < totalVarCount; j++)
        {
            int random = randomBit(seed);
            if(random == 1){unitCounter++;}
            buffVector[j][0] = Integer.toString(random).charAt(0);
        }

        //Если функция тривиальна (1 или 0 на всех значениях) или слишком мало комбинация на которых функция принимает значение истина, то генерируем заново
        if(     unitCounter == 0 || unitCounter == totalVarCount
                || unitCounter > Math.floor(maxTruePercent*totalVarCount)
                || unitCounter < Math.floor(minTruePercent*totalVarCount)
        ){
            Generator(totalVarCount);
        }

    }

    public static int randomBit(long seed){
        return (int) (Math.random() * ++seed);
    }

    private void GenerateVarValues(int varCount) {
        int numValues = 1;
        for(int i = 0; i < varCount; i++)
            numValues *= 2;

        VarValues = new char[numValues][varCount];
        for(int n = 0; n < numValues; n++) {
            String n_str = leftZeros(Integer.toBinaryString(n), varCount);
            for(int i = 0; i < varCount; i++)
                VarValues[n][i] = n_str.charAt(i);
        }
    }

    private static String leftZeros(String str, int len) {
        while(str.length() < len)
            str = "0" + str;
        return str;
    }
}
