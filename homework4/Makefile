#all: test1 test2 test3 test4
all: AVL_test

test1: test1.cpp AVL.o
	g++ -Wall -g -std=c++11 $^ -o $@

test2: test2.cpp AVL.o
	g++ -Wall -g -std=c++11 $^ -o $@

test3: test3.cpp AVL.o
	g++ -Wall -g -std=c++11 $^ -o $@

test4: test4.cpp AVL.o
	g++ -Wall -g -std=c++11 $^ -o $@

AVL.o: AVL.cpp AVL.hpp
	g++ -g -Wall -std=c++11 AVL.cpp -c

AVL_test: AVL.cpp AVL.hpp
	g++ -g -Wall -std=c++11 AVL.cpp -o avl_test

#clean:
#	rm *.o *.dot *.png test1 test2 test3 test4 *.std avl_test

clean:
	rm avl_test

run:
	./test1 small.dict.asc  > test1.std
	./test2 small.dict.asc  > test2.std
	./test3 small.dict.asc small.dict.des > test3.std
	./test4 small.dict.asc > test4.std
