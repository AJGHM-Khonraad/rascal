@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Paul Klint - Paul.Klint@cwi.nl - CWI}

module experiments::RascalTutor::CourseManager

// The CourseManager handles all requests from the web server:
// - compile: compile a course
// - edit: edit a concept
// - save: save a concept after editing
// - validateAnswer: validates the answer to a specific question
// - validateExam: validates a complete exam

import List;
import String;
import Integer;
import Graph;
import Set;
import Map;
import experiments::RascalTutor::CourseModel;
import experiments::RascalTutor::HTMLUtils;
import experiments::RascalTutor::ValueGenerator;
import experiments::RascalTutor::CourseCompiler;
import ValueIO;

import IO;
import Scripting;

// Show a concept.

public str showConcept(Concept C){
   html_file = C.file[extension = htmlExtension];
   return readFile(html_file);
}

// ------------------------------------ Compiling ------------------------------------------------

// Compile a concept
// *** called from Compile servlet in RascalTutor

public str compile(ConceptName rootConcept){
  if(rootConcept in listEntries(courseDir)){
     crs = compileCourse(rootConcept);
     return showConcept(crs.concepts[rootConcept]);
  } else
     throw "Course <rootConcept> not found";
}

// ------------------------------------ Editing --------------------------------------------------

// Edit a concept
// *** called from Edit servlet in RascalTutor

public str edit(ConceptName cn, bool newConcept){
 
  str content = "";
  if(newConcept){
    content = mkConceptTemplate("");
  } else {
    file = conceptFile(cn);
  	content = escapeForHtml(readFile(file));  //TODO: IO exception (not writable, does not exist)
  }
  return html(head(title("Editing <cn>") + prelude(rootname(cn))),
              body(
               div("conceptPane",
                 div("editArea",
                    "\<form method=\"POST\" action=\"/save\" id=\"editForm\"\>
                    \<textarea rows=\"15\" cols=\"60\" wrap=\"physical\" name=\"newcontent\" id=\"editTextArea\"\><content>\</textarea\>
                    \<input type=\"hidden\" name=\"concept\" value=\"<cn>\"\> \<br /\>
                    \<input type=\"hidden\" name=\"new\" value=\"<newConcept>\"\> \<br /\>
                    \<div id=\"editErrors\"\>errors\</div\>\n
                    \<input type=\"submit\" id=\"saveButton\" value=\"Save\"\>
                    \<div id=\"pleaseWaitMessage\"\>\<img src=\"/Courses/images/loader-light.gif\" width=\"16\" height=\"16\" /\> Processing the changes in concept <i(basename(cn))> and regenerating index and warnings for course <i(rootname(cn))>.\</div\>
                    \</form\>"
                  ))
             ));
}

// Save a concept
// *** called from servlet Edit in RascalTutor

public str save(ConceptName cn, str text, bool newConcept){
  if(newConcept) {
     lines = splitLines(text);
     sections = getSections(lines);
     cname = sections["Name"][0];
     if(/[^A-Za-z0-9]/ := cname)
       return saveFeedback("Name \"<cname>\" is not a proper concept name", "");
     fullName = cn + "/" + cname;
     	
     // Does the file for this concept already exist as a subconcept?
     if(exists(catenate(courseDir, fullName)))
     	return saveFeedback("Concept <fullName> exists already", "");
     
     // We have now the proper file name for the new concept and process it
     file = courseDir + fullName + "<cname>.<conceptExtension>";

     println("Write to file <file>");
     writeFile(file, combine(lines));
     
     try {
       c = compileAndGenerateConcept(file, true);
       return saveFeedback("", showConcept(c));
     } catch CourseError(e): {
       return saveFeedback(e, "");
     }
  } else {
    // Saving an existing concept
    try {
      file = conceptFile(cn);
      println("saving to <file> modified concept file.");
      writeFile(file, text);
     
      c = compileAndGenerateConcept(file, false);
 
      return saveFeedback(showConcept(c), "");
    } catch ConceptError(e): {
       return saveFeedback(e, "");
    }
    println("Other error");
    return showConcept(cn);
  }
}

// ------------------------------------ Question Handling ----------------------------------------

public set[QuestionName] goodAnswer = {};
public set[QuestionName] badAnswer = {};

str studentName = "";
str studentMail = "";
str studentNumber = "";

private map[str,map[str,str]] questionParams(map[str,str] params){
   paramMaps = ();
   for(key <- params){
      if(/^<cpt:[A-Za-z0-9\/_]+>_<qid:[A-Za-z0-9]+>:<param:[^\]]+>$/ := key){
           
          println("key = <key>, cpt = <cpt>, qid = <qid>, param = <param>");
          fullQid = "<cpt>_<qid>";
          if(!(paramMaps[fullQid]?)){
             paramMaps[fullQid] = ("concept": cpt, "exercise" : qid);
          }
          m = paramMaps[fullQid];
          m[param] = params[key];
          paramMaps[fullQid] = m;
       } else {
         switch(key){
         case "studentName": studentName = params[key];
         case "studentMail": studentMail = params[key];
         case "studentNumber" : studentNumber = params[key];
         default:
              println("unrecognized key: <key>");
         }
       
       }
   }
   println("paramMaps = <paramMaps>");
   return paramMaps;
}

private bool isExam = false;

// Validate an exam.
// *** called from servlet Edit in RascalTutor

public str validateExam(map[str,str] params){
  isExam = true;
  pm = questionParams(params);
  println("pm = <pm>");
  return validateAllAnswers(pm);
}

// Validate an answer, also handles the requests: "cheat" and "another"
// *** called from servlet Edit in RascalTutor

public str validateAnswer(map[str,str] params){
  isExam = false;
  pm = questionParams(params);
  println("pm = <pm>");
  qnames = domain(pm);
  if(size(qnames) != 1)
     throw "More than one answer";
  qname = toList(qnames)[0];
  return validateAnswer1(pm[qname]);
}

public str validateAllAnswers(map[str,map[str,str]] paramMaps){
  int nquestions = 0;
  int npass = 0;
  response = "";
  for(qid <- paramMaps){
      nquestions += 1;
      v = validateAnswer1(paramMaps[qid]);
      if(v == "pass")
         npass += 1;
      response += li("<qid>: <v>");
  }
  response = h1("Exam Results for <studentName>") + ul(response) + br() + "Passed <npass> out of <nquestions>. \<br\>Final score: \<b\><npass * 10.0 / nquestions>\</b\>.";
  return html(head(title("Exam results for <studentName>")), body(response));
}

public str validateAnswer1(map[str,str] params){

    ConceptName cpid = params["concept"];
    QuestionName qid = params["exercise"];
    
    answer = trim(params["answer"]) ? "";
    expr = params["exp"] ? "";
    cheat = params["cheat"] ? "no";
	another = params["another"] ? "no";
	
	lastQuestion = qid;
	q = getQuestion(cpid, qid);
	
	println("Validate: <params>");
	println("Validate: <q>");
	if(cheat == "yes")
	   return showCheat(cpid, qid, q, params);
	if(another == "yes")
	   return showAnother(cpid, qid, q);
	   
	switch(q){
      case choiceQuestion(cid,qid,descr,choices): {
        try {
           int c = toInt(answer);
           return (good(_) := choices[c]) ? correctAnswer(cpid, qid) : wrongAnswer(cpid, qid, "");
        } catch:
           return wrongAnswer(cpid, qid, "");
      }
      
      case textQuestion(cid,qid,descr,replies):
        return (toLowerCase(answer) in replies) ? correctAnswer(cpid, qid) : wrongAnswer(cpid, qid, "");
        
 
      case tvQuestion(cid, qid, qkind, qdetails): {
        setup  = qdetails.setup;
        lstBefore = qdetails.lstBefore;
        lstAfter  = qdetails.lstAfter;
        cndBefore = qdetails.cndBefore;
        cndAfter  = qdetails.cndAfter;
        holeInLst = qdetails.holeInLst;
        holeInCnd = qdetails.holeInCnd;
        vars   = qdetails.vars;
        auxVars = qdetails.auxVars;
        rtype = qdetails.rtype;
        hint = qdetails.hint;
        
        println("qdetails = <qdetails>");
        
        VarEnv env = ();
        generatedVars = [];
        for(<name, tp> <- vars){
          env[name] = <parseType(evalType(params[name] + ";")), params[name]>;
          generatedVars += name;
	    }
  
	    for(<name, exp> <- auxVars){
          exp1 = subst(exp, env) + ";";
          println("exp1 = <exp1>");
          env[name] = <parseType("<evalType(setup + exp1)>"), "<eval(setup + exp1)>">;
        }
        
        lstBefore = subst(lstBefore, env);
	    lstAfter = subst(lstAfter, env);
	    cndBefore = subst(cndBefore, env);
	    cndAfter = subst(cndAfter, env);
          
        switch(qkind){
          case valueOfExpr(): {
	        try {
	            if(lstBefore + lstAfter == ""){
	              println("YES!");
	              if(holeInCnd){
	                 computedAnswer = eval(setup + (cndBefore + answer + cndAfter + ";"));
	                 if(computedAnswer == true)
	                   return correctAnswer(cpid, qid);
	                 wrongAnswer(cpid, qid, hint);
	              } else {
	                 println("YES2");
	                 if(!endsWith(cndBefore, ";"))
	                   cndBefore += ";";
	                 computedAnswer = eval(setup + cndBefore);
	                 if(answer != ""){
	                    if(!endsWith(answer, ";"))
	                       answer += ";";
	                    givenAnswer = eval(setup + answer);
	                   if(computedAnswer == givenAnswer)
	                      return correctAnswer(cpid, qid);     
	                 }
	                 return wrongAnswer(cpid, qid, "I expected <computedAnswer>.");
	               } 
	            }
	            validate = (holeInLst) ? lstBefore + answer + lstAfter + cndBefore	             
	                                     : ((holeInCnd) ? lstBefore + cndBefore + answer + cndAfter
	                                                    : lstBefore + cndBefore + "==" + answer);
	            
	            println("Evaluating validate: <validate>");
	            output =  shell(setup + validate);
	            println("result is <output>");
	            
	            a = size(output) -1;
	            while(a > 0 && startsWith(output[a], "cancelled") ||startsWith(output[a], "rascal"))
	               a -= 1;
	               
	            errors = [line | line <- output, /[Ee]rror/ := line];
	            
	            if(size(errors) == 0 && cndBefore == "")
	               return correctAnswer(cpid, qid);
	               
	            if(size(errors) == 0 && output[a] == "bool: true")
	              return correctAnswer(cpid, qid);
	            if(hint != ""){
	               return wrongAnswer(cpid, qid, "I expected <subst(hint, env)>.");
	            }
	            //if(!(holeInLst || holeInCnd)){
	           //    return wrongAnswer(cpid, qid, "I expected <eval(subst(cndBefore, env))>.");
	           // }  
	            return wrongAnswer(cpid, qid, "I have no expected answer for you.");
	          } catch:
	             return wrongAnswer(cpid, qid, "Something went wrong!");
	      }

          case typeOfExpr(): {
	          try {
	            if(lstBefore == ""){ // Type question without listing
	               answerType = answer;
	               expectedType = "";
	               errorMsg = "";
	               if(holeInCnd){
	                  validate = cndBefore + answer + cndAfter;
	                  println("Evaluating validate: <validate>");
	                  answerType = evalType(setup + validate);
	                  expectedType = toString(generateType(rtype, env));
	               } else
	                  expectedType = evalType(setup + cndBefore);
	                  
	               println("answerType is <answerType>");
	               println("expectedType is <expectedType>");
	               if(answerType == expectedType)
	              		return correctAnswer(cpid, qid);
	              errorMsg = "I expected the answer <expectedType> instead of <answerType>.";
	              if(!holeInCnd){
	                 try parseType(answer); catch: errorMsg = "I expected the answer <expectedType>; \"<answer>\" is not a legal Rascal type.";
	              }
	              return  wrongAnswer(cpid, qid, errorMsg);
	            } else {   // Type question with a listing
	              validate = (holeInLst) ? lstBefore + answer + lstAfter + cndBefore	             
	                                     : ((holeInCnd) ? lstBefore + cndBefore + answer + cndAfter
	                                                    : lstBefore + cndBefore);
	            
	              println("Evaluating validate: <validate>");
	              output =  shell(setup + validate);
	              println("result is <output>");
	              
	              a = size(output) -1;
	              while(a > 0 && startsWith(output[a], "cancelled") ||startsWith(output[a], "rascal"))
	                 a -= 1;
	                 
	              expectedType = toString(generateType(rtype, env));
	              
	              errors = [line | line <- output, /[Ee]rror/ := line];
	              println("errors = <errors>");
	               
	              if(size(errors) == 0 && /^<answerType:.*>:/ := output[a]){
	                 println("answerType = <answerType>, expectedType = <expectedType>, answer = <answer>");
	                 ok = ((holeInLst || holeInCnd) ? answerType : answer) == expectedType;
	                 if(ok)
	                    return correctAnswer(cpid, qid);
	                    
	                 errorMsg = "I expected the answer <expectedType> instead of <answerType>.";
	                 if(!holeInCnd){
	                    try parseType(answer); catch: errorMsg = "I expected the answer <expectedType>; \"<answer>\" is not a legal Rascal type.";
	                 }
	                 wrongAnswer(cpid, qid, errorMsg);
	              }
	              
	              errorMsg = "";
	              for(error <- errors){
	                   if(/Parse error/ := error)
	                      errorMsg = "There is a syntax error in your answer. ";
	              }
	              if(errorMsg == "" && size(errors) > 0)
	                 errorMsg = "There is an error in your answer. ";
	                 
	              errorMsg += (holeInLst) ? "I expected a value of type <expectedType>. "
	                                      : "I expected the answer <expectedType>. ";
	                                      
	              if(!(holeInCnd || holeInLst)){
	                 try parseType(answer); catch: errorMsg = "Note that \"<answer>\" is not a legal Rascal type.";
	              }
	            
	              return  wrongAnswer(cpid, qid, errorMsg);
	            }
	          } catch:
	             return wrongAnswer(cpid, qid, "Cannot assess your answer.");
	      }
	    }
      }
    }
    throw wrongAnswer(cpid, qid, "Cannot validate your answer");
}

public str showCheat(ConceptName cpid, QuestionName qid, Question q, map[str,str] params){
   switch(q){
      case choiceQuestion(qid,descr,choices): {
        gcnt = 0;
        for(ch <- choices)
           if(good(txt) := ch)
           	  gcnt += 1;
        plural = (gcnt > 1) ? "s" : "";
        return cheatAnswer(cpid, qid, "The expected answer<plural>: <for(ch <- choices){><(good(txt) := ch)?txt:""> <}>");
      }
      
      case textQuestion(qid,descr,replies): {
        plural = (size(replies) > 1) ? "s" : "";
        return cheatAnswer(cpid, qid, "The expected answer<plural>: <for(r <- replies){><r> <}>");
      }
      
      case tvQuestion(qid, qkind, qdetails): {
        setup  = qdetails.setup;
        lstBefore = qdetails.lstBefore;
        lstAfter  = qdetails.lstAfter;
        cndBefore = qdetails.cndBefore;
        cndAfter  = qdetails.cndAfter;
        holeInLst = qdetails.holeInLst;
        holeInCnd = qdetails.holeInCnd;
        vars   = qdetails.vars;
        auxVars = qdetails.auxVars;
        rtype = qdetails.rtype;
        hint = qdetails.hint;
        
        switch(qkind){
          case valueOfExpr():
            return cheatAnswer(cpid, qid, "The expected answer: <hint>");
          
          case typeOfExpr():
            return cheatAnswer(cpid, qid, "The expected answer: <rtype>");
        }
      }
    }
    throw "Cannot give cheat for: <qid>";
}

public str showAnother(ConceptName cpid, QuestionName qid, Question q){
    return XMLResponses(("concept" : cpid, "exercise" : qid, "another" : showQuestion(cpid, q)));
}

public str cheatAnswer(ConceptName cpid, QuestionName qid, str cheat){
    return XMLResponses(("concept" : cpid, "exercise" : qid, "validation" : "true", "feedback" : cheat));
}

public list[str] positiveFeedback = [
"Good!",
"Go on like this!",
"I knew you could make this one!",
"You are making good progress!",
"Well done!",
"Yes!",
"More kudos",
"Correct!",
"You are becoming a pro!",
"You are becoming an expert!",
"You are becoming a specialist!",
"Excellent!",
"Better and better!",
"Another one down!",
"You are earning a place in the top ten!",
"Learning is fun, right?",
"Each drop of rain makes a hole in the stone.",
"A first step of a great journey.",
"It is the journey that counts.",
"The whole moon and the entire sky are reflected in one dewdrop on the grass.",
"There is no beginning to practice nor end to enlightenment; There is no beginning to enlightenment nor end to practice.",
"A journey of a thousand miles begins with a single step.",
"When you get to the top of the mountain, keep climbing.",
"No snowflake ever falls in the wrong place.",
"Sitting quietly, doing nothing, spring comes, and the grass grows by itself.",
"To follow the path, look to the master, follow the master, walk with the master, see through the master, become the master.",
"When you try to stay on the surface of the water, you sink; but when you try to sink, you float."
];

public list[str] negativeFeedback = [
"A pity!",
"A shame!",
"Try another question!",
"I know you can do better.",
"Nope!",
"Keep trying.",
"I am suffering with you :-(",
"Give it another try!",
"With some more practice you will do better!",
"Other people mastered this, and you can do even better!",
"It is the journey that counts!",
"Learning is fun, right?",
"After climbing the hill, the view will be excellent.",
"Hard work will be rewarded!",
"There\'s no meaning to a flower unless it blooms.",
"Not the wind, not the flag; mind is moving.",
"If you understand, things are just as they are; if you do not understand, things are just as they are.",
"Knock on the sky and listen to the sound.",
"The ten thousand questions are one question. If you cut through the one question, then the ten thousand questions disappear.",
"To do a certain kind of thing, you have to be a certain kind of person.",
"When the pupil is ready to learn, a teacher will appear.",
"If the problem has a solution, worrying is pointless, in the end the problem will be solved. If the problem has no solution, there is no reason to worry, because it can\'t be solved.",
"And the end of all our exploring will be to arrive where we started and know the place for the first time.",
"It is better to practice a little than talk a lot.",
"Water which is too pure has no fish.",
"All of the significant battles are waged within the self.",
"No snowflake ever falls in the wrong place.",
"It takes a wise man to learn from his mistakes, but an even wiser man to learn from others.",
"Only when you can be extremely pliable and soft can you be extremely hard and strong.",
"Sitting quietly, doing nothing, spring comes, and the grass grows by itself.",
"The obstacle is the path.",
"To know and not do is not yet to know.",
"The tighter you squeeze, the less you have.",
"When you try to stay on the surface of the water, you sink; but when you try to sink, you float."
];

public str correctAnswer(ConceptName cpid, QuestionName qid){
    if(!isExam){
    	ecpid = escapeConcept(cpid);
    	badAnswer -= qid;
    	goodAnswer += qid;
    	feedback = (arbInt(100) < 25) ? getOneFrom(positiveFeedback) : "";
    	return XMLResponses(("concept" : ecpid, "exercise" : qid, "validation" : "true", "feedback" : feedback));
    } else
        return "pass";
}

public str wrongAnswer(ConceptName cpid, QuestionName qid, str explanation){
    if(!isExam){
       ecpid = escapeConcept(cpid);
       badAnswer += qid;
       goodAnswer -= qid;
       feedback = explanation + ((arbInt(100) < 25) ? (" " + getOneFrom(negativeFeedback)) : "");
	   return  XMLResponses(("concept" : ecpid, "exercise" : qid, "validation" : "false", "feedback" : feedback));
	} else
	   return "fail";
}

public str saveFeedback(str error, str replacement){
  return (error != "") ? error : replacement;
}

public str XMLResponses(map[str,str] values){
    R = "\<responses\><for(field <- values){>\<response id=\"<field>\"\><escapeForHtml(values[field])>\</response\><}>\</responses\>";
    println("R = <R>");
    return R;
}