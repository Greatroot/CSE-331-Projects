### Written Answers: 14/20
- IntQueue1's AF should also say that the ith element in the queue is at entries[i]
- IntQueue2's AF should also say that the ith element in the queue is at entries[(i + front) % size]
- The representation invariant should define a set of concrete states that are
possibly coherent in the context of the abstraction function, i.e. they map
to an intelligible abstract state.  You are not doing that.
- The representation invariant for `IntQueue1` ought to be:
```
entries != null && !entries.contains(null)
```
- The representation invariant for `IntQueue2` ought to be:
```
entries != null && 0 <= front < entries.length && 0 <= size <= entries.length
```
- toString() wouldn't cause rep exposure because Strings are immutable so a client has no
way to modify the internal representation with the return value

### Design: 3/3

### Documentation & Specification (including JavaDoc): 3/3

### Testing (test suite quality & implementation): 2/3

### Code quality (code stubs/skeletons only, nothing else): 3/3

### Mechanics: 3/3

#### Overall Feedback

Good work! Be sure to address all the feedback!

#### More Details

- parent, child, relationship, and neighbor are terms you can define in your class level comment,
but they shouldn't be specfields.

- I'd allow the empty string as a node/edge label (but excluding null labels is good)

- @spec.effects for `Graph.addNode/addEdge` should say how specifically the Graph is modified

- The `Graph.getNode` method seems unnecessary

- Are you sure you want to allow the client to modify nodes' values? That could lead to confusion down the road.

- Are you sure you want a toString for your Graph? It seems unnecessary and would be a pain to implement.

- You shouldn't tell me you're planning on implementing private node/edge classes - that's an implementation detail. However, since you included it as a comment and didn't actually define them, I'll let it slide.

- You might want to consider implementing a way for the client to check if a node/edge exists in the Graph - it might come in handy later on.

- As an FYI, if you didn't want me to know about your files in the Ignore folder, you could have just not pushed them to Gitlab

- Your jUnit tests should also test for correct behavior if you add a duplicate node to the Graph

- You should delete NodeTest.java

- Your script tests can't test whether something's return value is correct

- Your script tests shouldn't be inside a Graph folder - that will break your test driver. Instead, keep them all in the same place as the example tests.

- You should rename your script tests so they're names tell me what they're testing

- You should also test ListChildren and ListNode

- Be sure to test interesting edge cases like cyclical graphs, correct alphabetical ordering for ListChildren/ListNode w/ lots of nodes/children, etc.
