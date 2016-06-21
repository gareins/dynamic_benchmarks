function random(n)
{
    var x = 0;
    var lst = [];
    for(var i = 0; i < n; i++)
    {
        x = (140671485 * x + 12820163) % 16777216;
        lst[i] = x;
    }
    return lst;
}

function bubble(n)
{
    var lst = random(n);
    while(true)
    {
        var swapped = false;
        for(var i = 1; i < n; i++)
        {
            if(lst[i] < lst[i - 1])
            {
                var tmp = lst[i];
                lst[i] = lst[i - 1];
                lst[i - 1] = tmp;
                swapped = true;
            }
        }
        
        if(!swapped)
            break;
    }
    return lst;    
}

var n = +process.argv[2];
var lst = bubble(n);
var s = "";
for(var i = 0; i < n; i++)
    s = s + lst[i] + " ";
console.log(s);



// console.log(fib())
