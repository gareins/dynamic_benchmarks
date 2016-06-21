# Collection of dynamic benchmarks

A comparison between a couple of dynamic procedural languages and their different implementations. Here is what is measured:
* Lua 5.3.3
* Luajit 2.0.4
* Python 3.5.1
* Pypy 2.4.0
* HipHop VM 3.13.2
* NodeJS v6.2.1
* OpenJDK 1.8.0_92 (reference)

Goal here was to compare languages and how their JITs perform at simple programs. I took five benchmarks from [The Computer Language
Benchmarks Game](http://benchmarksgame.alioth.debian.org/) and added another. I took these ones, because not all work with Pypy or Luajit implementations of their respective languages. Problems:
* binary tree
* fannkuchredux
* fasta
* nbody
* spectralnorm

Later I added two of mine problems: bubblesort (highly iterative) and non optimized fibonacci (highly recursive). And here are the results:

![Test img](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/binarytrees.svg)
![Test img2](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/binarytrees_log.svg)
