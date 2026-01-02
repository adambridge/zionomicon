import scala.annotation.tailrec

sealed trait AVLTree[+A] {
  def height: Int
  def size: Int
  def contains[A1 >: A](value: A1)(implicit ord: Ordering[A1]): Boolean
  def toList: List[A]
}

object AVLTree {
  case object Empty extends AVLTree[Nothing] {
    val height: Int = 0
    val size: Int = 0
    def contains[A](value: A)(implicit ord: Ordering[A]): Boolean = false
    def toList: List[Nothing] = List.empty
  }

  case class Node[A](
    value: A,
    left: AVLTree[A],
    right: AVLTree[A],
    height: Int,
    size: Int
  ) extends AVLTree[A] {
    def contains[A1 >: A](v: A1)(implicit ord: Ordering[A1]): Boolean = {
      val cmp = ord.compare(v, value)
      if (cmp == 0) true
      else if (cmp < 0) left.contains(v)
      else right.contains(v)
    }

    def toList: List[A] = left.toList ++ List(value) ++ right.toList
  }

  def empty[A]: AVLTree[A] = Empty

  def apply[A](values: A*)(implicit ord: Ordering[A]): AVLTree[A] =
    values.foldLeft(empty[A])((tree, value) => insert(tree, value))

  private def getHeight[A](tree: AVLTree[A]): Int = tree.height

  private def getSize[A](tree: AVLTree[A]): Int = tree.size

  private def balanceFactor[A](tree: AVLTree[A]): Int = tree match {
    case Empty => 0
    case Node(_, left, right, _, _) => getHeight(left) - getHeight(right)
  }

  private def makeNode[A](value: A, left: AVLTree[A], right: AVLTree[A]): Node[A] = {
    val h = 1 + math.max(getHeight(left), getHeight(right))
    val s = 1 + getSize(left) + getSize(right)
    Node(value, left, right, h, s)
  }

  private def rotateRight[A](tree: AVLTree[A]): AVLTree[A] = tree match {
    case Node(y, Node(x, xl, xr, _, _), yr, _, _) =>
      makeNode(x, xl, makeNode(y, xr, yr))
    case _ => tree
  }

  private def rotateLeft[A](tree: AVLTree[A]): AVLTree[A] = tree match {
    case Node(x, xl, Node(y, yl, yr, _, _), _, _) =>
      makeNode(y, makeNode(x, xl, yl), yr)
    case _ => tree
  }

  private def balance[A](tree: AVLTree[A]): AVLTree[A] = {
    val bf = balanceFactor(tree)
    
    if (bf > 1) {
      tree match {
        case Node(v, left, right, _, _) =>
          if (balanceFactor(left) < 0) {
            val newLeft = rotateLeft(left)
            rotateRight(makeNode(v, newLeft, right))
          } else {
            rotateRight(tree)
          }
        case _ => tree
      }
    } else if (bf < -1) {
      tree match {
        case Node(v, left, right, _, _) =>
          if (balanceFactor(right) > 0) {
            val newRight = rotateRight(right)
            rotateLeft(makeNode(v, left, newRight))
          } else {
            rotateLeft(tree)
          }
        case _ => tree
      }
    } else {
      tree
    }
  }

  def insert[A](tree: AVLTree[A], value: A)(implicit ord: Ordering[A]): AVLTree[A] = {
    tree match {
      case Empty => makeNode(value, Empty, Empty)
      case Node(v, left, right, _, _) =>
        val cmp = ord.compare(value, v)
        if (cmp < 0) {
          balance(makeNode(v, insert(left, value), right))
        } else if (cmp > 0) {
          balance(makeNode(v, left, insert(right, value)))
        } else {
          tree // Value already exists
        }
    }
  }

  @tailrec
  private def findMin[A](tree: AVLTree[A]): Option[A] = tree match {
    case Empty => None
    case Node(v, Empty, _, _, _) => Some(v)
    case Node(_, left, _, _, _) => findMin(left)
  }

  def delete[A](tree: AVLTree[A], value: A)(implicit ord: Ordering[A]): AVLTree[A] = {
    tree match {
      case Empty => Empty
      case Node(v, left, right, _, _) =>
        val cmp = ord.compare(value, v)
        if (cmp < 0) {
          balance(makeNode(v, delete(left, value), right))
        } else if (cmp > 0) {
          balance(makeNode(v, left, delete(right, value)))
        } else {
          // Found the node to delete
          (left, right) match {
            case (Empty, Empty) => Empty
            case (Empty, _) => right
            case (_, Empty) => left
            case (_, _) =>
              findMin(right) match {
                case Some(minRight) =>
                  balance(makeNode(minRight, left, delete(right, minRight)))
                case None => left
              }
          }
        }
    }
  }

  def isBalanced[A](tree: AVLTree[A]): Boolean = tree match {
    case Empty => true
    case Node(_, left, right, _, _) =>
      val bf = math.abs(balanceFactor(tree))
      bf <= 1 && isBalanced(left) && isBalanced(right)
  }

  def isBST[A](tree: AVLTree[A])(implicit ord: Ordering[A]): Boolean = {
    def isBSTHelper(tree: AVLTree[A], min: Option[A], max: Option[A]): Boolean = tree match {
      case Empty => true
      case Node(v, left, right, _, _) =>
        val minOk = min.forall(m => ord.compare(v, m) > 0)
        val maxOk = max.forall(m => ord.compare(v, m) < 0)
        minOk && maxOk && 
          isBSTHelper(left, min, Some(v)) && 
          isBSTHelper(right, Some(v), max)
    }
    isBSTHelper(tree, None, None)
  }

  def checkHeights[A](tree: AVLTree[A]): Boolean = tree match {
    case Empty => true
    case Node(_, left, right, h, _) =>
      val expectedHeight = 1 + math.max(getHeight(left), getHeight(right))
      h == expectedHeight && checkHeights(left) && checkHeights(right)
  }

  def checkSizes[A](tree: AVLTree[A]): Boolean = tree match {
    case Empty => true
    case Node(_, left, right, _, s) =>
      val expectedSize = 1 + getSize(left) + getSize(right)
      s == expectedSize && checkSizes(left) && checkSizes(right)
  }
}
