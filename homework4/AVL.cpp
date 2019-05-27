#include "AVL.hpp"

Node::Node(const string& e, Node *parent, Node *left, Node *right) {
    this->element = e;
    this->parent = parent;
    this->left = left;
    this->right = right;
    height = 1;
}

/* Get the parent of the node */
Node *Node::getParent() const {
    return parent;
}

/* Get the left child of the node */
Node *Node::getLeft() const {
    return left;
}

/* Get the right child of the node */
Node *Node::getRight() const {
    return right;
}

/* Get the element of the node */
string Node::getElement() const {
    return element;
}

/* Get the height of the node */
int Node::getHeight() const {
    return height;
}

/* Set the element of the node */
void Node::setElement(string s) {
    element = s;
}

/* Set the parent of the node */
void Node::setParent(Node *parent) {
    this->parent = parent;
}

/* Set the left child of the node */
void Node::setLeft(Node *left) {
    this->left = left;
}

/* Set the right child of the node */
void Node::setRight(Node *right) {
    this->right = right;
}

/* Check if the node is the left child of the parent */
bool Node::isLeft() const {
    // is the root
    if (parent == nullptr) {
        return false;
    }

    Node *l = parent->getLeft();
    if (l == this) {
        return true;
    }
    return false;
}

/* Check if the node is the right child of the parent */
bool Node::isRight() const {
    if (parent == nullptr) {
        return false;
    }

    Node *r = parent->getRight();
    if (r == this) {
        return true;
    }
    return false;
}

/* Get the height of the left child */
int Node::leftChildHeight() const {
    if (left == nullptr) {
        return 0;
    }

    return left->getHeight();
}

/* Get the height of the left child */
int Node::rightChildHeight() const {
    if (right == nullptr) {
        return 0;
    }
    return right->getHeight();
}

/* Update the height of the node */
int Node::updateHeight() {
    int lHeight = leftChildHeight();
    int rHeight = rightChildHeight();

    /* new height is the max between right and left child + 1 */
    if (lHeight > rHeight) {
        height = lHeight + 1;
    }
    else {
        height = rHeight + 1;
    }
    return height;
}

/* Checks if the node is balanced */
bool Node::isBalanced() {
    int balance = leftChildHeight() - rightChildHeight();
    if (balance > 1 || balance < -1) {
        return false;
    }
    return true;
}

Iterator::Iterator(Node *root) {
    preOrder(root);
}

Iterator::Iterator(Iterator &it) {
    this->preOrderedQueue = it.getQueue();
}

/* Push the nodes of the tree into queue with pre order */
void Iterator::preOrder(Node *root) {
    stack<Node *> nodeStack;
    nodeStack.push(root);

    while(nodeStack.empty() == false) {
        // remove the first item from the stack
        Node *cur = nodeStack.top();
        nodeStack.pop();

        // add the curr to the queue (preOrder)
        preOrderedQueue.push(cur);

        // push right and left (strickly in this order) child into the stack
        if (cur->getRight() != nullptr) {
            nodeStack.push(cur->getRight());
        }

        if (cur->getLeft() != nullptr) {
            nodeStack.push(cur->getLeft());
        }
    }

}

/* Returns the pre-ordered queue */
queue<Node *> Iterator::getQueue() {
    return preOrderedQueue;
}

/* Increases the iterator by one */
Iterator& Iterator::operator++() {
    if (this->hasNext()) {
        this->skip();
    }
    return *this;
}

/* Increases the iterator by one and return the previous one */
Iterator Iterator::operator++(int a) {
    Iterator prevIt(*this);
    if (this->hasNext()) {
        this->skip();
    }
    return prevIt;
}

/* Returns the element of a node */
string Iterator::operator*() {
    return this->preOrderedQueue.front()->getElement();
}

/* Checks if the two iterators point at the different elements */
bool Iterator::operator!=(Iterator it) {
    if ((this->preOrderedQueue.front() != it.preOrderedQueue.front())) {
        return true;
    }
    return false;
}

/* Checks if the two iterators point at the same element */
bool Iterator::operator==(Iterator it) {
    if ((this->preOrderedQueue.front() == it.preOrderedQueue.front())) {
        return true;
    }
    return false;
}

/* Checks if any node left in pre ordered queue */
bool Iterator::hasNext() {
    if (this->preOrderedQueue.empty()) {
        return false;
    }
    return true;
}

/* Skips the next node from the pre ordered queue */
void Iterator::skip() {
    this->preOrderedQueue.pop();
}

AVL::AVL() {
    size = 0;
    root = nullptr;
};

Node* AVL::getRoot() {
    return root;
}

bool AVL::add(string s) {
    Node* v = insert(root, nullptr, s);
    if (v == nullptr) {
        return false;
    }
    rebalance(v);
    return true;
}

/* Insert a node to the tree recursively */
Node* AVL::insert(Node *current, Node *previous, string s) {
    int compareResult;
    Node *newNode;

    // there is no node with element s, insert it to the tree
    if (current == nullptr) {
        if (root == nullptr) { // empty tree
            newNode = new Node(s, nullptr, nullptr, nullptr);
            root = newNode;
        }
        else {
            // allocation the new node and insert it as a leaf
            compareResult = previous->getElement().compare(s);
            newNode = new Node(s, previous, nullptr, nullptr);
            if (compareResult > 0) { // as a right child
                previous->setLeft(newNode);
            }
            else { // as a left child
                previous->setRight(newNode);
            }
        }
        return newNode;
    }

    /* search if the node with element s exists in the tree */
    compareResult = current->getElement().compare(s);
    if (compareResult == 0) {
        return nullptr;
    }
    else if (compareResult > 0) {
        return this->insert(current->getLeft(), current, s);
    }
    else {
        return this->insert(current->getRight(), current, s);
    }
}

/* Search for the string s in the AVL tree recursively */
Node* AVL::search(Node *current, string s) {
    int compareResult;
    /* doesn't exists in the current tree */
    if (current == nullptr) {
        return nullptr;
    }

    compareResult = current->getElement().compare(s);

    /* found the node with the elemene s */
    if (compareResult == 0) {
        return current;
    } /* search left subtree */
    else if (compareResult > 0) {
        return this->search(current->getLeft(), s);
    } /* search right subtree */
    else {
        return this->search(current->getRight(), s);
    }
}

/* Get the node which will participate in the rebalancing */
Node* AVL::rebalanceNode(Node* v) {
    if (v->leftChildHeight() > v->rightChildHeight()) {
        return v->getLeft();
    }
    else if (v->leftChildHeight() < v->rightChildHeight()) {
        return v->getRight();
    }
    else if (v->isLeft()) {
        return v->getLeft();
    }
    else {
        return v->getRight();
    }
}

/* Apply a single right rotation */
Node* AVL::singleRightRotation(Node *v, Node* w, Node* u) {
    // first update the parents of the nodes
    if (v != root) {
        if (v->isLeft()) {
            v->getParent()->setLeft(w);
        }
        else {
            v->getParent()->setRight(w);
        }
        w->setParent(v->getParent());
    }

    // apply rebalance
    v->setLeft(w->getRight());
    if (w->getRight() != nullptr) {
        w->getRight()->setParent(v);
    }
    w->setRight(v);
    v->setParent(w);

    // update the root of the tree
    if (v == root) {
        root = w;
        w->setParent(nullptr);
    }
    return w;
}

Node *AVL::singleLeftRotation(Node *v, Node *w, Node *u) {
    if (v != root) {
        if (v->isRight()) {
            v->getParent()->setRight(w);
        }
        else {
            v->getParent()->setLeft(w);
        }
        w->setParent(v->getParent());
    }

    v->setRight(w->getLeft());
    if (w->getLeft() != nullptr) {
        w->getLeft()->setParent(v);
    }
    w->setLeft(v);
    v->setParent(w);
    if (v == root) {
        root = w;
        w->setParent(nullptr);
    }
    return w;
}

void AVL::rebalance(Node *v) {
    Node *u=nullptr;
    Node *w=nullptr;

    while(v != nullptr) { // iterate throught the whole tree
        v->updateHeight();
        if (!v->isBalanced()) {
            w = rebalanceNode(v);
            u = rebalanceNode(w);

            if (w->isLeft() && u->isLeft()) { // single right rotation
                v = singleRightRotation(v, w, u);
            }
            else if (w->isRight() && w->isRight()) { // left single rotation
                v = singleLeftRotation(v, w, u);
            }
        }
        v = v->getParent();
    }
}

/* Checks if the AVL tree has a node with element s */
bool AVL::contains(string s) {
    if (this->search(root, s) == nullptr) {
        return false;
    }
    return true;
}

Iterator AVL::begin() const {
    Iterator *it = new Iterator(root);
    return *it;
}

int main() {
    AVL tree;
    string a("11");
    string b("10");
    string c("12");
    string d("13");
    string e("14");

    tree.add(a);
    tree.add(b);
    tree.add(c);
    tree.add(d);
    tree.add(e);
    Iterator it(tree.getRoot());


    cout << *it << endl;
    it++;
    cout << *it << endl;
    it++;
    cout << *it << endl;
    it++;
    cout << *it << endl;
    it++;
    cout << *it << endl;
};
