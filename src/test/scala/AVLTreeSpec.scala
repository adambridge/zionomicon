import zio.Scope
import zio.test._
import zio.test.Assertion._

object AVLTreeSpec extends ZIOSpecDefault {

  val genInt: Gen[Any, Int] = Gen.int(-1000, 1000)
  
  val genIntList: Gen[Any, List[Int]] = Gen.listOfBounded(0, 100)(genInt)

  def spec: Spec[TestEnvironment with Scope, Nothing] = suite("AVLTreeSpec")(
    suite("Insert Operations")(
      test("inserting values creates a balanced tree") {
        check(genIntList) { values =>
          val tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          assertTrue(AVLTree.isBalanced(tree))
        }
      },
      
      test("inserting values maintains BST property") {
        check(genIntList) { values =>
          val tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          assertTrue(AVLTree.isBST(tree))
        }
      },
      
      test("inserting values maintains correct heights") {
        check(genIntList) { values =>
          val tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          assertTrue(AVLTree.checkHeights(tree))
        }
      },
      
      test("inserting values maintains correct sizes") {
        check(genIntList) { values =>
          val tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          assertTrue(AVLTree.checkSizes(tree))
        }
      },
      
      test("all inserted distinct values are contained in tree") {
        check(genIntList) { values =>
          val tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          val distinctValues = values.distinct
          assertTrue(distinctValues.forall(tree.contains(_)))
        }
      },
      
      test("tree size equals number of distinct inserted values") {
        check(genIntList) { values =>
          val tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          assertTrue(tree.size == values.distinct.size)
        }
      },
      
      test("inserting same value multiple times keeps it once") {
        check(genInt) { value =>
          val tree = AVLTree.insert(AVLTree.insert(AVLTree.empty[Int], value), value)
          assertTrue(tree.size == 1 && tree.contains(value))
        }
      },
      
      test("tree maintains sorted order") {
        check(genIntList) { values =>
          val tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          val treeList = tree.toList
          assertTrue(treeList == treeList.sorted)
        }
      }
    ),
    
    suite("Delete Operations")(
      test("deleting values maintains balanced tree") {
        check(genIntList, genIntList) { (insertValues, deleteValues) =>
          var tree = insertValues.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          tree = deleteValues.foldLeft(tree)((t, v) => AVLTree.delete(t, v))
          assertTrue(AVLTree.isBalanced(tree))
        }
      },
      
      test("deleting values maintains BST property") {
        check(genIntList, genIntList) { (insertValues, deleteValues) =>
          var tree = insertValues.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          tree = deleteValues.foldLeft(tree)((t, v) => AVLTree.delete(t, v))
          assertTrue(AVLTree.isBST(tree))
        }
      },
      
      test("deleting values maintains correct heights") {
        check(genIntList, genIntList) { (insertValues, deleteValues) =>
          var tree = insertValues.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          tree = deleteValues.foldLeft(tree)((t, v) => AVLTree.delete(t, v))
          assertTrue(AVLTree.checkHeights(tree))
        }
      },
      
      test("deleting values maintains correct sizes") {
        check(genIntList, genIntList) { (insertValues, deleteValues) =>
          var tree = insertValues.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          tree = deleteValues.foldLeft(tree)((t, v) => AVLTree.delete(t, v))
          assertTrue(AVLTree.checkSizes(tree))
        }
      },
      
      test("deleted values are not contained in tree") {
        check(genIntList) { values =>
          var tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          val toDelete = values.take(values.length / 2).distinct
          tree = toDelete.foldLeft(tree)((t, v) => AVLTree.delete(t, v))
          assertTrue(toDelete.forall(v => !tree.contains(v)))
        }
      },
      
      test("remaining values after delete are still in tree") {
        check(genIntList) { values =>
          val distinctValues = values.distinct
          var tree = distinctValues.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          val toDelete = distinctValues.take(distinctValues.length / 2).toSet
          tree = toDelete.foldLeft(tree)((t, v) => AVLTree.delete(t, v))
          val remaining = distinctValues.filterNot(toDelete.contains)
          assertTrue(remaining.forall(tree.contains(_)))
        }
      },
      
      test("deleting non-existent value doesn't change tree") {
        check(genIntList, genInt) { (values, toDelete) =>
          val tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          if (!tree.contains(toDelete)) {
            val treeAfterDelete = AVLTree.delete(tree, toDelete)
            assertTrue(tree.size == treeAfterDelete.size && tree.toList == treeAfterDelete.toList)
          } else {
            assertTrue(true)
          }
        }
      },
      
      test("deleting all values results in empty tree") {
        check(genIntList) { values =>
          var tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          val distinctValues = values.distinct
          tree = distinctValues.foldLeft(tree)((t, v) => AVLTree.delete(t, v))
          assertTrue(tree.size == 0 && tree == AVLTree.Empty)
        }
      },
      
      test("tree maintains sorted order after deletes") {
        check(genIntList, genIntList) { (insertValues, deleteValues) =>
          var tree = insertValues.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
          tree = deleteValues.foldLeft(tree)((t, v) => AVLTree.delete(t, v))
          val treeList = tree.toList
          assertTrue(treeList == treeList.sorted)
        }
      }
    ),
    
    suite("Mixed Insert and Delete Operations")(
      test("interleaved inserts and deletes maintain balance") {
        check(Gen.listOfBounded(0, 200)(Gen.either(genInt, genInt))) { operations =>
          val tree = operations.foldLeft(AVLTree.empty[Int]) { (t, op) =>
            op match {
              case Left(insert) => AVLTree.insert(t, insert)
              case Right(delete) => AVLTree.delete(t, delete)
            }
          }
          assertTrue(AVLTree.isBalanced(tree))
        }
      },
      
      test("interleaved inserts and deletes maintain BST property") {
        check(Gen.listOfBounded(0, 200)(Gen.either(genInt, genInt))) { operations =>
          val tree = operations.foldLeft(AVLTree.empty[Int]) { (t, op) =>
            op match {
              case Left(insert) => AVLTree.insert(t, insert)
              case Right(delete) => AVLTree.delete(t, delete)
            }
          }
          assertTrue(AVLTree.isBST(tree))
        }
      },
      
      test("interleaved inserts and deletes maintain correct heights") {
        check(Gen.listOfBounded(0, 200)(Gen.either(genInt, genInt))) { operations =>
          val tree = operations.foldLeft(AVLTree.empty[Int]) { (t, op) =>
            op match {
              case Left(insert) => AVLTree.insert(t, insert)
              case Right(delete) => AVLTree.delete(t, delete)
            }
          }
          assertTrue(AVLTree.checkHeights(tree))
        }
      },
      
      test("interleaved inserts and deletes maintain correct sizes") {
        check(Gen.listOfBounded(0, 200)(Gen.either(genInt, genInt))) { operations =>
          val tree = operations.foldLeft(AVLTree.empty[Int]) { (t, op) =>
            op match {
              case Left(insert) => AVLTree.insert(t, insert)
              case Right(delete) => AVLTree.delete(t, delete)
            }
          }
          assertTrue(AVLTree.checkSizes(tree))
        }
      }
    ),
    
    suite("Edge Cases")(
      test("empty tree is balanced") {
        assertTrue(AVLTree.isBalanced(AVLTree.empty[Int]))
      },
      
      test("empty tree is BST") {
        assertTrue(AVLTree.isBST(AVLTree.empty[Int]))
      },
      
      test("single element tree is balanced") {
        val tree = AVLTree.insert(AVLTree.empty[Int], 42)
        assertTrue(AVLTree.isBalanced(tree) && tree.height == 1 && tree.size == 1)
      },
      
      test("inserting sorted ascending values maintains balance") {
        val tree = (1 to 50).foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
        assertTrue(AVLTree.isBalanced(tree) && AVLTree.isBST(tree))
      },
      
      test("inserting sorted descending values maintains balance") {
        val tree = (50 to 1 by -1).foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
        assertTrue(AVLTree.isBalanced(tree) && AVLTree.isBST(tree))
      },
      
      test("height is logarithmic in size") {
        check(genIntList) { values =>
          if (values.isEmpty) {
            assertTrue(true)
          } else {
            val tree = values.foldLeft(AVLTree.empty[Int])((t, v) => AVLTree.insert(t, v))
            val size = tree.size
            val height = tree.height
            // AVL tree height is at most 1.44 * log2(n+2) - 0.328
            val maxHeight = math.ceil(1.44 * (math.log(size + 2) / math.log(2)) + 1).toInt
            assertTrue(height <= maxHeight)
          }
        }
      }
    )
  )
}
