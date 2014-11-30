module Library

// Specific to delimited continuations (only experimental)

declares "cons(adt(\"Gen\",[]),\"NEXT\",[ label(\"cont\",func(\\value(),[])) ])"
declares "cons(adt(\"Gen\",[]),\"EXHAUSTED\",[])"


function NEXT(gen) {
    if(muprim("equal",muprim("get_name",gen),"NEXT")) {
        return true
    }
    return false
}


// Semantics of the all operator

coroutine ALL(tasks) guard { var len = size_array(tasks); len > 0 } {
    var workers = make_array(len),
        j = 0  
    workers[j] = create(tasks[j]())
    while(true) {
        while(next(workers[j])) {
            if(j == len - 1) {
                yield
            } else {
                j = j + 1
                workers[j] = create(tasks[j]())
            }
        }
        if(j > 0) {
            j = j - 1
        } else {
            exhaust
        }
    }
}

coroutine OR(tasks) guard { var len = size_array(tasks); len > 0 } {
    var j = 0 
    while(j < len) {
        tasks[j]()()
        j = j + 1
    }
}

coroutine ONE(task) {
    return next(create(task))
}

function RASCAL_ALL(genArray, generators) { 
    var len = size_array(genArray), 
        genInits = make_array(len),
        j = 0, 
        forward = true, 
        gen   
    while(true) {
        if(generators[j]) {
            if(forward) {
                genInits[j] = create(genArray[j])
            }
            gen = genInits[j]
            if(next(gen)) {
                forward = true
                j = j + 1
            } else {
                forward = false
                j = j - 1
            }
        } else {
            if(forward) {
                if(genArray[j]()) {
                    forward = true
                    j = j + 1
                } else {
                    return false
                }
            } else {
                j = j - 1
            }
        }
        if(j < 0) {
           return true
        }
        if(j == len) {
           forward = false
           j = j - 2
           if(j < 0) {
              return true
           }
        }
    }
}

// Initialize a pattern with a given value and exhaust all its possibilities

/******************************************************************************************/
/*                    Enumerators for all types                                               */
/******************************************************************************************/


// Enumerators are used by
// - ENUMERATE_AND_MATCH
// - ENUMERATE_AND_ASSIGN
// - ENUMERATE_CHECK_AND_ASSIGN
// All ENUM declarations have a parameter 'rVal' that is used to yield their value

coroutine ENUM_LITERAL(iLit, rVal) {
    yield iLit
}

coroutine ENUM_LIST(iLst, rVal) guard { var len = size_list(iLst); len > 0 } {
    var j = 0
    while(j < len) {
        yield get_list(iLst, j)
        j = j + 1
    }
}

coroutine ENUM_SET(iSet, rVal) 
guard { 
    var iLst = set2list(iSet), 
        len = size_list(iLst)
    len > 0 
}
{
    var j = 0
    while(j < len) {
        yield get_list(iLst, j)
        j = j + 1
    }
}

coroutine ENUM_MAP(iMap, rVal) 
guard { 
    var iKlst = keys(iMap), 
        len = size_list(iKlst) 
    len > 0 
}
{
    var j = 0
    while(j < len) {
        yield get_list(iKlst, j)
        j = j + 1
    }
}

coroutine ENUM_NODE(iNd, rVal) 
{
   var array, iLst, len, children, j = 0, prod, op, delta = 2, opname
   
   //println("ENUM_NODE", iNd);
   if(equal(get_name(iNd), "appl")){            // A concrete list?
      children = get_children(iNd)
      prod = children[0]
      //println("prod", prod);
      //println("get_name(prod)", get_name(prod))
      if(equal(get_name(prod), "regular")){     // regular(opname(), ...)
         op = get_children(prod)[0]
         opname = get_name(op)
         //println("opname", opname)
         // Consider layout and separators
         if(equal(opname, "iter-seps") || equal(opname, "iter-star-seps")){
            delta = 1 + size_list(get_children(op)[1]);
         }
         iLst = children[1]
         len = size_list(iLst)
         if(len > 0){
            while(j < len) {
               yield muprim("subscript_list_mint", iLst, j)
               j = j + delta
            }
         }
      } else {                                    
        return iNd;                                // Concrete node, but not a concrete list
      }
   } else {                                        // Not a concrete list
      array = get_children_and_keyword_values(iNd)
      len = size_array(array)                    
    
         if(len > 0){
         while(j < len) {
            yield array[j]
            j = j + 1
         }
       }
    }
}

coroutine ENUM_NODE_NO_KEYWORD_PARAMS(iNd, rVal) 
{
   var array, iLst, len, children, j = 0, prod, op, delta = 2, opname
   
   println("ENUM_NODE_NO_KEYWORD_PARAMS", iNd);
   // TODO concrete appl case?
   array = get_children(iNd)
   len = size_array(array)                    
    
   if(len > 0){
      while(j < len) {
         yield array[j]
         j = j + 1
       }
    }
}

coroutine ENUM_TUPLE(iTup, rVal) guard { var len = size_tuple(iTup); len > 0 } {
    var j = 0
    while(j < len) {
        yield get_tuple(iTup, j)
        j = j + 1
    }
}

coroutine ENUMERATE_AND_MATCH1(enumerator, pat) {
    var iElm 
    enumerator = create(enumerator, ref iElm)
    while(next(enumerator)) {
        pat(iElm)
    }
}

coroutine ENUMERATE_AND_MATCH(pat, iVal) { 
    typeswitch(iVal) {
        case list:         ENUMERATE_AND_MATCH1(ENUM_LIST   (iVal), pat)
        case lrel:         ENUMERATE_AND_MATCH1(ENUM_LIST   (iVal), pat)
        case node:         ENUMERATE_AND_MATCH1(ENUM_NODE   (iVal), pat)
        case constructor:  ENUMERATE_AND_MATCH1(ENUM_NODE   (iVal), pat)
        case map:          ENUMERATE_AND_MATCH1(ENUM_MAP    (iVal), pat)
        case set:          ENUMERATE_AND_MATCH1(ENUM_SET    (iVal), pat)
        case rel:          ENUMERATE_AND_MATCH1(ENUM_SET    (iVal), pat)
        case tuple:        ENUMERATE_AND_MATCH1(ENUM_TUPLE  (iVal), pat)
        default:           ENUMERATE_AND_MATCH1(ENUM_LITERAL(iVal), pat)
    }
}

coroutine ENUMERATE_AND_ASSIGN(rVar, iVal) {
    //println("ENUMERATE_AND_ASSIGN", iVal);
    typeswitch(iVal) {
        case list:         ENUM_LIST   (iVal, rVar)
        case lrel:         ENUM_LIST   (iVal, rVar)
        case node:         ENUM_NODE   (iVal, rVar)
        case constructor:  ENUM_NODE   (iVal, rVar)
        case map:          ENUM_MAP    (iVal, rVar)
        case set:          ENUM_SET    (iVal, rVar)
        case rel:          ENUM_SET    (iVal, rVar)
        case tuple:        ENUM_TUPLE  (iVal, rVar)
        default:           ENUM_LITERAL(iVal, rVar)
    }
}

coroutine ENUMERATE_CHECK_AND_ASSIGN1(enumerator, typ, rVar) {
    var iElm
    enumerator = create(enumerator, ref iElm) 
    while(next(enumerator)) {
        if(subtype(typeOf(iElm), typ)) {
             yield iElm
        }
    } 
}

coroutine ENUMERATE_CHECK_AND_ASSIGN(typ, rVar, iVal) {
    typeswitch(iVal) {
        case list:         ENUMERATE_CHECK_AND_ASSIGN1(ENUM_LIST   (iVal), typ, rVar)
        case lrel:         ENUMERATE_CHECK_AND_ASSIGN1(ENUM_LIST   (iVal), typ, rVar)
        case node:         ENUMERATE_CHECK_AND_ASSIGN1(ENUM_NODE   (iVal), typ, rVar)
        case constructor:  ENUMERATE_CHECK_AND_ASSIGN1(ENUM_NODE   (iVal), typ, rVar)
        case map:          ENUMERATE_CHECK_AND_ASSIGN1(ENUM_MAP    (iVal), typ, rVar)
        case set:          ENUMERATE_CHECK_AND_ASSIGN1(ENUM_SET    (iVal), typ, rVar)
        case rel:          ENUMERATE_CHECK_AND_ASSIGN1(ENUM_SET    (iVal), typ, rVar)
        case tuple:        ENUMERATE_CHECK_AND_ASSIGN1(ENUM_TUPLE  (iVal), typ, rVar)
        default:           ENUMERATE_CHECK_AND_ASSIGN1(ENUM_LITERAL(iVal), typ, rVar)
    }
}

/******************************************************************************************/
/*                    Ranges                                                                  */
/******************************************************************************************/

coroutine RANGE_INT(pat, iFirst, iEnd) {
    var j = mint(iFirst), 
        n = mint(iEnd)
    if(j < n) {
        while(j < n) {
            pat(rint(j))
            j = j + 1
        }
    } else {
        while(j > n) {
            pat(rint(j)) 
            j = j - 1
        }
    }
}

coroutine RANGE(pat, iFirst, iEnd) {
    var j, 
        n, 
        rone = rint(1)
   
   if(iFirst is int && iEnd is int){
         j = iFirst
         n = iEnd
   } else {
      j = prim("num_to_real", iFirst)
      n = prim("num_to_real", iEnd)
   }
    if(prim("less", j, n)) {
        while(prim("less", j, n)) {
            pat(j)
            j = prim("add", j, rone)
        }
    } else {
        while(prim("greater", j, n)) {
            pat(j)
            j = prim("subtract", j, rone)
       }
    }
}

coroutine RANGE_STEP_INT(pat, iFirst, iSecond, iEnd) {
    var j = mint(iFirst), 
        n = mint(iEnd), 
        step
    if(j < n) {
        step = mint(iSecond) - j
        if(step <= 0) {
            exhaust
        }   
        while(j < n) {
            pat(rint(j))
            j = j + step
        }
        exhaust
    } else {
        step = mint(iSecond) - j
        if(step >= 0) {
            exhaust
        }   
        while(j > n) {
            pat(rint(j))
            j = j + step;
        }
        exhaust
    }
}

coroutine RANGE_STEP(pat, iFirst, iSecond, iEnd) {
    var n = iEnd, 
        j, step
    if(iFirst is int && iEnd is int) {
        j = iFirst
    } else {
        j = prim("num_to_real", iFirst)
    }
    if(prim("less", j, n)) {
        step = prim("subtract", iSecond, j)
        
        if(prim("lessequal", step, rint(0))) {
            exhaust
        }
        while(prim("less", j, n)) {
            pat(j)
            j = prim("add", j, step)
        }
        exhaust
    } else {
        step = prim("subtract", iSecond, j)
        
        if(prim("greaterequal", step, rint(0))) {
            exhaust
        }
        while(prim("greater", j, n)) {
            pat(j)
            j = prim("add", j, step)
        }
        exhaust
    }
}

/******************************************************************************************/
/*                    Pattern matching                                                          */
/******************************************************************************************/

// Use N patterns to match N subjects

coroutine MATCH_N(pats, subjects) 
guard { 
    var plen = size_array(pats), 
        slen = size_array(subjects) 
    plen == slen 
} 
{
    var ipats = make_array(plen),
        j = 0, 
        pat
    ipats[j] = create(pats[j], subjects[j])
    //println("MATCH_N, plen, slen", plen, slen);
    while((j >= 0) && (j < plen)) {
         //println("MATCH_N", j, "pats[j]", pats[j], "subjects[j]", subjects[j])
        pat = ipats[j]
        if(next(pat)) {
            if(j < (plen - 1)) {
                j = j + 1
                ipats[j] = create(pats[j], subjects[j])
            } else {
                yield
            }
        } else {
            j = j - 1
        }
    }   
}

// Match a call pattern with a simple string as function symbol

coroutine MATCH_SIMPLE_CALL_OR_TREE(iName, pats, iSubject) guard iSubject is node {
    var args 
    //println("MATCH_SIMPLE_CALL_OR_TREE, iName, pats, iSubject:", iName, pats, iSubject)  
 
    if(equal(iName, get_name(iSubject))) {
        args = get_children_and_keyword_mmap(iSubject);
        //println("MATCH_SIMPLE_CALL_OR_TREE, args", args);
        MATCH_N(pats, args)
        exhaust
    }
    if(has_label(iSubject, iName)) {
        args = get_children_without_layout_or_separators(iSubject)
        //println("MATCH_SIMPLE_CALL_OR_TREE, args", args);
        MATCH_N(pats, args)
    }
}

// Match a call pattern with an arbitrary pattern as function symbol

coroutine MATCH_CALL_OR_TREE(pats, iSubject) guard iSubject is node {
    var args = get_name_and_children_and_keyword_mmap(iSubject)
    MATCH_N(pats, args)
}

coroutine MATCH_KEYWORD_PARAMS(keywords, pats, kwMap) guard kwMap is mmap { 
    var len = size_array(keywords), 
        subjects, j, kw
    //println("MATCH_KEYWORD_PARAMS", len, keywords, pats, kwMap)
    if(len == 0) {
        return
    }
    subjects = make_array(len)
    j = 0
    while(j < len) {
        kw = mstr(keywords[j])
        if(mmap_contains_key(kwMap, kw)) {
            subjects[j] = get_mmap(kwMap, kw)
        } else {
            exhaust
        }
        j = j + 1
    }
    //println("MATCH_KEYWORD_PARAMS, calling MATCH_N", pats, subjects)
    MATCH_N(pats, subjects)
}

coroutine MATCH_REIFIED_TYPE(pat, iSubject) guard iSubject is node { 
    var nc = get_name_and_children_and_keyword_mmap(iSubject), // TODO kw args?
        konstructor = nc[0], 
        symbol = nc[1]
    if(equal(konstructor, "type") && equal(symbol, pat)) {
        yield
    }
}

coroutine MATCH_TUPLE(pats, iSubject) guard iSubject is tuple {
    MATCH_N(pats, get_tuple_elements(iSubject))
}

coroutine MATCH_LITERAL(pat, iSubject) guard equal(pat, iSubject) {
    //println("MATCH_LITERAL", pat, iSubject)
    yield
}

coroutine MATCH_SUBSTRING(pat, rBegin, rEnd, iSubject)
//guard iSubject is str
{
    var len = size(iSubject);
    if(is_tail(iSubject, pat, mint(deref rBegin))){
        //println("MATCH_SUBSTRING succeeds", pat, deref rBegin, size(pat), iSubject);
        deref rEnd = len
        yield
    }
    //println("MATCH_SUBSTRING fails", pat, deref rBegin, iSubject);
}

coroutine MATCH_VAR(rVar, iSubject) {
    var iVal 
    if(is_defined(rVar)) {
        iVal = deref rVar
        if(equal(iSubject, iVal)) {
            yield iSubject
        }
        exhaust
    }
    yield iSubject
    undefine(rVar)
}

coroutine MATCH_ANONYMOUS_VAR(iSubject) {
    yield
}

coroutine MATCH_TYPED_VAR(typ, rVar, iSubject) guard subtype(typeOf(iSubject), typ) {
    yield iSubject
    undefine(rVar)
    exhaust
}

coroutine MATCH_TYPED_ANONYMOUS_VAR(typ, iSubject) guard subtype(typeOf(iSubject), typ) {
    yield
}

coroutine MATCH_VAR_BECOMES(rVar, pat, iSubject) {
    var cpat = create(pat, iSubject)
    while(next(cpat)) {
        yield iSubject
    }
}

coroutine MATCH_TYPED_VAR_BECOMES(typ, rVar, pat, iSubject) guard subtype(typeOf(iSubject), typ) {
    var cpat = create(pat, iSubject)
    while(next(cpat)) {
        yield iSubject
    }
}

coroutine MATCH_AS_TYPE(typ, pat, iSubject) guard subtype(typeOf(iSubject), typ) {
    pat(iSubject)
}

coroutine MATCH_ANTI(pat, iSubject) {
    var cpat = create(pat, iSubject)
    if(next(cpat)) {
        exhaust
    } else {
        yield
    }
}

/******************************************************************************************/
/*                    Match a "collection"                                                */
/******************************************************************************************/

// MATCH_COLLECTION is a generic controller for matching "collections". Currently list and set matching
// are implemented as instances of MATCH_COLLECTION.
// The algorithm has as parameters:
// - a list of patterns "pats"
// - a function "accept" that returns true when we have achieved a complete match of the subject
// - the subject "subject" itself

coroutine MATCH_COLLECTION(pats,       // Coroutines to match collection elements
                           accept,     // Function that accepts a complete match
                           subject     // The subject (a collection like list or set, together with optional admin)
                           ) {
    var patlen = size_array(pats),     // Length of pattern array
        j = 0,                         // Cursor in patterns
        matchers                       // Currently active pattern matchers
        
    if(patlen == 0) {
        if(accept(subject)) {
            yield 
        }
        exhaust
    }
    
    matchers = make_array(patlen)
    matchers[j] = create(pats[j], ref subject)   
    while(true) {
        while(next(matchers[j])) {                       // Move forward
            if((j == patlen - 1) && accept(subject)) {
                yield 
            } else {
                if(j < patlen - 1) {
                    j = j + 1
                    matchers[j] = create(pats[j], ref subject)
                }  
            }
        } 
        if(j > 0) {                                      // If possible, move backward
            j  = j - 1
        } else {
            exhaust
        }
    }
}

/******************************************************************************************/
/*					List matching  												  		  */
/******************************************************************************************/

// List matching creates a specific instance of MATCH_COLLECTION

coroutine MATCH_LIST(pats, iList) guard iList is list {
    var subject = MAKE_SUBJECT(iList, 0)
    MATCH_COLLECTION(pats, Library::ACCEPT_LIST_MATCH::1, subject)
}

// A list match is acceptable when the cursor points at the end of the list

function ACCEPT_LIST_MATCH(subject) {
   return size_list(GET_LIST(subject)) == GET_CURSOR(subject)
}

function GET_LIST(subject) { 
    return subject[0] 
}

function GET_CURSOR(subject) { 
    return subject[1] 
}

function MAKE_SUBJECT(iList, cursor) {
   var ar = make_array(2)
   ar[0] = iList
   ar[1] = cursor
   return ar
}

// All coroutines that may occur in a list pattern have the following parameters:
// - pat: the actual pattern to match one or more list elements
// - subject: a tuple consiting of
//   -- iSubject: the subject list
//   -- cursor: current position in subject list

// Any pattern in a list not handled by a special case

coroutine MATCH_PAT_IN_LIST(pat, rSubject) 
guard { 
    var iList = GET_LIST(deref rSubject), 
        start = GET_CURSOR(deref rSubject)
    start < size_list(iList) 
} 
{ 
    var cpat = create(pat, get_list(iList, start))
    while(next(cpat)) {
        yield MAKE_SUBJECT(iList, start + 1)   
    }
    deref rSubject = MAKE_SUBJECT(iList, start)
} 

// A literal in a list

coroutine MATCH_LITERAL_IN_LIST(pat, rSubject) 
guard { 
    var iList = GET_LIST(deref rSubject), 
        start = GET_CURSOR(deref rSubject) 
    start < size_list(iList) 
} 
{
    var elm = get_list(iList, start)
    if(equal(pat, elm)){
        yield MAKE_SUBJECT(iList, start + 1)
    }
    deref rSubject = MAKE_SUBJECT(iList, start)
}

coroutine MATCH_VAR_IN_LIST(rVar, rSubject) 
guard { 
    var iList = GET_LIST(deref rSubject), 
        start = GET_CURSOR(deref rSubject) 
    start < size_list(iList) 
}
{
    var iElem = get_list(iList, start), 
        iVal
    if(is_defined(rVar)) {
        iVal = deref rVar
        if(equal(iElem, iVal)) {
            yield(iElem, MAKE_SUBJECT(iList, start + 1))
        }
        exhaust
    }
    yield(iElem, MAKE_SUBJECT(iList, start + 1))
    undefine(rVar)
}

coroutine MATCH_TYPED_VAR_IN_LIST(typ, rVar, rSubject) 
guard { 
    var iList = GET_LIST(deref rSubject), 
        start = GET_CURSOR(deref rSubject) 
    start < size_list(iList) 
}
{
    var iElem = get_list(iList, start)
    if(subtype(typeOf(iElem), typ)) {
        yield(iElem, MAKE_SUBJECT(iList, start + 1))
    }
    deref rSubject = MAKE_SUBJECT(iList, start)
}

coroutine MATCH_ANONYMOUS_VAR_IN_LIST(rSubject) 
guard { 
    var iList = GET_LIST(deref rSubject), 
        start = GET_CURSOR(deref rSubject)
    start < size_list(iList) 
}
{
    yield MAKE_SUBJECT(iList, start + 1)
    deref rSubject = MAKE_SUBJECT(iList, start)
}

coroutine MATCH_TYPED_ANONYMOUS_VAR_IN_LIST(typ, rSubject) 
guard { 
    var iList = GET_LIST(deref rSubject), 
        start = GET_CURSOR(deref rSubject)
    start < size_list(iList) 
}
{
    var iElem = get_list(iList, start)
    if(subtype(typeOf(iElem), typ)) {
        yield MAKE_SUBJECT(iList, start + 1)
    }
    deref rSubject = MAKE_SUBJECT(iList, start)
}

coroutine MATCH_MULTIVAR_IN_LIST(rVar, iMinLen, iMaxLen, iLookahead, rSubject) {
    var iList = GET_LIST(deref rSubject), 
        start =  GET_CURSOR(deref rSubject), 
        available = size_list(iList) - start, 
        len = mint(iMinLen), 
        maxLen = min(mint(iMaxLen), available - mint(iLookahead)),
        iVal       
    if(is_defined(rVar)) {
        iVal = deref rVar                /* TODO: check length */
        if(occurs(iVal, iList, start)) {
            yield(iVal, MAKE_SUBJECT(iList, start + size_list(iVal)))
        }
        deref rSubject = MAKE_SUBJECT(iList, start)
        exhaust
    }  
    while(len <= maxLen) {
        yield(sublist(iList, start, len), MAKE_SUBJECT(iList, start + len))
        len = len + 1
    }
    deref rSubject = MAKE_SUBJECT(iList, start)
    undefine(rVar)
}

coroutine MATCH_LAST_MULTIVAR_IN_LIST(rVar, iMinLen, iMaxLen, iLookahead, rSubject) 
guard { 
    var iList = GET_LIST(deref rSubject), 
        start =  GET_CURSOR(deref rSubject), 
        available = size_list(iList) - start, 
        len = min(mint(iMaxLen), max(available - mint(iLookahead), 0))
    len >= 0
}
{
    var maxLen = len, 
        iVal
    if(is_defined(rVar)) {
        
        iVal = deref rVar                /* TODO: check length */
        if(occurs(iVal, iList, start)) {
            yield(iVal, MAKE_SUBJECT(iList, start + size_list(iVal)))
        }
        deref rSubject = MAKE_SUBJECT(iList, start)
        exhaust
    }  
    while(len <= maxLen) {               // TODO: loop?
        yield(sublist(iList, start, len), MAKE_SUBJECT(iList, start + len))
        len = len + 1
    }
    deref rSubject = MAKE_SUBJECT(iList, start)
    undefine(rVar)
}

coroutine MATCH_ANONYMOUS_MULTIVAR_IN_LIST(iMinLen, iMaxLen, iLookahead, rSubject) {
    var iList = GET_LIST(deref rSubject), 
        start =  GET_CURSOR(deref rSubject), 
        available = size_list(iList) - start, 
        len = mint(iMinLen) 
    available = min(mint(iMaxLen), available - mint(iLookahead))
    while(len <= available) {
        yield MAKE_SUBJECT(iList, start + len)
        len = len + 1
    }
    deref rSubject = MAKE_SUBJECT(iList, start)
}

coroutine MATCH_LAST_ANONYMOUS_MULTIVAR_IN_LIST(iMinLen, iMaxLen, iLookahead, rSubject) 
guard { 
    var iList = GET_LIST(deref rSubject), 
        start =  GET_CURSOR(deref rSubject), 
        available = size_list(iList) - start,
        len = min(mint(iMaxLen), available - mint(iLookahead))
    len >= mint(iMinLen)
}
{
    while(len <= available) {
        yield MAKE_SUBJECT(iList, start + len)
        len = len + 1
    }
    deref rSubject = MAKE_SUBJECT(iList, start)
}

coroutine MATCH_TYPED_MULTIVAR_IN_LIST(typ, rVar, iMinLen, iMaxLen, iLookahead, rSubject) {
    var iList = GET_LIST(deref rSubject), 
        start =  GET_CURSOR(deref rSubject), available = size_list(iList) - start,
        len = mint(iMinLen), 
        sub
    available = min(mint(iMaxLen), available - mint(iLookahead))
    if(subtype(typeOf(iList), typ)) {
        while(len <= available) {
            yield(sublist(iList, start, len), MAKE_SUBJECT(iList, start + len))
            len = len + 1
        }
    } else {
        while(len <= available) {
            sub = sublist(iList, start, len)
            if(subtype(typeOf(sub), typ)) {
                yield(sub, MAKE_SUBJECT(iList, start + len))
                len = len + 1
            } else {
                deref rSubject = MAKE_SUBJECT(iList, start)
                exhaust
            }
        }
    }
}

coroutine MATCH_LAST_TYPED_MULTIVAR_IN_LIST(typ, rVar, iMinLen, iMaxLen, iLookahead, rSubject) {
    var iList = GET_LIST(deref rSubject), 
        start =  GET_CURSOR(deref rSubject), 
        available = size_list(iList) - start,
        len = mint(iMinLen), 
        elmType
    available = min(mint(iMaxLen), available - mint(iLookahead))
    if(subtype(typeOf(iList), typ)) {
        while(len <= available) {
            yield(sublist(iList, start, len), MAKE_SUBJECT(iList, start + len))
            len = len + 1
        }
    } else {
        elmType = elementTypeOf(typ)
        while(len < available) {
            if(subtype(typeOf(get_list(iList, start + len)), elmType)) {
                len = len + 1
            } else {
                yield(sublist(iList, start, len), MAKE_SUBJECT(iList, start + len))
                deref rSubject = MAKE_SUBJECT(iList, start)
                exhaust
            }
        }
        yield(sublist(iList, start, len), MAKE_SUBJECT(iList, start + len))
    }
}

coroutine MATCH_TYPED_ANONYMOUS_MULTIVAR_IN_LIST(typ, iMinLen, iMaxLen, iLookahead, rSubject) {
    var iList = GET_LIST(deref rSubject), 
        start =  GET_CURSOR(deref rSubject), 
        available = size_list(iList) - start,
        len = mint(iMinLen)
    available = min(mint(iMaxLen), available - mint(iLookahead))
    if(subtype(typeOf(iList), typ)) {
        while(len <= available) {
            yield MAKE_SUBJECT(iList, start + len)
            len = len + 1
        }
    } else {
        while(len <= available) {
            if(subtype(typeOf(sublist(iList, start, len)), typ)) {
                yield  MAKE_SUBJECT(iList, start + len)
                len = len + 1
            } else {
                deref rSubject = MAKE_SUBJECT(iList, start)
                exhaust
            }
        }
   }
   deref rSubject = MAKE_SUBJECT(iList, start)
}

coroutine MATCH_LAST_TYPED_ANONYMOUS_MULTIVAR_IN_LIST(typ, iMinLen, iMaxLen, iLookahead, rSubject) {
    var iList = GET_LIST(deref rSubject), 
        start =  GET_CURSOR(deref rSubject), 
        available = size_list(iList) - start,
        len = mint(iMinLen), elmType
    available = min(mint(iMaxLen), available - mint(iLookahead))
    if(subtype(typeOf(iList), typ)) {
        while(len <= available) {
            yield MAKE_SUBJECT(iList, start + len)
            len = len + 1
        }
    } else {
        elmType = elementTypeOf(typ)
        while(len < available) {
            if(subtype(typeOf(get_list(iList, start + len)), elmType)) {
                len = len + 1
            } else {
                yield MAKE_SUBJECT(iList, start + len)
                deref rSubject = MAKE_SUBJECT(iList, start)
                exhaust
            }
        }
        yield MAKE_SUBJECT(iList, start + len)
    }
    deref rSubject = MAKE_SUBJECT(iList, start)
}

// Primitives for matching of concrete list patterns

// Tree node in concrete pattern: appl(iProd, argspat), where argspat is a list pattern
coroutine MATCH_APPL_IN_LIST(iProd, argspat, rSubject)  guard { 
    var iList = GET_LIST(deref rSubject),
        start = GET_CURSOR(deref rSubject);
        start < size_list(iList) 
    } {
    var iElem = get_list(iList, start), 
        children = get_children(iElem), 
        cpats
    if(equal(get_name(iElem), "appl") && equal(iProd, children[0])) {
        cpats = create(argspat, children[1])
        while(next(cpats)) {
            yield MAKE_SUBJECT(iList, start+1)
        }
    }
}

// Match appl(prod(lit(S),_,_), _) in a concrete list
coroutine MATCH_LIT_IN_LIST(iProd, rSubject)  guard { 
    var iList = GET_LIST(deref rSubject),
        start = GET_CURSOR(deref rSubject); 
        start < size_list(iList) 
    } {
    var iElem = get_list(iList, start), 
        children = get_children(iElem)
    if(equal(get_name(iElem), "appl") && equal(iProd, children[0])) {
        yield MAKE_SUBJECT(iList, start + 1)
    }
}

// Match and skip optional layout in concrete patterns
coroutine MATCH_OPTIONAL_LAYOUT_IN_LIST(rSubject) { 
    var iList = GET_LIST(deref rSubject),
        start = GET_CURSOR(deref rSubject), 
        iElem, children, prod, prodchildren;
    if(start < size_list(iList)) {
        iElem = get_list(iList, start)
        if(iElem is node && equal(get_name(iElem), "appl")) {
            children = get_children(iElem)
            prod = children[0]
            prodchildren = get_children(prod)
            if(equal(get_name(prodchildren[0]), "layouts")) {
                yield MAKE_SUBJECT(iList, start + 1)
                exhaust
            }
        }
    }
    yield MAKE_SUBJECT(iList, start)
} 

// Match a (or last) multivar in a concrete list

coroutine MATCH_CONCRETE_MULTIVAR_IN_LIST(rVar, iMinLen, iMaxLen, iLookahead, applConstr, listProd, applProd, rSubject) {
    var iList = GET_LIST(deref rSubject),
        start = GET_CURSOR(deref rSubject),
        cavailable = size(iList) - start, 
        clen = mint(iMinLen), 
        maxLen = min(mint(iMaxLen), max(cavailable - mint(iLookahead), 0)), 
        iVal, end
    if(is_defined(rVar)) {
        iVal = deref rVar                        /* TODO: check length */
        if(occurs(iVal, iList, start)) {
            yield(iVal, MAKE_SUBJECT(iList, start + size_list(iVal)))
        }
        exhaust
    }   
    while(clen <= maxLen) {
        end = start + clen
        yield(MAKE_CONCRETE_LIST(applConstr, listProd, applProd, sublist(iList, start, clen)), MAKE_SUBJECT(iList,end))
        clen = clen + 2
    }
    undefine(rVar)
}

coroutine MATCH_LAST_CONCRETE_MULTIVAR_IN_LIST(rVar, iMinLen, iMaxLen, iLookahead, applConstr, listProd, applProd, rSubject) 
guard { 
    var iList = GET_LIST(deref rSubject),
        start = GET_CURSOR(deref rSubject), 
        cavailable = size(iList) - start, 
        clen = min(mint(iMaxLen), max(cavailable - mint(iLookahead), 0))
    clen >= mint(iMinLen) 
} 
{
    var iVal, end
    if(is_defined(rVar)) {
        iVal = deref rVar                         /* TODO: check length */
        if(occurs(iVal, iList, start)) {
             yield(iVal, MAKE_SUBJECT(iList, start + size_list(iVal)))
        }
        exhaust
    }
    end = start + clen
    yield(MAKE_CONCRETE_LIST(applConstr, listProd, applProd, sublist(iList, start, clen)), MAKE_SUBJECT(iList,end))
    undefine(rVar)
}

// Skip a separator that may be present before or after a matching multivar
function SKIP_OPTIONAL_SEPARATOR(iList, start, offset, sep, available) {
    var elm, children, prod, prodchildren
    if(available >= offset + 2) {
        elm = get_list(iList, start + offset)
        if(elm is node) {
            children = get_children(elm)
            prod = children[0]
            prodchildren = get_children(prod)
            if(equal(prodchildren[0], sep)) {
                return 2
            }
        }
    }
    return 0
}

coroutine MATCH_CONCRETE_MULTIVAR_WITH_SEPARATORS_IN_LIST(rVar, iMinLen, iMaxLen, iLookahead, sep, applConstr, listProd, applProd, rSubject) { 
    var iList = GET_LIST(deref rSubject),
        start = GET_CURSOR(deref rSubject), 
        cavailable = size(iList) - start, 
        len =  mint(iMinLen), 
        skip_leading_separator = SKIP_OPTIONAL_SEPARATOR(iList, start, 0, sep, cavailable), 
        skip_trailing_separator = 0, 
        maxLen = max(cavailable - (mint(iLookahead) + skip_leading_separator), 0),
        iVal, sublen, end             
    if(is_defined(rVar)) {
        iVal = deref rVar
        if(occurs(iVal, iList, start)) {         // TODO: check length
            yield(iVal, MAKE_SUBJECT(iList, start + size_list(iVal)))
        }
        exhaust
    }
    while(len <= maxLen) {
        if(len == 0) {
            sublen = 0
        } else {
           sublen = (4 * (len - 1)) + 1
        }
        end = start + skip_leading_separator + sublen
        skip_trailing_separator = SKIP_OPTIONAL_SEPARATOR(iList, end, 1, sep, maxLen)
        end = end + skip_trailing_separator
        yield(MAKE_CONCRETE_LIST(applConstr, listProd, applProd, sublist(iList, start + skip_leading_separator, sublen)), MAKE_SUBJECT(iList,end))
        len = len + 1
    }
    undefine(rVar)
}

coroutine MATCH_LAST_CONCRETE_MULTIVAR_WITH_SEPARATORS_IN_LIST(rVar, iMinLen, iMaxLen, iLookahead, sep, applConstr, listProd, applProd, rSubject) 
guard { 
    var iList = GET_LIST(deref rSubject),
        start = GET_CURSOR(deref rSubject), 
        cavailable = size(iList) - start, 
        skip_leading_separator =  SKIP_OPTIONAL_SEPARATOR(iList, start, 0, sep, cavailable), 
        skip_trailing_separator = 0,
        sublen = max(cavailable - (mint(iLookahead) + skip_leading_separator), 0)          
    {
        if(mint(iLookahead) > 0 && sublen >= 2) {
            // skip trailing separator
            sublen = sublen - 2
            skip_trailing_separator = 2
        }
        sublen >= mint(iMinLen)
    } 
}
{   
    var iVal, end  
    if(is_defined(rVar)) {
        iVal = deref rVar
        if(occurs(iVal, iList, start)) {	     // TODO: check length
            yield(iVal, MAKE_SUBJECT(iList, start + size_list(iVal)))
        }
        exhaust
    }
    end = start + skip_leading_separator + sublen + skip_trailing_separator
    yield(MAKE_CONCRETE_LIST(applConstr, listProd, applProd, sublist(iList, start + skip_leading_separator, sublen)), MAKE_SUBJECT(iList,end))
    undefine(rVar)
}

// applConstr: 	Tree::appl
// listProd:   	Chain rule surrounding the concrete list
// applProd:  	list constructor
// elms:		actual list elements

function MAKE_CONCRETE_LIST(applConstr, listProd, applProd, elms) {
    var listResult
    
    //println("MAKE_CONCRETE_LIST, applConstr", applConstr)
    //println("MAKE_CONCRETE_LIST, listProd", listProd)
    //println("MAKE_CONCRETE_LIST, applProd", applProd)
    //println("MAKE_CONCRETE_LIST, elms", elms)
    
    //println("MAKE_CONCRETE_LIST, size", size(get_children(listProd)[1]))
    
    if(size(get_children(listProd)[1]) > 1){ // chain rule with concrete syntax
    	listResult = prim("appl_create", applConstr, applProd, elms)
    } else {
    	listResult = prim("appl_create", applConstr, listProd, prim("list_create", prim("appl_create", applConstr, applProd, elms)))
    }
    //println("MAKE_CONCRETE_LIST", listResult)
    return listResult
}

/******************************************************************************************/
/*					Set matching  												  		  */
/******************************************************************************************/

// Set matching creates a specific instance of MATCH_COLLECTION

coroutine MATCH_SET(iLiterals, pats, iSubject) guard iSubject is set {
    if(subset(iLiterals, iSubject)) {
        iSubject = prim("set_subtract_set", iSubject, iLiterals)
        MATCH_COLLECTION(pats, Library::ACCEPT_SET_MATCH::1, mset(iSubject))
    }
}

// A set match is acceptable when the set of remaining elements is empty

function ACCEPT_SET_MATCH(subject) {
   return size_mset(subject) == 0
}

coroutine ENUM_MSET(set, rElm) {
    var iLst = mset2list(set), 
        len = size_list(iLst), 
        j = 0
    while(j < len) {
        yield get_list(iLst, j)
        j = j + 1
    }
}

// All coroutines that may occur in a set pattern have the following parameters:
// - pat: the actual pattern to match one or more elements
// - available: the remaining, unmatched, elements in the subject set
// - rRemaining: reference parameter to return remaining set elements

coroutine MATCH_PAT_IN_SET(pat, rSubject) guard { var available = deref rSubject; size_mset(available) > 0 } {
    var gen = create(ENUM_MSET, available, ref elm), 
        cpat, elm
    while(next(gen)) {
        cpat = create(pat, elm)
        while(next(cpat)) {
            yield mset_subtract_elm(available, elm)
            deref rSubject = available
        }
    }
}

coroutine MATCH_VAR_IN_SET(rVar, rSubject) guard { var available = deref rSubject; size_mset(available) > 0 } {
    var elm, gen  
    if(is_defined(rVar)) {
        elm = deref rVar
        if(is_element_mset(elm, available)) {
            yield(elm, mset_subtract_elm(available, elm))
            deref rSubject = available
        }
        exhaust
    }
    gen = create(ENUM_MSET, available, ref elm)
    while(next(gen)) {
        yield(elm, mset_subtract_elm(available, elm))
        deref rSubject = available
    }
    undefine(rVar)
}

coroutine MATCH_TYPED_VAR_IN_SET(typ, rVar, rSubject) guard { var available = deref rSubject; size_mset(available) > 0 } {
    var gen = create(ENUM_MSET, available, ref elm),
        elm
    while(next(gen)) {
        if(subtype(typeOf(elm), typ)) {
            yield(elm, mset_subtract_elm(available, elm))
            deref rSubject = available
        }
    }
}

coroutine MATCH_ANONYMOUS_VAR_IN_SET(rSubject) guard { var available = deref rSubject; size_mset(available) > 0 } {
    var gen = create(ENUM_MSET, available, ref elm),
        elm
    while(next(gen)) { 
        yield mset_subtract_elm(available, elm)
        deref rSubject = available
   }
}

coroutine MATCH_TYPED_ANONYMOUS_VAR_IN_SET(typ, rSubject) guard { var available = deref rSubject; size_mset(available) > 0 } {
    var gen = create(ENUM_MSET, available, ref elm),
        elm
    while(next(gen)) { 
        if(subtype(typeOf(elm), typ)) {
            yield mset_subtract_elm(available, elm)
            deref rSubject = available
        }
    }
}

coroutine MATCH_MULTIVAR_IN_SET(rVar, rSubject) {
    var available = deref rSubject, 
        gen, subset
    if(is_defined(rVar)) {
        subset = deref rVar
        if(subset_set_mset(subset, available)) {
            yield(subset, mset_subtract_set(available, subset))
            deref rSubject = available
        }
        exhaust
    }
    gen = create(ENUM_SUBSETS, available, ref subset)
    while(next(gen)) {
        yield(set(subset), mset_subtract_mset(available, subset))
        deref rSubject = available
    }
    undefine(rVar)
}

coroutine MATCH_ANONYMOUS_MULTIVAR_IN_SET(rSubject) {
    var available = deref rSubject, 
        gen = create(ENUM_SUBSETS, available, ref subset),
        subset
    while(next(gen)) {
        yield mset_subtract_mset(available, subset)
        deref rSubject = available
    }
}

coroutine MATCH_LAST_MULTIVAR_IN_SET(rVar, rSubject) {
    var available = deref rSubject, 
        subset
    if(is_defined(rVar)) {
        subset = deref rVar
        if(equal_set_mset(subset, available)) {
            yield(subset, mset_empty())
            deref rSubject = available
        }
        exhaust
    }
    yield(set(available), mset_empty())
    deref rSubject = available
    undefine(rVar)
}

coroutine MATCH_LAST_ANONYMOUS_MULTIVAR_IN_SET(rSubject) {
    var available = deref rSubject
    yield mset_empty()
    deref rSubject = available
}

coroutine MATCH_TYPED_MULTIVAR_IN_SET(typ, rVar, rSubject) { 
    var available = deref rSubject, 
        gen = create(ENUM_SUBSETS, available, ref subset),
        subset
    while(next(gen)) {
        if(subtype(typeOfMset(subset), typ)) {
            yield(set(subset), mset_subtract_mset(available, subset))
            deref rSubject = available
        }
    }
}

coroutine MATCH_TYPED_ANONYMOUS_MULTIVAR_IN_SET(typ, rSubject) {
    var available = deref rSubject, 
        gen = create(ENUM_SUBSETS, available, ref subset),
        subset
    while(next(gen)) {
        if(subtype(typeOfMset(subset), typ)) {
            yield mset_subtract_mset(available, subset)
            deref rSubject = available
        }
    }
}

coroutine MATCH_LAST_TYPED_MULTIVAR_IN_SET(typ, rVar, rSubject) guard { var available = deref rSubject; subtype(typeOfMset(available), typ) } {
    yield(set(available), mset_empty())
    deref rSubject = available
}

coroutine MATCH_LAST_TYPED_ANONYMOUS_MULTIVAR_IN_SET(typ, rSubject) guard { var available = deref rSubject; subtype(typeOfMset(available), typ) } {
    yield mset_empty()
    deref rSubject = available
}

// The power set of a set of size n has 2^n-1 elements 
// so we enumerate the numbers 0..2^n-1
// if the nth bit of a number i is 1 then
// the nth element of the set should be in the
// ith subset 
 
coroutine ENUM_SUBSETS(set, rSubset) {
    var lst = mset2list(set), 
        last = 2 pow size_mset(set), 
        k = last - 1,
        j, elIndex, sub
    while(k >= 0) {
        j = k
        elIndex = 0 
        sub = make_mset()
        while(j > 0) {
            if(j mod 2 == 1) {
                sub = mset_destructive_add_elm(sub, get_list(lst, elIndex))
            }
            elIndex = elIndex + 1
            j = j / 2
        }
        if(k == 0) {
            yield sub
            exhaust
        } else {
            yield sub
        }
        k = k - 1  
    }
}

/******************************************************************************************/
/*					Descendant matching  												  */
/******************************************************************************************/


// ***** Match and descent for all types *****
// Enforces the same left-most innermost traversal order as the interpreter

coroutine MATCH_AND_DESCENT(pat, iVal) {
    typeswitch(iVal) {
        case list:        MATCH_AND_DESCENT_LIST (pat, iVal)
        case lrel:        MATCH_AND_DESCENT_LIST (pat, iVal)
        case node:        MATCH_AND_DESCENT_NODE (pat, iVal)
        case constructor: MATCH_AND_DESCENT_NODE (pat, iVal)
        case map:         MATCH_AND_DESCENT_MAP  (pat, iVal)
        case set:         MATCH_AND_DESCENT_SET  (pat, iVal)
        case rel:         MATCH_AND_DESCENT_SET  (pat, iVal)
        case tuple:       MATCH_AND_DESCENT_TUPLE(pat,iVal)
        default:          true
    }  
    pat(iVal)
}

coroutine MATCH_AND_DESCENT_LITERAL(pat, iSubject) {
    if(equal(pat, iSubject)) {
        yield
        exhaust
    }
    MATCH_AND_DESCENT(MATCH_LITERAL(pat), iSubject)
}

coroutine MATCH_AND_DESCENT_LIST(pat, iLst) {
    var last = size_list(iLst), 
        j = 0
    while(j < last) {
        MATCH_AND_DESCENT(pat, get_list(iLst, j))
        j = j + 1
    }
}

coroutine MATCH_AND_DESCENT_SET(pat, iSet) {
    var iLst = set2list(iSet), 
        last = size_list(iLst), 
        j = 0
    while(j < last) {
        MATCH_AND_DESCENT(pat, get_list(iLst, j))
        j = j + 1
    }
}

coroutine MATCH_AND_DESCENT_MAP(pat, iMap) {
    var iKlst = keys(iMap), 
        iVlst = values(iMap), 
        last = size_list(iKlst), 
        j = 0
    while(j < last) {
        MATCH_AND_DESCENT(pat, get_list(iKlst, j))
        MATCH_AND_DESCENT(pat, get_list(iVlst, j))
        j = j + 1
    }
}

coroutine MATCH_AND_DESCENT_NODE(pat, iNd) {
    var ar = get_children_and_keyword_values(iNd), 
        last = size_array(ar),
        j = 0
    while(j < last) {
        MATCH_AND_DESCENT(pat, ar[j])
        j = j + 1
    }
}

coroutine MATCH_AND_DESCENT_TUPLE(pat, iTup) {
    var last = size_tuple(iTup), 
        j = 0
    while(j < last) {
        MATCH_AND_DESCENT(pat, get_tuple(iTup, j))
        j = j + 1
    }
}

// ***** Regular expressions *****

coroutine MATCH_REGEXP(iRegexp, varrefs, iSubject) {
    var matcher = muprim("regexp_compile", iRegexp, iSubject), 
        j, rVar
    while(muprim("regexp_find", matcher)) {
        j = 0 
        while(j < size_array(varrefs)) {
            rVar = varrefs[j]
            deref rVar = muprim("regexp_group", matcher, j + 1)
            j = j + 1;
        }
        yield
    }
}

coroutine MATCH_REGEXP_IN_VISIT(iRegexp, varrefs, rBegin, rEnd, iSubject) {
    var matcher = muprim("regexp_compile", iRegexp, iSubject), 
        j, rVar
    muprim("regexp_set_region", matcher, mint(deref rBegin), mint(deref rEnd))
    while(muprim("regexp_find", matcher)) {
        j = 0 
        while(j < size_array(varrefs)) {
            rVar = varrefs[j]
            deref rVar = muprim("regexp_group", matcher, j + 1)
            j = j + 1;
        }
        deref rBegin = muprim("regexp_begin", matcher);
        deref rEnd = muprim("regexp_end", matcher);
        //println("MATCH_REGEXP_IN_VISIT succeeds", substring(iSubject, mint(deref rBegin), mint(deref rEnd)), iSubject);
        yield
        muprim("regexp_set_region", matcher, deref rBegin, deref rEnd)
    }
    //println("MATCH_REGEXP_IN_VISIT fails", iSubject);
}

/******************************************************************************************/
/*					Traversal functions  												  */
/******************************************************************************************/

// ***** Traversal strategies *****

// The various traversal strategies are implemented by the functions:
// - TRAVERSE_TOP_DOWN
// - TRAVERSE_TOP_DOWN_BREAK
// - TRAVERSE_BOTTOM_UP
// - TRAVERSE_BOTTOM_UP_BREAK
//
// Each strategy function has the following arguments:
// - phi, a compiler generated function that implements the visit expression
//   with the arguments:
//   - iSubject
//   - matched
//   - hasInsert
//   - rBegin
//   - rEnd
// - iSubject, the subject value to be traversed
// - rHasMatch, a reference parameter that records successfull matches
// - rBeenChanged, a reference parameter that records subject replacements (due to insert or =>)
// - rBegin, indicates start point of match (inclusive)
// - rEnd, indicates end point of match (exclusive); for now used for string visit to indicate size of matched substring
// - rebuild, a boolean indicating whether a new value for the subject should be built

function TRAVERSE_TOP_DOWN(phi, iSubject, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var matched = false, 
        changed = false
    //println("TRAVERSE_TOP_DOWN", phi, iSubject, deref rHasMatch, deref rBeenChanged, deref rBegin, deref rEnd, rebuild);
    
    if(iSubject is str){
       iSubject = iSubject    // avoid duplicate traversal of string
    } else {
        iSubject = phi(iSubject, ref matched, ref changed, rBegin, rEnd)
    }
    //println("TRAVERSE_TOP_DOWN, new iSubject", iSubject)
    if(rebuild) {
        deref rBeenChanged = changed || deref rBeenChanged
        changed = false
        iSubject = VISIT_CHILDREN(iSubject, Library::TRAVERSE_TOP_DOWN::7, phi, rHasMatch, ref changed, rBegin, rEnd, rebuild)
        deref rBeenChanged = changed || deref rBeenChanged
        return iSubject
    }
    return VISIT_CHILDREN_VOID(iSubject, Library::TRAVERSE_TOP_DOWN::7, phi, rHasMatch, ref changed, rBegin, rEnd, rebuild)
}

function TRAVERSE_TOP_DOWN_BREAK(phi, iSubject, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var matched = false, 
        changed = false
    if(iSubject is str){
        iSubject = iSubject    // avoid duplicate traversal of string
    } else {
        iSubject = phi(iSubject, ref matched, ref changed, rBegin, rEnd)
        deref rBeenChanged = changed || deref rBeenChanged    
        if(deref rHasMatch = matched || deref rHasMatch) {    
            return iSubject
        }
    }
    if(rebuild) {
        changed = false
        iSubject = VISIT_CHILDREN(iSubject, Library::TRAVERSE_TOP_DOWN_BREAK::7, phi, rHasMatch, ref changed, rBegin, rEnd, rebuild)
        deref rBeenChanged = changed || deref rBeenChanged
        return iSubject
    }
    return VISIT_CHILDREN_VOID(iSubject, Library::TRAVERSE_TOP_DOWN_BREAK::7, phi, rHasMatch, ref changed, rBegin, rEnd, rebuild)
}

function TRAVERSE_BOTTOM_UP(phi, iSubject, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var matched = false, 
        changed = false
    //println("TRAVERSE_BOTTOM_UP", phi, iSubject, deref rHasMatch, deref rBeenChanged, deref rBegin, deref rEnd, rebuild);
    if(rebuild) {
        iSubject = VISIT_CHILDREN(iSubject, Library::TRAVERSE_BOTTOM_UP::7, phi, rHasMatch, ref changed, rBegin, rEnd, rebuild)
        deref rBeenChanged = changed || deref rBeenChanged
        changed = false
    } else {
        VISIT_CHILDREN_VOID(iSubject, Library::TRAVERSE_BOTTOM_UP::7, phi, rHasMatch, ref changed, rBegin, rEnd, rebuild)
    }
    if(iSubject is str){ // avoid duplicate traversal of string
        return iSubject
    }
    
    iSubject = phi(iSubject, ref matched, ref changed, rBegin, rEnd)
    deref rBeenChanged = changed || deref rBeenChanged
    return iSubject
}

function TRAVERSE_BOTTOM_UP_BREAK(phi, iSubject, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) { 
    var matched = false, 
        changed = false
    if(rebuild) {
        iSubject = VISIT_CHILDREN(iSubject, Library::TRAVERSE_BOTTOM_UP_BREAK::7, phi, rHasMatch, ref changed, rBegin, rEnd, rebuild)
        deref rBeenChanged = changed || deref rBeenChanged
        changed = false
    } else {
        VISIT_CHILDREN_VOID(iSubject, Library::TRAVERSE_BOTTOM_UP_BREAK::7, phi, rHasMatch, ref changed, rBegin, rEnd, rebuild)
    }
    if(deref rHasMatch || (iSubject is str)) {    // avoid duplicate traversal of string
        return iSubject
    }
    iSubject = phi(iSubject, ref matched, ref changed, rBegin, rEnd)
    deref rHasMatch = matched || deref rHasMatch
    deref rBeenChanged = changed || deref rBeenChanged    
    return iSubject
}

// ***** Visit subject's children and return (potentially modified) value *****
// - iSubject
// - traverse_fun, the traversal strategy used
// - phi, compiled visit expression
// - rHasMatch, rBeenChanged, rBegin, rEnd, rebuild as above

function VISIT_CHILDREN(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) { 
    var children
    
    //println("VISIT_CHILDREN", iSubject, deref rHasMatch, deref rBeenChanged, deref rBegin, deref rEnd, rebuild);
    
    typeswitch(iSubject){
        case list: {
                children = VISIT_ELEMENTS(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
                if(deref rBeenChanged) { return prim("list", children) }
            }
        case lrel: {
                children = VISIT_ELEMENTS(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
                if(deref rBeenChanged) { return prim("list", children) }
            }
        case set: {
                children = VISIT_ELEMENTS(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
                if(deref rBeenChanged) { return prim("set", children) }
            }
        case rel: {
                children = VISIT_ELEMENTS(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
                if(deref rBeenChanged) { return prim("set", children) }
            }
        case tuple: {
                children = VISIT_ELEMENTS(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
                if(deref rBeenChanged) { return prim("tuple", children) }
            }
        case node: {
                return VISIT_NODE(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
            }
        case constructor: {
                return  VISIT_NODE(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
            }
        case map: {
                return VISIT_MAP(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) 
            }
        case str: {
                return VISIT_STR(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
            }
        default:
            return iSubject;
    }
    //println("VISIT_CHILDREN, returns", iSubject);
    
    return iSubject
}

function VISIT_ELEMENTS(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var len = size(iSubject),
        iChildren = make_iarray(len), 
        enumerator = create(ENUMERATE_AND_ASSIGN, ref iChild, iSubject), 
        j = 0,
        iChild, childHasMatch, childBeenChanged
        
    //println("VISIT_ELEMENTS", iSubject, len, deref rHasMatch, deref rBeenChanged, deref rBegin, deref rEnd, rebuild);
    
    while(next(enumerator)) {
        childHasMatch = false
        childBeenChanged = false
        iChild = traverse_fun(phi, iChild, ref childHasMatch, ref childBeenChanged, rBegin, rEnd, rebuild)
        iChildren[j] = iChild
        j = j + 1
        deref rHasMatch = childHasMatch || deref rHasMatch
        deref rBeenChanged = childBeenChanged || deref rBeenChanged
    }
    return iChildren
}

function VISIT_MAP(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var writer = prim("mapwriter_open"), 
        enumerator = create(ENUMERATE_AND_ASSIGN, ref iKey, iSubject),
        iKey, iVal, childHasMatch, childBeenChanged, replacement 
           
    while(next(enumerator)) {
        iVal = prim("map_subscript", iSubject, iKey)
        
        childHasMatch = false
        childBeenChanged = false
        iKey = traverse_fun(phi, iKey, ref childHasMatch, ref childBeenChanged, rBegin, rEnd, rebuild)
        deref rHasMatch = childHasMatch || deref rHasMatch
        deref rBeenChanged = childBeenChanged || deref rBeenChanged
        
        childHasMatch = false
        childBeenChanged = false
        iVal = traverse_fun(phi, iVal, ref childHasMatch, ref childBeenChanged, rBegin, rEnd, rebuild)
        deref rHasMatch = childHasMatch || deref rHasMatch
        deref rBeenChanged = childBeenChanged || deref rBeenChanged
        
        prim("mapwriter_add", writer, iKey, iVal)
    }
    replacement = prim("mapwriter_close", writer)
    if(deref rBeenChanged){
        return replacement
    } else {
        return iSubject
    }
}

function VISIT_NODE(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var len = size(iSubject),
        iChildren = make_iarray(len), 
        enumerator = create(ENUM_NODE_NO_KEYWORD_PARAMS, iSubject, ref iChild),  j,
        iChild, childHasMatch, childBeenChanged,
        kwMap = get_keyword_mmap(iSubject),
        keywords = get_keys_mmap(kwMap),
        values, iVal
  
    //println("VISIT_NODE", iSubject, len, deref rHasMatch, deref rBeenChanged, deref rBegin, deref rEnd, rebuild);
    
    // Visit positional arguments
    j = 0;
    while(next(enumerator)) {
        childHasMatch = false
        childBeenChanged = false
        iChild = traverse_fun(phi, iChild, ref childHasMatch, ref childBeenChanged, rBegin, rEnd, rebuild)
        iChildren[j] = iChild
        j = j + 1
        deref rHasMatch = childHasMatch || deref rHasMatch
        deref rBeenChanged = childBeenChanged || deref rBeenChanged
    }
    
    // Visit keyword arguments
    len = size_array(keywords)
    if(len > 0) {
        values = make_array(len)
        j = 0
        while(j < len) {
            childHasMatch = false
            childBeenChanged = false
            iVal = get_mmap(kwMap, keywords[j])
            values[j] = traverse_fun(phi, iVal, ref childHasMatch, ref childBeenChanged, rBegin, rEnd, rebuild)
            deref rHasMatch = childHasMatch || deref rHasMatch
            deref rBeenChanged = childBeenChanged || deref rBeenChanged
            j = j + 1
        }
        if(deref rBeenChanged){
            kwMap = make_keyword_mmap(keywords, values)
        }
    }

    if(deref rBeenChanged){
        if(iSubject is constructor){
            return prim("constructor", muprim("typeOf_constructor", iSubject), iChildren, kwMap)
        } else {
            return prim("node", muprim("get_name", iSubject), iChildren, kwMap)
        }
    }
    return iSubject
}

function VISIT_STR(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var writer = prim("stringwriter_open"),
        j = 0,
        len = size_str(iSubject),
        repl,
        childHasMatch, childBeenChanged, replacement
        
    //println("VISIT_STR", iSubject)
    deref rBegin = 0;
    deref rEnd = len;     
    while(j < len){
        childHasMatch = false
        childBeenChanged = false
        deref rBegin = j
        deref rEnd = len
        repl = phi(iSubject, ref childHasMatch, ref childBeenChanged, rBegin, rEnd)
        if(childHasMatch){
            if(mint(deref rBegin) > j){
              prim("stringwriter_add", writer, substring(iSubject, j, mint(deref rBegin)))
            }
            prim("stringwriter_add", writer, repl)
            j = mint(deref rEnd)
        } else {
            prim("stringwriter_add", writer, substring(iSubject, j, j + 1))
            j = j + 1
        }
        deref rHasMatch = childHasMatch || deref rHasMatch
        deref rBeenChanged = childBeenChanged || deref rBeenChanged
    }
    replacement = prim("stringwriter_close", writer)
    if(deref rBeenChanged){
        return replacement
    } else {
        return iSubject
    }
}

// ***** Visit subject's children but return no value *****
// - iSubject
// - traverse_fun, the traversal strategy used
// - phi, compiled visit expression
// - rHasMatch, rBeenChanged, rBegin, rEnd, rebuild as above

function VISIT_CHILDREN_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {    
    typeswitch(iSubject){
    case list:
        VISIT_ELEMENTS_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
    case lrel:
        VISIT_ELEMENTS_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)    
    case set:
        VISIT_ELEMENTS_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
    case rel:
        VISIT_ELEMENTS_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
    case tuple:
        VISIT_ELEMENTS_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
    case node:
        VISIT_NODE_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
    case constructor:
        VISIT_NODE_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
    case map:
        VISIT_MAP_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
    case str:
        VISIT_STR_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild)
    default:
        return iSubject
    }
    return iSubject
}

function VISIT_ELEMENTS_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var enumerator = create(ENUMERATE_AND_ASSIGN, ref iChild, iSubject), 
        childBeenChanged = false,
        iChild, childHasMatch
        
    while(next(enumerator)) {
        childHasMatch = false
        traverse_fun(phi, iChild, ref childHasMatch, ref childBeenChanged, rBegin, rEnd, rebuild)
        deref rHasMatch = childHasMatch || deref rHasMatch
    }
    return
}

function VISIT_NODE_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var enumerator = create(ENUM_NODE_NO_KEYWORD_PARAMS, iSubject, ref iChild), 
        iChild, childHasMatch, childBeenChanged = false,
        kwMap = get_keyword_mmap(iSubject),
        keywords = get_keys_mmap(kwMap),
        j = 0, len = size_array(keywords)

    //println("VISIT_NODE", iSubject, len, deref rHasMatch, deref rBeenChanged, deref rBegin, deref rEnd, rebuild)

    while(next(enumerator)) { 
        childHasMatch = false
        traverse_fun(phi, iChild, ref childHasMatch, ref childBeenChanged, rBegin, rEnd, rebuild)
        deref rHasMatch = childHasMatch || deref rHasMatch
    }    
    while(j < len) {
        childHasMatch = false
        traverse_fun(phi, get_mmap(kwMap, keywords[j]), ref childHasMatch, ref childBeenChanged, rBegin, rEnd, rebuild)
        deref rHasMatch = childHasMatch || deref rHasMatch
        j = j + 1
    }
    return
}

function VISIT_MAP_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var enumerator = create(ENUMERATE_AND_ASSIGN, ref iKey, iSubject), 
        childBeenChanged = false,
        iKey, iVal, childHasMatch
        
    while(next(enumerator)) {
        childHasMatch = false
        traverse_fun(phi, iKey, ref childHasMatch, ref childBeenChanged, rBegin, rEnd, rebuild)
        deref rHasMatch = childHasMatch || deref rHasMatch
        
        childHasMatch = false
        traverse_fun(phi, prim("map_subscript", iSubject, iKey), ref childHasMatch, ref childBeenChanged, rBegin, rEnd, rebuild)
        deref rHasMatch = childHasMatch || deref rHasMatch
    }
    return
}

function VISIT_STR_VOID(iSubject, traverse_fun, phi, rHasMatch, rBeenChanged, rBegin, rEnd, rebuild) {
    var j = 0,
        len = size_str(iSubject), 
        childHasMatch, childBeenChanged = false
        
    deref rBegin = 0;
    deref rEnd = len;     
    while(j < len){
        childHasMatch = false
        deref rBegin = j
        deref rEnd = len
        phi(iSubject, ref childHasMatch, ref childBeenChanged, rBegin, rEnd)
        if(childHasMatch){
            j = mint(deref rEnd)
        } else {
            j = j + 1
        }
        deref rHasMatch = childHasMatch || deref rHasMatch
    }
    return
}
