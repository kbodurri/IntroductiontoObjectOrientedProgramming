#include "AVL.hpp"

/* Constructor */
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

/* Default Constructor */
Iterator::Iterator() {
    current = nullptr;
}

/* Constructor */
Iterator::Iterator(Node *root) {
    if (root != nullptr) {
        nodeStack.push(root);
        next();
    }
}

/* Copy constructor */
Iterator::Iterator(Iterator &it) {
    current = it.getCurrent();
    nodeStack = it.getStack();
}

/* Push the nodes of the tree into stack */
void Iterator::next() {
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
bool Iterator::hasNext() {
    return !nodeStack.empty();
}

/* Returns the stack of unvisited nodes*/
stack<Node *> Iterator::getStack() {
    return nodeStack;
}

Node* Iterator::getCurrent() {
    return current;
}

/* Increases the iterator by one */
Iterator& Iterator::operator++() {
    next();
    return *this;
}

/* Increases the iterator by one and return the previous one */
Iterator Iterator::operator++(int a) {
    Iterator prevIt(*this);
    next();
    return prevIt;
}

/* Returns the element of a node */
string Iterator::operator*() {
    return current->getElement();
}

/* Checks if the two iterators point at the different elements */
bool Iterator::operator!=(Iterator it) {
    if (hasNext() == it.hasNext() && this->current == nullptr && it.getCurrent() == nullptr) {
        return false;
    }

    if (current != it.getCurrent()) {
        return true;
    }
    return false;
}

/* Checks if the two iterators point at the same element */
bool Iterator::operator==(Iterator it) {
    if (hasNext() == it.hasNext() && this->current == nullptr && it.getCurrent() == nullptr) {
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
};

/* Copy constructor */
AVL::AVL(AVL &copy) : AVL() {
    Iterator it, end;
    it = copy.begin();
    end = copy.end();

    while(it != end) {
        add(*it);

        it++;
    }
}

/* Returns the root of the tree */
Node* AVL::getRoot() {
    return root;
}

/* Adds a node to the tree */
bool AVL::add(string s) {
    Node* v = insert(root, nullptr, s);
    if (v == nullptr) {
        return false;
    }
    rebalance(v);
    size++;
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

Node* AVL::deleteNode(Node* current, string s) {
    int compareResult;
    Node* tmp = nullptr;
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
            delete tmp;
        }
        else {
            for (Node *iterNode=current->getRight(); iterNode!=nullptr; iterNode = iterNode->getLeft()){
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
    rebalance(current);
    return current;
}

/* Remove a node from the tree */
bool AVL::rmv(string s) {
    Node *tmp = deleteNode(root, s);
    if (tmp == nullptr) {
        return true;
    }
    return false;
}

/* Search for the string s in the AVL tree recursively */
Node* AVL::search(Node *current, string s) const {
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
void AVL::print2DotFile(char *filename) {
    // open file
    ofstream graphFile(filename, ios::out | ios:: trunc);
    if (!graphFile.is_open()) {
        cerr << "Error while opening file for writing." << endl;
        graphFile.close();
        exit(-1);
    }
    graphFile << "digraph AVL {";

    // init iterator to parse the tree
    Iterator it, end;
    it = this->begin();
    end = this->end();
    while (it != end) {
        Node *tmp = it.getCurrent();
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
    Iterator it, end;
    it = this->begin();
    end = this->end();

    while(it != end) {
        out << *it << " ";
        it++;
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
    v->updateHeight();
    w->updateHeight();
    u->updateHeight();
    return w;
}

/* Apply a single left rotation */
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
    v->updateHeight();
    w->updateHeight();
    u->updateHeight();
    return w;
}

/* Apply a right rotation and then a left rotation */
Node* AVL::doubleRightLeftRotation(Node *v, Node *w, Node *u) {
    singleRightRotation(w, u, v);
    singleLeftRotation(v, u, w);
    return u;
}

/* Apply a right rotation and then a left rotation */
Node* AVL::doubleLeftRightRotation(Node *v, Node *w, Node *u) {
    singleLeftRotation(w, u, v);
    singleRightRotation(v, u, w);
    return u;
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
Iterator AVL::begin() const {
    Iterator it(root);
    return it;
}

/* Returns an empty iterator */
Iterator AVL::end() const {
    Iterator it;
    return it;
}

/* Writes the pre order iteration of the tree to a stream*/
std::ostream& operator<<(std::ostream& out, const AVL& tree) {
    Iterator it, end;
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
    queue<string> nodes;
    Iterator it, end;
    it = this->begin();
    end = this->end();

    // get the nodes of current tree that avl hasn't
    while (it != end) {
        if (!avl.contains(*it)) {
            nodes.push(*it);
        }
        it++;
    }

    // delete the nodes from current tree
    while (!nodes.empty()) {
        this->rmv(nodes.front());
        nodes.pop();
    }

    // get the nodes from avl which will add in the current tree
    it = avl.begin();
    end = avl.end();
    while (it != end) {
        if (!this->contains(*it)) {
            nodes.push(*it);
        }
        it++;
    }

    // add the new nodes to the current tree
    while (!nodes.empty()) {
        this->add(nodes.front());
        nodes.pop();
    }
    return *this;
}

/* merges two trees and returns a new tree */
AVL AVL::operator+(const AVL& avl) {
    AVL result;
    Iterator it, end;
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
    Iterator it, end;
    it = avl.begin();
    end = avl.end();

    while (it != end) {
        this->add(*it);
        it++;
    }
}

/* Adds a node with the element e at the current tree */
AVL& AVL::operator+=(const string& e) {
    this->add(e);
    return *this;
}

/* Removes a node with the element e at the current tree */
AVL& AVL::operator-=(const string& e) {
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

int main() {
    AVL tree;
    string a("10");
    string b("11");
    string c("12");
    string d("13");
    string e("14");
    string f("15");
    string g("16");

    tree.add(a);
    tree.add(b);
    tree.add(c);
    tree.add(d);
    tree.add(e);
    tree.add(f);
    tree.add(g);

    AVL secondTree;
    tree -= a;
    tree -= g;
    secondTree.add("18");
    cout << tree << endl;
    cout << "------" << endl;
    cout << secondTree << endl;
    tree.add("17");
    AVL thirdTree;
    tree += secondTree;
    cout << "------" << endl;
    cout << tree << endl;
};
