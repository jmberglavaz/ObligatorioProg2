package um.edu.uy.TADs.Implementations;

import um.edu.uy.Exceptions.EmptyTreeException;
import um.edu.uy.Exceptions.FullNodoException;
import um.edu.uy.Exceptions.KeyNotFoundException;
import um.edu.uy.Exceptions.ParentNotFoundException;
import um.edu.uy.TADs.Interfaces.MyBinaryTree;
import um.edu.uy.TADs.Interfaces.MyList;

public class MyTreeImpl<K,T> implements MyBinaryTree<K,T> {
    private SimpleBinaryNode<K,T> root;

    public MyTreeImpl() {
        this.root = null;
    }

    @Override
    public T find(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        SimpleBinaryNode<K,T> result = findNode(key);
        return (result != null) ? result.getData() : null;
    }

    private SimpleBinaryNode<K, T> findNode(K key) {
        if (root == null) {
            return null;
        }
        return recursiveFind(key, this.root);
    }

    private SimpleBinaryNode<K,T> recursiveFind(K key, SimpleBinaryNode<K,T> currentNodo) {
        if (currentNodo == null) {
            return null;
        }

        if (currentNodo.getKey().equals(key)) {
            return currentNodo;
        }

        SimpleBinaryNode<K,T> leftResult = recursiveFind(key, currentNodo.getLeftChild());
        if (leftResult != null) {
            return leftResult;
        }

        return recursiveFind(key, currentNodo.getRightChild());
    }

    private SimpleBinaryNode<K,T> findParent(SimpleBinaryNode<K,T> currentNode, K key) {
        if (currentNode == null) {
            return null;
        }

        if ((currentNode.getLeftChild() != null && currentNode.getLeftChild().getKey().equals(key)) ||
                (currentNode.getRightChild() != null && currentNode.getRightChild().getKey().equals(key))) {
            return currentNode;
        }

        SimpleBinaryNode<K,T> result = findParent(currentNode.getLeftChild(), key);
        if (result != null) {
            return result;
        }

        return findParent(currentNode.getRightChild(), key);
    }

    @Override
    public void insert(K key, T data, K parentKey) {
        if (key == null || data == null) {
            throw new IllegalArgumentException("Key and data cannot be null");
        }

        if (this.root == null) {
            if (parentKey != null) {
                throw new ParentNotFoundException();
            }
            this.root = new SimpleBinaryNode<>(key, data);
        } else {
            if (parentKey == null) {
                throw new IllegalArgumentException("Parent key cannot be null when tree is not empty");
            }
            if (!recursiveInsert(key, data, parentKey, this.root)) {
                throw new ParentNotFoundException();
            }
        }
    }

    private boolean recursiveInsert(K key, T data, K parentKey, SimpleBinaryNode<K,T> currentNodo) {
        if (currentNodo.getKey().equals(parentKey)) {
            if (currentNodo.getLeftChild() == null) {
                currentNodo.setLeftChild(new SimpleBinaryNode<>(key, data));
                return true;
            } else if (currentNodo.getRightChild() == null) {
                currentNodo.setRightChild(new SimpleBinaryNode<>(key, data));
                return true;
            } else {
                throw new FullNodoException();
            }
        }

        boolean inserted = false;
        if (currentNodo.getLeftChild() != null) {
            inserted = recursiveInsert(key, data, parentKey, currentNodo.getLeftChild());
            if (inserted) {
                return true;
            }
        }

        if (currentNodo.getRightChild() != null) {
            inserted = recursiveInsert(key, data, parentKey, currentNodo.getRightChild());
        }

        return inserted;
    }

    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key no valid");
        }

        if (this.root == null) {
            throw new EmptyTreeException();
        }

        SimpleBinaryNode<K,T> nodeToDelete = findNode(key);
        if (nodeToDelete == null) {
            throw new KeyNotFoundException();
        }

        // Caso especial: eliminar la raíz
        if (this.root.getKey().equals(key)) {
            deleteRoot();
            return;
        }

        SimpleBinaryNode<K,T> parent = findParent(this.root, key);
        deleteNodeWithParent(nodeToDelete, parent);
    }

    private void deleteRoot() {
        if (this.root.getLeftChild() == null && this.root.getRightChild() == null) { //Si la raiz es el unico elemento
            this.root = null;
        } else if (this.root.getLeftChild() == null) { //Solo tiene hijo derecho
            this.root = this.root.getRightChild();
        } else if (this.root.getRightChild() == null) {
            this.root = this.root.getLeftChild();// Solo tiene hijo izquierdo
        } else {
            SimpleBinaryNode<K,T> leaf = findAnyLeaf(this.root);
            SimpleBinaryNode<K,T> leafParent = findParent(this.root, leaf.getKey());

            // Reemplazar datos de la raíz con los de la hoja
            this.root.setKey(leaf.getKey());
            this.root.setData(leaf.getData());

            // Eliminar la hoja (que no tiene hijos)
            if (leafParent.getLeftChild() == leaf) {
                leafParent.setLeftChild(null);
            } else {
                leafParent.setRightChild(null);
            }
        }
    }

    private void deleteNodeWithParent(SimpleBinaryNode<K,T> nodeToDelete, SimpleBinaryNode<K,T> parent) {
        if (nodeToDelete.getLeftChild() == null && nodeToDelete.getRightChild() == null) {
            // Nodo es hoja - simplemente eliminarlo
            if (parent.getLeftChild() == nodeToDelete) {
                parent.setLeftChild(null);
            } else {
                parent.setRightChild(null);
            }
        } else if (nodeToDelete.getLeftChild() == null) {
            // Solo tiene hijo derecho - reemplazar con ese hijo
            if (parent.getLeftChild() == nodeToDelete) {
                parent.setLeftChild(nodeToDelete.getRightChild());
            } else {
                parent.setRightChild(nodeToDelete.getRightChild());
            }
        } else if (nodeToDelete.getRightChild() == null) {
            // Solo tiene hijo izquierdo - reemplazar con ese hijo
            if (parent.getLeftChild() == nodeToDelete) {
                parent.setLeftChild(nodeToDelete.getLeftChild());
            } else {
                parent.setRightChild(nodeToDelete.getLeftChild());
            }
        } else {
            // Tiene ambos hijos - reemplazar con cualquier hoja
            SimpleBinaryNode<K,T> leaf = findAnyLeaf(nodeToDelete);
            SimpleBinaryNode<K,T> leafParent = findParent(this.root, leaf.getKey());

            // Reemplazar datos del nodo con los de la hoja
            nodeToDelete.setKey(leaf.getKey());
            nodeToDelete.setData(leaf.getData());

            // Eliminar la hoja
            if (leafParent.getLeftChild() == leaf) {
                leafParent.setLeftChild(null);
            } else {
                leafParent.setRightChild(null);
            }
        }
    }

    private SimpleBinaryNode<K,T> findAnyLeaf(SimpleBinaryNode<K,T> currentNode) {
        if (currentNode == null) {
            return null;
        }

        // Si es hoja, la retornamos
        if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) {
            return currentNode;
        }

        //Seguir la busqueda por el sub arbol izquierdo
        if (currentNode.getLeftChild() != null) {
            SimpleBinaryNode<K,T> leftLeaf = findAnyLeaf(currentNode.getLeftChild());
            if (leftLeaf != null) {
                return leftLeaf;
            }
        }

        //Seguir la busqueda por el sub arbol derecho si no se encontro en el izquierdo
        if (currentNode.getRightChild() != null) {
            return findAnyLeaf(currentNode.getRightChild());
        }

        return null;
    }

    public int size() {
        if (this.root == null) {
            return 0;
        }
        return recursiveSize(this.root);
    }

    private int recursiveSize(SimpleBinaryNode<K,T> currentNodo) {
        if (currentNodo == null) {
            return 0;
        }
        return 1 + recursiveSize(currentNodo.getLeftChild()) + recursiveSize(currentNodo.getRightChild());
    }

    public int countLeaf() {
        if (this.root == null) {
            return 0;
        }
        return recursiveCountLeaf(this.root);
    }

    private int recursiveCountLeaf(SimpleBinaryNode<K,T> currentNodo) {
        if (currentNodo == null) {
            return 0;
        }

        if (currentNodo.getLeftChild() == null && currentNodo.getRightChild() == null) {
            return 1;
        }

        return recursiveCountLeaf(currentNodo.getLeftChild()) + recursiveCountLeaf(currentNodo.getRightChild());
    }

    public int countCompleteElements() {
        if (this.root == null) {
            return 0;
        }
        return recursiveCountCompleteElements(this.root);
    }

    private int recursiveCountCompleteElements(SimpleBinaryNode<K,T> currentNodo) {
        if (currentNodo == null) {
            return 0;
        }

        int count = 0;
        if (currentNodo.getLeftChild() != null && currentNodo.getRightChild() != null) {
            count = 1;
        }

        return count + recursiveCountCompleteElements(currentNodo.getLeftChild()) +
                recursiveCountCompleteElements(currentNodo.getRightChild());
    }

    public MyList<K> inOrder() {
        MyList<K> result = new MyArrayListImpl<>(10);
        if (this.root != null) {
            recursiveInOrder(this.root, result);
        }
        return result;
    }

    private void recursiveInOrder(SimpleBinaryNode<K,T> currentNode, MyList<K> result) {
        if (currentNode == null) {
            return;
        }

        recursiveInOrder(currentNode.getLeftChild(), result);
        result.add(currentNode.getKey());
        recursiveInOrder(currentNode.getRightChild(), result);
    }

    public MyList<K> preOrder() {
        MyList<K> result = new MyArrayListImpl<>(10);
        if (this.root != null) {
            recursivePreOrder(this.root, result);
        }
        return result;
    }

    private void recursivePreOrder(SimpleBinaryNode<K,T> currentNode, MyList<K> result) {
        if (currentNode == null) {
            return;
        }

        result.add(currentNode.getKey());
        recursivePreOrder(currentNode.getLeftChild(), result);
        recursivePreOrder(currentNode.getRightChild(), result);
    }

    public MyList<K> postOrder() {
        MyList<K> result = new MyArrayListImpl<>(10);
        if (this.root != null) {
            recursivePostOrder(this.root, result);
        }
        return result;
    }

    private void recursivePostOrder(SimpleBinaryNode<K,T> currentNode, MyList<K> result) {
        if (currentNode == null) {
            return;
        }

        recursivePostOrder(currentNode.getLeftChild(), result);
        recursivePostOrder(currentNode.getRightChild(), result);
        result.add(currentNode.getKey());
    }

    public MyList<K> porNivel() {
        return null;
    }

    public MyList<T> keyToValueList(MyList<K> keysList) {
        if (keysList == null) {
            throw new IllegalArgumentException("Keys list cannot be null");
        }

        MyList<T> result = new MyArrayListImpl<>(10);
        for (int key = 0 ; key < keysList.size() ; key++) {
            T value = find(keysList.get(key));
            if (value != null) {
                result.add(value);
            }
        }
        return result;
    }
}