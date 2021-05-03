import React from 'react'
import { useState } from 'react';

import db, { timestamp } from './firebase';


import './QuestionBlock.css'
import { useStateValue } from './StateProvider';
function QuestionBlock() {
    const [langCode,setLangCode] = useState();
    const [versionIndex,setVersionIndex] = useState();
    const [header,setHeader] = useState();
    const [headerArray,setHeaderArray] = useState([]);
    const [question,setQuestion] = useState();
    const [problemStatement,setProblemSTatement] = useState();
    const [footer_1,setFooter_1] = useState("");
    const [footer_1Array,setFooter_1Array] = useState([]);
    const [publicTestCase,setPublicTestCase] = useState()
    const [privateTestCase,setPrivateTestCase] = useState();
    const [privateTestCaseOutput,setPrivateTestCaseOutput] = useState();
    const [footer_2,setFooter_2] = useState("");
    const [footer_2Array,setFooter_2Array] = useState([]);
    const [{user} , dispatch] = useStateValue();
    var testCases = []
    const getData = (e) => {
      e.preventDefault();
      
      testCases.length = 0;
      testCases.push(publicTestCase)
      testCases.push(privateTestCase)
      console.log(user.email)      
      if(headerArray!=null&&question!=null&&footer_1Array!=null&&testCases!=null&&footer_2Array!=null&&problemStatement!=null&&privateTestCaseOutput!=null&&versionIndex!=null)
            {  
             db.collection("topics").doc(langCode).collection("questions").add({  
               "0_problemStatement" : problemStatement,
                "1_header" : headerArray,
                "2_question": question,
                "3_answer_1": footer_1Array,
                "4_testcases": testCases,
                "5_answer_2": footer_2Array,
                "6privateTestCaseOutput": privateTestCaseOutput, 
                "9_email": user.email,            
                "112_compilerDetails": versionIndex,
                "timestamp": timestamp,
                "isApproved": false,
              });
              alert("Successfully Added")
              setPrivateTestCaseOutput(null);
              setPublicTestCase(null);
              setPrivateTestCase(null);
              setProblemSTatement(null);
              setHeaderArray([]);
              setFooter_1Array([]);
              setFooter_2Array([]);
              setLangCode(null);
              setVersionIndex(null);
            }
            else {
              alert("All fields are Required")
            }
  
    }
   
    return (
        <div className = "questionBlock">
            <form className="form">
        

        <div className = "block">
        <label><b>Enter Language Code</b></label> 
          <textarea  placeholder = "Enter Language code from the language table(Click on table icon to view the Language Table)" value={langCode} onChange={e => {
            setLangCode(e.target.value);
            }}  ></textarea>
        </div>
        <div className = "block">
        <label><b>Enter Version Index</b></label> 
          <textarea  placeholder = "Enter version index from the chart corresponding to the language code selected" value={versionIndex} onChange={e => {
            setVersionIndex(e.target.value);
            }}  ></textarea>
        </div>
        <div className = "block">
        <label><b>Problem Statement</b></label> 
          <textarea  placeholder = "Detail Problem Statement" value={problemStatement} onChange={e => {
            setProblemSTatement(e.target.value);
            }}  ></textarea>
        </div>
        <div className = "block">
          <label><b>HEADER</b></label> 
          <textarea placeholder = {"#include<iostream> \nusing namespace std; \nint functionName() \n{"}  value={header} onChange={e => {
            setHeader(e.target.value);
            setHeaderArray(e.target.value.split("\n"));
            }}  ></textarea>
        </div>
        <div className = "block">
          <label ><b>QUESTION</b></label> 
          <textarea placeholder = {"/*What is to be done*/"}  value={question} onChange={e => setQuestion(e.target.value)}  ></textarea>
        </div>
        <div className = "block">
          <label ><b>FOOTER_1</b></label> 
          <textarea  placeholder = {"} \nint main() \n{ \n"} value={footer_1} onChange={e => {setFooter_1(e.target.value);setFooter_1Array(e.target.value.split("\n"))}}></textarea>
        </div>
        <div className = "block">
          <label><b>PUBLIC TEST CASE</b></label> 
          <textarea  placeholder = {"cout<<..."} value={publicTestCase} onChange={e => setPublicTestCase(e.target.value)}  ></textarea>
        </div>
        <div className = "block">
          <label><b>PRIVATE TEST CASE</b></label> 
          <textarea  placeholder = {"cout<<..."} value={privateTestCase} onChange={e => setPrivateTestCase(e.target.value)}  ></textarea>
        </div>
        <div className = "block">
          <label><b>FOOTER_2</b></label> 
          <textarea  placeholder = {"return 0; \n}"} value={footer_2} onChange={e => {setFooter_2(e.target.value);setFooter_2Array(e.target.value.split("\n"))}}  ></textarea>
        </div>
        <div className = "block">

          <label><b>PRIVATE TESTCASE OUTPUT</b></label> 
          <textarea  placeholder = {"Paste the Private TestCaseOutput Exactly from terminal."} value={privateTestCaseOutput} onChange={e => {
            setPrivateTestCaseOutput(e.target.value);
            }}  ></textarea>
        </div>
        <button className = "Button" onClick = {getData}>DRAFT</button>
          
    </form>
        </div>
    )
}

export default QuestionBlock
