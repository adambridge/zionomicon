object AVLTreeExample extends App {
  
  // Create an AVL tree and insert values
  println("=== AVL Tree Example ===\n")
  
  // Example 1: Building a tree with random insertions
  println("1. Inserting values: 50, 25, 75, 10, 30, 60, 80")
  var tree = AVLTree.empty[Int]
  val values = List(50, 25, 75, 10, 30, 60, 80)
  tree = values.foldLeft(tree)((t, v) => AVLTree.insert(t, v))
  
  println(s"   Tree size: ${tree.size}")
  println(s"   Tree height: ${tree.height}")
  println(s"   Is balanced: ${AVLTree.isBalanced(tree)}")
  println(s"   Is BST: ${AVLTree.isBST(tree)}")
  println(s"   In-order traversal: ${tree.toList}")
  println()
  
  // Example 2: Worst-case scenario for regular BST (sorted insertion)
  println("2. Inserting sorted values 1-15 (worst case for unbalanced BST)")
  val sortedTree = (1 to 15).foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
  
  println(s"   Tree size: ${sortedTree.size}")
  println(s"   Tree height: ${sortedTree.height}")
  println(s"   Is balanced: ${AVLTree.isBalanced(sortedTree)}")
  println(s"   Max possible height (unbalanced): ${sortedTree.size}")
  println(s"   AVL tree maintains logarithmic height!")
  println()
  
  // Example 3: Contains operations
  println("3. Testing contains operation")
  println(s"   Contains 30: ${tree.contains(30)}")
  println(s"   Contains 100: ${tree.contains(100)}")
  println()
  
  // Example 4: Delete operations
  println("4. Deleting values: 25, 75")
  tree = AVLTree.delete(tree, 25)
  tree = AVLTree.delete(tree, 75)
  
  println(s"   Tree size after deletions: ${tree.size}")
  println(s"   Tree height: ${tree.height}")
  println(s"   Is balanced: ${AVLTree.isBalanced(tree)}")
  println(s"   In-order traversal: ${tree.toList}")
  println()
  
  // Example 5: Building from sequence
  println("5. Building tree using apply method")
  val tree2 = AVLTree(42, 17, 99, 3, 54, 67, 12)
  println(s"   In-order traversal: ${tree2.toList}")
  println(s"   Size: ${tree2.size}, Height: ${tree2.height}")
  println()
  
  // Example 6: Large tree demo
  println("6. Large tree with 1000 elements")
  val largeTree = (1 to 1000).foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
  println(s"   Size: ${largeTree.size}")
  println(s"   Height: ${largeTree.height}")
  println(s"   Is balanced: ${AVLTree.isBalanced(largeTree)}")
  println(s"   Theoretical max height: ${math.ceil(1.44 * (math.log(1002) / math.log(2))).toInt}")
  println(s"   AVL tree guarantees O(log n) operations!")
}
