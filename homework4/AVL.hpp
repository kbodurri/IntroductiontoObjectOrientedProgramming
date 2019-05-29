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
        Iterator();
        Iterator(Node *root);
        Iterator(Iterator &it);
        stack<Node *> getStack();
        Node *getCurrent();

        Iterator& operator++();
        Iterator operator++(int a);
        bool operator!=(Iterator it);
        bool operator==(Iterator it);
        string operator*();

    private:
        Node *current;
        stack<Node *> nodeStack;

        bool hasNext();
        bool isEmpty();
        void next();
};

class AVL {
    int size;
    Node* root;

    public:
        AVL();
        AVL(AVL&);
        bool contains(string);
        bool add(string);
        bool rmv(string);
        void print2DotFile(char *filename);
        Node* getRoot();
        //~AVL();

        Iterator begin() const;
        Iterator end() const;
    private:
        Node* search(Node*, string);
        Node* insert(Node*, Node*, string);
        Node* deleteNode(Node *, string);
        Node* rebalanceNode(Node*);
        void rebalance(Node*);
        Node* singleRightRotation(Node*, Node*, Node*);
        Node* singleLeftRotation(Node*, Node*, Node*);
        Node* doubleRightLeftRotation(Node*, Node*, Node*);
        Node* doubleLeftRightRotation(Node*, Node*, Node*);
};


#endif
