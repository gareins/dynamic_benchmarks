<?php
function random_list($n)
{
    $lst = array();
    $x = 0;
    for ($i = 0; $i < $n; ++$i) {
        $x = (140671485 * $x + 12820163) % 16777216;
        array_push($lst, $x);
    }
    return $lst;
}

function bubble($n)
{
    $lst = random_list($n);
    while(true)
    {
        $swapped = false;
        for($i = 1; $i < $n; ++$i)
        {            
            if($lst[$i] < $lst[$i - 1])
            {
                $tmp = $lst[$i];
                $lst[$i] = $lst[$i - 1];
                $lst[$i - 1] = $tmp;
                $swapped = true;
            }
        }
        
        if($swapped == false)
            break;
    }   
    return $lst;
}

$n = $_SERVER['argv'][1];
$lst = bubble($n);

for($i = 0; $i < $n; ++$i)
    echo $lst[$i] . " ";

?>

