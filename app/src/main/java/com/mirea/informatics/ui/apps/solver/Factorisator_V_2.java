package com.mirea.informatics.ui.apps.solver;

import java.util.ArrayList;
import java.util.Collections;

public class Factorisator_V_2 {

    //Символы разделители
    private String splitSymbol = "\\+";
    private String splitSymbol2 = "\\*";
    private static String interstitialSeparator = "+";
    private static String insideSeparator = "*";

    //массив термов
    public String[] v = new String[0];

    //строка невостребованных переменных
    String unclaimedBuff = "";

    //массив операндов и представление термов в виде биарных строк
    ArrayList<String> operands = new ArrayList<>();
    ArrayList<String> binaryTerms = new ArrayList<>();

    String output = "";
    private String expr = "";

    CustomData data = new CustomData();

    public void PrepareData(String expression) {

        System.out.println(expression);
        expr = expression;

        expression = expression.replace(" ", "");
        expression = expression.replace("(", "");
        expression = expression.replace(")", "");

        v = expression.split(splitSymbol);

        //Заполняем алфавит
        for(int i = 0; i<v.length; i++){
            String[] buffer = v[i].split(splitSymbol2);
            for(int j = 0; j<buffer.length; j++){
                if(operands.contains(buffer[j])==false){operands.add(buffer[j]);}
            }
        }

        //лексико-графическая сортировка алфавита
        for(int i = operands.size()-1; i>0; i--){
            for(int j = 0; j<i; j++) {
                if (operands.get(j).charAt(operands.get(j).length() - 1) > operands.get(j + 1).charAt(operands.get(j + 1).length() - 1)) {
                    String a = operands.get(j);
                    operands.set(j, operands.get(j + 1));
                    operands.set(j + 1, a);
                }
            }
        }

        //создание бинарных строк
        for(int i = 0; i<v.length; i++){
            String[] buffer = v[i].split(splitSymbol2);
            String binaryString = "";
            System.out.println(v[i]);

            int l = 0;
            while (buffer[0].equals(operands.get(l))==false){
                binaryString += "0";
                l++;
            }

            int z = 0;
            for(int m = l; m<operands.size(); m++){
                if(operands.get(m).equals(buffer[z])==true && z<buffer.length){
                    binaryString += "1";
                    if(z!=buffer.length-1){z+=1;};
                }else if(operands.get(m).equals(buffer[z])==false || m>buffer.length) {
                    binaryString += "0";
                }
            }

            binaryTerms.add(binaryString);

        }

        for (String a: binaryTerms
             ) {
            //System.out.println(a);
        }

        Factorisation();
    }

    private void Factorisation(){

        //Ищем, в какие термы входит каждый операнд *список списков
        ArrayList<ArrayList<Integer>> collisionTerms = new ArrayList<>();

        for(int i = 0 ; i <binaryTerms.get(0).length(); i++) {

            int m = 0;
            ArrayList<Integer> collisions = new ArrayList<>();

            for (String a : binaryTerms) {
                m++;
                if(a.charAt(i) == '1'){
                    collisions.add(m);
                }
            }

            collisionTerms.add(collisions);
        }

        data.alphabet = operands;
        data.positions = collisionTerms;
        data.Sort();
        //data.PrintAlphabet();
        //data.PrintCollisions();
        System.out.println("End");

        //Если таких вхождений нет, то функцию нельзя факторизировать.
        if(data.positions.get(0).size()>1){
            ReconstructFunction();
        }else{Exit(expr);}
    }

    private void ReconstructFunction(){

        String tmp = "";

        ArrayList<Integer> d = new ArrayList<>();
        boolean globalExist = false;

        //ищем операнды которые входящ во все термы
        for(int i = 0; i<data.positions.size(); i++){

            if(data.positions.get(i).size() == binaryTerms.size()){
                System.out.println("Global operands "+data.alphabet.get(i));
                globalExist = true;
                tmp += data.alphabet.get(i)+insideSeparator;
                d.add(i);
            }

        }
        if(globalExist){
            tmp = removeByIndex(tmp, tmp.length()-1);
            tmp = "("+tmp+")"+insideSeparator;
            System.out.println(tmp);
        }

        System.out.println("/**********");
        data.PrintAlphabet();
        System.out.println("______");
        data.PrintCollisions();
        System.out.println("**********/");

        //Если есть только глобальные переменные, то вынесем их из исходной строки и завершим факторизацию
        String c = expr;
        c = c.replace(" ", "");
        c = c.replace("(", "");
        c = c.replace(")", "");
        for(int i = 0; i < d.size(); i++){
            c = c.replace("*"+data.alphabet.get(i),"");
            c = c.replace(data.alphabet.get(i)+"*","");
        }
        c = "("+c+")";


        if(d.size()!=0){
            data.Delete(d);
            data.PrintCollisions();
        }
        else{
            //Если нет глобальных операндо, то чекним, есть ли операнды и термы, из которых нельзя ничего вынести
            unclaimedBuff = data.GetUnclaimedOperands(insideSeparator,interstitialSeparator, v);
            if(unclaimedBuff.length()>0)unclaimedBuff = removeByIndex(unclaimedBuff,unclaimedBuff.length()-1);
        }

        //если присутствуют только глобальные операнды, то на этом заканичиваем, иначе в рекурсию
        if(data.positions.get(0).size()==1){
            Exit(tmp+c);
        }
        else {
            //Уходим в рекурсию
            String a = RecurcivePart(data, (ArrayList<Integer>) data.positions.get(0).clone());

            tmp += "(" + a + ")";
            //добавляем невостребованные термы если они есть
            if (unclaimedBuff.length() > 0) {
                tmp += interstitialSeparator + unclaimedBuff;
            }
            output = tmp;
            System.out.println(tmp);
        }

    }

    private String RecurcivePart(CustomData container, ArrayList<Integer> prevTerms){

        System.out.println("Enter Rec");
        System.out.println(container.alphabet +" " + prevTerms);

        int lastIndex = 0;
        int maxIndex = container.positions.get(0).size();

        System.out.println(maxIndex);

        //Глубокое копирование необходимых данных
        ArrayList<String> buffAlphabet = new ArrayList<>();
        for(String obj : container.alphabet)
        {
            String b = obj;
            buffAlphabet.add(b);
        }

        ArrayList<ArrayList<Integer>> buffPositions = new ArrayList<>();
        for(ArrayList<Integer> obj: container.positions)
        {
            ArrayList<Integer> b = new ArrayList<>();
            for (Integer c: obj) {
                b.add(c);
            }
            buffPositions.add(b);
        }

        ArrayList<Integer> collisions = new ArrayList<>();
        ArrayList<Integer> buffer = new ArrayList<>();
        ArrayList<Integer> terms = (ArrayList<Integer>)container.positions.get(0).clone(); //Становятся prevTerms на следующей итерации рекурсии
        ArrayList<ArrayList<Integer>> crossCollision = new ArrayList<>();
        ArrayList<ArrayList<Integer>> crossCollision2 = new ArrayList<>();

        buffer = (ArrayList<Integer>)container.positions.get(0).clone();


        String tmp = ""; // строка текущего вынесения
        String tmp2 = ""; // строка следующего уровня рекурсии
        String unclaimed = "";

        tmp += "(";

        CustomData buff = new CustomData();
        buff.alphabet = buffAlphabet;
        buff.positions = buffPositions;

        System.out.println(container.positions+" " +buff.alphabet);

        if(maxIndex != 1){
            collisions.add(0);
        }

        if(container.positions.size()>0 && maxIndex != 1) {
            tmp += container.alphabet.get(0) + insideSeparator;
        }

        if(maxIndex==1) {

            container.SortByFirstPosIndex();
            container.PrintCollisions();
            container.PrintAlphabet();
            tmp += container.alphabet.get(0)+"*";

            for (int i = 1; i != container.positions.size(); i++) {

                if (!Collections.disjoint(prevTerms, container.positions.get(i))) {
                    //System.out.println(prevTerms);
                    if (container.positions.get(i).size() == maxIndex && container.positions.get(i).containsAll(container.positions.get(i-1))) {

                        System.out.println("Added from index 0 " + container.alphabet.get(i)+ " on iter " + i);
                        //tmp += container.alphabet.get(i) + interstitialSeparator;
                        tmp += container.alphabet.get(i) + insideSeparator;

                    }else {
                        tmp = removeByIndex(tmp,tmp.length()-1);
                        tmp += interstitialSeparator+ container.alphabet.get(i) + insideSeparator;
                    }

                }else {
                    System.out.println("Added to unclaimed " + container.alphabet.get(i) + " on iter " + i);
                    unclaimed += container.alphabet.get(i) + insideSeparator;
                }

            }
            if(unclaimed.length()>1){unclaimed = removeByIndex(unclaimed, unclaimed.length()-1);}

        }else{

            for(int i = 1; i < container.positions.size(); i++){

                if(!Collections.disjoint(prevTerms,container.positions.get(i))){

                    if (container.positions.get(i).size() == maxIndex) {

                        if (container.positions.get(i).containsAll(container.positions.get(lastIndex))) {
                            System.out.println("Added");
                            collisions.add(i);
                            tmp += container.alphabet.get(i) + insideSeparator;
                            lastIndex = i;
                        }else {
                            crossCollision.add(container.positions.get(i));
                            crossCollision2.add(buff.positions.get(i));
                        }
                    }
                }
            }
        }

        tmp = removeByIndex(tmp, tmp.length()-1);
        //if(unclaimed.length()>0){unclaimed = removeByIndex(unclaimed,unclaimed.length()-1);}
        tmp += ")";


        if(container.positions.size()>0 && maxIndex != 1){

            System.out.println("Delete max indexes");

            if(crossCollision.size()>0) {
                System.out.println("REmove exclusive");
                container.RemoveExclusive(crossCollision, container.positions.get(0));
                container.Sort();
                container.DeleteOutOfBounds(container.positions.get(0));
                container.PrintAlphabet();
                container.PrintCollisions();
            }

            if(container.positions.size()>0){
                container.DeleteMaxIndexes();
                System.out.println(container.MaxIndexesCount());
                container.PrintAlphabet();
                if(container.positions.size()>0) {
                    tmp += insideSeparator  + RecurcivePart(container,terms);
                }
            }
        }

         if(buff.positions.size() > 0 && maxIndex != 1 && ( collisions.size()>1 || crossCollision2.size()>0)){

             System.out.println("Delete coll");
             String unc = "";

             buff.Delete(collisions);
             System.out.println(buff.positions);
             System.out.println(buff.alphabet);

             if(crossCollision2.size()>0) {

                 System.out.println("REmove match" + buffer);
                 buff.RemoveMatch(crossCollision2, buffer);
                 //buff.Sort();
                 buff.DeleteOutOfBounds(buff.positions.get(0));
                 buff.PrintAlphabet();
                 buff.PrintCollisions();

                 unc = buff.GetUnclaimedOperands(insideSeparator, interstitialSeparator);

                 System.out.println("unc " + unc);
             }
             if(!buff.positions.get(0).containsAll(container.positions.get(0))){
                 if(buff.positions.size() > 0){
                     tmp2 += interstitialSeparator + RecurcivePart(buff,(ArrayList<Integer>)buff.positions.get(0));
                 }
             }
         }

         //разделить это говно на 2 списка. 1 уйдёт в совпадение по 1 циклу, второй уйдёт в buff

        tmp += tmp2;
        if(unclaimed.length()>0){tmp += interstitialSeparator + unclaimed;}

        System.out.println("Exit Rec");
        System.out.println(tmp);
        System.out.println(unclaimed);

        return tmp;
    }

    private String removeByIndex(String str, int index) {
        return str.substring(0,index)+str.substring(index+1);
    }

    public void Exit(String out){
        System.out.println(out);
        output = out;
    }
}

class CustomData{

   public ArrayList<String> alphabet = new ArrayList<>();
   public ArrayList<ArrayList<Integer>> positions = new ArrayList<>();

   //Сортировка по возрастанию индекса вхождения
   public void Sort() {
       for (int i = 0; i < positions.size() - 1; i++) {
           for (int j = 0; j < positions.size() - 1 -i; j++) {
               if (positions.get(j).size() < positions.get(j + 1).size()) {

                   String operand = alphabet.get(j);
                   alphabet.set(j, alphabet.get(j + 1));
                   alphabet.set(j + 1, operand);

                   ArrayList<Integer> buff = positions.get(j);
                   positions.set(j, positions.get(j + 1));
                   positions.set(j + 1, buff);
               }
           }
       }
   }

   //Только для длин = 1
   public void SortByFirstPosIndex(){

       for (int i = positions.size()-1; i >= 1; i--) {
           for (int j = 0; j < positions.size() - 1; j++) {
               if (positions.get(j).get(0) > positions.get(j + 1).get(0)) {

                   String operand = alphabet.get(j);
                   alphabet.set(j, alphabet.get(j + 1));
                   alphabet.set(j + 1, operand);

                   ArrayList<Integer> buff = positions.get(j);
                   positions.set(j, positions.get(j + 1));
                   positions.set(j + 1, buff);
               }
           }
       }

   }

   //Поиск количества элементов с макс индексом вхождения
    public int MaxIndexesCount(){
       int max = positions.get(0).size();
       int count = 0;
        for (ArrayList<Integer> a: positions) {
            if(a.size()==max){count++;}
        }
        return count;
    }

    //Удаляет элементы с макс. индексом вхождения
    public void DeleteMaxIndexes(){
       if(positions.size()>0) {
           int maxIndex = positions.get(0).size();
           for (int i = positions.size() - 1; i >= 0; i--) {
               if (positions.get(i).size() == maxIndex) {
                   Delete(i);
               }
           }
       }
       else{}
    }

    //Удаления нескольких операндов
   public void Delete(ArrayList<Integer> b){

       for(int i = b.size()-1; i>=0; i--){
           Delete(b.get(i));
       }

   }

   public void Delete(int index){
       positions.remove(index);
       alphabet.remove(index);
   }

   public void PrintAlphabet(){
       for (String a: alphabet) {
           System.out.println(a);
       }
   }

   public void PrintCollisions(){
       for (ArrayList<Integer> a: positions) {
           System.out.println(a);
       }
   }

    //Удаляет операнды которые не входят в данный терм
   public void DeleteOutOfBounds(ArrayList<Integer> a){

       for(int i = positions.size()-1; i>=0; i--){
           if(Collections.disjoint(a,positions.get(i))){
               Delete(i);
           }
       }

   }

    //Возвращяет строку невостребованных термов
   public String GetUnclaimedOperands(String inside, String interstitial, String[] v){
       String output = "";
       int maxIndexCount = MaxIndexesCount();

       ArrayList<ArrayList<Integer>> unclaimedIndexes = new ArrayList<>();
       ArrayList<Integer> toDelete = new ArrayList<>();

       for(int i = positions.size()-1; i>=0; i--){

           if(positions.get(i).size()==1){
               int m = 0;
               for(int j = 0; j<maxIndexCount; j++){
                   if(Collections.disjoint(positions.get(j),positions.get(i))){
                       m++;
                   }
               }

               if(m==maxIndexCount){
                   toDelete.add(i);
                   if(!unclaimedIndexes.contains(positions.get(i))){
                       unclaimedIndexes.add(positions.get(i));
                       output += v[positions.get(i).get(0)-1]+interstitial;
                   }
               }

           }else {break;}

       }
       Collections.sort(toDelete);
       Delete(toDelete);
       return output;
   }

   public String GetUnclaimedOperands(String inside, String interstitial){
       String output = "";
       ArrayList<Integer> indexes = new ArrayList<>();

       for(int i = 1; i<positions.size(); i++){
           if(Collections.disjoint(positions.get(0),positions.get(i))){
               indexes.add(i);
           }
       }
       Collections.sort(indexes);
       if(indexes.size()>0) {
           output += alphabet.get(indexes.get(0));

           for (int i = 2; i < indexes.size(); i++) {
               if (positions.get(indexes.get(i - 1)).equals(positions.get(indexes.get(i)))) {
                   output += inside + alphabet.get(indexes.get(i));
               } else {
                   output += interstitial + alphabet.get(indexes.get(i));
               }
           }
       }

       return output;
   }

    //Удаляет совпадающие элементы двух операндов
   public void RemoveMatch(ArrayList<ArrayList<Integer>> a, ArrayList<Integer> b){

       for (ArrayList<Integer> c: a) {
           ArrayList<Integer> buff = new ArrayList<>();
           for(int i = 0; i<c.size();i++){
               System.out.println(c);
               if(b.contains(c.get(i))){
                   buff.add(i);
                   System.out.println("Remove match " + i);
               }else{
               }
           }
           DeleteOperands(positions.indexOf(c),buff);
       }

   }

    //Удаляет различающиеся элементы двух операндов
   public void RemoveExclusive(ArrayList<ArrayList<Integer>> a, ArrayList<Integer> b){

       for (ArrayList<Integer> c: a) {
           ArrayList<Integer> buff = new ArrayList<>();
           for(int i = 0; i<c.size();i++){
               if(b.contains(c.get(i))){
               }else{
                   buff.add(i);
               }
           }
           DeleteOperands(positions.indexOf(c),buff);
       }

   }

    //Удаляет вхождение операнда в терм т.е индекс из списка вхождений
   public void DeleteOperands(int operand, ArrayList<Integer> term){
       for(int i = term.size()-1; i>=0; i--){
           int n = term.get(i);
           positions.get(operand).remove(n);
       }
   }


}