# Collection of dynamic benchmarks

A comparison between a couple of dynamic procedural languages and their different implementations. Here is what is measured:
* Lua 5.3.3
* Luajit 2.0.4
* Python 3.5.1
* Pypy 2.4.0
* HipHop VM 3.13.2
* NodeJS v6.2.1
* OpenJDK 1.8.0_92 (reference)

Goal here was to compare languages and how their interpreters or JIT compilers perform at simple programs. I took five benchmarks from [The Computer Language
Benchmarks Game](http://benchmarksgame.alioth.debian.org/) and added another. I took these ones, because not all work with Pypy or Luajit implementations of their respective languages. Problems:
* binary tree
* fannkuchredux
* fasta
* nbody
* spectralnorm

Later I added two of mine problems: bubblesort (highly iterative) and non optimized fibonacci (highly recursive). And here are the results:

![Binary trees](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/binarytrees.svg)
![Binary trees log](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/binarytrees_log.svg)

![Bubble sort](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/bubble.svg)
![Bubble sort log](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/bubble_log.svg)

![Fannkuchredux](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/fannkuchredux.svg)
![Fannkuchredux log](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/fannkuchredux_log.svg)

![Fasta](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/fasta.svg)
![Fasta log](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/fasta_log.svg)

![Fibonacci](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/fib.svg)
![Fibonacci log](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/fib_log.svg)

![N Body](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/nbody.svg)
![N Body log](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/nbody_log.svg)

![Spectralnorm](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/spectralnorm.svg)
![Spectralnorm log](https://rawgit.com/gareins/dynamic_benchmarks/master/.imgs_readme/spectralnorm_log.svg)

## Conclusion

Here are my thoughts on these results:
* Lua - a very fast interpreter, faster than some JITs here. Biggest drawback is when (re)alocating heavily - see Binary trees.
* Luajit - crazy fast, fastest dynamic jit, fastest at quick programs, when running more time, it get surpassed by Java. Problem again are binary trees.
* Python - slowest interpreter here, even though I did not test PHP5 interpreter, which would probably be even slower. Worst case scenario is recursive heavy Fibonacci program.
* Pypy - ususally scores inbetween slower (HHVM, Lua and Python) and faster (Node, Luajit, Java) programs. It still has a lot of problems when a lot of allocating is taking place.
* HHVM - tries to beat Pypys performance and sometimes it actually does, but still not quite there.
* Node - a very fast JIT, beaten only by Luajit and Java, though usually a couple times faster than Pypy.
* Java - when shorter programs are executed it is still beaten by Luajit but when execution is longer than a couple of seconds it overtakes Luajit and is fastest.
