#ifndef __AVL_HPP_
#define __AVL_HPP_

#include <iostream>
#include <fstream>
#include <stack>
#include <queue>

using namespace std;


class Node {
    Node *parent, *left, *right;
    int height;
    string element;

    public:
        Node(const string& e, Node *parent, Node *left, Node *right);

        Node*  getParent() const;
        Node*  getLeft() const;
        Node*  getRight() const;
        string getElement() const;
        int    getHeight() const;

        void setLeft(Node *);
        void setRight(Node *);
        void setParent(Node *);
        void setElement(string e);

        bool isLeft() const;
        bool isRight() const;
        int  rightChildHeight() const;
        int  leftChildHeight() const;
        int  updateHeight();
        bool isBalanced();
};

class Iterator {
    public:
        Iterator(Node *root);
        Iterator(Iterator &it);
        queue<Node *> getQueue();

        Iterator& operator++();
        Iterator operator++(int a);
        bool operator!=(Iterator it);
        bool operator==(Iterator it);
        string operator*();

    private:
        queue<Node *> preOrderedQueue;

        void preOrder(Node *root);
        bool hasNext();
        void skip();
};


#endif
