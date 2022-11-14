# Assignment 1

## Basic Information

Name: Yingjie Chen

UFID: 8398-1600

UF Email account: yingjie.chen@ufl.edu

## Function Prototypes

The program functions mainly include 4 parts: **Basic Operations**, **Insert**, **Insert**, **Delete**, and **Search**.

### Node Structure

Including 4 elements:

- `key` for store value
- `height` of current tree whose root is current node
- Two subtrees references.

```java
class AVLTreeNode {
    int key;
    int height;
    AVLTreeNode left;
    AVLTreeNode right;
}
```

Before operations of following parts, method below will be called firstly:

```java
public void initialize()
```

### Encapsulated Class

```java
class SelfBalancedBinarySearchTree {

    private AVLTreeNode root;
    private final String outputFilename = "output_file.txt";

    /**
     * initial operation about creating output file
     */
    public void initialize() {
        ...
    }

    /**
     * Insert Operation
     */

    /**
     * Given a key, Insert it into AVL Tree (search + insert + re-balance)
     */
    public void insert(int key) {
        ...
    }

    /**
     * Delete Operation
     */

    /**
     * Given a key, Delete it from AVL Tree (search + delete + re-balance)
     */
    public void delete(int key) {
        ...
    }

    /**
     * Query Operations
     */

    /**
     * Query Operations for Specific Key
     */
    public String search(int key) {
        ...
    }

    /**
     * Query Operations for Keys in Specific Range
     */
    public String search(int key1, int key2) {
        ...
    }
    
    /**
     * Other related operations
     */
    ...
}
```

### Basic Operations

In this part, there are several basic operations needed during **Insert**, **Insert**, **Delete**, and **Search**.

#### Get Height

Given a node of AVL tree, and Get its height

```java
private int getHeight(AVLTreeNode root)
```

#### Update Height

Given a node of AVL tree, and Update its height to the maximum value between the height of left subtree and the height of right subtree

```java
private void updateHeight(AVLTreeNode root)
```

#### Get Balance Factor

Given a node of AVL tree, and calculate its balance factor by letting the height of left subtree minus the height of right subtree.

```java
private int getBalanceFactor(AVLTreeNode root)
```

#### Right Rotation

Right rotation of a node:

1. Left child becomes the new root
2. The right child of the left child of the root will become the left child of the root.
3. The root will become the right child of the left child of the root.

```java
private AVLTreeNode rightRotate(AVLTreeNode root)
```

#### Left Rotation

Left rotation of a node
1. Right child becomes the new root
2. The left child of the right child of the root will become the right child of the root.
3. The root will become the left child of the right child of the root.

```java
private AVLTreeNode leftRotate(AVLTreeNode root)
```

#### Keep a Node Balance

Keep a node balance.

By examining the balance factor of the given node, the balance factor of the left node of the given node, and the balance factor of the right node of the given node to decide to choose which one operation including LL, LR, RR, and RL

```java
private AVLTreeNode keepBalance(AVLTreeNode root)
```

### Insert

This part includes operation about insertion.

Firstly, find the target external node and insert as a new lead. Secondly, re-balance the whole trees.

```java
public void insert(int key)
```

The method above will call the recursive method below to insert a new node.

```java
private AVLTreeNode insert(AVLTreeNode root, int key)
```

### Delete

This part includes operation about deletion.

Firstly, find the target node and delete it. Secondly, re-balance the whole trees.

There will tree conditions:

1. The target node has no child, just delete it directly and re-balance.
2. The target node has one child, return the child to the parent of the target node, and re-balance.
3. The target node has two children, need to find the successor node, use this successor to replace the target node, delete the successor, re-balance.

```java
public void delete(int key)
```

The method above will call the recursive method below to delete a node having the `key`.

```java
private AVLTreeNode delete(AVLTreeNode root, int key)
```

### Search

#### Search Specific Value

Using binary search to search the specific key.

```java
public String search(int key)
```

The method above will call the recursive method below to search a node having the `key`.

```java
private AVLTreeNode search(AVLTreeNode root, int key)
```

#### Search Values in a Range

Using binary search to search keys in the specific range.

```java
public String search(int key1, int key2)
```

The method above will call the recursive method below to search nodes having the `key` in a range.

```java
private void search(AVLTreeNode root, int key1, int key2, List<Integer> list)
```

## How to Run

### File structure

All files will be in the folder.

```
.
├── avltree.java
├── input_file.txt
├── Makefile
└── REPORT.pdf
```

#### Source Code

- `avltree.java` is the entering point running the operation of `AVLTree`. This file includes the source code of `AVLTree`

  ```java
  public class avltree {
      ...
  }
  
  /**
   * The Implementation of AVL Tree
   */
  class SelfBalancedBinarySearchTree {
      ...
  }
  
  /**
   * AVL Tree Node
   * includes:
   * 1. key, the stored value
   * 2. height, the height of current tree whose root is current node
   * 3. left & right, left and right reference of left and right subtrees
   */
  class AVLTreeNode {
      ...
  }
  ```

### Input File Preparation

- Create a file including operations

  For an example, create a file named `input_file.txt` whose content is below

  ```
  Initialize()
  Insert(21)
  Insert(108)
  Insert(5)
  Insert(1897)
  Insert(4325)
  Delete(108)
  Search(1897)
  Insert(102)
  Insert(65)
  Delete(102)
  Delete(21)
  Insert(106)
  Insert(23)
  Search(23,99)
  Insert(32)
  Insert(220)
  Search(33)
  Search(21)
  Delete(4325)
  Search(32)
  ```

- Copy the file into the folder `AVLTree`

### Running

- Go into the folder `AVLTree`

- Execute `make` command to compile the files.
- Execute `java avltree <filename>` to run.
  - `<filename>` is the input file created.
- There will be an output file named `output_file.txt` generated.

### Output

All the output information will be printed in a output file name `output_file.txt` in the folder.

For an example:

```
1897
23,65
NULL
NULL
32
```

