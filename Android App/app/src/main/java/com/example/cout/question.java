package com.example.cout;

import android.util.Log;

import java.util.List;

public class question {
    public String problemStatement,header,answer_1,answer_2,testCase,question,publicAnswer, privateAnswer,privateTestCaseOutput;
    question(){
        this.header = "";
        this.question="";
        this.answer_1="";
        this.testCase="";
        this.problemStatement = "";
        this.answer_2="";

    }
    void reset(){
        this.header = "";
        this.question="";
        this.answer_1="";
        this.testCase="";
        this.problemStatement = "";
        this.answer_2="";
        this.publicAnswer = "";
        this.privateAnswer = "";
        this.privateTestCaseOutput = "";
    }

    void addHeader(List headerList){
        for (int i=0;i<headerList.size()-1;i++)
        {
            Log.d("TAG",headerList.get(i).toString());
            this.header =this.header + headerList.get(i).toString() + "\n";
        }
        this.header = this.header + headerList.get(headerList.size()-1);
    }
    void addAnswer(List listAnswer_1,List listTestCases,List listAnswer_2){
        for (int i= 0;i<listAnswer_1.size()-1;i++){
            this.answer_1 += (listAnswer_1.get(i).toString())+"\n";
        }
        this.answer_1 += (listAnswer_1.get(listAnswer_1.size()-1)+"\n");

        for (int i= 0;i<listAnswer_2.size()-1;i++){
            this.answer_2 += (listAnswer_2.get(i).toString()) + "\n";
        }
        this.answer_2 += (listAnswer_2.get(listAnswer_2.size()-1)+"\n");

        this.publicAnswer = this.answer_1 + listTestCases.get(0).toString() + "\n" + this.answer_2;
        this.privateAnswer = this.answer_1 + listTestCases.get(1).toString() + "\n" + this.answer_2;

    }

}
