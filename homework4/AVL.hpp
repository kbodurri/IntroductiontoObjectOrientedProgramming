#ifndef __AVL_HPP_
#define __AVL_HPP_

#include <iostream>
#include <fstream>
#include <stack>
#include <queue>

using namespace std;

class AVL {
    private:
        class Node {
            Node *parent, *left, *right;
            int height;
            string element;

            public:
                Node(const string&, Node*, Node*, Node*);

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

        int size;
        Node* root;
        Node* parentFromDeleteChild;

        void freeAVL();
        Node* search(Node*, string) const;
        Node* insert(Node*, Node*, string);
        Node* deleteNode(Node *, string);
        Node* rebalanceNode(Node*);
        void rebalance(Node*);
        Node* singleRightRotation(Node*, Node*, Node*);
        Node* singleLeftRotation(Node*, Node*, Node*);
        Node* doubleRightLeftRotation(Node*, Node*, Node*);
        Node* doubleLeftRightRotation(Node*, Node*, Node*);

    public:
        class Iterator {
            public:
                Iterator();
                Iterator(Node*);
                stack<Node *> getStack() const;
                Node *getCurrent() const;

                Iterator& operator++();
                Iterator operator++(int);
                bool operator!=(Iterator);
                bool operator==(Iterator);
                string operator*();

            private:
                Node *current;
                stack<Node *> nodeStack;

                bool hasNext();
                void next();
        };

        AVL();
        AVL(const AVL&);
        Node* getRoot();
        bool contains(string) const;
        bool add(string);
        bool rmv(string);
        void print2DotFile(char*) const;
        void pre_order(std::ostream&);
        friend std::ostream& operator<<(std::ostream &, const AVL&);
        AVL& operator=(const AVL&);
        AVL operator+(const AVL&);
        AVL& operator+=(const AVL&);
        AVL& operator+=(const string&);
        AVL& operator-=(const string&);
        AVL operator+(const string&);
        AVL operator-(const string& e);
        ~AVL();

        Iterator begin() const;
        Iterator end() const;
};
#endif
