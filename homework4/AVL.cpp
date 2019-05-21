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
    if (!element.compare(l->getElement())) {
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
    if (!element.compare(r->getElement())) {
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


int main() {
    Node left("left child", nullptr, nullptr, nullptr);
    Node right("right child", nullptr, nullptr, nullptr);
    Node father("father", nullptr, &left, &right);
    Node leftleft("leftleft", nullptr, nullptr, nullptr);
    Node leftleftleft("leftleftleft", &leftleft, nullptr, nullptr);
    left.setParent(&father);
    left.setLeft(&leftleft);
    leftleft.setLeft(&leftleftleft);
    right.setParent(&father);
    left.updateHeight();
    leftleft.updateHeight();

    Iterator it(&father);
    Iterator it2(it);

    std::cout << *it << '\n';
    it2++;
    std::cout << *it2 << '\n';
}
