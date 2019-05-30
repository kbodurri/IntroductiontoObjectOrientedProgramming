/*
 * Node class, used for AVL trees.
 *
 * Contributors: Klajdi Bodurri && Eirini Tsitsopoulou.
*/


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
