### Written Answers: 10/10

### Design: 3/3

### Documentation & Specification (including JavaDoc): 3/3

### Code quality (code and internal comments including RI/AF): 3/3

### Testing (test suite quality & implementation): 3/3

### Mechanics: 3/3

#### Overall Feedback

Great work!

#### More Details

- Your representation invariant should also state your restrictions on node and
  edge uniqueness, as you stated in your abstract state

- Your collection of Nodes is a private implementation detail. You should not
  mention in it in `spec.effects`, as it exposes implementation to clients
  unnecessarily. Instead, you should generalize it to your graph (ex: "add Node
  to graph" rather than "add Node to our collection of Nodes")

- Regarding your TODO comments, you don't need to test your Edge class methods
  as Edge is private, but remember to add the tests you mentioned for the public
  Graph methods you didn't test

- Your checkRep guards against empty Strings for Nodes/Edges, but your addNode
  and addEdge methods do not check those conditions, nor do you specify the
  requirement in your specs!

- I suggest having tests loops between multiple nodes (ex: n1 -> n2, n2 -> n1)
