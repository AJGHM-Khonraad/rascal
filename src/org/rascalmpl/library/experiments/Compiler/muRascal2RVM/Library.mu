module Library

/*
function main[1,1,args] { return next(init(create(TRUE))); }

function TRUE[0,0,] { return true; }   // should be true
 
function FALSE[0,0,] { return false; }


function AND_U_U[2,2,lhs,rhs]{
  return prim("and_bool_bool", lhs, rhs);
}

function AND_M_U[2,2,lhs,rhs,clhs]{
   clhs = init(create(lhs));
   while(hasNext(clhs)){
     if(next(clhs)){
        if(rhs){
           yield true;
        };
     };          
   };
   return 0;
}

function AND_U_M[2,2,lhs,rhs,crhs]{
   if(lhs){
      crhs = init(create(rhs));
      while(hasNext(crhs)){
        if(next(crhs)){
           yield true;
        } else {
          return false;
        };
      };         
   };
   return false;
}

function AND_M_M[2,2,lhs,rhs,clhs,crhs]{
   clhs = init(create(lhs));
   while(hasNext(clhs)){
     if(next(clhs)){
        crhs = init(create(rhs));
        while(hasNext(crhs)){
          if(next(crhs)){
             yield true;
          } else {
            return false;
          };
        };       
     };          
   };
   return false;
}

function ONE[1,1,arg, carg]{
   carg = init(create(arg));
   return next(arg);
}

function ALL[1,1,arg,carg]{
   carg = init(create(arg));
   while(hasNext(carg)){
        yield next(carg);
   };
   return false;
}        
*/
// Pattern matching

function MATCH[1,2,pat,subject,cpat]{
   cpat = init(pat, subject);
   while(hasNext(cpat)){
      if(next(cpat)){
         yield true;
      } else {
        return false;
      };
   };
   return false;
}

function MATCH_INT[1,2,pat,subject]{
   return prim("equals_num_num", pat, subject);
}

function MATCH_STR[1,2,pat,subject]{
   return prim("equals_str_str", pat, subject);
}

function MATCH_VAR[1, 4, name, scopeId, pos,subject]{
   var(name,scopeId,pos) = subject;
   return true;
}

function MATCH_LIST[1, 2, pat,subject,patlen,sublen,
						  p,cursor,forward,matcher,
						  matchers,pats,success,nextCursor]{

     patlen = prim("size_list", pats);
     sublen = prim("size_list", subject);
     p = 0; 
     cursor = 0;
     forward = true;
     matcher = init(get pats[p], subject, cursor);
     matchers = prim("make_list", 0);
     while(true){
         while(hasNext(matcher)){
        	[success, nextCursor] = next(matcher,forward);
            if(success){
               forward = true;
               cursor = nextCursor;
               matchers = prim("addition_elm_list", matcher, matchers);
               p = prim("addition_num_num", p, 1);
               if(prim("and_bool_bool",
                       prim("equals_num_num", p, patlen),
                       prim("equals_num_num", cursor, sublen))) {
              	   yield true; 
               } else {
                   matcher = init(get pats[p], subject, cursor);
               };    
            };
         };
         if(prim("greater_num_num", p, 0)){
               p = prim("subtraction_num_num", p, 1);
               matcher = prim("head_list", matchers);
               matchers = prim("tail_list", matchers);
               forward = false;
         } else {
               return false;
         };
     };
}

function MATCH_PAT_IN_LIST[1, 3, pat, subject, start, cpat]{
    cpat = init(pat, get subject[start]);
    
    while(hasNext(cpat)){
       if(next(cpat)){
          return [true, prim("addition_num_num", start, 1)];
       };   
    };
    return [false, start];
  
} 
 
function MATCH_VAR_IN_LIST[1, 5, name, scopeId, pos, subject, start, n]{
    n = start;
    while(prim("less_num_num", n, prim("size_list", subject))){
        var(name, scopeId, pos) = prim("sublist", subject, start, prim("subtraction_num_num", n, start));
        yield [true, n];
        n = prim("addition_num_num", n, 1);
     };
     return [false, start];
 }
