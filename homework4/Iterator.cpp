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
    if (root != nullptr) {
        nodeStack.push(root);
        next();
    }
}

/* Copy constructor */
AVL::Iterator::Iterator(const AVL::Iterator &it) {
    current = it.getCurrent();
    nodeStack = it.getStack();
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
