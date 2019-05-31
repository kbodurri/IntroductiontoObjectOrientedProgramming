/*
 * AVL trees functionality (insert, delete, search)
 *
 * Contributors: Klajdi Bodurri && Eirini Tsitsopoulou.
*/
#include "AVL.hpp"

/* Constructor */
AVL::Node::Node(const string& e, AVL::Node *parent, AVL::Node *left, AVL::Node *right) {
    this->element = e;
    this->parent = parent;
    this->left = left;
    this->right = right;
    height = 1;
}

/* Get the parent of the node */
AVL::Node *AVL::Node::getParent() const {
    return parent;
}

/* Get the left child of the node */
AVL::Node *AVL::Node::getLeft() const {
    return left;
}

/* Get the right child of the node */
AVL::Node *AVL::Node::getRight() const {
    return right;
}

/* Get the element of the node */
string AVL::Node::getElement() const {
    return element;
}

/* Get the height of the node */
int AVL::Node::getHeight() const {
    return height;
}

/* Set the element of the node */
void AVL::Node::setElement(string s) {
    element = s;
}

/* Set the parent of the node */
void AVL::Node::setParent(AVL::Node *parent) {
    this->parent = parent;
}

/* Set the left child of the node */
void AVL::Node::setLeft(AVL::Node *left) {
    this->left = left;
}

/* Set the right child of the node */
void AVL::Node::setRight(AVL::Node *right) {
    this->right = right;
}

/* Check if the node is the left child of the parent */
bool AVL::Node::isLeft() const {
    // is the root
    if (parent == nullptr) {
        return false;
    }

    AVL::Node *l = parent->getLeft();
    if (l == this) {
        return true;
    }
    return false;
}

/* Check if the node is the right child of the parent */
bool AVL::Node::isRight() const {
    if (parent == nullptr) {
        return false;
    }

    AVL::Node *r = parent->getRight();
    if (r == this) {
        return true;
    }
    return false;
}

/* Get the height of the left child */
int AVL::Node::leftChildHeight() const {
    if (left == nullptr) {
        return 0;
    }

    return left->getHeight();
}

/* Get the height of the left child */
int AVL::Node::rightChildHeight() const {
    if (right == nullptr) {
        return 0;
    }
    return right->getHeight();
}

/* Update the height of the node */
int AVL::Node::updateHeight() {
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
bool AVL::Node::isBalanced() {
    int balance = leftChildHeight() - rightChildHeight();
    if (balance > 1 || balance < -1) {
        return false;
    }
    return true;
}

/*
 * Iterator class, used for AVL trees.
 *
 * Contributors: Klajdi Bodurri && Eirini Tsitsopoulou.
*/

/* Default Constructor */
AVL::Iterator::Iterator() {
    current = nullptr;
}

/* Constructor */
AVL::Iterator::Iterator(AVL::Node *root) {
    current = nullptr;
    if (root != nullptr) {
        nodeStack.push(root);
        next();
    }
}

/* Push the nodes of the tree into stack */
void AVL::Iterator::next() {
    if(hasNext()) {
        // remove the first item from the stack
        current = nodeStack.top();
        nodeStack.pop();

        // push right and left (strickly in this order) child into the stack
        if (current->getRight() != nullptr) {
            nodeStack.push(current->getRight());
        }

        if (current->getLeft() != nullptr) {
            nodeStack.push(current->getLeft());
        }
    }
    else {
        current = nullptr;
    }
}

/* Checks if any node left in pre ordered queue */
bool AVL::Iterator::hasNext() {
    return !nodeStack.empty();
}

/* Returns the stack of unvisited nodes*/
stack<AVL::Node *> AVL::Iterator::getStack() const {
    return nodeStack;
}

AVL::Node* AVL::Iterator::getCurrent() const {
    return current;
}

/* Increases the iterator by one */
AVL::Iterator& AVL::Iterator::operator++() {
    next();
    return *this;
}

/* Increases the iterator by one and return the previous one */
AVL::Iterator AVL::Iterator::operator++(int a) {
    AVL::Iterator prevIt(*this);
    next();
    return prevIt;
}

/* Returns the element of a node */
string AVL::Iterator::operator*() {
    return current->getElement();
}

/* Checks if the two iterators point at the different elements */
bool AVL::Iterator::operator!=(AVL::Iterator it) {
    if (hasNext() == it.hasNext() && current == nullptr && it.getCurrent() == nullptr) {
        return false;
    }

    if (current != it.getCurrent()) {
        return true;
    }
    return false;
}

/* Checks if the two iterators point at the same element */
bool AVL::Iterator::operator==(AVL::Iterator it) {
    if (hasNext() == it.hasNext() && current == nullptr && it.getCurrent() == nullptr) {
        return true;
    }
    if (current == it.getCurrent()) {
        return true;
    }
    return false;
}

/* Default constructor */
AVL::AVL() {
    size = 0;
    root = nullptr;
    parentFromDeleteChild = nullptr;
};

/* Copy constructor */
AVL::AVL(const AVL &copy) : AVL() {
    Iterator it, end;
    it = copy.begin();
    end = copy.end();

    while(it != end) {
        add(*it);
        it++;
    }
}

/* Returns the root of the tree */
AVL::Node* AVL::getRoot() {
    return root;
}

/* Adds a node to the tree */
bool AVL::add(string s) {
    AVL::Node* v = insert(root, nullptr, s);
    if (v == nullptr) {
        return false;
    }
    rebalance(v);
    size++;
    return true;
}

/* Insert a node to the tree recursively */
AVL::Node* AVL::insert(AVL::Node *current, AVL::Node *previous, string s) {
    int compareResult;
    AVL::Node *newNode;

    // there is no node with element s, insert it to the tree
    if (current == nullptr) {
        if (root == nullptr) { // empty tree
            newNode = new AVL::Node(s, nullptr, nullptr, nullptr);
            root = newNode;
        }
        else {
            // allocation the new node and insert it as a leaf
            compareResult = previous->getElement().compare(s);
            newNode = new AVL::Node(s, previous, nullptr, nullptr);
            if (compareResult > 0) { // as a left child
                previous->setLeft(newNode);
            }
            else { // as a right child
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

AVL::Node* AVL::deleteNode(AVL::Node* current, string s) {
    int compareResult;
    AVL::Node* tmp = nullptr;
    if (current == nullptr) {
        return nullptr;
    }

    compareResult = current->getElement().compare(s);
    if (compareResult > 0) { // search left subtree
        current->setLeft(deleteNode(current->getLeft(), s));
    }
    else if (compareResult < 0) { // search right subtree
        current->setRight(deleteNode(current->getRight(), s));
    }
    else { // found the node
        if (current->getLeft() == nullptr || current->getRight() == nullptr) { // leaf or one-child
            if (current->getLeft() == nullptr) {
                tmp = current->getRight();
            }
            else {
                tmp = current->getLeft();
            }

            if (tmp == nullptr) { // no children, is a leaf node
                tmp = current;
                current = nullptr;
            }
            else { // copy the non empty child
                current->setElement(tmp->getElement());
                current->setRight(tmp->getRight());
                current->setLeft(tmp->getLeft());
            }
            if (tmp == root) {
                root = nullptr;
            }
            parentFromDeleteChild = tmp->getParent();
            delete tmp;
        }
        else {
            for (AVL::Node *iterNode=current->getRight(); iterNode!=nullptr; iterNode = iterNode->getLeft()){
                tmp = iterNode;
            };

            // copy the element
            current->setElement(tmp->getElement());
            current->setRight(deleteNode(current->getRight(), tmp->getElement()));
        }
    }

    if (current == nullptr) {
        return nullptr;
    }
    return current;
}

/* Remove a node from the tree */
bool AVL::rmv(string s) {
    AVL::Node *tmp = deleteNode(root, s);
    if (tmp == nullptr) {
        return true;
    }
    if (parentFromDeleteChild != nullptr) {
        rebalance(parentFromDeleteChild);
        parentFromDeleteChild = nullptr;
    }
    return false;
}

/* Search for the string s in the AVL tree recursively */
AVL::Node* AVL::search(Node *current, string s) const {
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

/* Checks if the AVL tree has a node with element s */
bool AVL::contains(string s) const {
    if (this->search(root, s) == nullptr) {
        return false;
    }
    return true;
}

/* Writes the tree to a file in a graphiz form */
void AVL::print2DotFile(char *filename) const {
    // open file
    ofstream graphFile(filename, ios::out | ios:: trunc);
    if (!graphFile.is_open()) {
        cerr << "Error while opening file for writing." << endl;
        graphFile.close();
        exit(-1);
    }
    graphFile << "digraph AVL {";

    // init iterator to parse the tree
    AVL::Iterator it, end;
    it = this->begin();
    end = this->end();
    while (it != end) {
        AVL::Node *tmp = it.getCurrent();
        if (tmp->getLeft() != nullptr) {
            graphFile << tmp->getElement() << " -> ";
            graphFile << tmp->getLeft()->getElement() << ";\n";
        }
        if (tmp->getRight() != nullptr) {
            graphFile << tmp->getElement() << " -> ";
            graphFile << tmp->getRight()->getElement() << ";\n";
        }

        if (root == tmp && tmp->getLeft() == nullptr && tmp->getRight() == nullptr) {
            graphFile << tmp->getElement() << ";\n";
        }
        it++;
    }
    graphFile << "}";
}

/* Writes the pre order iteration of the tree to a stream*/
void AVL::pre_order(std::ostream& out) {
    AVL::Iterator it, end;
    it = this->begin();
    end = this->end();

    while(it != end) {
        out << *it << " ";
        it++;
    }
}

/* Get the node which will participate in the rebalancing */
AVL::Node* AVL::rebalanceNode(AVL::Node* v) {
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
AVL::Node* AVL::singleRightRotation(AVL::Node *v, AVL::Node* w, AVL::Node* u) {
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
    v->updateHeight();
    w->updateHeight();
    u->updateHeight();
    return w;
}

/* Apply a single left rotation */
AVL::Node *AVL::singleLeftRotation(AVL::Node *v, AVL::Node *w, AVL::Node *u) {
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
    v->updateHeight();
    w->updateHeight();
    u->updateHeight();
    return w;
}

/* Apply a right rotation and then a left rotation */
AVL::Node* AVL::doubleRightLeftRotation(AVL::Node *v, AVL::Node *w, AVL::Node *u) {
    singleRightRotation(w, u, v);
    singleLeftRotation(v, u, w);
    return u;
}

/* Apply a right rotation and then a left rotation */
AVL::Node* AVL::doubleLeftRightRotation(AVL::Node *v, AVL::Node *w, AVL::Node *u) {
    singleLeftRotation(w, u, v);
    singleRightRotation(v, u, w);
    return u;
}

void AVL::rebalance(AVL::Node *v) {
    AVL::Node *u=nullptr;
    AVL::Node *w=nullptr;

    while(v != nullptr) { // iterate throught the whole tree
        v->updateHeight();
        if (!v->isBalanced()) {
            w = rebalanceNode(v);
            u = rebalanceNode(w);

            if (w->isLeft() && u->isLeft()) { // single right rotation
                v = singleRightRotation(v, w, u);
            }
            else if (w->isRight() && u->isRight()) { // left single rotation
                v = singleLeftRotation(v, w, u);
            }
            else if (u->isLeft()) { // double right left rotation
                v = doubleRightLeftRotation(v, w, u);
            }
            else { // double left right rotation
                v = doubleLeftRightRotation(v, w, u);
            }
        }
        v = v->getParent();
    }
}

/* Creates and returns an iterator of the tree */
AVL::Iterator AVL::begin() const {
    Iterator it(root);
    return it;
}

/* Returns an empty iterator */
AVL::Iterator AVL::end() const {
    Iterator it;
    return it;
}

/* Writes the pre order iteration of the tree to a stream*/
std::ostream& operator<<(std::ostream& out, const AVL& tree) {
    AVL::Iterator it, end;
    it = tree.begin();
    end = tree.end();


    while(it != end) {
        out << *it << " ";
        it++;
    }

    return out;
}

/* makes the current tree equal to the avl */
AVL& AVL::operator=(const AVL& avl) {
    AVL::Iterator it, end;
    it = this->begin();
    end = this->end();

    // remove all nodes from current tree
    freeAVL();

    // add all the nodes from avl to the current tree
    it = avl.begin();
    end = avl.end();
    while (it != end) {
        this->add(*it);
        it++;
    }
    return *this;
}

/* merges two trees and returns a new tree */
AVL AVL::operator+(const AVL& avl) {
    AVL result;
    AVL::Iterator it, end;
    it = this->begin();
    end = this->end();

    while(it != end) {
        result.add(*it);
        it++;
    }

    it = avl.begin();
    end = avl.end();

    while(it != end) {
        result.add(*it);
        it++;
    }
    return result;
}

/* Add the nodes of the avl to the current tree */
AVL& AVL::operator+=(const AVL& avl) {
    AVL::Iterator it, end;
    it = avl.begin();
    end = avl.end();

    while (it != end) {
        this->add(*it);
        it++;
    }
    return *this;
}

/* Adds a node with the element e at the current tree */
AVL& AVL::operator+=(const string& e) {
    this->add(e);
    return *this;
}

/* Removes a node with the element e at the current tree */
AVL& AVL::operator-=(const string& e) {
    //cout << e << endl;
    this->rmv(e);
    return *this;
}

/* Copies the current tree to a new and adds a node */
AVL AVL::operator+(const string& e) {
    AVL result(*this);
    result.add(e);
    return result;
}

/* Copies the current tree to a new and removes a node */
AVL AVL::operator-(const string& e) {
    AVL result(*this);
    result.rmv(e);
    return result;
}

void AVL::freeAVL() {
    AVL::Iterator it, end;
    it = this->begin();
    end = this->end();

    while (it != end) {
        delete it.getCurrent();
        it++;
    }
    size = 0;
    root = nullptr;
    parentFromDeleteChild = nullptr;
}

AVL::~AVL() {
    freeAVL();
}
