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

### Basic Operations

In this part, there are several basic operations needed during **Insert**, **Insert**, **Delete**, and **Search**.

#### Get Height

```java
private int getHeight(AVLTreeNode root)
```

#### Update Height

```java
private void updateHeight(AVLTreeNode root)
```

#### Get Balance Factor

```java
private int getBalanceFactor(AVLTreeNode root)
```

#### Right Rotation

```java
private AVLTreeNode rightRotate(AVLTreeNode root)
```

#### Left Rotation

```java
private AVLTreeNode leftRotate(AVLTreeNode root)
```

#### Keep a Node Balance

```java
private AVLTreeNode keepBalance(AVLTreeNode root)
```

### Insert

### Delete

### Search

