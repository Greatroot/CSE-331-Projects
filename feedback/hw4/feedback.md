### Written Answers: 25/26

### Code Quality: 2/3

### Mechanics: 3/3

### General Feedback
Solid work overall! See comments below about sortedInsert.
### Specific Feedback
The loop in polynomial division needs to terminate when p becomes 0.  If you
don't have this condition in the loop, then an infinite loop can occur when the
degree of the divisor is 0, since the degree of p = 0 is also 0.

In part 2b, the changes are required to the following methods:
checkRep, equals, toString, getExpt, and hashCode. (-0)

Final immutable fields cannot be modified after they are instantiated; the
compiler would complain about any attempt to do so.  Therefore, we can reason
that RatNum and RatTerm cannot contain any bugs with regard to the
representation invariant as long as we ensure the coherency of the data at
initialization.  Therefore, RatNum and RatTerm are special cases that do not
need calls to checkRep at the beginning and end of every public method, aside
from the constructor. (-0)

Your implementation of sortedInsert is more complicated than it needs to be. In particular, you treat multiple edge cases as their own block, when it would be better to incorporate them into the general case.
