module lang::rascal::tests::basic::Equality

import util::Math;
import Set;
import Map;
import Type;

// values have an equivalence relation
test bool reflexEq(value x) = x == x;
test bool transEq(value x, value y, value z) = (x == y && y == z) ==> (x == z);
test bool commutativeEq(value x, value y) = (x == y) <==> (y == x);

// values are partially ordered
test bool reflexLTE(value x) = (x <= x);
test bool antiSymmetricLTE(value x, value y) = (x <= y && y <= x) ==> (x == y);
test bool transLTE(value x, value y, value z) = (x <= y && y <= z) ==> x <= z;

// numbers are totally ordered
test bool numTotalLTE(num x, num y) = x <= y || y <= x;
test bool numAntiSymmetricLTE(num x, num y) = (x <= y && y <= x) ==> (x == y);
test bool numTransLTE(num x, num y, num z) = (x <= y && y <= z) ==> (x <= z);
test bool numValueReflex(num x) { value y = x; return x == y && y == x; }

// ints are totally ordered
test bool intTotalLTE(int x, int y) = x <= y || y <= x;
test bool intAntiSymmetricLTE(int x, int y) = (x <= y && y <= x) ==> (x == y);
test bool intTransLTE(int x, int y, int z) = (x <= y && y <= z) ==> (x <= z);
test bool intValueReflex(int x) { value y = x; return x == y && y == x; }

// reals are totally ordered
test bool realTotalLTE(real x, real y) = x <= y || y <= x;
test bool realAntiSymmetricLTE(real x, real y) = (x <= y && y <= x) ==> (x == y);
test bool realTransLTE(real x, real y, real z) = (x <= y && y <= z) ==> (x <= z);
test bool realValueReflex(real x) { value y = x; return x == y && y == x; }

// rat are totally ordered
test bool ratTotalLTE(rat x, rat y) = x <= y || y <= x;
test bool ratAntiSymmetricLTE(rat x, rat y) = (x <= y && y <= x) ==> (x == y);
test bool ratTransLTE(rat x, rat y, rat z) = (x <= y && y <= z) ==> (x <= z);
test bool ratValueReflex(rat x) { value y = x; return x == y && y == x; }

// strings are totally ordered
test bool numTotalLTE(str x, str y) = x <= y || y <= x;
test bool strAntiSymmetricLTE(str x, str y) = (x <= y && y <= x) ==> (x == y);
test bool strTransLTE(str x, str y, str z) = (x <= y && y <= z) ==> x <= z;
test bool strValueReflex(rat x) { value y = x; return x == y && y == x; }

// lists are partially ordered
test bool listReflexLTE(list[value] x) = (x <= x);
test bool listAntiSymmetricLTE(list[value] x, list[value] y) = (x <= y && y <= x) ==> (x == y);
test bool listTransLTE(list[value] x, list[value] y, list[value] z) = (x <= y && y <= z) ==> x <= z;

// sets are ordered via sub-set relation
test bool subsetOrdering1(set[value] x, set[value] y) = x <= x + y; 
test bool subsetOrdering2(set[value] x, set[value] y) = (x <= y) <==> (x == {} || all(e <- x, e in y));

// sets are partially ordered
test bool setReflexLTE(set[value] x) = (x <= x);
test bool setAntiSymmetricLTE(set[value] x, set[value] y) = (x <= y && y <= x) ==> (x == y);
test bool setTransLTE(set[value] x, set[value] y, set[value] z) = (x <= y && y <= z) ==> x <= z;

// map are ordered via sub-map relation
test bool submapOrdering1(map[value,value] x, map[value,value] y) = x <= y + x; // remember map join is not commutative
test bool submapOrdering2(map[value,value]x, map[value,value] y) = (x <= y) <==> (x == () || all(e <- x, e in y, y[e] == x[e]));

// maps are partially ordered
test bool setReflexLTE(map[value,value] x) = (x <= x);
test bool setAntiSymmetricLTE(map[value,value] x, map[value,value] y) = (x <= y && y <= x) ==> (x == y);
test bool setTransLTE(map[value,value] x, map[value,value] y, map[value,value] z) = (x <= y && y <= z) ==> x <= z;

// conversions
test bool intToReal(int i) = i == toReal(i);
test bool ratToReal(rat r) = r == toReal(r);
test bool intToReal(int i) = i <= toReal(i);
test bool ratToReal(rat r) = r <= toReal(r);
test bool intToReal(int i) = toReal(i) >= i;
test bool ratToReal(rat r) = toReal(r) >= r;
test bool lessIntReal(int i) = !(i < toReal(i));
test bool lessRatReal(int i) = !(i < toReal(i));

// set containment
test bool differentElements(int i) = size({i, toReal(i), toRat(i,1)}) == 3; // yes, really 3.
test bool differentElement2(int i, rat r) = i == r ==> size({i,r}) == 2; // yes, really 2.
test bool differentElement2(int i, real r) = i == r ==> size({i,r}) == 2; // yes, really 2.

// map keys
test bool differentKeys(int i,real r) = ((i:10,r:20)[toReal(i)]?0) == 0;
test bool differentKeys2(int i,rat r) = ((i:10,r:20)[toRat(i,1)]?0) == 0;
test bool differentKeys3(int i) = size((i:10) + (toRat(i,1):20) + (toReal(i):30)) == 3;

// == vs eq
test bool eqImpliesEquals(value x, value y) = eq(x,y) ==> (x == y);
test bool nonComparabilityImpliesNonEq(value x, value y) = !comparable(typeOf(x),typeOf(y)) ==> !eq(x,y);
test bool comparabilityImpliesEquivalence(value x, value y) = comparable(typeOf(x),typeOf(y)) ==> (eq(x,y) <==> x == y);

