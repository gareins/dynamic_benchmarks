<?php
function fib($n)
{
    if($n == 0) return 0;
    else if($n == 1) return 1;
    else return fib($n - 1) + fib($n - 2);
}

print(fib($_SERVER['argv'][1]));
print("\n");
?>
