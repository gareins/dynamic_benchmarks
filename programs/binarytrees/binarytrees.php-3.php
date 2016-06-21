<?php 
/* The Computer Language Benchmarks Game
   http://benchmarksgame.alioth.debian.org/

   contributed by Peter Baltruschat
   modified by Levi Cameron
*/

function bottomUpTree($item, $depth)
{
   if (!$depth) return array(null,null,$item);
   $item2 = $item + $item;
   $depth--;
   return array(
      bottomUpTree($item2-1,$depth),
      bottomUpTree($item2,$depth),
      $item);
}

function itemCheck($treeNode) { 
   return $treeNode[2]
      + ($treeNode[0][0] === null ? itemCheck($treeNode[0]) : $treeNode[0][2])
      - ($treeNode[1][0] === null ? itemCheck($treeNode[1]) : $treeNode[1][2]);
}

$minDepth = 4;

$n = ($argc == 2) ? $argv[1] : 1;
$maxDepth = max($minDepth + 2, $n);
$stretchDepth = $maxDepth + 1;

$stretchTree = bottomUpTree(0, $stretchDepth);
printf("stretch tree of depth %d\t check: %d\n",
$stretchDepth, itemCheck($stretchTree));
unset($stretchTree);

$longLivedTree = bottomUpTree(0, $maxDepth);

$iterations = 1 << ($maxDepth);
do
{
   $check = 0;
   for($i = 1; $i <= $iterations; ++$i)
   {
      $t = bottomUpTree($i, $minDepth);
      $check += itemCheck($t);
      unset($t);
      $t = bottomUpTree(-$i, $minDepth);
      $check += itemCheck($t);
      unset($t);
   }
   
   printf("%d\t trees of depth %d\t check: %d\n", $iterations<<1, $minDepth, $check);
   
   $minDepth += 2;
   $iterations >>= 2;
}
while($minDepth <= $maxDepth);

printf("long lived tree of depth %d\t check: %d\n",
$maxDepth, itemCheck($longLivedTree));
?>
